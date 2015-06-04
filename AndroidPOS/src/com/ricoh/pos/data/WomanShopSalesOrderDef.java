package com.ricoh.pos.data;

/**
 * UNIT_PRICEまでは、仕入れ側のDBと同じ内容。仕入れ側DBはデータの削除や
 * 仕入れ価格の変動などがありえるので、こちらは販売当時のデータのコピー
 * として、商品データの全データを保持する。
 */
public enum WomanShopSalesOrderDef {
    PRODUCT_CODE("product_id"),         // 商品コード
    CATEGORY_NAME("category_name"),     // カテゴリー
    PRODUCT_NAME("product_name"),       // 商品名
    PURCHASE_PRICE("purchase_price"),   // 仕入れ価格(CSVではunit_selling_price)
    UNIT_PRICE("unit_price"),           // 商品単価(CSVではMRP)
    QTY("sales_count"),                  // 販売できた数量
    SINGLE_SALES_ID("single_sales_id");// 親にあたる販売情報のID

    private String name;
    WomanShopSalesOrderDef(String name) { this.name = name;}
    public String getName() {return name;}
}
