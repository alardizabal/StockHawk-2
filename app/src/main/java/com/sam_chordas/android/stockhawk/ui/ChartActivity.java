package com.sam_chordas.android.stockhawk.ui;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.WindowManager;

import com.github.mikephil.charting.charts.CandleStickChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.CandleData;
import com.github.mikephil.charting.data.CandleDataSet;
import com.github.mikephil.charting.data.CandleEntry;
import com.sam_chordas.android.stockhawk.R;

import java.util.ArrayList;

public class ChartActivity extends Activity {

  private final String LOG_TAG = getClass().getSimpleName();

  private CandleStickChart chart;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN);
    setContentView(R.layout.activity_chart);

    chart = (CandleStickChart) findViewById(R.id.chart1);
    chart.setBackgroundColor(Color.WHITE);

    chart.setDescription("Ticker");

    // if more than 60 entries are displayed in the chart, no values will be
    // drawn
    chart.setMaxVisibleValueCount(60);

    // scaling can now only be done on x- and y-axis separately
    chart.setPinchZoom(false);

    chart.setDrawGridBackground(false);

    XAxis xAxis = chart.getXAxis();
    xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
    xAxis.setDrawGridLines(false);

    YAxis leftAxis = chart.getAxisLeft();
//        leftAxis.setEnabled(false);
    leftAxis.setLabelCount(7, false);
    leftAxis.setDrawGridLines(false);
    leftAxis.setDrawAxisLine(false);

    YAxis rightAxis = chart.getAxisRight();
    rightAxis.setEnabled(false);

    chart.resetTracking();

    ArrayList<CandleEntry> yVals1 = new ArrayList<CandleEntry>();

    for (int i = 0; i < 20; i++) {
//      float mult = (mSeekBarY.getProgress() + 1);
//      float val = (float) (Math.random() * 40) + mult;

      float high = (float) (Math.random() * 9) + 8f;
      float low = (float) (Math.random() * 9) + 8f;

      float open = (float) (Math.random() * 6) + 1f;
      float close = (float) (Math.random() * 6) + 1f;

      boolean even = i % 2 == 0;

      yVals1.add(new CandleEntry(i, 100, 40, 60, 80));
//      yVals1.add(new CandleEntry(i, val + high, val - low, even ? val + open : val - open,
//              even ? val - close : val + close));
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
}
