package com.ahmed.newpro.Adapter;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.ahmed.newpro.Model.mOrder;
import com.ahmed.newpro.R;
import com.ahmed.newpro.order_details;
import com.ahmed.newpro.Adapter.App;
import com.ahmed.newpro.order_path_details;
import com.ahmed.newpro.orders;


import java.text.SimpleDateFormat;

import static com.ahmed.newpro.Adapter.App.CHANNEL_1_ID;
import static com.ahmed.newpro.Adapter.App.id;

public class NotificationHelper {

    public static void displayNotification(Context context, mOrder m,String title,String message) {



//        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        String fullDate = format.format(m.getDateTime());
//       String  date = fullDate.substring(0, fullDate.indexOf(" "));
//        String time = fullDate.substring(fullDate.indexOf(" " + 1));


        Intent messageActivity = new Intent(context, order_path_details.class);
        messageActivity.putExtra("order_id", m.getOrderId());
        messageActivity.putExtra("date", m.getDate());
        messageActivity.putExtra("price", Float.parseFloat(m.getTotalPrice())+5.00f);
        messageActivity.putExtra("time", m.getTime());
        messageActivity.putExtra("status", m.getStatus());
        messageActivity.putExtra("num_item", m.getNum_items());
        messageActivity.putExtra("cash_methods", m.getPaymentMethod());
        messageActivity.putExtra("Delivery_methods", m.getDeliveryMethod());

        messageActivity.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, messageActivity,
                PendingIntent.FLAG_ONE_SHOT);

        Notification notification = new NotificationCompat.Builder(context, CHANNEL_1_ID)
                .setSmallIcon(R.drawable.ic_cutlery)
                .setContentTitle(title)
                .setContentText(message)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                .setContentIntent(pendingIntent)
                .setColor(Color.parseColor("#FF5722"))
                .setAutoCancel(true)
                .setDefaults(Notification.DEFAULT_SOUND)
                .setVibrate(new long[0])
                .build();
        NotificationManagerCompat notificationManagerCompat2 = NotificationManagerCompat.from(context);
        notificationManagerCompat2.notify(id++, notification);
    }


}
