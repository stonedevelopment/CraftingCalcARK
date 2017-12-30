package arc.resource.calculator.db;

import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import arc.resource.calculator.db.dao.EngramDao;

public abstract class AppDatabase extends RoomDatabase {
    private static final String DB_NAME = "database.db";
    private static volatile AppDatabase sInstance;

    public static synchronized AppDatabase getInstance( Context context ) {
        if ( sInstance == null )
            sInstance = createInstance( context );

        return sInstance;
    }

    private static AppDatabase createInstance( final Context context ) {
        return Room.databaseBuilder( context.getApplicationContext(),
                AppDatabase.class, DB_NAME ).build();
    }

    public abstract EngramDao getEngramDao();
}
