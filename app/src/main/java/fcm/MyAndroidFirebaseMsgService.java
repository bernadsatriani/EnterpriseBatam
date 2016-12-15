package fcm;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.bpbatam.AppConstant;
import com.bpbatam.AppController;
import com.bpbatam.LoginActivity;
import com.bpbatam.enterprise.MainActivity;
import com.bpbatam.enterprise.MainMenuActivity;
import com.bpbatam.enterprise.R;
import com.bpbatam.enterprise.model.AuthUser;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class MyAndroidFirebaseMsgService extends FirebaseMessagingService {
    private static final String TAG = "MyAndroidFCMService";
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        //Log data to Log Cat
        Log.d(TAG, "From: " + remoteMessage.getFrom());
        Log.d(TAG, "Notification Message Body: " + remoteMessage.getNotification().getBody());
        //create notification
       /* Uri sound= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder mNotificationBuilder = new NotificationCompat.Builder( this).setSound(sound);*/

        createNotification(remoteMessage.getNotification().getBody());
    }

    private void createNotification( String messageBody) {
        AuthUser authUser = AppController.getInstance().getSessionManager().getUserProfile();
        Intent intent;
        if (authUser.data.size() > 0){
            for (AuthUser.Datum dat : authUser.data){
                AppConstant.USER_NAME = dat.user_name;
            }
            intent = new Intent( this , MainMenuActivity.class );
        }else{
            intent = new Intent( this , LoginActivity.class );
        }


        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent resultIntent = PendingIntent.getActivity( this , 0, intent,
                PendingIntent.FLAG_ONE_SHOT);

        Uri notificationSoundURI = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder mNotificationBuilder = new NotificationCompat.Builder( this)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("Notification")
                .setContentText(messageBody)
                .setAutoCancel( true )
                .setSound(notificationSoundURI)
                .setPriority(Notification.PRIORITY_MAX)
                .setLights(0xff00ff00, 300, 100)
                .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
                .setVibrate(new long[]{1000, 1000})
                .setTicker("New messages from EPBatam!")
                .setContentIntent(resultIntent);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(0, mNotificationBuilder.build());
    }
}