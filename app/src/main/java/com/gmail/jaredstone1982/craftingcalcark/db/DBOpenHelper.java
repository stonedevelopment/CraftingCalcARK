package com.gmail.jaredstone1982.craftingcalcark.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBOpenHelper extends SQLiteOpenHelper {
    private static final String LOGTAG = "DB_HELPER";
    private static final String DATABASE_NAME = "engrams.db";
    private static final int DATABASE_VERSION = 8;

    private static final String DROP_TABLE_IF_EXISTS = "DROP TABLE IF EXISTS ";

    public static final String TABLE_ENGRAM = "engram";
    public static final String COLUMN_ENGRAM_ID = "engramid";
    public static final String COLUMN_ENGRAM_NAME = "engramname";
    public static final String COLUMN_ENGRAM_DESCRIPTION = "engramdesc";
    public static final String COLUMN_ENGRAM_IMAGE_ID = "engramimage";

    public static final String TABLE_RESOURCE = "resource";
    public static final String COLUMN_RESOURCE_ID = "resourceid";
    public static final String COLUMN_RESOURCE_NAME = "resourcename";
    public static final String COLUMN_RESOURCE_IMAGE_ID = "resourceimage";
    public static final String COLUMN_RESOURCE_QUANTITY = "resourcequantity";

    public static final String TABLE_CATEGORY = "category";
    public static final String COLUMN_CATEGORY_ID = "categoryid";
    public static final String COLUMN_CATEGORY_NAME = "categoryname";
    public static final String COLUMN_CATEGORY_IMAGE_ID = "categoryimage";

    public static final String TABLE_QUEUE = "queue";
    public static final String COLUMN_QUEUE_ID = "queueid";
    public static final String COLUMN_QUEUE_QUANTITY = "queuequantity";

    public static final String COLUMN_TRACK_ENGRAM = "trackengram";

    private static final String TABLE_ENGRAM_CREATE =
            "CREATE TABLE " + TABLE_ENGRAM + " (" +
                    COLUMN_ENGRAM_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_ENGRAM_NAME + " TEXT, " +
                    COLUMN_ENGRAM_DESCRIPTION + " TEXT, " +
                    COLUMN_ENGRAM_IMAGE_ID + " INTEGER " +
                    ")";

    private static final String TABLE_RESOURCE_CREATE =
            "CREATE TABLE " + TABLE_RESOURCE + " (" +
                    COLUMN_RESOURCE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_RESOURCE_NAME + " TEXT, " +
                    COLUMN_RESOURCE_IMAGE_ID + " INTEGER, " +
                    COLUMN_RESOURCE_QUANTITY + " INTEGER, " +
                    COLUMN_TRACK_ENGRAM + " INTEGER, " +
                    "FOREIGN KEY (" + COLUMN_TRACK_ENGRAM + ") REFERENCES " + TABLE_ENGRAM + " (" + COLUMN_ENGRAM_ID + ")" +
                    ")";

    private static final String TABLE_CATEGORY_CREATE =
            "CREATE TABLE " + TABLE_CATEGORY + " (" +
                    COLUMN_CATEGORY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_CATEGORY_NAME + " TEXT, " +
                    COLUMN_CATEGORY_IMAGE_ID + " INTEGER, " +
                    COLUMN_TRACK_ENGRAM + " INTEGER, " +
                    "FOREIGN KEY (" + COLUMN_TRACK_ENGRAM + ") REFERENCES " + TABLE_ENGRAM + " (" + COLUMN_ENGRAM_ID + ")" +
                    ")";

    public static final String TABLE_QUEUE_CREATE =
            "CREATE TABLE " + TABLE_QUEUE + " (" +
                    COLUMN_QUEUE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_QUEUE_QUANTITY + " INTEGER, " +
                    COLUMN_TRACK_ENGRAM + " INTEGER, " +
                    "FOREIGN KEY (" + COLUMN_TRACK_ENGRAM + ") REFERENCES " + TABLE_ENGRAM + " (" + COLUMN_ENGRAM_ID + ")" +
                    ")";

    public DBOpenHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TABLE_ENGRAM_CREATE);
        db.execSQL(TABLE_RESOURCE_CREATE);
        db.execSQL(TABLE_CATEGORY_CREATE);
        Log.d(LOGTAG, "++ Tables (ENGRAM, RESOURCE, CATEGORY) have been created");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        dropTable(db, TABLE_CATEGORY);
        dropTable(db, TABLE_RESOURCE);
        dropTable(db, TABLE_ENGRAM);
        dropTable(db, TABLE_QUEUE);

        Log.d(LOGTAG, "** Database has been upgraded from v" + oldVersion + " to v" + newVersion);
        onCreate(db);
    }

    public static void createAllTables(SQLiteDatabase database) {
        createTable(database, TABLE_ENGRAM);
        createTable(database, TABLE_RESOURCE);
        createTable(database, TABLE_CATEGORY);
        createTable(database, TABLE_QUEUE);
    }

    public static void dropAllTables(SQLiteDatabase database) {
        dropTable(database, TABLE_QUEUE);
        dropTable(database, TABLE_CATEGORY);
        dropTable(database, TABLE_RESOURCE);
        dropTable(database, TABLE_ENGRAM);
    }

    public static void dropTable(SQLiteDatabase db, String table) {
        db.execSQL(DROP_TABLE_IF_EXISTS + table);
    }

    private static void createTable(SQLiteDatabase db, String table) {
        switch (table) {
            case TABLE_CATEGORY:
                db.execSQL(TABLE_CATEGORY_CREATE);
                break;

            case TABLE_ENGRAM:
                db.execSQL(TABLE_ENGRAM_CREATE);
                break;

            case TABLE_RESOURCE:
                db.execSQL(TABLE_RESOURCE_CREATE);
                break;

            case TABLE_QUEUE:
                db.execSQL(TABLE_QUEUE_CREATE);
                break;

            default:
                break;

        }
    }
}
