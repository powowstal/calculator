package com.postal.test_application;

import android.os.AsyncTask;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import com.postal.test_application.static_variable_data.Variables;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;

public class MainActivity extends AppCompatActivity {

    Variables v = new Variables();
    NumberPicker np_water;
    TextView tv_hot_price, tv_tariff_hot, tv_info_hot_water, tv_info_cold_water;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        tv_info_hot_water = (TextView) findViewById(R.id.tv_info_hot_water);
        tv_info_cold_water = (TextView) findViewById(R.id.tv_info_cold_water);

        tv_tariff_hot = (TextView) findViewById(R.id.tv_tariff_hot);
        tv_hot_price = (TextView) findViewById(R.id.tv_hot_price);

        np_water = (NumberPicker) findViewById(R.id.np_water);
        np_water.setMaxValue(100);
        np_water.setMinValue(0);

        np_water.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {//NP смена количества кубов
            @Override
            public void onValueChange(NumberPicker numberPicker, int i, int i2) {
//                Toast toast = Toast.makeText(getApplicationContext(),
//                        tv_tariff_hot.getText().toString()+ " Choose "+i2, Toast.LENGTH_SHORT);
//                toast.show();
                Double val = new BigDecimal(83.1*3).setScale(2, RoundingMode.UP).doubleValue();//Double.parseDouble(((String) tv_tariff_hot.getText()).replace(",",".")) * i2;
                tv_hot_price.setText(val.toString()); Log.i("MyMsg","val = "+val);

            }
        });
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
            tv_info_hot_water.setText(info);
            tv_tariff_hot.setText(tariff);

        }
    }
}


