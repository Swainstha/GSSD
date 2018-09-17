package com.example.swainstha.roomies;

/**
 * Created by swainstha on 9/17/18.
 */

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;

import java.util.Date;


@Database(entities = {Expenses.class}, version = 1)
public abstract class ExpensesRoomDatabase extends RoomDatabase {
    public abstract ExpensesDao expensesDao();

    private static volatile ExpensesRoomDatabase INSTANCE;

    static ExpensesRoomDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (ExpensesRoomDatabase.class) {
                if (INSTANCE == null) {
                    // Create database here
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            ExpensesRoomDatabase.class, "word_database")
                            .addCallback(sRoomDatabaseCallback)
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    private static ExpensesRoomDatabase.Callback sRoomDatabaseCallback =
            new RoomDatabase.Callback(){

                @Override
                public void onOpen (@NonNull SupportSQLiteDatabase db){
                    super.onOpen(db);
                    new PopulateDbAsync(INSTANCE).execute();
                }
            };

    private static class PopulateDbAsync extends AsyncTask<Void, Void, Void> {

        private final ExpensesDao mDao;

        PopulateDbAsync(ExpensesRoomDatabase db) {
            mDao = db.expensesDao();
        }

        @Override
        protected Void doInBackground(final Void... params) {
            mDao.deleteAll();
            //Expenses expenses = new Expenses("Hello",300);
            //mDao.insert(expenses);
            return null;
        }
    }
}