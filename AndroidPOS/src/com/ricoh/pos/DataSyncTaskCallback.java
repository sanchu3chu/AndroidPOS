package com.ricoh.pos;

public interface DataSyncTaskCallback {
	/**
	 * Called on succeeding to import from SD / export to SD
	 * 
	 */
	void onSuccessSyncData();

	/**
	 * Called on failing to import from SD / export to SD
	 * @param resId 
	 * error message id
	 */
	void onFailedSyncData(int resId);
}
