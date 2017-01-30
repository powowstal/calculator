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

public class MainActivity extends AppCompatActivity {

    Variables v = new Variables();
    NumberPicker numberPicker;
    TextView tv,tv1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        tv = (TextView) findViewById(R.id.text_view_hot_price);
        tv1 = (TextView) findViewById(R.id.text_view_tariff_hot);

        numberPicker = (NumberPicker) findViewById(R.id.numberPicker_water);
        numberPicker.setMaxValue(100);
        numberPicker.setMinValue(0);

        numberPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker numberPicker, int i, int i2) {


                Toast toast = Toast.makeText(getApplicationContext(),
                        tv1.getText().toString()+ " Choose "+i2, Toast.LENGTH_SHORT);
                toast.show();
                Double val = Double.parseDouble(((String) tv1.getText()).replace(",",".")) * i2;
                tv.setText(val.toString());

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
            TextView f = (TextView) findViewById(R.id.textView_info_hot_water);
            f.setText(info);
            f = (TextView) findViewById(R.id.text_view_tariff_hot);
            f.setText(tariff);

        }
    }
}


