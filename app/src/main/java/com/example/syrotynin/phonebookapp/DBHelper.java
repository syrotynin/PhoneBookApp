package com.example.syrotynin.phonebookapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by Syrotynin on 13.03.2015.
 */
public class DBHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "contacts.db";
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_TABLE = "Contacts";

    // поля таблицы
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_NAME = "username";
    public static final String COLUMN_PHONE_NUMBER = "phone";

    // запрос на создание базы данных
    private static final String DATABASE_CREATE = "create table "
            + DATABASE_TABLE + "(" + COLUMN_ID
            + " integer primary key autoincrement, " + COLUMN_NAME
            + " text not null, " + COLUMN_PHONE_NUMBER + " text not null" + ");";

    public DBHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        Log.d("DBHelp", "In Constructor");
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DATABASE_CREATE);
        Log.d("DBHelp", "Create DB");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(DBHelper.class.getName(), "Upgrading database from version "
                + oldVersion + " to " + newVersion
                + ", which will destroy all old data");
        db.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE);
        onCreate(db);
    }

    public long createNewContact(String username, String phoneNumber) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues initialValues = createContentValues(username, phoneNumber);

        long row = db.insert(DATABASE_TABLE, null, initialValues);
        db.close();

        Log.d("DBHelp", "Insert contact: "+username);
        return row;
    }

    /**
     * Создаёт новый элемент списка кгнтактов. Если создан успешно - возвращается
     * номер строки rowId, иначе -1
     */
    public boolean updateContact(long rowId, String username, String phoneNumber) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues updateValues = createContentValues(username, phoneNumber);

        return db.update(DATABASE_TABLE, updateValues, COLUMN_ID + "=" + rowId,
                null) > 0;
    }

    /**
     * Удаляет элемент списка
     */
    public void deleteContact(long rowId) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(DATABASE_TABLE, COLUMN_ID + "=" + rowId, null);
        db.close();
    }

    /**
     * Возвращает курсор со всеми элементами списка дел
     *
     * @return курсор с результатами всех записей
     */
    public Cursor getAllContacts() {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.query(DATABASE_TABLE, new String[] { COLUMN_ID,
                        COLUMN_NAME, COLUMN_PHONE_NUMBER },
                null, null, null, null, null);
    }

    /**
     * Возвращает курсор с указанной записи
     */
    public Cursor getContact(long rowId) throws SQLException {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor contactCursor = db.query(true, DATABASE_TABLE,
                new String[] { COLUMN_ID, COLUMN_NAME, COLUMN_PHONE_NUMBER },
                COLUMN_ID + "=" + rowId, null,
                null, null, null, null);
        if (contactCursor != null) {
            contactCursor.moveToFirst();
        }
        return contactCursor;
    }

    private ContentValues createContentValues(String username, String phoneNumber) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, username);
        values.put(COLUMN_PHONE_NUMBER, phoneNumber);
        return values;
    }

    public static ArrayList<Contact> cursor_to_contacts(Cursor cursor){
        ArrayList<Contact> array = new ArrayList<Contact>();

        if (cursor.moveToFirst()){
            do{
                String name = cursor.getString(cursor.getColumnIndex(COLUMN_NAME));
                String phone = cursor.getString(cursor.getColumnIndex(COLUMN_PHONE_NUMBER));
                int id = cursor.getInt(cursor.getColumnIndex(COLUMN_ID));

                Log.d("DBHelp",id + " " + name + " " + phone);

                array.add(new Contact(id, name, phone));
            }while(cursor.moveToNext());
        }
        cursor.close();

        return array;
    }

}
