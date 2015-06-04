package com.ricoh.pos;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.ricoh.pos.data.SingleSalesRecord;
import com.ricoh.pos.model.SalesCalenderManager;
import com.ricoh.pos.model.SalesRecordManager;

import java.util.ArrayList;
import java.util.Date;

public class SalesRecordListFragment extends ListFragment {

	/**
	 * The serialization (saved instance state) Bundle key representing the
	 * activated item position. Only used on tablets.
	 */
	private static final String STATE_ACTIVATED_POSITION = "activated_position";

	/**
	 * The fragment's current callback object, which is notified of list item
	 * clicks.
	 */
	private Callbacks mCallbacks = sDummyCallbacks;

	/**
	 * The current activated item position. Only used on tablets.
	 */
	private int mActivatedPosition = ListView.INVALID_POSITION;

	private Date selectedDate;

	private ArrayList<SingleSalesRecord> oneDaySalesRecords;

	/**
	 * A callback interface that all activities containing this fragment must
	 * implement. This mechanism allows activities to be notified of item
	 * selections.
	 */
	public interface Callbacks {
		/**
		 * Callback for when an item has been selected.
		 */
		public void onItemSelected(String id);

		public void onItemLongSelected(Date id);
	}

	/**
	 * A dummy implementation of the {@link Callbacks} interface that does
	 * nothing. Used only when this fragment is not attached to an activity.
	 */
	private static Callbacks sDummyCallbacks = new Callbacks() {
		@Override
		public void onItemSelected(String id) {

		}

		@Override
		public void onItemLongSelected(Date id) {

		}
	};


	/**
	 * Mandatory empty constructor for the fragment manager to instantiate the
	 * fragment (e.g. upon screen orientation changes).
	 */
	public SalesRecordListFragment() {
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		this.selectedDate = SalesCalenderManager.getInstance().getSelectedDate();
		this.oneDaySalesRecords = SalesRecordManager.getInstance().restoreSingleSalesRecordsOfTheDay(selectedDate);

		Date initialDate = this.oneDaySalesRecords.get(0).getSalesDate();
		SalesCalenderManager.getInstance().setSelectedSalesDate(initialDate);

		setListAdapter(new ArrayAdapter<String>(
				getActivity(),
				android.R.layout.simple_list_item_activated_1,
				android.R.id.text1,
				getSalesRecordListTitles(this.selectedDate)));
	}

	@Override
	public void onActivityCreated(Bundle savedState) {
		super.onActivityCreated(savedState);

		getListView().setOnItemLongClickListener(new OnItemLongClickListener() {
			@Override
			public boolean onItemLongClick(AdapterView<?> adapterView, View view,
										   int position, long id) {
				Date clickedDate = oneDaySalesRecords.get(position).getSalesDate();
				SalesCalenderManager.getInstance().setSelectedSalesDate(clickedDate);
				mCallbacks.onItemLongSelected(clickedDate);
				return true;
			}
		});
	}

	private ArrayList<String> getSalesRecordListTitles(Date date) {
		//ArrayList<SingleSalesRecord> records =  SalesRecordManager.getInstance().restoreSingleSalesRecordsOfTheDay(date);
		ArrayList<String> titles = new ArrayList<String>();

		// TODO: Fix Me
		//titles.add("ALL");

		for (SingleSalesRecord record : this.oneDaySalesRecords) {
			titles.add(record.getSalesDate().toString());
		}

		return titles;
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);

		// Restore the previously serialized activated item position.
		if (savedInstanceState != null && savedInstanceState.containsKey(STATE_ACTIVATED_POSITION)) {
			setActivatedPosition(savedInstanceState.getInt(STATE_ACTIVATED_POSITION));
		}

	}

	@Override
	public void onStart() {
		super.onStart();

		// Select top of category
		getListView().setItemChecked(0, true);
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);

		// Activities containing this fragment must implement its callbacks.
		if (!(activity instanceof Callbacks)) {
			throw new IllegalStateException("Activity must implement fragment's callbacks.");
		}

		mCallbacks = (Callbacks) activity;

	}

	@Override
	public void onDetach() {
		super.onDetach();

		// Reset the active callbacks interface to the dummy implementation.
		mCallbacks = sDummyCallbacks;
	}

	@Override
	public void onListItemClick(ListView listView, View view, int position, long id) {
		super.onListItemClick(listView, view, position, id);

		// Notify the active callbacks interface (the activity, if the
		// fragment is attached to one) that an item has been selected.

		Date clickedDate = this.oneDaySalesRecords.get(position).getSalesDate();
		SalesCalenderManager.getInstance().setSelectedSalesDate(clickedDate);

		mCallbacks.onItemSelected(clickedDate.toString());
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		if (mActivatedPosition != ListView.INVALID_POSITION) {
			// Serialize and persist the activated item position.
			outState.putInt(STATE_ACTIVATED_POSITION, mActivatedPosition);
		}
	}

	/**
	 * Turns on activate-on-click mode. When this mode is on, list items will be
	 * given the 'activated' state when touched.
	 */
	public void setActivateOnItemClick(boolean activateOnItemClick) {
		// When setting CHOICE_MODE_SINGLE, ListView will automatically
		// give items the 'activated' state when touched.
		getListView().setChoiceMode(
				activateOnItemClick ? ListView.CHOICE_MODE_SINGLE : ListView.CHOICE_MODE_NONE);
	}

	private void setActivatedPosition(int position) {
		if (position == ListView.INVALID_POSITION) {
			getListView().setItemChecked(mActivatedPosition, false);
		} else {
			getListView().setItemChecked(position, true);
		}

		mActivatedPosition = position;
	}

}
