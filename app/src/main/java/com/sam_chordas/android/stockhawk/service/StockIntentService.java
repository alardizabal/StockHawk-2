package com.sam_chordas.android.stockhawk.service;

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.gcm.TaskParams;

/**
 * Created by sam_chordas on 10/1/15.
 */
public class StockIntentService extends IntentService {

  private String ticker;

  public StockIntentService(){
    super(StockIntentService.class.getName());
  }

  public StockIntentService(String name) {
    super(name);
  }

  @Override protected void onHandleIntent(Intent intent) {
    Log.d(StockIntentService.class.getSimpleName(), "Stock Intent Service");
    StockTaskService stockTaskService = new StockTaskService(this);
    Bundle args = new Bundle();
    this.ticker = intent.getStringExtra("symbol");
    if (intent.getStringExtra("tag").equals("add")){
      args.putString("symbol", this.ticker);
    }
    // We can call OnRunTask from the intent service to force it to run immediately instead of
    // scheduling a task.
    try {
      stockTaskService.onRunTask(new TaskParams(intent.getStringExtra("tag"), args));
    } catch(Exception e) {
      Handler handler = new Handler(getMainLooper());
      handler.post(new Runnable() {
        @Override
        public void run() {
          Toast.makeText(getApplicationContext(), "Ticker " + ticker + " not found", Toast.LENGTH_SHORT).show();
        }
      });
    }
  }
}
