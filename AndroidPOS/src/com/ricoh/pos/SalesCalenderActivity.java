package com.ricoh.pos;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.View;

import com.caldroid.CaldroidFragment;
import com.caldroid.CaldroidListener;
import com.ricoh.pos.data.SingleSalesRecord;
import com.ricoh.pos.model.SalesCalenderManager;
import com.ricoh.pos.model.SalesRecordManager;

public class SalesCalenderActivity extends FragmentActivity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_sales_calender);

		CaldroidFragment caldroidFragment = new CaldroidFragment();
		Bundle args = new Bundle();
		Calendar cal = Calendar.getInstance();
		args.putInt(CaldroidFragment.MONTH, cal.get(Calendar.MONTH) + 1);
		args.putInt(CaldroidFragment.YEAR, cal.get(Calendar.YEAR));
		caldroidFragment.setArguments(args);
		
		addCalenderListener(caldroidFragment);

		FragmentTransaction t = getSupportFragmentManager().beginTransaction();
		t.replace(R.id.calendar1, caldroidFragment);
		t.commit();
		
	}
	
	private void addCalenderListener(CaldroidFragment fragment){
		CaldroidListener listener = new CaldroidListener(){

			@Override
			public void onSelectDate(Date date, View view) {
				
				ArrayList<SingleSalesRecord> salesRecordsOfTheDay = 
						SalesRecordManager.getInstance().restoreSingleSalesRecordsOfTheDay(date);
				
				if (salesRecordsOfTheDay.size() == 0) {
					// No sales record.
					// TODO: show alert dialog or something
					return;
				} 
				
				SalesCalenderManager.getInstance().setSelectedDate(date);
				
				Intent intent = new Intent(SalesCalenderActivity.this, SalesRecordListActivity.class);
				startActivity(intent);
			}
			
		};
		
		fragment.setCaldroidListener(listener);
	}

}
