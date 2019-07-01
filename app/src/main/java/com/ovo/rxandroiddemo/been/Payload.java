package com.ovo.rxandroiddemo.been;

public class Payload {
    private String symbolId;
    private String period;
    private String[] time;
    private long[] priceOpen;
    private long[] priceHigh;
    private long[] priceLow;
    private long[] priceLast;
    private long[] amount;
    private long[] volume;
    private long[] count;

    public String getSymbolId() {
        return symbolId;
    }

    public void setSymbolId(String symbolId) {
        this.symbolId = symbolId;
    }

    public String getPeriod() {
        return period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public String[] getTime() {
        return time;
    }

    public void setTime(String[] time) {
        this.time = time;
    }

    public long[] getPriceOpen() {
        return priceOpen;
    }

    public void setPriceOpen(long[] priceOpen) {
        this.priceOpen = priceOpen;
    }

    public long[] getPriceHigh() {
        return priceHigh;
    }

    public void setPriceHigh(long[] priceHigh) {
        this.priceHigh = priceHigh;
    }

    public long[] getPriceLow() {
        return priceLow;
    }

    public void setPriceLow(long[] priceLow) {
        this.priceLow = priceLow;
    }

    public long[] getPriceLast() {
        return priceLast;
    }

    public void setPriceLast(long[] priceLast) {
        this.priceLast = priceLast;
    }

    public long[] getAmount() {
        return amount;
    }

    public void setAmount(long[] amount) {
        this.amount = amount;
    }

    public long[] getVolume() {
        return volume;
    }

    public void setVolume(long[] volume) {
        this.volume = volume;
    }

    public long[] getCount() {
        return count;
    }

    public void setCount(long[] count) {
        this.count = count;
    }
}
