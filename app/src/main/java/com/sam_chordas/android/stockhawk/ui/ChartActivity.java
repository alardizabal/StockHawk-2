package com.sam_chordas.android.stockhawk.ui;

import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.WindowManager;

import com.github.mikephil.charting.charts.CandleStickChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.CandleData;
import com.github.mikephil.charting.data.CandleDataSet;
import com.github.mikephil.charting.data.CandleEntry;
import com.sam_chordas.android.stockhawk.R;
import com.sam_chordas.android.stockhawk.models.PriceInfo;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class ChartActivity extends AppCompatActivity {

  private final String LOG_TAG = getClass().getSimpleName();
  static final String SAVED_COMPANY_NAME = "savedCompanyName";
  static final String SAVED_TICKER = "savedTicker";
  static final String SAVED_STOCK_DATA = "loadedStockData";

  private CandleStickChart chart;

  private String ticker;
  private String companyName;
  private ArrayList<PriceInfo> priceInfoArrayList;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN);
    setContentView(R.layout.activity_chart);
    getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    ticker = getIntent().getStringExtra("ticker");
    setTitle(ticker);
    if (savedInstanceState == null) {
      downloadData();
    }
  }

  @Override
  protected void onSaveInstanceState(Bundle outState) {
    super.onSaveInstanceState(outState);
    outState.putString(SAVED_COMPANY_NAME, companyName);
    outState.putString(SAVED_TICKER, ticker);
    outState.putParcelableArrayList(SAVED_STOCK_DATA, priceInfoArrayList);
  }

  @Override
  protected void onRestoreInstanceState(Bundle savedInstanceState) {
    if (savedInstanceState != null) {

      companyName = savedInstanceState.getString(SAVED_COMPANY_NAME);
      ticker = savedInstanceState.getString(SAVED_TICKER);
      priceInfoArrayList = savedInstanceState.getParcelableArrayList(SAVED_STOCK_DATA);
      onDownloadCompleted();
    }
    super.onRestoreInstanceState(savedInstanceState);
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    if (item.getItemId() == android.R.id.home) {
      finish();
      return true;
    } else {
      return false;
    }
  }

  private void downloadData() {
    OkHttpClient httpClient = new OkHttpClient();

    Request request = new Request.Builder()
            .url("http://chartapi.finance.yahoo.com/instrument/1.0/" + ticker + "/chartdata;type=quote;range=3m/json")
            .build();

    httpClient.newCall(request).enqueue(new Callback() {
      @Override
      public void onResponse(Response response) throws IOException {
        if (response.code() == 200) { //on Success
          try {
            String result = response.body().string();
            if (result.startsWith("finance_charts_json_callback( ")) {
              result = result.substring(29, result.length() - 2);
            }
            JSONObject object = new JSONObject(result);
            companyName = object.getJSONObject("meta").getString("Company-Name");

            priceInfoArrayList = new ArrayList<>();

            JSONArray series = object.getJSONArray("series");
            for (int i = 0; i < series.length(); i++) {
              JSONObject seriesItem = series.getJSONObject(i);
              SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
              String date = android.text.format.DateFormat.
                      getMediumDateFormat(getApplicationContext()).
                      format(dateFormat.parse(seriesItem.getString("Date")));

              PriceInfo priceInfo = new PriceInfo();
              priceInfo.setDate(Float.parseFloat(seriesItem.getString("Date")));
              priceInfo.setClose(Float.parseFloat(seriesItem.getString("close")));
              priceInfo.setHigh(Float.parseFloat(seriesItem.getString("high")));
              priceInfo.setLow(Float.parseFloat(seriesItem.getString("low")));
              priceInfo.setOpen(Float.parseFloat(seriesItem.getString("open")));
              priceInfoArrayList.add(priceInfo);
            }

            onDownloadCompleted();
          } catch (Exception e) {
            onDownloadFailed();
            e.printStackTrace();
          }
        } else {
          onDownloadFailed();
        }
      }

      @Override
      public void onFailure(Request request, IOException e) {
        onDownloadFailed();
      }
    });
  }
  private void onDownloadCompleted() {
    ChartActivity.this.runOnUiThread(new Runnable() {
      @Override
      public void run() {
        chart = (CandleStickChart) findViewById(R.id.chart1);
        chart.setBackgroundColor(Color.WHITE);

        chart.setDescription(companyName);

        // if more than 60 entries are displayed in the chart, no values will be
        // drawn
        chart.setMaxVisibleValueCount(100);

        // scaling can now only be done on x- and y-axis separately
        chart.setPinchZoom(false);

        chart.setDrawGridBackground(false);

        XAxis xAxis = chart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(true);

        YAxis leftAxis = chart.getAxisLeft();
        leftAxis.setLabelCount(7, false);
        leftAxis.setDrawGridLines(true);
        leftAxis.setDrawAxisLine(false);

        YAxis rightAxis = chart.getAxisRight();
        rightAxis.setEnabled(false);

        chart.resetTracking();

        ArrayList<CandleEntry> yVals1 = new ArrayList<>();
        for (int i = 0; i < priceInfoArrayList.size(); i++) {
          PriceInfo p = priceInfoArrayList.get(i);
          yVals1.add(new CandleEntry(i, p.getHigh(), p.getLow(), p.getOpen(), p.getClose()));
        }

        CandleDataSet set1 = new CandleDataSet(yVals1, "Data Set");
        set1.setAxisDependency(YAxis.AxisDependency.LEFT);
        set1.setColor(Color.rgb(80, 80, 80));
        set1.setShadowColor(Color.DKGRAY);
        set1.setShadowWidth(0.7f);
        set1.setDecreasingColor(Color.RED);
        set1.setDecreasingPaintStyle(Paint.Style.FILL);
        set1.setIncreasingColor(Color.rgb(122, 242, 84));
        set1.setIncreasingPaintStyle(Paint.Style.STROKE);
        set1.setNeutralColor(Color.BLUE);
        set1.setHighlightLineWidth(1f);

        CandleData data = new CandleData(set1);

        chart.setData(data);
        chart.invalidate();

        chart.getLegend().setEnabled(false);
      }
    });
  }
  private void onDownloadFailed() {
//    GraphActivity.this.runOnUiThread(new Runnable() {
//      @Override
//      public void run() {
//        lineChart.setVisibility(View.GONE);
//        progressCircle.setVisibility(View.GONE);
//        errorMessage.setVisibility(View.VISIBLE);
//        setTitle(R.string.error);
//      }
//    });
  }
}
