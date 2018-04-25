package com.example.cosfar.myapplication;


import java.util.ArrayList;
import java.util.Locale;
import java.io.FileNotFoundException;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import android.app.Activity;
import android.content.Context;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;


public class MainActivity extends AppCompatActivity {
    final String FILENAME = "file";
    private Element [] nets;
    //private Measures [] measures;
    private ArrayList<Measures> measures;
    private WifiManager wifiManager;
    private List<ScanResult> wifiList;
    int num = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                detectWifi();
                Snackbar.make(view, "Сканирование...", Snackbar.LENGTH_SHORT)
                        .setAction("Action", null).show();
            }
        });


    }



    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if(!item.isChecked()) item.setChecked(true);

        switch(id){
            case R.id.save :

                Toast.makeText(this, "save...", Toast.LENGTH_SHORT).show();

                writeFile(wifiList);
                num++;
                return true;

            case R.id.check :
                Toast.makeText(this, "check...", Toast.LENGTH_SHORT).show();
                Log.d("CHECK", "Click");
                readFile();
                return true;

            case R.id.del :
                Toast.makeText(this, "check...", Toast.LENGTH_SHORT).show();
                Log.d("DEL", "Click");
                deleteFile(FILENAME);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


    /**
     * считываем данные о доступных сетях, разбираем их и передаем адаптеру для наполнения списка
     */
    public void detectWifi(){

        this.wifiManager = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        //запуск сканирования
        this.wifiManager.startScan();
        //результат сканирования
        this.wifiList = this.wifiManager.getScanResults();

        Log.d("TAG", wifiList.toString());
        //массив из данных о сети
        this.nets = new Element[wifiList.size()];
        String item;
        String[] vector_item;
        String item_essid;
        String item_bssid;
        String item_level;
        String ssid;
        String address;
        String level;

        //вывод результатов сканирования
        for (int i = 0; i<wifiList.size(); i++){
            item = wifiList.get(i).toString();
            vector_item = item.split(",");
            item_essid = vector_item[0];
            item_bssid = vector_item[1];
            item_level = vector_item[3];
            ssid = item_essid.split(": ")[1];
            address = item_bssid.split(": ")[1];
            level = item_level.split(": ")[1];
            nets[i] = new Element(ssid, address, level);
        }

        AdapterElements adapterElements = new AdapterElements(this);
        ListView netList = (ListView) findViewById(R.id.listItem);
        netList.setAdapter(adapterElements);
    }


    class AdapterElements extends ArrayAdapter<Object> {
        Activity context;

        public AdapterElements(Activity context) {
            super(context, R.layout.items, nets);
            this.context = context;
        }

        /**
         * заполнение пункта списка данными
         * @param position позиция пункта в списках
         * @param convertView ?
         * @param parent ?
         * @return
         */
        public View getView(int position, View convertView, ViewGroup parent){
            LayoutInflater inflater = context.getLayoutInflater();
            // поиск макета
            View item = inflater.inflate(R.layout.items, null);
            //заполнение имя сети
            TextView tvSsid = (TextView) item.findViewById(R.id.tvSSID);
            tvSsid.setText(nets[position].getTitle());
            //заполнение тип защиты
            TextView tvAddress = (TextView)item.findViewById(R.id.tvAddress);
            tvAddress.setText(nets[position].getAddress());
            //заполнение уровень сигнала
            TextView tvLevel = (TextView)item.findViewById(R.id.tvLevel);
            String level = nets[position].getLevel();
            tvLevel.setText(level);
            return item;
        }
    }

    void writeFile(List<ScanResult> wifiList) {
        try {
            Log.d("SAVE", wifiList.toString());
            // отрываем поток для записи
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(
            openFileOutput(FILENAME, MODE_PRIVATE | MODE_APPEND )));
            String item;
            String[] vector_item;
            String bssid;
            String level;
            for (int i = 0; i<wifiList.size(); i++) {
                item = wifiList.get(i).toString();
                vector_item = item.split(",");
                bssid = vector_item[1];
                level = vector_item[3];
                bssid = bssid.split(": ")[1];
                level = level.split(": ")[1];
                String text = "%" + num + "@B" + bssid + "@L" + level;
                //пишем в файл %0@B28:ff:3e:1b:4b:70@L-32
                bw.write(text);
                bw.newLine();
                //пишем в конструктор
                //measures.add(new Measures(num, bssid, Integer.parseInt(level)));

            }
            // закрываем поток
            bw.close();
            Log.d("file", "Файл записан");


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    void readFile() {
        try {
            int posNumMeasure;
            int posBssidMeasure;
            int posLevelMeasure;
            int numMeasure;
            String bssid;
            int level;
            int[][] checking = new int[10][2];
            String item;
            String[] vector_item;
            String bssidCheck;
            String levelCheck;
            int min = 100000000;
            int max = -1;
            int numMin = -1;
            // открываем поток для чтения
            BufferedReader br = new BufferedReader(new InputStreamReader(
                    openFileInput(FILENAME)));
            String str = "";
            measures = new ArrayList<>();
            int i=0;
            // читаем содержимое %0@B28:ff:3e:1b:4b:70@L-32
            while ((str = br.readLine()) != null) {
                posNumMeasure = str.indexOf("%");
                posBssidMeasure = str.indexOf("@B");
                posLevelMeasure = str.indexOf("@L");
                numMeasure = Integer.parseInt(str.substring(posNumMeasure+1,posBssidMeasure));
                bssid = str.substring(posBssidMeasure+2,posLevelMeasure);
                level = Integer.parseInt(str.substring(posLevelMeasure+2));
                for (int j = 0; j<wifiList.size(); j++) {
                    item = wifiList.get(j).toString();
                    vector_item = item.split(",");
                    bssidCheck = vector_item[1];
                    levelCheck = vector_item[3];
                    bssidCheck = bssidCheck.split(": ")[1];
                    levelCheck = levelCheck.split(": ")[1];

                    if (bssid.equals(bssidCheck)){
                        checking[numMeasure][0]++;
                        checking[numMeasure][1] +=  Math.abs(level - Integer.parseInt(levelCheck));
                        break;
                    }
                }

                measures.add(new Measures(numMeasure,bssid,level));
                i++;
                Log.d("filer", str);

            }

            for (i=0;i<checking.length;i++) {
                Log.d("filer",  i + " K= " + checking[i][0] + " L= " + checking[i][1]);

            }

            //алгоритм поиска
            for (i=0;i<checking.length;i++){
                if ((checking[i][0]>max)&&(checking[i][0] != 0)){
                    max = checking[i][0];
                }
            }
            for (i=0;i<checking.length;i++) {
                if (checking[i][0] == max) {
                    if (checking[i][1] <= min){
                        min = checking[i][1];
                        numMin = i;
                    }
                }
            }
            Toast.makeText(this, "вы в " + numMin + " K= " + checking[numMin][0] + " L= " + checking[numMin][1], Toast.LENGTH_SHORT).show();
            Log.d("Result",  numMin + " K= " + checking[numMin][0] + " L= " + checking[numMin][1]);




        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



}
