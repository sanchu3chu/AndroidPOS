package com.ricoh.pos;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.db.chart.listener.OnEntryClickListener;
import com.db.chart.model.LineSet;
import com.db.chart.view.LineChartView;
import com.ricoh.pos.data.DailyData;
import com.ricoh.pos.data.SingleSalesRecord;
import com.ricoh.pos.data.WomanShopFormatter;
import com.ricoh.pos.model.SalesCalenderManager;
import com.ricoh.pos.model.SalesRecordManager;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class WeeklyTrendsInSalesActivity extends Activity {

	private Date targetDate;
	private Calendar cal = Calendar.getInstance();
	private SalesRecordManager salesRecordManager;
	private LineChartView chart;
	final static int afterSevenDays = 7;
	final static int beforeSevenDays = -7;
	final static int beforeSixDays = -6;
	private boolean mIsClickEvent;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_weekly_trends_in_sales);

		chart = (LineChartView) findViewById(R.id.linechart);

		this.targetDate = new Date();
		cal.setTime(this.targetDate);

		TextView nextWeek = (TextView) findViewById(R.id.next_week);
		nextWeek.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {

				Calendar lastDateOfPreviousWeek = Calendar.getInstance();
				lastDateOfPreviousWeek.setTime(targetDate);
				lastDateOfPreviousWeek.add(Calendar.DAY_OF_MONTH, afterSevenDays);

				targetDate = lastDateOfPreviousWeek.getTime();
				cal.setTime(targetDate);

				setWeeklyData(getWeeklyData());
				setDateDataTexts();
				chart.dismiss();
				createGraph(getWeeklyData());
				chart.show();
			}
		});

		TextView previousWeek = (TextView) findViewById(R.id.previous_week);
		previousWeek.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {

				Calendar lastDateOfNextWeek = Calendar.getInstance();
				lastDateOfNextWeek.setTime(targetDate);
				lastDateOfNextWeek.add(Calendar.DAY_OF_MONTH, beforeSevenDays);

				targetDate = lastDateOfNextWeek.getTime();
				cal.setTime(targetDate);

				setWeeklyData(getWeeklyData());
				setDateDataTexts();
				chart.dismiss();
				createGraph(getWeeklyData());
				chart.show();
			}
		});
	}

	/**
	 * 見ている週の日付に紐づいたTextViewに値をセットする
	 */
	private void setDateDataTexts() {

		Calendar startDay = Calendar.getInstance();
		startDay.setTime(this.targetDate);
		startDay.add(Calendar.DAY_OF_MONTH, beforeSixDays);
		SimpleDateFormat sdf = new SimpleDateFormat(getString(R.string.d), Locale.US);
		TextView startDayText = (TextView) findViewById(R.id.startDay);
		startDayText.setText(sdf.format(startDay.getTime()));

		sdf = new SimpleDateFormat(getString(R.string.MMM_yyyy), Locale.US);
		TextView startMonthAndYearText = (TextView) findViewById(R.id.startMonthAndYear);
		startMonthAndYearText.setText(sdf.format(startDay.getTime()));

		sdf = new SimpleDateFormat(getString(R.string.d), Locale.US);
		TextView endDayText = (TextView) findViewById(R.id.endDay);
		endDayText.setText(sdf.format(this.targetDate));

		sdf = new SimpleDateFormat(getString(R.string.MMM_yyyy), Locale.US);
		TextView endMonthAndYearText = (TextView) findViewById(R.id.endMonthAndYear);
		endMonthAndYearText.setText(sdf.format(this.targetDate));
	}

	/**
	 * 週の売り上げ、値引き額、利益をTextViewにセットする。
	 *
	 * @param dailyDataList 一日の総売り上げ、総値引き額、総利益を持つクラスの一週間分
	 */
	private void setWeeklyData(List<DailyData> dailyDataList) {

		NumberFormat format = NumberFormat.getInstance();
		format.setMaximumFractionDigits(2);

		long totalSales = 0;
		long totalDiscount = 0;
		long totalProfit = 0;

		for (int i = 0; i < dailyDataList.size(); i++) {
			totalSales += dailyDataList.get(i).getSales();
			totalDiscount += dailyDataList.get(i).getDiscount();
			totalProfit += dailyDataList.get(i).getProfit();
		}

		TextView weeklySales = (TextView) findViewById(R.id.weekly_sales);
		weeklySales.setText(format.format(WomanShopFormatter.convertPaisaToRupee(totalSales)));

		TextView weeklyDiscount = (TextView) findViewById(R.id.weekly_discount);
		weeklyDiscount.setText(format.format(WomanShopFormatter.convertPaisaToRupee(totalDiscount)));

		TextView weeklyProfit = (TextView) findViewById(R.id.weekly_profit);
		weeklyProfit.setText(format.format(WomanShopFormatter.convertPaisaToRupee(totalProfit)));
	}

	/**
	 * 一日の総売り上げ、総値引き額、総利益を取得し、一週間分のリスト化する。
	 *
	 * @return dailyDataList
	 */
	private List<DailyData> getWeeklyData() {

		List<DailyData> dailyDataList = new ArrayList<DailyData>();
		salesRecordManager = salesRecordManager.getInstance();

		for (int i = 0; i <= 6; i++) {

			cal.add(Calendar.DAY_OF_MONTH, -1 * i);

			DailyData dailyData = new DailyData();
			dailyData.setDate(cal.getTime());
			dailyData.setSales(salesRecordManager.getOneDayTotalSales(cal.getTime()));
			dailyData.setDiscount(salesRecordManager.getOneDayTotalDiscount(cal.getTime()));
			dailyData.setProfit(salesRecordManager.getOneDayTotalNetProfit(cal.getTime()));
			dailyDataList.add(dailyData);

			cal.add(Calendar.DAY_OF_MONTH, i);
		}

		return dailyDataList;
	}

	/**
	 * 一週間分のデータをグラフ化する。
	 *
	 * @param dailyDataList 一週間分のデータ
	 */
	private void createGraph(final List<DailyData> dailyDataList) {

		LineSet dataSet = new LineSet();
		long maxValLongVal = 0;
		SimpleDateFormat sdf = new SimpleDateFormat(getString(R.string.d), Locale.US);

		Collections.sort(dailyDataList);

		for (int i = 0; i < dailyDataList.size(); i++) {
			dataSet.addPoint(sdf.format(dailyDataList.get(i).getDate()), convertPaisaToFloatRupee(dailyDataList.get(i).getSales()));
			maxValLongVal = Math.max(maxValLongVal, dailyDataList.get(i).getSales());
		}

		dataSet.setColor(Color.parseColor(getString(R.string.graph_color)));
		dataSet.setThickness(getResources().getDimensionPixelSize(R.dimen.graph_line_thickness));
		dataSet.setDashed(new float[]{getResources().getDimensionPixelSize(R.dimen.graph_line_interval), getResources().getDimensionPixelSize(R.dimen.graph_line_interval)});
		dataSet.setDotsRadius(getResources().getDimensionPixelSize(R.dimen.graph_radius_size));
		dataSet.setDotsColor(Color.parseColor(getString(R.string.graph_color)));

		chart.addData(dataSet);

		chart.setYAxis(false);
		chart.setAxisColor(Color.LTGRAY);

		chart.setFontSize(getResources().getDimensionPixelSize(R.dimen.graph_label));
		chart.setAxisLabelsSpacing(getResources().getDimensionPixelSize(R.dimen.graph_axis_label_space));
		chart.setLabelsColor(Color.GRAY);

		int maxVal = (int) Math.ceil(WomanShopFormatter.convertPaisaToRupee(maxValLongVal));
		int numberOfDigits = (String.valueOf(maxVal)).length();
		int step = (int) Math.pow(10, numberOfDigits - 1);
		BigDecimal result = new BigDecimal(maxVal);
		int graphMaxVal = (result.scaleByPowerOfTen(-numberOfDigits + 1).intValue() + 1) * step;

		if (graphMaxVal <= 10) {
			chart.setAxisBorderValues(0, 10);
			chart.setStep(5);
		} else {
			chart.setAxisBorderValues(0, graphMaxVal);
			chart.setStep(graphMaxVal / 2);
		}

		final Toast toast = Toast.makeText(WeeklyTrendsInSalesActivity.this, getString(R.string.no_data), Toast.LENGTH_SHORT);

		chart.setOnEntryClickListener(new OnEntryClickListener() {
			@Override
			public void onClick(int setIndex, int entryIndex, Rect entryRect) {

				ArrayList<SingleSalesRecord> salesRecordsOfTheDay =
						SalesRecordManager.getInstance().restoreSingleSalesRecordsOfTheDay(dailyDataList.get(entryIndex).getDate());

				if (salesRecordsOfTheDay.size() == 0) {
					toast.show();
					return;
				}

				if (mIsClickEvent) return;
				mIsClickEvent = true;

				SalesCalenderManager.getInstance().setSelectedDate(dailyDataList.get(entryIndex).getDate());

				Intent intent = new Intent(WeeklyTrendsInSalesActivity.this, SalesRecordListActivity.class);
				startActivity(intent);
			}
		});
	}

	/**
	 * DBやクラスではパイサ単位で格納されている金額をルピー表記に変換する。
	 * グラフのノードを表示するときに使用する。
	 * (グラフのノード表示に、float型が求められているため。)
	 * このクラスでしか、今のところ使用していない。2016/3/24現在。
	 *
	 * @param paisa 金額。単位パイサ（＝整数）
	 * @return float 引数paisaのルピー表記。(rrrr.ppのfloat表記)
	 */
	private static float convertPaisaToFloatRupee(long paisa) {
		BigDecimal result = new BigDecimal(paisa);
		return result.scaleByPowerOfTen(-2).floatValue();
	}

	@Override
	protected void onResume() {
		super.onResume();

		mIsClickEvent = false;

		setWeeklyData(getWeeklyData());
		setDateDataTexts();
		chart.dismiss();
		createGraph(getWeeklyData());
		chart.show();
	}
}