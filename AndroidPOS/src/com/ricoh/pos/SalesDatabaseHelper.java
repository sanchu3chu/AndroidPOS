package com.ricoh.pos;

 import android.content.Context;
 import android.database.sqlite.SQLiteDatabase;
 import android.database.sqlite.SQLiteOpenHelper;
 import android.util.Log;

 import com.ricoh.pos.data.WomanShopSalesDef;
 import com.ricoh.pos.data.WomanShopSalesOrderDef;


/**
 * 販売実績DBの生成用ヘルパー
 * ２つのテーブルを生成する。
 */
public class SalesDatabaseHelper extends SQLiteOpenHelper {
    public static final String SALES_DB_NAME = "sales.db";
    public static final String WS_SALES_TABLE_NAME = "tbl_ws_sales";
    public static final String WS_SALES_ORDER_TABLE_NAME = "tbl_ws_sales_order";

    public SalesDatabaseHelper(Context context) {
        super(context, SALES_DB_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("Create Table " + WS_SALES_TABLE_NAME + " ("
                        + "_id Integer Primary Key, "
                        + WomanShopSalesDef.DATE.name() + " Text, "
                        + WomanShopSalesDef.DISCOUNT.name() + " Real, "
                        + WomanShopSalesDef.USER_AGES.name() + " Text)"
        );
        Log.d("debug", "SingleSales Table onCreate");

        db.execSQL("Create Table " + WS_SALES_ORDER_TABLE_NAME + " ("
                        + "_id Integer Primary Key, "
                        + WomanShopSalesOrderDef.PRODUCT_CODE.name() + " Text, "
                        + WomanShopSalesOrderDef.PRODUCT_NAME.name() + " Text, "
                        + WomanShopSalesOrderDef.CATEGORY_NAME.name() + " Text, "
                        + WomanShopSalesOrderDef.PURCHASE_PRICE.name() + " Real, "
                        + WomanShopSalesOrderDef.UNIT_PRICE.name() + " Real, "
                        + WomanShopSalesOrderDef.QTY.name() + " Integer, "
                        + WomanShopSalesOrderDef.SINGLE_SALES_ID.name() + " Integer)"
        );
        Log.d("debug", "SingleSalesOrder table onCreate");
    }

    @Override
    public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {
    }

}