package com.ricoh.pos.model;

import java.util.ArrayList;
import java.util.Date;

import android.database.sqlite.SQLiteDatabase;

import com.ricoh.pos.data.SingleSalesRecord;

public class SalesRecordManager {
	
	private static SalesRecordManager instance;
	public static final String SALES_DATE_KEY = "SalesDate";
	
	private SalesRecordManager(){
	}
	
	public static SalesRecordManager getInstance(){
		if (instance == null) {
			instance = new SalesRecordManager();
		}
		return instance;
	}
	
	public void storeSingleSalesRecord(SQLiteDatabase database, SingleSalesRecord record){
		if (record == null) {
			throw new IllegalArgumentException("The passing record is null");
		}
		
		WomanShopSalesIOManager.getInstance().insertSalesRecord(record);
	}

    /**
     * 指定されたその日の売り上げリストを返す
     * @param date 日付けデータ。時分秒のパラメータはあっても無視される。
     * @return 検索結果配列
     */
	public ArrayList<SingleSalesRecord> restoreSingleSalesRecordsOfTheDay(Date date){
        return WomanShopSalesIOManager.getInstance().searchByDate(date, true);
	}

    /**
     * 指定した日付けデータと一致するデータを返す。こちらは日付けをユニークデータとして扱って検索する。
     * @param date　日付けデータ
     * @return 該当するSingleSalesRecord形式のデータインスタンス。検索でhitしなければnullが返る。
     */
	public SingleSalesRecord getSingleSalesRecord(Date date){
        ArrayList<SingleSalesRecord> records = WomanShopSalesIOManager.getInstance().searchByDate(date, false);
        if (records.size() != 1) {
            return null;
        }

        return records.get(0);
    }
	
	public double getOneDayTotalSales(Date date){
		ArrayList<SingleSalesRecord> salesRecords = restoreSingleSalesRecordsOfTheDay(date);
		double totalSales = 0;
		for (SingleSalesRecord record : salesRecords) {
			totalSales += record.getTotalSales();
		}
		return totalSales;
	}
	
	public double getOneDayTotalRevenue(Date date){
		ArrayList<SingleSalesRecord> salesRecords = restoreSingleSalesRecordsOfTheDay(date);
		double totalRevenue = 0;
		for (SingleSalesRecord record : salesRecords) {
			totalRevenue += record.getTotalRevenue();
		}
		return totalRevenue;
	}

}
