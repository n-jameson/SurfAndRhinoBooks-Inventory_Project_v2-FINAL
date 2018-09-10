package com.example.android.surfandrhinobooks;

import android.content.Context;
import android.database.Cursor;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import com.example.android.surfandrhinobooks.data.BookContract;

public class BookCursorAdapter extends CursorAdapter {

    private static final String TAG = "MyActivity";

    /**
     * Constructs a new {@link BookCursorAdapter}.
     *
     * @param context The context
     * @param c       The cursor from which to get the data.
     */
    public BookCursorAdapter(Context context, Cursor c, int flags) {
        super(context, c, flags);
    }

    /**
     * Makes a new blank list item view. No data is set (or bound) to the views yet.
     *
     * @param context app context
     * @param cursor  The cursor from which to get the data. The cursor is already
     *                moved to the correct position.
     * @param parent  The parent to which the new view is attached to
     * @return the newly created list item view.
     */
    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        // Inflate a list item view using the layout specified in list_item.xml
        return LayoutInflater.from(context).inflate(R.layout.list_item, parent, false);
    }

    /**
     * This method binds the book data (in the current row pointed to by cursor) to the given
     * list item layout. For example, the name for the current book can be set on the name TextView
     * in the list item layout.
     *
     * @param view    Existing view, returned earlier by newView() method
     * @param context app context
     * @param cursor  The cursor from which to get the data. The cursor is already moved to the
     *                correct row.
     */
    @Override
    public void bindView(View view, final Context context, Cursor cursor) {
        // Find individual views that we want to modify in the list item layout
        TextView nameTextView = (TextView) view.findViewById(R.id.name);
        TextView priceTextView = (TextView) view.findViewById(R.id.price);
        TextView quantityTextView = (TextView) view.findViewById(R.id.quantity);
        TextView supplierTextView = (TextView) view.findViewById(R.id.supplier);
        TextView phoneNumberTextView = (TextView) view.findViewById(R.id.phone_number);

        // Find the columns of book attributes that we're interested in
        int nameColumnIndex = cursor.getColumnIndex(BookContract.BookEntry.COLUMN_BOOK_NAME);
        int priceColumnIndex = cursor.getColumnIndex(BookContract.BookEntry.COLUMN_BOOK_PRICE);
        int quantityColumnIndex = cursor.getColumnIndex(BookContract.BookEntry.COLUMN_BOOK_QUANTITY);
        int supplierColumnIndex = cursor.getColumnIndex(BookContract.BookEntry.COLUMN_BOOK_SUPPLIER);
        int phoneNumberColumnIndex = cursor.getColumnIndex(BookContract.BookEntry.COLUMN_BOOK_SUPPLIER_PHONE_NUMBER);

        // Read the book attributes from the Cursor for the current book
        final String bookName = cursor.getString(nameColumnIndex);
        final int intBookPrice = cursor.getInt(priceColumnIndex);
        String bookPrice = Integer.toString(intBookPrice);
        int intBookQuantity = cursor.getInt(quantityColumnIndex);
        final String bookQuantity = Integer.toString(intBookQuantity);
        String bookSupplier = cursor.getString(supplierColumnIndex);
        String bookSupplierPhoneNumber = cursor.getString(phoneNumberColumnIndex);

        // If the book supplier is empty string or null, then use some default text
        // that says "Supplier Unknown", so the TextView isn't blank.
        if (TextUtils.isEmpty(bookSupplier)) {
            bookSupplier = context.getString(R.string.unknown_supplier);
        }

        if (TextUtils.isEmpty(bookSupplierPhoneNumber)) {
            bookSupplierPhoneNumber = context.getString(R.string.unknown_phone_number);
        }

        // Update the TextViews with the attributes for the current book
        nameTextView.setText(bookName);
        priceTextView.setText(bookPrice);
        quantityTextView.setText(bookQuantity);
        supplierTextView.setText(bookSupplier);
        phoneNumberTextView.setText(bookSupplierPhoneNumber);

        // Store ID and quantity values for use when sale button is pressed (sent to sellOneProduct method)
        final long id = cursor.getLong(cursor.getColumnIndex(BookContract.BookEntry._ID));
        final int qty = intBookQuantity;

        // Execute sellOneProduct() method when the "sale" button is pressed
        view.findViewById(R.id.sale).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CatalogActivity catalogActivity = (CatalogActivity) context;

                catalogActivity.sellOneProduct(id, qty);
            }
        });
    }
}
