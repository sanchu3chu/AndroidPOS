package com.ricoh.pos.data;

public enum WomanShopSalesDef {
    DATE("sales_date"),          // 販売日
    DISCOUNT("discount"),        // この商談全体での割り引き額
    USER_AGES("user_ages");     // お客さんの年代

    private String name;
    WomanShopSalesDef(String name){ this.name = name;}
    public String getName() {return name;}
}
