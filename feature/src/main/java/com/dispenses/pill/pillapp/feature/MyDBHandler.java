package com.dispenses.pill.pillapp.feature;

/**
 * Created by Win10 on 07/02/2018.
 */

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MyDBHandler extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "productDB.db";
    public static final String TABLE_ALARMS = "alarm";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_ALARMNAME = "alarmname";
    public static final String COLUMN_TIME = "time";

    public static final String TABLE_BOTTLEX = "bottlex";
    public static final String COLUMN_IDX = "_id";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_STARTTIME = "starttime";
    public static final String COLUMN_ENDTIMETIME = "endtime";
    public static final String COLUMN_PILLAMT = "pillAmt";

    public static final String TABLE_BOTTLEY = "bottley";
    public static final String COLUMN_IDY = "_id";
    public static final String COLUMN_NAMEY = "namey";
    public static final String COLUMN_STARTTIMEY = "starttimey";
    public static final String COLUMN_ENDTIMETIMEY = "endtimey";
    public static final String COLUMN_PILLAMTY = "pillAmty";


    public static final String TABLE_BOTTLEZ = "bottlez";
    public static final String COLUMN_IDZ = "_id";
    public static final String COLUMN_NAMEZ = "namez";
    public static final String COLUMN_STARTTIMEZ = "starttimez";
    public static final String COLUMN_ENDTIMETIMEZ = "endtimez";
    public static final String COLUMN_PILLAMTZ = "pillAmtz";

    public static final String TABLE_HISTORY = "history";
    public static final String COLUMN_IDH = "_id";
    public static final String COLUMN_pillAmountH = "pillAmt";
    public static final String COLUMN_TIMEH = "time";
    public static final String COLUMN_pillBox = "pillBox";


    public MyDBHandler(Context context, String name,
                       SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
    }

    /**
     * Called when the database is created for the first time. This is where the
     * creation of tables and the initial population of the tables should happen.
     *
     * @param db The database.
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        String create_products_table = "CREATE TABLE " + TABLE_ALARMS + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY, " + COLUMN_ALARMNAME + " TEXT," +
                COLUMN_TIME + " TEXT )";
        db.execSQL(create_products_table);

        String create_bottleone_table = "CREATE TABLE " + TABLE_BOTTLEX + " ("+
                COLUMN_IDX + " INTEGER PRIMARY KEY, " + COLUMN_NAME + " TEXT, "
                + COLUMN_STARTTIME + " TEXT," + COLUMN_ENDTIMETIME + " TEXT," +
                COLUMN_PILLAMT + " INTEGER )";
        db.execSQL(create_bottleone_table);

        String create_bottletwo_table =  "CREATE TABLE " + TABLE_BOTTLEY + " (" +
                COLUMN_IDY + " INTEGER PRIMARY KEY, " +
                COLUMN_NAMEY + " TEXT , " + COLUMN_STARTTIMEY + " TEXT," +
                COLUMN_ENDTIMETIMEY + " TEXT," + COLUMN_PILLAMTY + " INTEGER )";
        db.execSQL(create_bottletwo_table);

        String create_bottlethree_table =  "CREATE TABLE " + TABLE_BOTTLEZ + " ("+
                COLUMN_IDZ + " INTEGER PRIMARY KEY, " +
                COLUMN_NAMEZ + " TEXT , " + COLUMN_STARTTIMEZ + " TEXT," +
                COLUMN_ENDTIMETIMEZ + " TEXT," + COLUMN_PILLAMTZ + " INTEGER )";
        db.execSQL(create_bottlethree_table);

        String create_history_table =  "CREATE TABLE " + TABLE_HISTORY + " ("+
                COLUMN_IDH + " INTEGER PRIMARY KEY, " +
                COLUMN_pillAmountH + " TEXT , " + COLUMN_TIMEH + " TEXT," + COLUMN_pillBox + " TEXT )";
        db.execSQL(create_history_table);
    }

    /**
     * Called when the database needs to be upgraded. The implementation
     * should use this method to drop tables, add tables, or do anything else it
     * needs to upgrade to the new schema version.
     * <p/>
     * <p>
     * The SQLite ALTER TABLE documentation can be found
     * <a href="http://sqlite.org/lang_altertable.html">here</a>. If you add new columns
     * you can use ALTER TABLE to insert them into a live table. If you rename or remove columns
     * you can use ALTER TABLE to rename the old table, then create the new table and then
     * populate the new table with the contents of the old table.
     * </p><p>
     * This method executes within a transaction.  If an exception is thrown, all changes
     * will automatically be rolled back.
     * </p>
     *
     * @param db         The database.
     * @param oldVersion The old database version.
     * @param newVersion The new database version.
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ALARMS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_BOTTLEX);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_BOTTLEY);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_BOTTLEZ);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_HISTORY);
        onCreate(db);
    }

    /**
     * Gets all Products in the Database and returns a cursor of them.
     * If there are no items in the database then the cursor returns null
     *
     * @return A Cursor of all products or null
     */
    public Cursor getAllProducts() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_ALARMS, new String[] {COLUMN_ID, COLUMN_ALARMNAME,
                COLUMN_TIME}, null, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
            return cursor;
        }
        else
        {
            return null;
        }
    }

    public Cursor getallbottlex() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_BOTTLEX, new String[] {COLUMN_IDX,COLUMN_NAME, COLUMN_STARTTIME,
                COLUMN_ENDTIMETIME,COLUMN_PILLAMT}, null, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
            return cursor;
        }
        else
        {
            return null;
        }
    }

    public Cursor getallbottley() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_BOTTLEY, new String[] {COLUMN_IDY,COLUMN_NAMEY, COLUMN_STARTTIMEY,
                COLUMN_ENDTIMETIMEY,COLUMN_PILLAMTY}, null, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
            return cursor;
        }
        else
        {
            return null;
        }
    }


    public Cursor getallbottlez() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_BOTTLEZ, new String[] {COLUMN_IDZ,COLUMN_NAMEZ, COLUMN_STARTTIMEZ,
                COLUMN_ENDTIMETIMEZ,COLUMN_PILLAMTZ}, null, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
            return cursor;
        }
        else
        {
            return null;
        }
    }

    public Cursor gethistory() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_HISTORY, new String[] {COLUMN_IDH,COLUMN_pillAmountH, COLUMN_TIMEH,
                COLUMN_pillBox}, null, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
            return cursor;
        }
        else
        {
            return null;
        }
    }











    public void addAlarm(Alarm alarm) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_ALARMNAME, alarm.getAlarmName());
        values.put(COLUMN_TIME, alarm.getTime());
        SQLiteDatabase db = this.getWritableDatabase();
        db.insert(TABLE_ALARMS, null, values);
        db.close();
    }

    public void bottlexAdd(bottlexGetSet bottlexGetSet) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, bottlexGetSet.getName());
        values.put(COLUMN_STARTTIME, bottlexGetSet.getStarttime());
        values.put(COLUMN_ENDTIMETIME, bottlexGetSet.getEndtime());
        values.put(COLUMN_PILLAMT, bottlexGetSet.getPillAmt());
        SQLiteDatabase db = this.getWritableDatabase();
        db.insert(TABLE_BOTTLEX, null, values);
        db.close();
    }

    public void bottleyAdd(bottleyGetSet bottleyGetSet) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAMEY, bottleyGetSet.getNamey());
        values.put(COLUMN_STARTTIMEY, bottleyGetSet.getStarttimey());
        values.put(COLUMN_ENDTIMETIMEY, bottleyGetSet.getEndtimey());
        values.put(COLUMN_PILLAMTY, bottleyGetSet.getPillAmty());
        SQLiteDatabase db = this.getWritableDatabase();
        db.insert(TABLE_BOTTLEY, null, values);
        db.close();
    }

    public void bottlezAdd(bottlezGetSet bottlezGetSet) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAMEZ, bottlezGetSet.getNamez());
        values.put(COLUMN_STARTTIMEZ, bottlezGetSet.getStarttimez());
        values.put(COLUMN_ENDTIMETIMEZ, bottlezGetSet.getEndtimez());
        values.put(COLUMN_PILLAMTZ, bottlezGetSet.getPillAmtz());
        SQLiteDatabase db = this.getWritableDatabase();
        db.insert(TABLE_BOTTLEZ, null, values);
        db.close();
    }

    public void Addhistory(historyGetSet historyGetSet) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_pillAmountH, historyGetSet.getPillAmt());
        values.put(COLUMN_TIMEH, historyGetSet.getTime());
        values.put(COLUMN_pillBox, historyGetSet.getPillBox());
        SQLiteDatabase db = this.getWritableDatabase();
        db.insert(TABLE_HISTORY, null, values);
        db.close();
    }

    public void deleteAllHistory()
    {
        String q = "DELETE FROM " + TABLE_HISTORY;
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL(q);
        db.close();
    }


    public boolean deleteSchedule(String name) {
        boolean result = false;
        String q = "SELECT * FROM " + TABLE_BOTTLEX + " WHERE " + COLUMN_NAME
                + " = \"" + name + "\"";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(q, null);
        bottlexGetSet p = new bottlexGetSet();
        if (cursor.moveToFirst()) {
            cursor.moveToFirst();
            p.set_id(Integer.parseInt(cursor.getString(0)));
            db.delete(TABLE_BOTTLEX, COLUMN_ID + " = ?",
                    new String[] { String.valueOf(p.get_id())});
            cursor.close();
            result = true;
        }
        db.close();
        return result;
    }

    public boolean deleteScheduley(String name) {
        boolean result = false;
        String q = "SELECT * FROM " + TABLE_BOTTLEY + " WHERE " + COLUMN_NAMEY
                + " = \"" + name + "\"";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(q, null);
        bottleyGetSet p = new bottleyGetSet();
        if (cursor.moveToFirst()) {
            cursor.moveToFirst();
            p.set_id(Integer.parseInt(cursor.getString(0)));
            db.delete(TABLE_BOTTLEY, COLUMN_ID + " = ?",
                    new String[] { String.valueOf(p.get_id())});
            cursor.close();
            result = true;
        }
        db.close();
        return result;
    }



    public boolean deleteSchedulez(String name) {
        boolean result = false;
        String q = "SELECT * FROM " + TABLE_BOTTLEZ + " WHERE " + COLUMN_NAMEZ
                + " = \"" + name + "\"";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(q, null);
        bottlezGetSet p = new bottlezGetSet();
        if (cursor.moveToFirst()) {
            cursor.moveToFirst();
            p.set_id(Integer.parseInt(cursor.getString(0)));
            db.delete(TABLE_BOTTLEZ, COLUMN_ID + " = ?",
                    new String[] { String.valueOf(p.get_id())});
            cursor.close();
            result = true;
        }
        db.close();
        return result;
    }
    /**
     * Finds a product in the database and returns it to the caller. If this function does not find
     * a product, then the returned product is null
     * @param productname Name of the product to find
     * @return A valid product or a null product
     */
    public Alarm findProduct(String productname) {
        String query = "Select * FROM " + TABLE_ALARMS + " WHERE " + COLUMN_ALARMNAME +
                " = \"" + productname + "\"";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        Alarm p = new Alarm();
        if (cursor.moveToFirst()) {
            cursor.moveToFirst();
            p.setID(Integer.parseInt(cursor.getString(0)));
            p.setAlarmName(cursor.getString(1));
            p.setTime(cursor.getString(2));
            cursor.close();
        } else {
            p = null;
        }
        db.close();
        return p;
    }

    /**
     * This function delete's a product in TABLE_PRODUCTS based on the ID of the product retrieved
     * by it's product name.
     * @param productname The name of the product to delete
     * @return True if deleted false otherwise.
     */
    public boolean deleteProduct(String productname) {
        boolean result = false;
        String q = "SELECT * FROM " + TABLE_ALARMS + " WHERE " + COLUMN_ALARMNAME
                + " = \"" + productname + "\"";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(q, null);
        Alarm p = new Alarm();
        if (cursor.moveToFirst()) {
            cursor.moveToFirst();
            p.setID(Integer.parseInt(cursor.getString(0)));
            db.delete(TABLE_ALARMS, COLUMN_ID + " = ?",
                    new String[] { String.valueOf(p.getID())});
            cursor.close();
            result = true;
        }
        db.close();
        return result;
    }

    /**
     * Updates the product passed to this function.
     * @param p The product to update
     * @return True if updated, false otherwise.
     */
    public boolean updateProduct(Alarm p) {
        boolean result = false;
        String q = "SELECT * FROM " + TABLE_ALARMS + " WHERE " + COLUMN_ALARMNAME + " = " + p.getAlarmName();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor c = db.rawQuery(q, null);
        if (c.moveToFirst())
        {
            String q2 = "UPDATE " + TABLE_ALARMS + " SET " + COLUMN_ALARMNAME + " = \""
                    + p.getAlarmName() + "\", " + COLUMN_TIME + " = " + p.getTime()
                    ;
            db.execSQL(q2);
            result = true;
        }
        db.close();
        return result;
    }

    /**
     * Deletes all records in the product database regardless if the
     * database contains records or not.
     */
    public void deleteAllProducts()
    {
        String q = "DELETE FROM " + TABLE_ALARMS;
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL(q);
        db.close();
    }

}
