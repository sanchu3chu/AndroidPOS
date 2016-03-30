package com.ricoh.pos.model;

import android.database.sqlite.SQLiteDatabase;

import com.ricoh.pos.data.DailyData;
import com.ricoh.pos.data.SingleSalesRecord;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class SalesRecordManager {

	private static SalesRecordManager instance;

	private SalesRecordManager() {
	}

	public static SalesRecordManager getInstance() {
		if (instance == null) {
			instance = new SalesRecordManager();
		}
		return instance;
	}

	public void storeSingleSalesRecord(SQLiteDatabase database, SingleSalesRecord record) {
		if (record == null) {
			throw new IllegalArgumentException("The passing record is null");
		}

		WomanShopSalesIOManager.getInstance().insertSalesRecord(record);
	}

	/**
	 * 指定されたその日の売り上げリストを返す
	 *
	 * @param date 日付けデータ。時分秒のパラメータはあっても無視される。
	 * @return 検索結果配列
	 */
	public ArrayList<SingleSalesRecord> restoreSingleSalesRecordsOfTheDay(Date date) {
		return WomanShopSalesIOManager.getInstance().searchByDate(date, true);
	}

	/**
	 * 指定した日付けデータと一致するデータを返す。こちらは日付けをユニークデータとして扱って検索する。
	 *
	 * @param date 　日付けデータ
	 * @return 該当するSingleSalesRecord形式のデータインスタンス。検索でhitしなければnullが返る。
	 */
	public SingleSalesRecord getSingleSalesRecord(Date date) {
		ArrayList<SingleSalesRecord> records = WomanShopSalesIOManager.getInstance().searchByDate(date, false);
		if (records.size() != 1) {
			return null;
		}

		return records.get(0);
	}

	/**
	 * 指定された日付の総売り上げ額を取得する
	 *
	 * @param date 日付データ
	 * @return 指定された日付の総売り上げ額(単位パイサ)
	 */
	public long getOneDayTotalSales(Date date) {
		ArrayList<SingleSalesRecord> salesRecords = restoreSingleSalesRecordsOfTheDay(date);
		long totalSales = 0;
		for (SingleSalesRecord record : salesRecords) {
			totalSales += record.getTotalSales();
		}
		return totalSales;
	}

	/**
	 * 指定された日付の総利益(値引き前)を取得する
	 *
	 * @param date 日付データ
	 * @return 指定された日付の総利益(単位パイサ)
	 */
	public long getOneDayTotalRevenue(Date date) {
		ArrayList<SingleSalesRecord> salesRecords = restoreSingleSalesRecordsOfTheDay(date);
		long totalRevenue = 0;
		for (SingleSalesRecord record : salesRecords) {
			totalRevenue += record.getTotalRevenue();
		}
		return totalRevenue;
	}

	/**
	 * 指定された日付の総値引き額を取得する
	 *
	 * @param date 日付データ
	 * @return 指定された日付の総値引き額(単位パイサ)
	 */
	public long getOneDayTotalDiscount(Date date) {
		ArrayList<SingleSalesRecord> salesRecords = restoreSingleSalesRecordsOfTheDay(date);
		long totalDiscount = 0;
		;
		for (SingleSalesRecord record : salesRecords) {
			totalDiscount += record.getDiscountValue();
		}
		return totalDiscount;
	}

	/**
	 * 指定された日付の総純利益
	 *
	 * @param date 日付データ
	 * @return 指定された日付の総利益から総値引き額を除いた額(単位パイサ)
	 */
	public long getOneDayTotalNetProfit(Date date) {
		return (getOneDayTotalRevenue(date) - getOneDayTotalDiscount(date));
	}

	/**
	 * 指定された日付を含む過去1週間分のDailyData型のリストを取得する
	 *
	 * @param date 日付データ
	 * @return dairyDataList DairyData型のList
	 */
	public List<DailyData> getWeeklyData(Date date) {

		ArrayList<SingleSalesRecord> records = WomanShopSalesIOManager.getInstance().searchWeeklyDataByDate(date);
		HashMap<String, ArrayList<SingleSalesRecord>> map = new HashMap();

		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");

		//日付ごとの買い物リストを生成
		for (int i = 0; i < records.size(); i++) {
			if (map.containsKey(sdf.format(records.get(i).getSalesDate()))) {
				map.get(sdf.format(records.get(i).getSalesDate())).add(records.get(i));
			} else {
				ArrayList<SingleSalesRecord> singleSalesRecordCategorizedByDate = new ArrayList<>();
				singleSalesRecordCategorizedByDate.add(records.get(i));
				map.put(sdf.format(records.get(i).getSalesDate()), singleSalesRecordCategorizedByDate);
			}
		}

		List<DailyData> dailyDataList = new ArrayList<>();

		//日付ごとの総売り上げ、総値引き額、総利益を計算
		for (String keyDate : map.keySet()) {
			DailyData dailyData = new DailyData();
			for (int i = 0; i < map.get(keyDate).size(); i++) {
				dailyData.setDate(map.get(keyDate).get(i).getSalesDate());
				dailyData.setSales(dailyData.getSales() + map.get(keyDate).get(i).getTotalSales());
				dailyData.setDiscount(dailyData.getDiscount() + map.get(keyDate).get(i).getDiscountValue());
				dailyData.setProfit(dailyData.getProfit() + map.get(keyDate).get(i).getTotalSales() - map.get(keyDate).get(i).getTotalCost() - map.get(keyDate).get(i).getDiscountValue());
			}
			dailyDataList.add(dailyData);
		}

		Calendar cal = Calendar.getInstance();
		cal.setTime(date);

		//売り上げがない日を取得し、その日の総売り上げ、総値引き額、総利益に0を格納する
		for (int i = 0; i <= 6; i++) {
			cal.add(Calendar.DAY_OF_MONTH, -1 * i);
			if (!map.containsKey(sdf.format(cal.getTime()))) {
				DailyData noSalesDailyData = new DailyData();
				noSalesDailyData.setDate(cal.getTime());
				noSalesDailyData.setSales(0);
				noSalesDailyData.setDiscount(0);
				noSalesDailyData.setProfit(0);
				dailyDataList.add(noSalesDailyData);
			}
			cal.add(Calendar.DAY_OF_MONTH, i);
		}

		return dailyDataList;

	}
}
