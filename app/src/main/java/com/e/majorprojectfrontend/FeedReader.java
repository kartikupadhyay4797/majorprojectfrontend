package com.e.majorprojectfrontend;

import android.provider.BaseColumns;

public class FeedReader {
    private FeedReader() {}

    /* Inner class that defines the table contents */
    public static class FeedEntry implements BaseColumns {
        public static final String TABLE_NAME = "Cart";
        public static final String ITEM_NAME = "itemname";
        public static final String ITEM_CAPACITY = "itemcapacity";
        public static final String ITEM_QLTY = "itemquality";
        public static final String ITEM_ID = "itemid";
        public static final String ITEM_DEALER_ID = "itemdealer";
        public static final String ITEM_QTY = "itemqty";
        public static final String ITEM_TYPE = "itemtype";
        public static final String ITEM_PRICE = "itemprice";

    }
}
