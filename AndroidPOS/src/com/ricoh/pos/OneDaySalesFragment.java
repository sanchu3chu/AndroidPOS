package com.ricoh.pos;

import java.text.NumberFormat;
import java.util.Date;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ricoh.pos.data.WomanShopFormatter;
import com.ricoh.pos.model.SalesCalenderManager;
import com.ricoh.pos.model.SalesRecordManager;

public class OneDaySalesFragment extends Fragment {

	@Override  
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.fragment_oneday_sales, container, false);
		
		Date date = SalesCalenderManager.getInstance().getSelectedSalesDate();

		TextView oneDaySalesView = (TextView) v.findViewById(R.id.oneDaySales);
		long oneDaySalesPaisa = SalesRecordManager.getInstance().getOneDayTotalSales(date);

		TextView oneDayDiscountView = (TextView) v.findViewById(R.id.oneDayDiscount);
		long oneDayDiscountPaisa = SalesRecordManager.getInstance().getOneDayTotalDiscount(date);

		TextView oneDayNetProfitView = (TextView) v.findViewById(R.id.oneDayNetProfit);
		long oneDayNetProfitPaisa = SalesRecordManager.getInstance().getOneDayTotalNetProfit(date);
		
		if(oneDayNetProfitPaisa < 0){
			oneDayNetProfitView.setTextColor(getResources().getColor(R.color.warn));
		}

		NumberFormat format = NumberFormat.getInstance();
		format.setMaximumFractionDigits(2);
		
		oneDaySalesView.setText(format.format(WomanShopFormatter.convertPaisaToRupee(oneDaySalesPaisa)));
		oneDayDiscountView.setText(format.format(WomanShopFormatter.convertPaisaToRupee(oneDayDiscountPaisa)));
		oneDayNetProfitView.setText(format.format(WomanShopFormatter.convertPaisaToRupee(oneDayNetProfitPaisa)));
		
		return v;
	}

	
}
