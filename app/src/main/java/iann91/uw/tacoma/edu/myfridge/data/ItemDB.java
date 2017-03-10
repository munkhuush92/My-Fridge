package iann91.uw.tacoma.edu.myfridge.data;

import android.app.Application;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

import iann91.uw.tacoma.edu.myfridge.R;
import iann91.uw.tacoma.edu.myfridge.item.Item;

/**
 * Created by imnic on 3/7/2017.
 */

public class ItemDB extends Application{
    private static final String ITEM_TABLE = "Food";
    private static final String NAME_FOOD_ITEM = "nameFoodItem";
    private static final String PERSON_ID = "PersonID";

    public static final int DB_VERSION = 1;
    public static final String DB_NAME = "Item.db";

    private ItemDBHelper mItemDBHelper;
    private SQLiteDatabase mSQLiteDatabase;


    public ItemDB() {}

    public ItemDB(Context context) {
        mItemDBHelper = new ItemDBHelper(
                context, DB_NAME, null, DB_VERSION);
        mSQLiteDatabase = mItemDBHelper.getWritableDatabase();
    }


    public boolean insertItem(String nameFoodItem, String sizeFoodItem, int PersonID, String foodType) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("nameFoodItem", nameFoodItem);
        contentValues.put("sizeFoodItem", sizeFoodItem);
        contentValues.put("PersonID", PersonID);
        contentValues.put("foodType", foodType);

        long rowId = mSQLiteDatabase.insert(ITEM_TABLE, null, contentValues);
        return rowId != -1;

    }

    public void closeDB() {
        mSQLiteDatabase.close();
    }

    public void removeSingleItem(String name, int id) {
        //Open the database
                //Execute sql query to remove from database
        //NOTE: When removing by String in SQL, value must be enclosed with ''
        mSQLiteDatabase.execSQL("DELETE FROM " + ITEM_TABLE + " WHERE " + NAME_FOOD_ITEM + "= '" + name + "'" + " AND " + PERSON_ID  + "= " + id);

        //Close the database
        mSQLiteDatabase.close();
    }



    /**
     * Returns the list of items from the local Item table.
     * @return list
     */
    public List<Item> getItems(String theCategory) {

        String[] columns = {
                "nameFoodItem", "sizeFoodItem", "PersonID", "foodType"
        };


        Cursor c = mSQLiteDatabase.query(
                ITEM_TABLE,  // The table to query
                columns,                               // The columns to return
                "foodType=?",                                // The columns for the WHERE clause
                new String[]{theCategory},                            // The values for the WHERE clause
                null,                                     // don't group the rows
                null,                                     // don't filter by row groups
                null                                 // The sort order
        );
        c.moveToFirst();
        List<Item> list = new ArrayList<Item>();
        for (int i=0; i<c.getCount(); i++) {
            String nameFoodItem = c.getString(0);
            String sizeFoodItem = c.getString(1);
            String PersonID = c.getString(2);
            String foodType = c.getString(3);
            Item item = new Item(nameFoodItem, sizeFoodItem, PersonID, foodType);
            list.add(item);
            c.moveToNext();
        }

        return list;
    }

    public void deleteCourses() {
        mSQLiteDatabase.delete(ITEM_TABLE, null, null);
    }



    class ItemDBHelper extends SQLiteOpenHelper {

        private final String CREATE_ITEM_SQL;

        private final String DROP_ITEM_SQL;

        public ItemDBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
            super(context, name, factory, version);
            CREATE_ITEM_SQL = context.getString(R.string.CREATE_ITEM_SQL);
            DROP_ITEM_SQL = context.getString(R.string.DROP_ITEM_SQL);

        }

        @Override
        public void onCreate(SQLiteDatabase sqLiteDatabase) {
            sqLiteDatabase.execSQL(CREATE_ITEM_SQL);
        }

        @Override
        public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
            sqLiteDatabase.execSQL(DROP_ITEM_SQL);
            onCreate(sqLiteDatabase);
        }
    }



}
