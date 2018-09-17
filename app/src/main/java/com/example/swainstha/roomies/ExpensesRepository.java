package com.example.swainstha.roomies;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import java.util.List;

/**
 * Created by swainstha on 9/17/18.
 */

public class ExpensesRepository {

    private ExpensesDao expensesDao;
    private LiveData<List<Expenses>> mAllExpenses;

    ExpensesRepository(Application application) {
        ExpensesRoomDatabase db = ExpensesRoomDatabase.getDatabase(application);
        expensesDao = db.expensesDao();
        mAllExpenses = expensesDao.getAllExpenses();
    }

    LiveData<List<Expenses>> getAllWords() {
        return mAllExpenses;
    }


    public void insert (Expenses expenses) {
        new insertAsyncTask(expensesDao).execute(expenses);
    }

    private static class insertAsyncTask extends AsyncTask<Expenses, Void, Void> {

        private ExpensesDao mAsyncTaskDao;

        insertAsyncTask(ExpensesDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Expenses... params) {
            mAsyncTaskDao.insert(params[0]);
            return null;
        }
    }
}
