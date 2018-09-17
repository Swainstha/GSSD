package com.example.swainstha.roomies;


import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class ExpensesFragment extends Fragment implements ExpensesDialogFragment.OnAddExpensesListener {


    private ExpensesViewModel mExpensesViewModel;
    private ExpensesListAdapter adapter;
    private ImageButton button;
    public ExpensesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mExpensesViewModel = ViewModelProviders.of(this).get(ExpensesViewModel.class);
        return inflater.inflate(R.layout.fragment_expenses, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        RecyclerView recyclerView = view.findViewById(R.id.recyclerview);
        button = view.findViewById(R.id.fab);
        adapter = new ExpensesListAdapter(getContext());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        mExpensesViewModel.getAllExpenses().observe(this, new Observer<List<Expenses>>() {
            @Override
            public void onChanged(@Nullable final List<Expenses> expenses) {
                // Update the cached copy of the words in the adapter.
                adapter.setExpenses(expenses);
            }
        });


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog();
            }
        });
    }

    @Override
    public void onAddExpensesSubmit(String item, int cost) {
        Expenses expenses = new Expenses(item, cost);
        mExpensesViewModel.insert(expenses);
    }

    public void showDialog() {
        FragmentManager manager = getActivity().getSupportFragmentManager();
        ExpensesDialogFragment cart = new ExpensesDialogFragment();
        cart.setTargetFragment(this, 0);
        cart.show(manager, "My Expenses");
    }

}
