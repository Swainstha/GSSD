package com.example.swainstha.roomies;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;


/**
 * A simple {@link Fragment} subclass.
 */
public class ExpensesDialogFragment extends DialogFragment {


    private EditText item_name;
    private EditText item_cost;
    private Button expenses_button;
    public ExpensesDialogFragment() {
        // Required empty public constructor
    }

    private OnAddExpensesListener callback;

    public interface OnAddExpensesListener {
        public void onAddExpensesSubmit(String item, int cost);
    }

    public static ExpensesDialogFragment newInstance(String title) {

        ExpensesDialogFragment frag = new ExpensesDialogFragment();

        Bundle args = new Bundle();

        args.putString("title", title);

        frag.setArguments(args);

        return frag;

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_expenses_dialog, container, false);
        item_name = v.findViewById(R.id.item_name);
        item_cost = v.findViewById(R.id.item_cost);


        try {
            callback = (OnAddExpensesListener) getTargetFragment();
        } catch (ClassCastException e) {
            throw new ClassCastException("Calling Fragment must implement OnAddFriendListener");
        }

        expenses_button = v.findViewById(R.id.expenses_add);
        expenses_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i("Dialog","Button clicked in dialog");
                final String item_name_ = item_name.getText().toString();
                final int item_cost_ = Integer.parseInt(item_cost.getText().toString());
                Log.i("Data", item_name_);
                Log.i("Data", item_cost_ + "");
                callback.onAddExpensesSubmit(item_name_,item_cost_);
                dismiss();
            }
        });

        return v;
    }

    @Override

    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

        super.onViewCreated(view, savedInstanceState);

        // Fetch arguments from bundle and set title

        getDialog().setTitle("Add");

        // Show soft keyboard automatically and request focus to field

        getDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);

    }

//    @Override
//    public void onResume() {
//        super.onResume();
//        ViewGroup.LayoutParams params = getDialog().getWindow().getAttributes();
//        params.width = FrameLayout.LayoutParams.MATCH_PARENT;
//        params.height = FrameLayout.LayoutParams.MATCH_PARENT;
//
//
//        getDialog().getWindow().setAttributes((android.view.WindowManager.LayoutParams) params);
//    }

}
