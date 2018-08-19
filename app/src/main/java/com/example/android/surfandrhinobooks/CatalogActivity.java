package com.example.android.surfandrhinobooks;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.android.surfandrhinobooks.data.BookContract.BookEntry;
import com.example.android.surfandrhinobooks.data.BooksDbHelper;

public class CatalogActivity extends AppCompatActivity {

    private BooksDbHelper mDbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_catalog);

        mDbHelper = new BooksDbHelper(this);

    }

    @Override
    protected void onStart() {
        super.onStart();
        // Tests that books are being added to database
        insertBook();
        insertBook();
        insertBook();
        displayDatabaseInfo();
    }

    /**
     * Temporary helper method to display information in the onscreen TextView about the state of
     * the books database.
     */
    private void displayDatabaseInfo() {
        // To access our database, we instantiate our subclass of SQLiteOpenHelper
        // and pass the context, which is the current activity.
        BooksDbHelper mDbHelper = new BooksDbHelper(this);

        // Create and/or open a database to read from it
        SQLiteDatabase db = mDbHelper.getReadableDatabase();

        String[] project = {
                BookEntry._ID,
                BookEntry.COLUMN_BOOK_NAME,
                BookEntry.COLUMN_BOOK_PRICE,
                BookEntry.COLUMN_BOOK_QUANTITY,
                BookEntry.COLUMN_BOOK_SUPPLIER,
                BookEntry.COLUMN_BOOK_SUPPLIER_PHONE_NUMBER,
        };

        Cursor cursor = db.query(
                BookEntry.TABLE_NAME,
                project,
                null,
                null,
                null,
                null,
                null);

        TextView displayView = (TextView) findViewById(R.id.text_view_books);

        try {
            // Create a header in the Text View that looks like this:
            //
            // The books table contains <number of rows in Cursor> books.
            // _id - name of book - price of book - quantity of book -
            // supplier of book - supplier phone number
            //
            // In the while loop below, iterate through the rows of the cursor and display
            // the information from each column in this order.
            displayView.setText("The books table contains " + cursor.getCount() + " books.\n\n");
            displayView.append(BookEntry._ID + " - " +
                    BookEntry.COLUMN_BOOK_NAME+ " - " + BookEntry.COLUMN_BOOK_PRICE+ " - " +
                    BookEntry.COLUMN_BOOK_QUANTITY + " - " + BookEntry.COLUMN_BOOK_SUPPLIER + " - " +
                    BookEntry.COLUMN_BOOK_SUPPLIER_PHONE_NUMBER + "\n");

            // Figure out the index of each column
            int idColumnIndex = cursor.getColumnIndex(BookEntry._ID);
            int nameColumnIndex = cursor.getColumnIndex(BookEntry.COLUMN_BOOK_NAME);
            int priceColumnIndex = cursor.getColumnIndex(BookEntry.COLUMN_BOOK_PRICE);
            int quantityColumnIndex = cursor.getColumnIndex(BookEntry.COLUMN_BOOK_QUANTITY);
            int supplierColumnIndex = cursor.getColumnIndex(BookEntry.COLUMN_BOOK_SUPPLIER);
            int phoneColumnIndex = cursor.getColumnIndex(BookEntry.COLUMN_BOOK_SUPPLIER_PHONE_NUMBER);


            // Iterate through all the returned rows in the cursor
            while (cursor.moveToNext()) {
                // Use that index to extract the String or Int value of the word
                // at the current row the cursor is on.
                int currentID = cursor.getInt(idColumnIndex);
                String currentName = cursor.getString(nameColumnIndex);
                int currentPrice = cursor.getInt(priceColumnIndex);
                int currentQuantity = cursor.getInt(quantityColumnIndex);
                String currentSupplier = cursor.getString(supplierColumnIndex);
                String currentPhone = cursor.getString(phoneColumnIndex);
                // Display the values from each column of the current row in the cursor in the TextView
                displayView.append(("\n" + currentID + " - " + currentName
                        + " - " + currentPrice + " - " + currentQuantity + " - " +
                        currentSupplier + " - " + currentPhone));
            }
        } finally {
            // Always close the cursor when you're done reading from it. This releases all its
            // resources and makes it invalid.
            cursor.close();
        }
    }

    private void insertBook() {
        SQLiteDatabase db = mDbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(BookEntry.COLUMN_BOOK_NAME, "Gullivers Travels");
        values.put(BookEntry.COLUMN_BOOK_PRICE, 8);
        values.put(BookEntry.COLUMN_BOOK_QUANTITY, 10);
        values.put(BookEntry.COLUMN_BOOK_SUPPLIER, "Grohl Books");
        values.put(BookEntry.COLUMN_BOOK_SUPPLIER_PHONE_NUMBER, "4344344343");

        long newRowId = db.insert(BookEntry.TABLE_NAME, null, values);
        Log.v("CatalogActivity", "New row ID" + newRowId);
    }

}
