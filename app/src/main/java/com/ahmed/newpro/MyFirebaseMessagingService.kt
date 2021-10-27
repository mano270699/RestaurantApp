package com.ahmed.newpro


import android.content.SharedPreferences
import android.util.Log
import com.ahmed.newpro.Adapter.NotificationHelper
import com.ahmed.newpro.Model.mOrder
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage


class MyFirebaseMessagingService : FirebaseMessagingService() {
    private var PRIVATE_MODE = 0
    private val PREF_NAME = "userInfo"
    lateinit var sharedPref: SharedPreferences
    lateinit var editor: SharedPreferences.Editor;
    var USERID_KEY = "UserID"
    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        // ...

        // TODO(developer): Handle FCM messages here.
        // Not getting messages here? See why this may be: https://goo.gl/39bRNJ
        Log.d(TAG, "From: " + remoteMessage.from)

        // Check if message contains a data payload.
        if (remoteMessage.data.size > 0) {
            Log.d(TAG, "Message data payload: " + remoteMessage.data)
            var res=remoteMessage.data;
            sharedPref = getSharedPreferences(PREF_NAME, PRIVATE_MODE)

            editor = sharedPref.edit()
            editor.apply()
            var customer_id = res.get("cutomer_id")!!.toInt()


                val order = mOrder(res.get("id")!!.toInt(),
                    res.get("date"),
                    res.get("time"),
                    res.get("price"),
                    res.get("status"),
                    res.get("num_item")!!.toInt(),
                    res.get("paymentMethod"),
                    res.get("deliveryMethod"));
            if(customer_id==sharedPref.getInt(USERID_KEY, 0)) {
                NotificationHelper.displayNotification(this,
                    order,
                    remoteMessage.notification!!.title,
                    remoteMessage.notification!!.body);
            }
        }

        // Check if message contains a notification payload.
        if (remoteMessage.notification != null) {
            Log.d(TAG, "Message Notification Body: " + remoteMessage.notification!!.body)
        }

        // Also if you intend on generating your own notifications as a result of a received FCM
        // message, here is where that should be initiated. See sendNotification method below.
    }
    companion object {

        private const val TAG = "MyFirebaseMsgService"
    }
}
