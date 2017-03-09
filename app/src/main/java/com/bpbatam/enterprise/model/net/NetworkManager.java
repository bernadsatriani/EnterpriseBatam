package com.bpbatam.enterprise.model.net;


import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.bpbatam.AppConstant;
import com.bpbatam.enterprise.R;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.HttpVersion;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;
import javax.security.cert.Certificate;

import okhttp3.Credentials;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NetworkManager {

    public static NetworkManager instance;
    private static Context context;
    public static NetworkManager getInstance(Class<NetworkService> networkServiceClass)
    {
        if(instance == null) {
            instance = new NetworkManager();
            context = context.getApplicationContext();
        }

        return instance;
    }

    static OkHttpClient okHttpClient;
    private static Integer defTimeout = 65;


    public static boolean isConnectingToInternet(Context ctx) {
        ConnectivityManager connectivity = (ConnectivityManager) ctx.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity != null) {
            NetworkInfo[] info = connectivity.getAllNetworkInfo();
            if (info != null)
                for (int i = 0; i < info.length; i++)
                    if (info[i].getState() == NetworkInfo.State.CONNECTED) {
                        return true;
                    }
        }
        return false;
    }

    public static String httpsConnection(Context ctx, String url, List<NameValuePair> data, Integer timeout) {
        String result = "";
        try {
            if (isConnectingToInternet(ctx)) {
                try {
                    HttpClient httpClient = getNewHttpClient(timeout);
                    HttpPost httpPost = new HttpPost(url);
                    httpPost.setEntity(new UrlEncodedFormEntity(data));
                    HttpResponse httpResponse = httpClient.execute(httpPost);
                    HttpEntity httpEntity = httpResponse.getEntity();
                    if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                        result = EntityUtils.toString(httpEntity);
                    } else if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_ACCEPTED) {
                        result = createClientResponse(0000, "Request has accepted.");
                    } else if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_NOT_FOUND) {
                        result = createClientResponse(9998, "Unable connect to server, please try again later.");
                    } else if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_INTERNAL_SERVER_ERROR) {
                        result = createClientResponse(9997, "Internal server error, We're really sorry about this, and will work hard to get this resolved as soon as possible..");
                    } else {
                        result = createClientResponse(9996, "Internal server error, We're really sorry about this, and will work hard to get this resolved as soon as possible.");
                    }
                } catch (SocketTimeoutException e) {
                    result = createClientResponse(3001, "Connection timeout, please check your internet connection");
                } catch (UnknownHostException e) {
                    result = createClientResponse(3002, "Connection timeout, please check your internet connection");
                } catch (Exception ex) {
                    result = createClientResponse(3003, "Unable connect to server, please try again");
//                    ex.printStackTrace();
                }
            } else {
                result = createClientResponse(3000, "Internet not available, please check your internet connection");
            }
        } catch (Exception ex) {
//            ex.printStackTrace();
            result = createClientResponse(3004, ex.getMessage());
        }
        return result;
    }

    private static HttpClient getNewHttpClient(Integer timout) {
        try {
            KeyStore trustStore = KeyStore.getInstance(KeyStore
                    .getDefaultType());
            trustStore.load(null, null);

            org.apache.http.conn.ssl.SSLSocketFactory sf = new MySSLSocketFactory(trustStore);
            sf.setHostnameVerifier(org.apache.http.conn.ssl.SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);

            HttpParams params = new BasicHttpParams();
            HttpProtocolParams.setVersion(params, HttpVersion.HTTP_1_1);
            HttpProtocolParams.setContentCharset(params, HTTP.UTF_8);
            HttpConnectionParams.setSoTimeout(params, timout * 1000);
            SchemeRegistry registry = new SchemeRegistry();
            registry.register(new Scheme("http", PlainSocketFactory
                    .getSocketFactory(), 80));
            registry.register(new Scheme("https", sf, 443));

            ClientConnectionManager ccm = new ThreadSafeClientConnManager(
                    params, registry);

            return new DefaultHttpClient(ccm, params);
        } catch (Exception e) {
            return new DefaultHttpClient();
        }
    }

    public static String createClientResponse(int code, String msg) {
        return "{\"res_response_code\":\"" + code + "\",\"res_response_msg\":\"" + msg
                + "\"}";
    }

    private static class MySSLSocketFactory extends org.apache.http.conn.ssl.SSLSocketFactory {
        SSLContext sslContext = SSLContext.getInstance("TLS");

        public MySSLSocketFactory(KeyStore truststore)
                throws NoSuchAlgorithmException, KeyManagementException,
                KeyStoreException, UnrecoverableKeyException {
            super(truststore);

            TrustManager tm = new X509TrustManager() {
                public void checkClientTrusted(X509Certificate[] chain,
                                               String authType) throws CertificateException {
                }

                public void checkServerTrusted(X509Certificate[] chain,
                                               String authType) throws CertificateException {
                    try {
                        chain[0].checkValidity();
                    } catch (Exception e) {
                        throw new CertificateException("Certificate not valid or trusted.");
                    }
                }

                public X509Certificate[] getAcceptedIssuers() {
                    return null;
                }
            };

            sslContext.init(null, new TrustManager[]{tm}, null);
        }

        @Override
        public Socket createSocket(Socket socket, String host, int port,
                                   boolean autoClose) throws IOException, UnknownHostException {
            return sslContext.getSocketFactory().createSocket(socket, host,
                    port, autoClose);
        }

        @Override
        public Socket createSocket() throws IOException {
            return sslContext.getSocketFactory().createSocket();
        }
    }

    public static NetworkService getNetworkService(Context context) throws CertificateException, NoSuchAlgorithmException, KeyStoreException, KeyManagementException, IOException {

        /*okHttpClient = new OkHttpClient.Builder()
                .readTimeout(60, TimeUnit.SECONDS)
                .connectTimeout(60, TimeUnit.SECONDS)
                .build();
        */

        okHttpClient = new OkHttpClient.Builder()
                .sslSocketFactory(getSSLSocketFactory(context))
                .readTimeout(60, TimeUnit.SECONDS)
                .connectTimeout(60, TimeUnit.SECONDS)
                .build();

        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
                .create();

        Retrofit retrofit = new Retrofit.Builder()

                .baseUrl(AppConstant.DOMAIN_URL + AppConstant.API_VERSION)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        NetworkService networkService = retrofit.create(NetworkService.class);
        return  networkService;
    }


    public static NetworkService getNetworkServiceUpload(Context context) throws CertificateException, NoSuchAlgorithmException, KeyStoreException, KeyManagementException, IOException {
        okHttpClient = new OkHttpClient.Builder()
                .sslSocketFactory(getSSLSocketFactory(context))
                .readTimeout(60, TimeUnit.SECONDS)
                .connectTimeout(60, TimeUnit.SECONDS)
                .build();

        /*okHttpClient = new OkHttpClient().newBuilder().addInterceptor(new Interceptor() {
            @Override
            public okhttp3.Response intercept(Interceptor.Chain chain) throws IOException {
                Request originalRequest = chain.request();

                Request.Builder builder = originalRequest.newBuilder().header("Authorization",
                        Credentials.basic("", ""));

                Request newRequest = builder.build();
                return chain.proceed(newRequest);
            }
        }).build();*/

        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(AppConstant.DOMAIN_URL_UPLOAD + AppConstant.API_VERSION)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        NetworkService networkService = retrofit.create(NetworkService.class);
        return  networkService;
    }

    private static TrustManager[] getWrappedTrustManagers(TrustManager[] trustManagers) {
        final X509TrustManager originalTrustManager = (X509TrustManager) trustManagers[0];
        return new TrustManager[]{
                new X509TrustManager() {
                    public X509Certificate[] getAcceptedIssuers() {
                        return originalTrustManager.getAcceptedIssuers();
                    }
                    public void checkClientTrusted(X509Certificate[] certs, String authType) {
                        try {
                            originalTrustManager.checkClientTrusted(certs, authType);
                        } catch (CertificateException ignored) {
                        }
                    }
                    public void checkServerTrusted(X509Certificate[] certs, String authType) {
                        try {
                            originalTrustManager.checkServerTrusted(certs, authType);
                        } catch (CertificateException ignored) {
                        }
                    }
                }
        };
    }

    private static SSLSocketFactory getSSLSocketFactory(Context ctx)
            throws CertificateException, KeyStoreException, IOException,
            NoSuchAlgorithmException, KeyManagementException {
        CertificateFactory cf = CertificateFactory.getInstance("X.509");
        InputStream caInput = ctx.getResources().openRawResource(R.raw.star_bpbatam_go_id); // your certificate file
        java.security.cert.Certificate ca = cf.generateCertificate(caInput);
        caInput.close();
        KeyStore keyStore = KeyStore.getInstance("BKS");
        keyStore.load(null, null);
        keyStore.setCertificateEntry("ca", ca);
        String tmfAlgorithm = TrustManagerFactory.getDefaultAlgorithm();
        TrustManagerFactory tmf = TrustManagerFactory.getInstance(tmfAlgorithm);
        tmf.init(keyStore);
        TrustManager[] wrappedTrustManagers = getWrappedTrustManagers(tmf.getTrustManagers());
        SSLContext sslContext = SSLContext.getInstance("TLS");
        sslContext.init(null, wrappedTrustManagers, null);
        return sslContext.getSocketFactory();
    }

    public static NetworkService getNetworkService() {
        okHttpClient = new OkHttpClient.Builder()
                .readTimeout(60, TimeUnit.SECONDS)
                .connectTimeout(60, TimeUnit.SECONDS)
                .build();

        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(AppConstant.DOMAIN_URL + AppConstant.API_VERSION)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        NetworkService networkService = retrofit.create(NetworkService.class);
        return  networkService;
    }
}
