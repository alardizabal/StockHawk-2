package com.sam_chordas.android.stockhawk.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by alardizabal on 8/13/16.
 */
public class PriceInfo implements Parcelable {

  @SerializedName("Date")
  @Expose
  private Float date;
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
  public Float getDate() {
    return date;
  }

  /**
   *
   * @param date
   * The date
   */
  public void setDate(Float date) {
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

  // Parcelable
  public int describeContents() {
    return 0;
  }

  public void writeToParcel(Parcel out, int flags) {
    out.writeFloat(date);
    out.writeFloat(open);
    out.writeFloat(high);
    out.writeFloat(low);
    out.writeFloat(close);
  }

  public static final Parcelable.Creator<PriceInfo> CREATOR
          = new Parcelable.Creator<PriceInfo>() {
    public PriceInfo createFromParcel(Parcel in) {
      return new PriceInfo(in);
    }

    public PriceInfo[] newArray(int size) {
      return new PriceInfo[size];
    }
  };

  private PriceInfo(Parcel in) {
    date = in.readFloat();
    open = in.readFloat();
    high = in.readFloat();
    low = in.readFloat();
    close = in.readFloat();
  }

  // Constructor
  public PriceInfo() {

  }
}
