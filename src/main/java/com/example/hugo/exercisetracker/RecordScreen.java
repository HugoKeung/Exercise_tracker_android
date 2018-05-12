package com.example.hugo.exercisetracker;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;


import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by hugo on 01/05/17.
 */

//TODO ugly setData, setTitle

public class RecordScreen extends AppCompatActivity {
    int position;
    ArrayList<Exercise> exList;
    TextView cover;
    WebView view;
    boolean weight = true;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        Bundle bundle = getIntent().getExtras();

        exList = MainActivity.loadData();
        if (bundle != null) {
            position = bundle.getInt("position");
            System.out.println("something n bundle");
            weight = false;
            System.out.println(weight + "zZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZ");
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.record_screen);
        cover = (TextView) findViewById(R.id.tv_cover);
        view = (WebView) findViewById(R.id.wv_graph);

        setTitle();
        setData();

//only make graph if already have data
        //TODO second bit of or (loadWeight chcek) not working
        if (exList.get(position).getRecord().size()>1 || MainActivity.loadWeight().getRecord().size() >1 ) {
            cover.setText("Loading the graph, please wait.");

            view.setWebViewClient(new WebViewClient(){
                @Override
                public void onPageFinished(WebView view, String url) {
                    cover.setVisibility(View.INVISIBLE);
                    view.setVisibility(View.VISIBLE);
                }
            });

            view.getSettings().setJavaScriptEnabled(true);
            view.getSettings().setBuiltInZoomControls(true);


            String temp;
            if (weight == false){
                temp = makeArrayString(exList.get(position).getRecord());
                System.out.println("not weight");
            }
           else temp = makeArrayString(MainActivity.loadWeight().getRecord());
            view.loadDataWithBaseURL(null, htmlCode(temp), "text/html", "utf-8", null);
        }
        else {
            notEnoughDataError();
        }

    }

    void setData(){

        HashMap<String, Double> dataList;
        if (weight == false) {
            dataList = exList.get(position).getRecord();
        }
        else dataList = MainActivity.loadWeight().getRecord();
        TextView newDate;
        TextView newValue;
        TableRow newRow;
        TableLayout table = (TableLayout) findViewById(R.id.table_data);
        TextView tvl = (TextView) findViewById(R.id.left_col);
        TextView tvr = (TextView) findViewById(R.id.right_col);
        for (String date: dataList.keySet()){
            newDate = new TextView(this);
            newDate.setText(date);

            newDate.setLayoutParams(tvl.getLayoutParams());

            newDate.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);

            newValue = new TextView(this);
            newValue.setText(dataList.get(date).toString());
            newValue.setLayoutParams(tvr.getLayoutParams());
            newValue.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);

            newRow = new TableRow(this);
            newRow.addView(newDate);
            newRow.addView(newValue);
            table.addView(newRow);

        }

    }

    void setTitle(){
        TextView tvName = (TextView) this.findViewById(R.id.tv_exerciseName);
        if (weight == false) {
            tvName.setText(exList.get(position).getExerciseName());
        }
        else tvName.setText("Weight Record");
    }

    //TODO potential problem here, used HashMap instead of linkedHashMap

    String makeArrayString(HashMap<String, Double> record){
        StringBuilder sb = new StringBuilder();

        for (String date: record.keySet()){
            sb.append("[" + stringToNewDate(date) + ", " + record.get(date) + "],");
        }

        sb.deleteCharAt(sb.length()-1);
        System.out.println(sb.toString());
        return sb.toString();
    }

    String stringToNewDate(String input){

        String day, year, month;
        String[] date = input.split("-");

        day = date[0];
        month = date[1];
        year = date[2];
//date month start with 0
        int minusOne = Integer.parseInt(month);
        minusOne -=1;

        return "new Date(" + year + ", " +  minusOne + ", " + day+ ")" ;


    }

    void notEnoughDataError(){
        cover.setText("Not enough data for graph");

    }


    String htmlCode(String arrayString){

        return "  <html>\n" +
                "  <head>\n" +
                "    <script type=\"text/javascript\" src=\"https://www.gstatic.com/charts/loader.js\"></script>\n" +
                "    <script type=\"text/javascript\">\n" +
                "      google.charts.load('current', {'packages':['corechart']});\n" +
                "      google.charts.setOnLoadCallback(drawChart);\n" +
                "\n" +
                "      function drawChart() {\n" +
                "        var data = google.visualization.arrayToDataTable([\n" +
                "          ['Date', 'Weight'],\n" +
                arrayString +
                "        ]);\n" +
                "\n" +
                "        var options = {\n" +
                "          title: 'Weight record',\n" +
                "          hAxis: {" +
                "          format: 'M/d'}," +
                "          vAxis: {" +
                "          title: 'weight'}, " +
                "          legend: { position: 'none' }\n" +
                "        };\n" +
                "\n" +
                "        var chart = new google.visualization.LineChart(document.getElementById('curve_chart'));\n" +
                "\n" +
                "        chart.draw(data, options);\n" +
                "      }\n" +
                "    </script>\n" +
                "  </head>\n" +
                "  <body>\n" +
                "    <div id=\"curve_chart\" style=\"width: 100%; height: 100%\"></div>\n" +
                "  </body>\n" +
                "</html>";
    }

}
