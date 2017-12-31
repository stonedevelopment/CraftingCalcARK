package arc.resource.calculator.db;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.migration.Migration;
import android.content.Context;
import android.support.annotation.NonNull;

import arc.resource.calculator.db.dao.CategoryDao;
import arc.resource.calculator.db.dao.ComplexResourceDao;
import arc.resource.calculator.db.dao.CompositionDao;
import arc.resource.calculator.db.dao.DLCDao;
import arc.resource.calculator.db.dao.EngramDao;
import arc.resource.calculator.db.dao.QueueDao;
import arc.resource.calculator.db.dao.ResourceDao;
import arc.resource.calculator.db.dao.StationDao;
import arc.resource.calculator.db.entity.Category;
import arc.resource.calculator.db.entity.ComplexResource;
import arc.resource.calculator.db.entity.Composition;
import arc.resource.calculator.db.entity.DLC;
import arc.resource.calculator.db.entity.Engram;
import arc.resource.calculator.db.entity.Queue;
import arc.resource.calculator.db.entity.Resource;
import arc.resource.calculator.db.entity.Station;

@Database( version = 7,
        entities = { Category.class,
                ComplexResource.class,
                Composition.class,
                DLC.class,
                Engram.class,
                Queue.class,
                Resource.class,
                Station.class } )

public abstract class AppDatabase extends RoomDatabase {
    private static final String DB_NAME = "database.db";
    private static volatile AppDatabase sInstance;

    public static synchronized AppDatabase getInstance( Context context ) {
        if ( sInstance == null )
            sInstance = createInstance( context );

        return sInstance;
    }

    private static final Migration MIGRATION_6_7 = new Migration( 6, 7 ) {
        @Override
        public void migrate( @NonNull SupportSQLiteDatabase database ) {
            // Since we didn't alter the table, there's nothing else to do here.
        }
    };

    private static AppDatabase createInstance( final Context context ) {
        return Room.databaseBuilder( context.getApplicationContext(),
                AppDatabase.class, DB_NAME )
                .addMigrations( MIGRATION_6_7 )
                .build();
    }

    public abstract CategoryDao categoryDao();

    public abstract ComplexResourceDao complexResourceDao();

    public abstract CompositionDao compositionDao();

    public abstract DLCDao dlcDao();

    public abstract EngramDao engramDao();

    public abstract QueueDao queueDao();

    public abstract ResourceDao resourceDao();

    public abstract StationDao stationDao();
}
