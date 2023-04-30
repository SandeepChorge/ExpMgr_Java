package com.madtitan94.suggestions.activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.gson.Gson;
import com.madtitan94.suggestions.R;
import com.madtitan94.suggestions.adapters.TransactionAdapter;
import com.madtitan94.suggestions.databinding.ActivityMainBinding;
import com.madtitan94.suggestions.databinding.TransactionBottomsheetBinding;
import com.madtitan94.suggestions.db.AppDatabase;
import com.madtitan94.suggestions.pojoClasses.HashTag;
import com.madtitan94.suggestions.pojoClasses.TranasctionListItem;
import com.madtitan94.suggestions.pojoClasses.Transaction;
import com.madtitan94.suggestions.utils.Helper;
import com.madtitan94.suggestions.utils.OPERATION_TYPE;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, TransactionAdapter.TransactionClicked {

    Context context;
    AlertDialog.Builder resultAlert;

    BottomSheetDialog bottomSheetDialog;
    TransactionBottomsheetBinding bottomsheetBinding;
    AppDatabase db;

    ActivityMainBinding binding;
    List<TranasctionListItem> recentTransactions= new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_main);
        context = this;
        initViews();

        db = AppDatabase.getDatabase(this);
        //getTestData();

        binding.fabBottomSheet.setOnClickListener(this);

        binding.expToday.setOnClickListener(this);
        binding.expWeek.setOnClickListener(this);
        binding.expMonth.setOnClickListener(this);

        setupRecentTransactions();
        // showDialog();

        // context = this;
      /*  resultAlert = new AlertDialog.Builder(context);
        resultAlert.setCancelable(true);
        resultAlert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Log.e("OK clicked","yep");
            }
        });
*/

    }

    private void setupRecentTransactions() {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                recentTransactions = db.transactionDao().getTransactions();
                Helper.appendLog("79 recentTransactions "+new Gson().toJson(recentTransactions!=null?recentTransactions:" IS EMPTY"));
                return null;
            }

            @Override
            protected void onPostExecute(Void unused) {
                super.onPostExecute(unused);
                try {


                    if (recentTransactions == null)
                        recentTransactions = new ArrayList<>();
                    TransactionAdapter adapter = new TransactionAdapter(context, recentTransactions, MainActivity.this);
                    binding.recentTransactions.setAdapter(adapter);
                    binding.recentTransactions.setLayoutManager(new LinearLayoutManager(context));
                    adapter.notifyDataSetChanged();
                }catch (Exception ex){
                    ex.printStackTrace();
                }
            }
        }.execute();
    }

    private void getTestData(){
        try {
            new AsyncTask<Void, Void, Void>() {
                @Override
                protected Void doInBackground(Void... voids) {
                    List<HashTag> tags = new ArrayList<>();
                    tags.add(new HashTag(1,"Breakfast","","",1));
                    tags.add(new HashTag(2,"Brunch","","",1));
                    tags.add(new HashTag(3,"Lunch","","",1));
                    tags.add(new HashTag(4,"Snacks","","",1));
                    tags.add(new HashTag(5,"Evening Snacks","","",1));
                    tags.add(new HashTag(6,"Dinner","","",1));
                    tags.add(new HashTag(7,"Dinner Contribution","","",1));
                    tags.add(new HashTag(8,"Contribution","","",1));
                    db.hashTagDao().insertAll(tags);
                    return null;
                }
            }.execute();
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

    private void initViews() {
        /*try {
            bottomSheetDialog = new BottomSheetDialog(context);

            bottomsheetBinding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.transaction_bottomsheet,
                    null, false);
            bottomSheetDialog.setContentView(bottomsheetBinding.getRoot());
            bottomSheetDialog.getWindow().setSoftInputMode(
                    WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE|
                            WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
            bottomsheetBinding.searchEd.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    try {
                        if (s!=null ) {
                            Log.e("72 textChanged ",""+s.toString());
                            getFilteredTags(s.toString());
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });

        } catch (Exception ex) {
            ex.printStackTrace();
        }*/
    }

    private void getFilteredTags(String query){
        new AsyncTask<Void, Void, Void>() {
            List<HashTag> hashTags = new ArrayList<>();
            @Override
            protected Void doInBackground(Void... voids) {
                Log.e("Will be searching for","_ "+query);
                hashTags = db.hashTagDao().getTagsByName(query);
                return null;
            }

            @Override
            protected void onPostExecute(Void unused) {
                super.onPostExecute(unused);
                if (hashTags == null){
                    hashTags = new ArrayList<>();
                }
                generateChips(hashTags);

            }
        }.execute();
    }

    private void generateChips(List<HashTag> tagList){
        try {

        /*    Log.e("generateChips",""+new Gson().toJson(tagList));
            bottomsheetBinding.chipGroup.removeAllViews();
            if (tagList!=null && tagList.size()>0){
                for (int index = 0; index < tagList.size(); index++) {
                    final String tagName = tagList.get(index).getTagName();
                    final Chip chip = new Chip(this);
                    int paddingDp = (int) TypedValue.applyDimension(
                            TypedValue.COMPLEX_UNIT_DIP, 10,
                            getResources().getDisplayMetrics()
                    );
                    chip.setPadding(paddingDp, paddingDp, paddingDp, paddingDp);
                    chip.setText(tagName);
                    chip.setCloseIconResource(R.drawable.ic_cancel_2);
                    chip.setCloseIconVisible(true);
                    //Added click listener on close icon to remove tag from ChipGroup
                    chip.setOnCloseIconClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            tagList.remove(tagName);
                            bottomsheetBinding.chipGroup.removeView(chip);
                        }
                    });

                    bottomsheetBinding.chipGroup.addView(chip);
                }
            }
*/
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

    public void showDialog() {
        if (bottomSheetDialog!=null){
            bottomSheetDialog.show();
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.expToday:
                Helper.appendLog("Todays date "+Helper.DT_getFilterDate(1));
                break;

            case R.id.expWeek:
                Helper.appendLog("Week Start date "+Helper.DT_getFilterDate(2));
                Helper.appendLog("Week End date "+Helper.DT_getFilterDate(3));
                break;
            case R.id.expMonth:
                //Helper.appendLog("Todays date "+Helper.getFilterDate(1));
                break;

            case R.id.fab_bottomSheet:
                startActivity(new Intent(context,BottomSheetActivity.class));
                break;


        }
    }


    @Override
    public void OnTransactionClicked(OPERATION_TYPE operationType, int position, Transaction transaction) {

    }
}