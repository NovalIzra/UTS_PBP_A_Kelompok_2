package com.travel.travelskuy;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.databinding.DataBindingUtil;

import android.app.DatePickerDialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;

import com.google.android.material.snackbar.Snackbar;
import com.travel.travelskuy.database.AppDatabase;
import com.travel.travelskuy.databinding.ActivityBusBinding;
import com.travel.travelskuy.databinding.ActivityFligthTicketBinding;
import com.travel.travelskuy.session.SessionManager;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class BusActivity extends AppCompatActivity {

    private DatePickerDialog datePickerDialog;
    private SimpleDateFormat dateFormatter;
    private AppDatabase database;
    String CHANNEL_ID="10001";

    ActivityBusBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this,R.layout.activity_bus);
        binding.getLifecycleOwner();
        database = AppDatabase.getInstance(this);

        dateFormatter = new SimpleDateFormat("dd-MM-yyyy", Locale.US);

        binding.btndate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDateDialog();
            }
        });

        binding.edtdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDateDialog();
            }
        });

        binding.btnorder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String from = binding.edtfrom.getText().toString().trim();
                String to = binding.edtto.getText().toString().trim();
                String waktu = binding.edtdate.getText().toString().trim();
                String passenger = binding.edtpassenger.getText().toString().trim();

                if (from.isEmpty() && to.isEmpty() && waktu.isEmpty() && passenger.isEmpty()){
                    Snackbar.make(v, "isi semua kolom ", Snackbar.LENGTH_LONG).show();
                }else {
                    database.orderTicketDao().insertorder(from,to,waktu,Integer.parseInt(passenger),"", SessionManager.getIsUsername(getBaseContext()),"bus");
                    notifikasi("tiket Bus berhasil di order", "travelskuy");
                    Snackbar.make(v, "order ticket berhasil", Snackbar.LENGTH_LONG).show();
                    finish();
                }

            }
        });

    }


    private void showDateDialog(){

        Calendar newCalendar = Calendar.getInstance();


        datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);

                binding.edtdate.setText(dateFormatter.format(newDate.getTime()));
            }

        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));


        datePickerDialog.show();
    }

    public void notifikasi(String pesan, String pengirim)
    {
        String notification_title = pengirim;
        String notification_message = pesan;

        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(this)
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setContentTitle(notification_title)
                        .setContentText(notification_message);
        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);
        PendingIntent resultPendingIntent =
                PendingIntent.getActivity(
                        this,
                        0,
                        intent,
                        PendingIntent.FLAG_UPDATE_CURRENT
                );

        mBuilder.setContentIntent(resultPendingIntent);
        int mNotificationId = (int) System.currentTimeMillis();
        NotificationManager mNotifyMgr =
                (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O)
        {
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel notificationChannel = new NotificationChannel(CHANNEL_ID, "NOTIFICATION_CHANNEL_NAME", importance);

            mBuilder.setChannelId(CHANNEL_ID);
            mNotifyMgr.createNotificationChannel(notificationChannel);
        }
        mNotifyMgr.notify(mNotificationId, mBuilder.build());
    }
}