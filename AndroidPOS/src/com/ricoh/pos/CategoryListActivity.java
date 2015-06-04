package com.ricoh.pos;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;

/**
 * An activity representing a list of Products. This activity has different
 * presentations for handset and tablet-size devices. On handsets, the activity
 * presents a list of items, which when touched, lead to a
 * {@link CategoryDetailActivity} representing item details. On tablets, the
 * activity presents the list of items and item details side-by-side using two
 * vertical panes.
 * <p>
 * The activity makes heavy use of fragments. The list of items is a
 * {@link CategoryListFragment} and the item details (if present) is a
 * {@link CategoryDetailFragment}.
 * <p>
 * This activity also implements the required
 * {@link CategoryListFragment.Callbacks} interface to listen for item
 * selections.
 */
public class CategoryListActivity extends FragmentActivity implements
		CategoryListFragment.Callbacks, TotalPaymentFragment.OnOkButtonClickListener{

	/**
	 * Whether or not the activity is in two-pane mode, i.e. running on a tablet
	 * device.
	 */
	private boolean mTwoPane;
	private String latestItemId;
	private String latestSearchWord;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_category_list);
		latestItemId = getString(R.string.category_title_default);
		if (findViewById(R.id.category_detail_container) != null) {
			// The detail container view will be present only in the
			// large-screen layouts (res/values-large and
			// res/values-sw600dp). If this view is present, then the
			// activity should be in two-pane mode.
			mTwoPane = true;

			// In two-pane mode, list items should be given the
			// 'activated' state when touched.
			((CategoryListFragment) getSupportFragmentManager()
					.findFragmentById(R.id.category_list))
					.setActivateOnItemClick(true);
			
			Bundle arguments = new Bundle();
			arguments.putString(CategoryDetailFragment.ARG_ITEM_ID, getString(R.string.category_title_default));
			final CategoryDetailFragment fragment = new CategoryDetailFragment();
			fragment.setArguments(arguments);
			getSupportFragmentManager().beginTransaction()
					.replace(R.id.category_detail_container, fragment).commit();
			
			// add TotalPaymentFragment
			TotalPaymentFragment paymentFragment = new TotalPaymentFragment();
			getSupportFragmentManager().beginTransaction()
					.replace(R.id.total_payment_container, paymentFragment).commit();

			Log.d("CategoryDetailActivity", "createEditText");
			final EditText editText = (EditText)findViewById(R.id.id_search_text_box);
			editText.addTextChangedListener(new TextWatcher() {
				@Override
				public void beforeTextChanged(CharSequence s, int start, int count, int after) {
					Log.d("beforeTextChanged", "beforeTextChanged");
				}

				@Override
				public void onTextChanged(CharSequence s, int start, int before, int count) {
					latestSearchWord = s.toString();
					Bundle arguments = new Bundle();
					arguments.putString(CategoryDetailFragment.ARG_ITEM_ID, latestItemId);
					arguments.putString(CategoryDetailFragment.ARG_SEARCH_WORD, latestSearchWord);
					CategoryDetailFragment fragment = new CategoryDetailFragment();
					fragment.setArguments(arguments);
					getSupportFragmentManager().beginTransaction()
							.replace(R.id.category_detail_container, fragment).commit();
				}

				@Override
				public void afterTextChanged(Editable s) {
					Log.d("afterTextChanged", "afterTextChanged");
				}
			});
		}

		// TODO: If exposing deep links into your app, handle intents here.
	}

	/**
	 * Callback method from {@link CategoryListFragment.Callbacks} indicating
	 * that the item with the given ID was selected.
	 */
	@Override
	public void onItemSelected(String id) {
		latestItemId = id;
		if (mTwoPane) {
			// In two-pane mode, show the detail view in this activity by
			// adding or replacing the detail fragment using a
			// fragment transaction.
			Bundle arguments = new Bundle();
			arguments.putString(CategoryDetailFragment.ARG_ITEM_ID, id);
			arguments.putString(CategoryDetailFragment.ARG_SEARCH_WORD, latestSearchWord);
			CategoryDetailFragment fragment = new CategoryDetailFragment();
			fragment.setArguments(arguments);
			getSupportFragmentManager().beginTransaction()
					.replace(R.id.category_detail_container, fragment).commit();
		} else {
			// In single-pane mode, simply start the detail activity
			// for the selected item ID.
			Intent detailIntent = new Intent(this, CategoryDetailActivity.class);
			detailIntent.putExtra(CategoryDetailFragment.ARG_ITEM_ID, id);
			startActivity(detailIntent);
		}
	}

	@Override
	public void onOkClicked() {
		startActivity(new Intent(this, RegisterConfirmActivity.class));
	}
}
