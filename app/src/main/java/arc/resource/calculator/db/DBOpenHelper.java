package arc.resource.calculator.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import arc.resource.calculator.helpers.Helper;

/**
 * Copyright (C) 2016, Jared Stone
 * -
 * Author: Jared Stone
 * Title: A:RC, a resource calculator for ARK:Survival Evolved
 * -
 * Web: https://github.com/jaredstone1982/CraftingCalcARK
 * Email: jaredstone1982@gmail.com
 * Twitter: @MasterxOfxNone
 * -
 * This work is licensed under a Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International License.
 */
public class DBOpenHelper extends SQLiteOpenHelper {
    private static DBOpenHelper sInstance;

    private static final String LOGTAG = "DB_HELPER";
    private static final String DATABASE_NAME = "ark.db";

    private static final int DATABASE_VERSION = 6;

    private static final String DROP_TABLE_IF_EXISTS = "DROP TABLE IF EXISTS ";

    public static final String TABLE_RESOURCE = "resource";
    public static final String COLUMN_RESOURCE_ID = "resourceid";
    public static final String COLUMN_RESOURCE_NAME = "resourcename";
    public static final String COLUMN_RESOURCE_IMAGE_ID = "resourceimage";

    public static final String TABLE_ENGRAM = "engram";
    public static final String COLUMN_ENGRAM_ID = "engramid";
    public static final String COLUMN_ENGRAM_NAME = "engramname";
    public static final String COLUMN_ENGRAM_DESCRIPTION = "engramdesc";
    public static final String COLUMN_ENGRAM_IMAGE_ID = "engramimage";
    public static final String COLUMN_ENGRAM_CATEGORY_ID = "engramcategory";

    public static final String TABLE_CATEGORY = "category";
    public static final String COLUMN_CATEGORY_ID = "categoryid";
    public static final String COLUMN_CATEGORY_NAME = "categoryname";
    public static final String COLUMN_CATEGORY_LEVEL = "categorylevel";
    public static final String COLUMN_CATEGORY_PARENT = "categoryparent";

    public static final String TABLE_COMPOSITION = "composition";
    public static final String COLUMN_COMPOSITION_ID = "compositionid";
    public static final String COLUMN_COMPOSITION_QUANTITY = "compositionquantity";

    public static final String TABLE_QUEUE = "queue";
    public static final String COLUMN_QUEUE_ID = "queueid";
    public static final String COLUMN_QUEUE_QUANTITY = "queuequantity";

    public static final String COLUMN_TRACK_ENGRAM = "trackengram";
    public static final String COLUMN_TRACK_RESOURCE = "trackresource";

    private static final String TABLE_ENGRAM_CREATE =
            "CREATE TABLE " + TABLE_ENGRAM + " (" +
                    COLUMN_ENGRAM_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_ENGRAM_NAME + " TEXT, " +
                    COLUMN_ENGRAM_DESCRIPTION + " TEXT, " +
                    COLUMN_ENGRAM_IMAGE_ID + " INTEGER, " +
                    COLUMN_ENGRAM_CATEGORY_ID + " INTEGER" +
                    ")";

    private static final String TABLE_COMPOSITION_CREATE =
            "CREATE TABLE " + TABLE_COMPOSITION + " (" +
                    COLUMN_COMPOSITION_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_COMPOSITION_QUANTITY + " INTEGER, " +
                    COLUMN_TRACK_RESOURCE + " INTEGER, " +
                    COLUMN_TRACK_ENGRAM + " INTEGER, " +
                    "FOREIGN KEY (" + COLUMN_TRACK_RESOURCE + ") REFERENCES " + TABLE_RESOURCE + " (" + COLUMN_RESOURCE_ID + "), " +
                    "FOREIGN KEY (" + COLUMN_TRACK_ENGRAM + ") REFERENCES " + TABLE_ENGRAM + " (" + COLUMN_ENGRAM_ID + ")" +
                    ")";

    public static final String TABLE_QUEUE_CREATE =
            "CREATE TABLE " + TABLE_QUEUE + " (" +
                    COLUMN_QUEUE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_QUEUE_QUANTITY + " INTEGER, " +
                    COLUMN_TRACK_ENGRAM + " INTEGER, " +
                    "FOREIGN KEY (" + COLUMN_TRACK_ENGRAM + ") REFERENCES " + TABLE_ENGRAM + " (" + COLUMN_ENGRAM_ID + ")" +
                    ")";

    private static final String TABLE_RESOURCE_CREATE =
            "CREATE TABLE " + TABLE_RESOURCE + " (" +
                    COLUMN_RESOURCE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_RESOURCE_NAME + " TEXT, " +
                    COLUMN_RESOURCE_IMAGE_ID + " INTEGER" +
                    ")";

    private static final String TABLE_CATEGORY_CREATE =
            "CREATE TABLE " + TABLE_CATEGORY + " (" +
                    COLUMN_CATEGORY_ID + " INTEGER PRIMARY KEY, " +
                    COLUMN_CATEGORY_NAME + " TEXT, " +
                    COLUMN_CATEGORY_LEVEL + " INTEGER, " +
                    COLUMN_CATEGORY_PARENT + " INTEGER" +
                    ")";

    public static synchronized DBOpenHelper getInstance(Context context) {
        if (sInstance == null) {
            sInstance = new DBOpenHelper(context.getApplicationContext());
        }

        return sInstance;
    }

    private DBOpenHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Helper.Log(LOGTAG, "** Database (" + DATABASE_NAME + " v" + DATABASE_VERSION + ") not found, creating..");

        createAllTables(db);

        Helper.Log(LOGTAG, "** Database successfully created.");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Helper.Log(LOGTAG, "** Database (" + DATABASE_NAME + " v" + oldVersion + ") version has changed, upgrading..");

        dropAllTables(db);
        createAllTables(db);

        Helper.Log(LOGTAG, "** Database successfully upgraded from v" + oldVersion + " to v" + newVersion);
    }

    private static void createAllTables(SQLiteDatabase database) {
        Helper.Log(LOGTAG, "-- Creating all tables.");

        createTable(database, TABLE_ENGRAM);
        createTable(database, TABLE_COMPOSITION);
        createTable(database, TABLE_QUEUE);
        createTable(database, TABLE_RESOURCE);
        createTable(database, TABLE_CATEGORY);

        Helper.Log(LOGTAG, "-- All tables created.");
    }

    private static void dropAllTables(SQLiteDatabase database) {
        Helper.Log(LOGTAG, "-- Dropping all tables.");

        dropTable(database, TABLE_CATEGORY);
        dropTable(database, TABLE_RESOURCE);
        dropTable(database, TABLE_QUEUE);
        dropTable(database, TABLE_COMPOSITION);
        dropTable(database, TABLE_ENGRAM);

        Helper.Log(LOGTAG, "-- All tables dropped.");
    }

    protected static void dropTable(SQLiteDatabase db, String table) {
        Helper.Log(LOGTAG, "-> Dropping table: " + table);

        db.execSQL(DROP_TABLE_IF_EXISTS + table);
    }

    protected static void createTable(SQLiteDatabase db, String table) {
        Helper.Log(LOGTAG, "-> Creating table: " + table);

        switch (table) {
            case TABLE_RESOURCE:
                db.execSQL(TABLE_RESOURCE_CREATE);
                break;

            case TABLE_ENGRAM:
                db.execSQL(TABLE_ENGRAM_CREATE);
                break;

            case TABLE_COMPOSITION:
                db.execSQL(TABLE_COMPOSITION_CREATE);
                break;

            case TABLE_CATEGORY:
                db.execSQL(TABLE_CATEGORY_CREATE);
                break;

            case TABLE_QUEUE:
                db.execSQL(TABLE_QUEUE_CREATE);
                break;

            default:
                Helper.Log(LOGTAG, "!!> Table " + table + " does not exist in database, aborting.");
        }
    }

    public static int getDatabaseVersion() {
        return DATABASE_VERSION;
    }
}
