package com.bpbatam;

import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;

import com.bpbatam.enterprise.model.Diposisi_List_Folder;
import com.bpbatam.enterprise.model.Disposisi_Detail;
import com.bpbatam.enterprise.model.Persuratan_List_Folder;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;


public final class AppConstant {
	// Shared Preferences
	public static SharedPreferences pref;

	// Editor for Shared preferences
	public static SharedPreferences.Editor editor;
	public static final String LANGUAGE = "language";
	public static final String SESSION_PREFERENCE = "sesion_preference";
	public static final String EMPTY_STRING = "";
	public static final String LANGUAGE_DEFAULT = "en";
	public static String HASHID ;
	public static String REQID ;
	public static String USER ;
	public static String USER_NAME ;
	public static String PASSWORD ;
	public static String KEY_PASSWORD = "S.1bc29400";
	public static String KEY_USER = "bc12b22e93149c175cc034f69b031f35";
	public static String PILIH_PESAN = "1" ;
	public static String SEMUA_PESAN = "2" ;
	public static String TIDAK_PESAN = "3" ;
	public static String IMEI ;
	public static int EMAIL_ID ;
	public static String DISPO_ID ;
	public static String POSITION_CHILD = "";
	public static String BBS_LINK = "";
	public static Diposisi_List_Folder diposisiListFolder;
	public static ArrayList<String> AryListMenuChek;
	public static String sCategoryID = "";
	public static String sCategoryName = "";
	public static boolean EXIT;
	public static final String PARAM_FCM = "param_fcm";
	public static String ACTIVITY_FROM;


	//public static final String DOMAIN_URL = "http://182.253.221.109:44311";

	//public static final String DOMAIN_URL = "http://118.97.149.129:7001";
	public static final String DOMAIN_URL = "https://ep-api.bpbatam.go.id";
	//public static final String DOMAIN_URL_UPLOAD = "http://epdev.bpbatam.go.id";
	public static final String DOMAIN_URL_UPLOAD = "http://ep.bpbatam.go.id";
	public final static String API_VERSION = "/";

	public static int PERSURATAN_UMUM_UNREAD_COUNT;
	public static int PERSURATAN_UMUM_TOTAL_COUNT;
	public static int PERSURATAN_PRIBADI_UNREAD_COUNT;
	public static int PERSURATAN_PRIBADI_TOTAL_COUNT;
	public static int PERSURATAN_STATUS_PROSES_UNREAD_COUNT;
	public static int PERSURATAN_STATUS_PROSES_TOTAL_COUNT;
	public static int PERSURATAN_STATUS_PRIBADI_UNREAD_COUNT;
	public static int PERSURATAN_STATUS_PRIBADI_TOTAL_COUNT;
	public static int PERSURATAN_PERMOHONAN_UNREAD_COUNT;
	public static int PERSURATAN_PERMOHONAN_TOTAL_COUNT;

	public static int DISPOSISI_UMUM_UNREAD_COUNT;
	public static int DISPOSISI_UMUM_TOTAL_COUNT;
	public static int DISPOSISI_PRIBADI_UNREAD_COUNT;
	public static int DISPOSISI_PRIBADI_TOTAL_COUNT;

	public static JSONArray gPostJson;
	public static JSONObject gObjJson;
	public static SQLiteDatabase myDb;
	public static String FOLDER_DOWNLOAD = "/Batam/Enterprise/Download";
	public static String STORAGE_CARD = Environment.getExternalStorageDirectory().toString() + "/Batam/Enterprise";

	public static String[] menuList;
	public static int[] menuIcon;
	public static int[] menuIconSelected;
	public static String PDFVIEW_FROM;
	public static String PDFVIEW_SOURCE;
	public static String PDF_FILENAME;

	public static boolean B_USER_DISITRI ;
	public static boolean B_DISPOS ;
	public static String USER_DISTRI = "";
	public static String USER_CC = "";
	public static String FOLDER_DISPOS = "";
}
