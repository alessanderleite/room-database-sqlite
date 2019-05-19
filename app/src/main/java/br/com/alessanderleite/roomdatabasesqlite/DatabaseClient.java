package br.com.alessanderleite.roomdatabasesqlite;

import android.arch.persistence.room.Room;
import android.content.Context;

public class DatabaseClient {

    private Context mContext;
    private static DatabaseClient mInstance;

    //our app database object
    private AppDatabase appDatabase;

    public DatabaseClient(Context mContext) {
        this.mContext = mContext;

        appDatabase = Room.databaseBuilder(mContext, AppDatabase.class, "MyToDos").build();
    }

    public static synchronized DatabaseClient getInstance(Context mContext) {
        if (mInstance == null) {
            mInstance = new DatabaseClient(mContext);
        }
        return mInstance;
    }

    public AppDatabase getAppDatabase() {
        return appDatabase;
    }
}
