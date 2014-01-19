package com.ricoh.pos;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

public class SalesRecordListActivity extends FragmentActivity implements
SalesRecordListFragment.Callbacks{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_salesrecord_list);

		if (findViewById(R.id.salesrecord_list) != null) {
			
			((SalesRecordListFragment) getSupportFragmentManager()
					.findFragmentById(R.id.salesrecord_list))
					.setActivateOnItemClick(true);
		}
		
		SalesRecordDetailFragment fragment = new SalesRecordDetailFragment();
		getSupportFragmentManager().beginTransaction()
				.replace(R.id.salesrecord_detail_container, fragment).commit();

		// TODO: If exposing deep links into your app, handle intents here.
	}

	/**
	 * Callback method from {@link CategoryListFragment.Callbacks} indicating
	 * that the item with the given ID was selected.
	 */
	@Override
	public void onItemSelected(String id) {
		/*
		Bundle arguments = new Bundle();
		arguments.putString(CategoryDetailFragment.ARG_ITEM_ID, id);
		SalesRecordDetailFragment fragment = new SalesRecordDetailFragment();
		fragment.setArguments(arguments);
		getSupportFragmentManager().beginTransaction()
				.replace(R.id.salesrecord_detail_container, fragment).commit();
				*/

	}
	
}