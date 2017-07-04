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
package arc.resource.calculator.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import arc.resource.calculator.db.DatabaseContract.CategoryEntry;
import arc.resource.calculator.db.DatabaseContract.ComplexResourceEntry;
import arc.resource.calculator.db.DatabaseContract.CompositionEntry;
import arc.resource.calculator.db.DatabaseContract.DLCEntry;
import arc.resource.calculator.db.DatabaseContract.EngramEntry;
import arc.resource.calculator.db.DatabaseContract.QueueEntry;
import arc.resource.calculator.db.DatabaseContract.ResourceEntry;
import arc.resource.calculator.db.DatabaseContract.StationEntry;
import arc.resource.calculator.util.Util;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String TAG = DatabaseHelper.class.getSimpleName();

    /*
    DATABASE_VERSION Changelog:
    v7, Added 'favorite' column to table 'engram'
    v6, Converted drawable to folder/file to use with assets
    v5, Updated database to allow custom _ids for Resources and Engrams.
    v4, Added Stations and Levels
    v3, Added Engram Yield column
    v2, Added Versioning (DLC)
    v1, Initial create
     */
    public static final int DATABASE_VERSION = 7;
    public static final String DATABASE_NAME = "database.db";

    private static final String DROP_TABLE_IF_EXISTS = "DROP TABLE IF EXISTS ";

    public DatabaseHelper( Context context ) {
        super( context, DATABASE_NAME, null, DATABASE_VERSION );
    }

    @Override
    public void onCreate( SQLiteDatabase database ) {
        Util.Log( TAG, "** Database (" + DATABASE_NAME + " v" + DATABASE_VERSION + ") not found, creating it.." );

        final String SQL_CREATE_ENGRAM_TABLE = "CREATE TABLE " + EngramEntry.TABLE_NAME + " (" +
                EngramEntry._ID + " INTEGER NOT NULL, " +
                EngramEntry.COLUMN_NAME + " TEXT NOT NULL, " +
                EngramEntry.COLUMN_DESCRIPTION + " TEXT NOT NULL, " +
                EngramEntry.COLUMN_IMAGE_FOLDER + " TEXT NOT NULL, " +
                EngramEntry.COLUMN_IMAGE_FILE + " TEXT NOT NULL, " +
                EngramEntry.COLUMN_YIELD + " INTEGER NOT NULL, " +
                EngramEntry.COLUMN_LEVEL + " INTEGER NOT NULL, " +
                EngramEntry.COLUMN_FAVORITE + " TEXT, " +
                EngramEntry.COLUMN_CATEGORY_KEY + " INTEGER NOT NULL, " +
                EngramEntry.COLUMN_STATION_KEY + " INTEGER NOT NULL, " +
                EngramEntry.COLUMN_DLC_KEY + " INTEGER NOT NULL, " +
                "FOREIGN KEY (" + EngramEntry.COLUMN_CATEGORY_KEY + ") REFERENCES " + CategoryEntry.TABLE_NAME + " (" + CategoryEntry._ID + "), " +
                "FOREIGN KEY (" + EngramEntry.COLUMN_STATION_KEY + ") REFERENCES " + StationEntry.TABLE_NAME + " (" + StationEntry._ID + "), " +
                "FOREIGN KEY (" + EngramEntry.COLUMN_DLC_KEY + ") REFERENCES " + DLCEntry.TABLE_NAME + " (" + DLCEntry._ID + ")" +
                ")";

        final String SQL_CREATE_COMPOSITION_TABLE = "CREATE TABLE " + CompositionEntry.TABLE_NAME + " (" +
                CompositionEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                CompositionEntry.COLUMN_QUANTITY + " INTEGER NOT NULL, " +
                CompositionEntry.COLUMN_RESOURCE_KEY + " INTEGER NOT NULL, " +
                CompositionEntry.COLUMN_ENGRAM_KEY + " INTEGER NOT NULL, " +
                CompositionEntry.COLUMN_DLC_KEY + " INTEGER NOT NULL, " +
                "FOREIGN KEY (" + CompositionEntry.COLUMN_RESOURCE_KEY + ") REFERENCES " + ResourceEntry.TABLE_NAME + " (" + ResourceEntry._ID + "), " +
                "FOREIGN KEY (" + CompositionEntry.COLUMN_ENGRAM_KEY + ") REFERENCES " + EngramEntry.TABLE_NAME + " (" + EngramEntry._ID + "), " +
                "FOREIGN KEY (" + CompositionEntry.COLUMN_DLC_KEY + ") REFERENCES " + DLCEntry.TABLE_NAME + " (" + DLCEntry._ID + ")" +
                ")";

        final String SQL_CREATE_RESOURCE_TABLE = "CREATE TABLE " + ResourceEntry.TABLE_NAME + " (" +
                ResourceEntry._ID + " INTEGER NOT NULL, " +
                ResourceEntry.COLUMN_NAME + " TEXT NOT NULL, " +
                ResourceEntry.COLUMN_IMAGE_FOLDER + " TEXT NOT NULL, " +
                ResourceEntry.COLUMN_IMAGE_FILE + " TEXT NOT NULL, " +
                ResourceEntry.COLUMN_DLC_KEY + " INTEGER NOT NULL, " +
                "FOREIGN KEY (" + ResourceEntry.COLUMN_DLC_KEY + ") REFERENCES " + DLCEntry.TABLE_NAME + " (" + DLCEntry._ID + ")" +
                ")";

        final String SQL_CREATE_CATEGORY_TABLE = "CREATE TABLE " + CategoryEntry.TABLE_NAME + " (" +
                CategoryEntry._ID + " INTEGER NOT NULL, " +
                CategoryEntry.COLUMN_NAME + " TEXT NOT NULL, " +
                CategoryEntry.COLUMN_PARENT_KEY + " INTEGER NOT NULL, " +
                CategoryEntry.COLUMN_STATION_KEY + " INTEGER NOT NULL, " +
                CategoryEntry.COLUMN_DLC_KEY + " INTEGER NOT NULL, " +
                "FOREIGN KEY (" + CategoryEntry.COLUMN_STATION_KEY + ") REFERENCES " + StationEntry.TABLE_NAME + " (" + StationEntry._ID + "), " +
                "FOREIGN KEY (" + CategoryEntry.COLUMN_DLC_KEY + ") REFERENCES " + DLCEntry.TABLE_NAME + " (" + DLCEntry._ID + ")" +
                ")";

        final String SQL_CREATE_COMPLEX_RESOURCE_TABLE = "CREATE TABLE " + ComplexResourceEntry.TABLE_NAME + " (" +
                ComplexResourceEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                ComplexResourceEntry.COLUMN_ENGRAM_KEY + " INTEGER NOT NULL, " +
                ComplexResourceEntry.COLUMN_RESOURCE_KEY + " INTEGER NOT NULL, " +
                "FOREIGN KEY (" + ComplexResourceEntry.COLUMN_ENGRAM_KEY + ") REFERENCES " + EngramEntry.TABLE_NAME + " (" + EngramEntry._ID + "), " +
                "FOREIGN KEY (" + ComplexResourceEntry.COLUMN_RESOURCE_KEY + ") REFERENCES " + ResourceEntry.TABLE_NAME + " (" + ResourceEntry._ID + ")" +
                ")";

        final String SQL_CREATE_QUEUE_TABLE = "CREATE TABLE " + QueueEntry.TABLE_NAME + " (" +
                QueueEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                QueueEntry.COLUMN_QUANTITY + " INTEGER NOT NULL, " +
                QueueEntry.COLUMN_ENGRAM_KEY + " INTEGER NOT NULL, " +
                "FOREIGN KEY (" + QueueEntry.COLUMN_ENGRAM_KEY + ") REFERENCES " + EngramEntry.TABLE_NAME + " (" + EngramEntry._ID + ")" +
                ")";

        final String SQL_CREATE_DLC_TABLE = "CREATE TABLE " + DLCEntry.TABLE_NAME + " (" +
                CategoryEntry._ID + " INTEGER NOT NULL, " +
                CategoryEntry.COLUMN_NAME + " TEXT NOT NULL " +
                ")";

        final String SQL_CREATE_STATION_TABLE = "CREATE TABLE " + StationEntry.TABLE_NAME + " (" +
                StationEntry._ID + " INTEGER NOT NULL, " +
                StationEntry.COLUMN_NAME + " TEXT NOT NULL, " +
                StationEntry.COLUMN_IMAGE_FOLDER + " TEXT NOT NULL, " +
                StationEntry.COLUMN_IMAGE_FILE + " TEXT NOT NULL, " +
                StationEntry.COLUMN_DLC_KEY + " INTEGER NOT NULL, " +
                "FOREIGN KEY (" + StationEntry.COLUMN_DLC_KEY + ") REFERENCES " + DLCEntry.TABLE_NAME + " (" + DLCEntry._ID + ")" +
                ")";

        CreateTable( database, DLCEntry.TABLE_NAME, SQL_CREATE_DLC_TABLE );
        CreateTable( database, StationEntry.TABLE_NAME, SQL_CREATE_STATION_TABLE );
        CreateTable( database, CategoryEntry.TABLE_NAME, SQL_CREATE_CATEGORY_TABLE );
        CreateTable( database, ResourceEntry.TABLE_NAME, SQL_CREATE_RESOURCE_TABLE );
        CreateTable( database, EngramEntry.TABLE_NAME, SQL_CREATE_ENGRAM_TABLE );
        CreateTable( database, CompositionEntry.TABLE_NAME, SQL_CREATE_COMPOSITION_TABLE );
        CreateTable( database, ComplexResourceEntry.TABLE_NAME, SQL_CREATE_COMPLEX_RESOURCE_TABLE );
        CreateTable( database, QueueEntry.TABLE_NAME, SQL_CREATE_QUEUE_TABLE );

        Util.Log( TAG, "** Database successfully created." );
    }

    @Override
    public void onUpgrade( SQLiteDatabase database, int oldVersion, int newVersion ) {
        Util.Log( TAG, "** Database (" + DATABASE_NAME + " v" + oldVersion + ") version has changed, upgrading.." );

        DropTable( database, QueueEntry.TABLE_NAME );
        DropTable( database, ComplexResourceEntry.TABLE_NAME );
        DropTable( database, CompositionEntry.TABLE_NAME );
        DropTable( database, EngramEntry.TABLE_NAME );
        DropTable( database, ResourceEntry.TABLE_NAME );
        DropTable( database, CategoryEntry.TABLE_NAME );
        DropTable( database, StationEntry.TABLE_NAME );
        DropTable( database, DLCEntry.TABLE_NAME );

        onCreate( database );

        Util.Log( TAG, "** Database successfully upgraded from v" + oldVersion + " to v" + newVersion );
    }

    private void CreateTable( SQLiteDatabase database, String table, String sql ) {
        Util.Log( TAG, "-> Creating table: " + table );

        database.execSQL( sql );
    }

    private void DropTable( SQLiteDatabase database, String table ) {
        Util.Log( TAG, "-> Dropping table: " + table );

        database.execSQL( DROP_TABLE_IF_EXISTS + table );
    }
}
