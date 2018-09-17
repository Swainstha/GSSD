package com.example.swainstha.roomies;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;

import java.util.List;

/**
 * Created by swainstha on 9/17/18.
 */

public class ExpensesViewModel extends AndroidViewModel {

    private ExpensesRepository mRepository;

    private LiveData<List<Expenses>> mAllExpenses;

    public ExpensesViewModel (Application application) {
        super(application);
        mRepository = new ExpensesRepository(application);
        mAllExpenses = mRepository.getAllWords();
    }

    LiveData<List<Expenses>> getAllExpenses() { return mAllExpenses; }

    public void insert(Expenses expenses) { mRepository.insert(expenses); }
}