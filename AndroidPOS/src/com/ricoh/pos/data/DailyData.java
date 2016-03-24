package com.ricoh.pos.data;

import java.util.Date;

public class DailyData implements Comparable<DailyData> {

	//sales、discount、profitの単位は、Paisaである。表示するときに、
	// WomanShopFormatter.convertPaisaToRupee()を使って、
	// PaisaからRupeeに変換すること。
	private long sales;//Paisa
	private long discount;//Paisa
	private long profit;//Paisa
	private Date date;

	public long getSales() {
		return sales;
	}

	public void setSales(long sales) {
		this.sales = sales;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public long getProfit() {
		return profit;
	}

	public void setProfit(long profit) {
		this.profit = profit;
	}

	public long getDiscount() {
		return discount;
	}

	public void setDiscount(long discount) {
		this.discount = discount;
	}

	public int compareTo(DailyData target) {
		if (this.date != null && target.date == null) {
			return 1;
		}
		if (this.date == null && target.date != null) {
			return -1;
		}

		if (this.date != null && target.date != null) {
			int dateDiff = this.date.compareTo(target.date);
			if (dateDiff > 0) {
				return 1;
			}
			if (dateDiff < 0) {
				return -1;
			}
		}

		return 0;
	}
}
