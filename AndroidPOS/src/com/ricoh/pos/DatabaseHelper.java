package com.ricoh.pos;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.ricoh.pos.data.WomanShopDataDef;

public class DatabaseHelper extends SQLiteOpenHelper {

	public DatabaseHelper(Context context) {
		super(context, "products_dummy.db", null, 1);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		/*
		db.execSQL("Create Table products_dummy (" + "_id Integer Primary Key, "
				+ WomanShopDataDef.PRODUCT_CODE.name() + " Text UNIQUE, "
				+ WomanShopDataDef.PRODUCT_CATEGORY.name() + " Text, "
				+ WomanShopDataDef.ITEM_CATEGORY.name() + " Text, "
				+ WomanShopDataDef.QTY.name() + " Integer, "
				+ WomanShopDataDef.SALE_PRICE.name() + " Real, "
				+ WomanShopDataDef.TOTAL_SALE_PRICE.name() + " Real, "
				+ WomanShopDataDef.COST_TO_ENTREPRENEUR.name() + " Real, "
				+ WomanShopDataDef.TOTAL_COST_TO_ENTREP.name() + " Real, "
				+ WomanShopDataDef.TOTAL_PROFIT_TO_ENTREP.name() + " Real)");
		*/
		db.execSQL("Create Table products_dummy (" + "_id Integer Primary Key, "
				+ WomanShopDataDef.PRODUCT_CODE.name() + " Text UNIQUE, "
				+ WomanShopDataDef.PRODUCT_CATEGORY.name() + " Text, "
				+ WomanShopDataDef.ITEM_CATEGORY.name() + " Text, "
				//+ WomanShopDataDef.QTY.name() + " Integer, "
				+ WomanShopDataDef.SALE_PRICE.name() + " Real, "
				//+ WomanShopDataDef.TOTAL_SALE_PRICE.name() + " Real, "
				+ WomanShopDataDef.COST_TO_ENTREPRENEUR.name() + " Real)");
				//+ WomanShopDataDef.TOTAL_COST_TO_ENTREP.name() + " Real, "
				//+ WomanShopDataDef.TOTAL_PROFIT_TO_ENTREP.name() + " Real)");
		Log.d("debug", "Database onCreate");
	}

	@Override
	public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {
	}

}