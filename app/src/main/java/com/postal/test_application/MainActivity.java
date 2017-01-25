package com.postal.test_application;

import android.os.AsyncTask;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.NumberPicker;
import android.widget.TextView;

import com.postal.test_application.static_variable_data.Variables;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public class MainActivity extends AppCompatActivity {

    Variables v = new Variables();
    NumberPicker numberPicker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
//        String APP_PREFERENCES_LAST_TIME_LOAD = "LAST_TIME_LOAD";
//        SharedPreferences.Editor editor =  null;
//        editor.putLong(APP_PREFERENCES_LAST_TIME_LOAD, System.currentTimeMillis());
//        editor.apply();


//        String APP_PREFERENCES = "mysettings";
//        SharedPreferences mSettings;
//        mSettings = getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);
//        if (mSettings.contains(APP_PREFERENCES_LAST_TIME_LOAD)) {
//            // Получаем число из настроек
//            //Log.i("MyMsg", " value "+mSettings.getLong(APP_PREFERENCES_LAST_TIME_LOAD, 0));
//        }


//        String APP_PREFERENCES_COUNTER = "time";
//        String APP_PREFERENCES_TIME_DATA_SET = "APP_PREFERENCES_TIME";
//        SharedPreferences mSettings = null;
//
//        mSettings = getSharedPreferences(APP_PREFERENCES_TIME_DATA_SET, Context.MODE_PRIVATE);//context
//        SharedPreferences.Editor editor = mSettings.edit();
//        editor.putLong(APP_PREFERENCES_COUNTER, System.currentTimeMillis());
//        editor.apply();
//
////        String APP_PREFERENCES_COUNTER = "time";
////        String APP_PREFERENCES_TIME_DATA_SET = "APP_PREFERENCES_TIME";
////        SharedPreferences mSettings = null;
//        mSettings = getSharedPreferences(APP_PREFERENCES_TIME_DATA_SET, Context.MODE_PRIVATE);//context
//        Long mCounter = mSettings.getLong(APP_PREFERENCES_COUNTER, 0);
//        Log.i("MyMsg", " value "+mCounter.toString());
//        Log.i("MyMsg", " value "+Calendar.getInstance().getTimeInMillis());

//        SmartLocation.with(getApplicationContext()).location()
//                .oneFix()
//                .start(new OnLocationUpdatedListener() {
//                    @Override
//                    public void onLocationUpdated(Location location) {
//
//                        Log.i("MyMsg", "Location changed: Lat: "
//                                + location.getLatitude() + " Lng: "
//                                + location.getLongitude());
//                    }
//
//                     });


//        MyLocation.LocationResult locationResult = new MyLocation.LocationResult(){
//            @Override
//            public void gotLocation(Location location){
//                Log.i("MyMsg", "CHANG!!");
//                if (location != null)
//                    Log.i("MyMsg", "Location changed: Lat: "
//                            + location.getLatitude() + " Lng: "
//                            + location.getLongitude()
//                            +"      time "+ new SimpleDateFormat("yyyy_MM_dd_HH-mm-ss")
//                            .format(location.getTime()));
//                if (location == null)Log.i("MyMsg", "KU KU ");
//            }
//        };
//        MyLocation myLocation = new MyLocation();
//        myLocation.getLocation(this, locationResult);

//        Thread dataCollection = new Thread("AlarmReceiver") {
//            @Override
//            public void run() {
//        LocationGetter a = new LocationGetter(getApplicationContext());
//        Coordinates b = a.getLocation(1000*60*2,1000*60);
//        Log.i("MyMsg", "latitude "+b.latitude);
//        Log.i("MyMsg", "longitude "+b.longitude);
//            }
//        };
//
//        dataCollection.start();

        numberPicker = (NumberPicker) findViewById(R.id.numberPicker_water);
        numberPicker.setMaxValue(100);
        numberPicker.setMinValue(0);
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button2:startSoc();//button - твоя кнопка
                Log.i("MyMsg","кнопка нажата");
                break;
        }
    }
    private void startSoc() {
        try{
            Task Ts = new Task();
            Ts.execute();
        }catch (Exception e){Log.i("MyMsg","Error "+e);}
    }

    private class Task extends AsyncTask {
        String info,tariff;
        @Override
        protected Object doInBackground(Object[] objects) {

            Document doc;
            Elements connect;
            try{
                doc = Jsoup.connect("http://kyivenergo.ua/ru/te-home/garyacha_voda").get();
                connect = doc.select(".table td span span");
                Log.i(v.Msg, "Гвп с сушылкой: "+connect.text());
                tariff = connect.text();

                doc = Jsoup.connect("http://kyivenergo.ua/ru/te-home/garyacha_voda").get();
                connect = doc.select(".table td > strong span");
                Log.i(v.Msg, "Гвп без сушилки: "+connect.text());

                doc = Jsoup.connect("http://kyivenergo.ua/ru/te-home/garyacha_voda").get();
                connect = doc.select(".font14 > span");
                Log.i(v.Msg, "надпись: "+connect.text());
                info = connect.text();
            }catch (Exception e){ Log.i(v.Msg, "Exception: "+e);}

            return null;
        }

        @Override
        protected void onPostExecute(Object o) {
            super.onPostExecute(o);
            TextView f = (TextView) findViewById(R.id.textView_info_hot_water);
            f.setText(info);
            f = (TextView) findViewById(R.id.text_view_tariff_hot);
            f.setText(tariff);

        }
    }
}


