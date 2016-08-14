package com.sam_chordas.android.stockhawk.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by alardizabal on 8/13/16.
 */
public class PriceInfo {

  @SerializedName("Date")
  @Expose
  private String date;
  @SerializedName("open")
  @Expose
  private Float open;
  @SerializedName("high")
  @Expose
  private Float high;
  @SerializedName("low")
  @Expose
  private Float low;
  @SerializedName("close")
  @Expose
  private Float close;

  /**
   *
   * @return
   * The date
   */
  public String getDate() {
    return date;
  }

  /**
   *
   * @param date
   * The date
   */
  public void setDate(String date) {
    this.date = date;
  }

  /**
   *
   * @return
   * The open
   */
  public Float getOpen() {
    return open;
  }

  /**
   *
   * @param open
   * The open
   */
  public void setOpen(Float open) {
    this.open = open;
  }

  /**
   *
   * @return
   * The high
   */
  public Float getHigh() {
    return high;
  }

  /**
   *
   * @param high
   * The high
   */
  public void setHigh(Float high) {
    this.high = high;
  }

  /**
   *
   * @return
   * The low
   */
  public Float getLow() {
    return low;
  }

  /**
   *
   * @param low
   * The low
   */
  public void setLow(Float low) {
    this.low = low;
  }

  /**
   *
   * @return
   * The close
   */
  public Float getClose() {
    return close;
  }

  /**
   *
   * @param close
   * The close
   */
  public void setClose(Float close) {
    this.close = close;
  }
}
