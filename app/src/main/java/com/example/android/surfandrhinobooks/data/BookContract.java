package com.example.android.surfandrhinobooks.data;

import android.provider.BaseColumns;

public final class BookContract {

    // To prevent someone from accidentally instantiating the contract class,
    // give it an empty constructor.
    private BookContract() {}

    /**
     * Inner class that defines constant values for the books database table.
     * Each entry in the table represents a single book.
     */
    public static final class BookEntry implements BaseColumns {

        /** Name of database table for books */
        public final static String TABLE_NAME = "Books";

        /**
         * Unique ID number for the book (only for use in the database table).
         *
         * Type: INTEGER
         */
        public final static String _ID = BaseColumns._ID;

        /**
         * Name of the book.
         *
         * Type: TEXT
         */
        public final static String COLUMN_BOOK_NAME ="book";

        /**
         * Price of the book.
         *
         * Type: INT
         */
        public final static String COLUMN_BOOK_PRICE ="price";

        /**
         * Quantity of the book available.
         *
         * Type: INT
         */
        public final static String COLUMN_BOOK_QUANTITY = "quantity";

        /**
         * Supplier of the book.
         *
         *
         * Type: STRING
         */
        public final static String COLUMN_BOOK_SUPPLIER = "supplier";

        /**
         * Phone number of supplier of the book.
         *
         * Type: STRING - THIS IS INTENTIONAL. This would allow me to parse
         * my string into the correct format (whether with dashes or whatever else I want
         * it to look like when someone enters a number
         */
        public final static String COLUMN_BOOK_SUPPLIER_PHONE_NUMBER = "phone";
    }

}
