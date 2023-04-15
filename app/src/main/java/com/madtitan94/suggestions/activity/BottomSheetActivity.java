package com.madtitan94.suggestions.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.madtitan94.suggestions.R;
import com.madtitan94.suggestions.adapters.HashTagAdapter;
import com.madtitan94.suggestions.databinding.TransactionBottomsheetBinding;
import com.madtitan94.suggestions.db.AppDatabase;
import com.madtitan94.suggestions.pojoClasses.HashTag;
import com.madtitan94.suggestions.pojoClasses.Transaction;
import com.madtitan94.suggestions.utils.Helper;
import com.madtitan94.suggestions.utils.OPERATION_TYPE;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class BottomSheetActivity extends AppCompatActivity implements HashTagAdapter.HashTagClicked {

    TransactionBottomsheetBinding binding;
    AppDatabase db;
    Context context;

    List<HashTag> suggestionTagList = new ArrayList<>();
    HashTagAdapter suggestionTagAdapter;

    List<HashTag> mappedTagList = new ArrayList<>();
    HashTagAdapter mappedTagAdapter;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.transaction_bottomsheet);
        context = this;
        initViews();

        db = AppDatabase.getDatabase(this);
        //getTestData();
        getFilteredTags("");
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
        try {
            binding.searchEd.addTextChangedListener(new TextWatcher() {
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
                            Helper.appendLog("72 textChanged "+s.toString());
                            getFilteredTags(s.toString());
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });

            //binding.searchEd.getdraw

            binding.addNewTag.setOnClickListener(new View.OnClickListener() {
                @RequiresApi(api = Build.VERSION_CODES.N)
                @Override
                public void onClick(View view) {
                    if (binding.searchEd.getText().toString().trim().isEmpty()){
                        Helper.showToast(context,"Tag Name Cannot Be Empty!");
                    }else {
                        boolean isPresent = mappedTagList.stream().anyMatch(it->it.getTagName().trim().
                                equalsIgnoreCase(binding.searchEd.getText().toString().trim()));

                        if (isPresent){
                            Helper.showToast(context,"This Tag Is Already Mapped With The Transaction!");
                            return;
                        }

                        isPresent = suggestionTagList.stream().anyMatch(it->it.getTagName().trim().
                                equalsIgnoreCase(binding.searchEd.getText().toString().trim()));

                        if (isPresent){
                            Helper.showToast(context,"Please Select The Tag From Suggestions!");
                            return;
                        }

                        insertNewTag(binding.searchEd.getText().toString().trim());
                    }
                }
            });

            binding.save.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (binding.tranAmount.getText().toString().trim().isEmpty()
                            || binding.tranAmount.getText().toString().trim().equalsIgnoreCase(".")
                            || Double.parseDouble(binding.tranAmount.getText().toString().trim()) ==0){
                        Helper.showToast(context,"Please Enter Valid Amount!");
                        return;
                    }

                    insertNewTranasction();
                    //Helper.showToast(context,"Passed");
                }
            });
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

    @SuppressLint("StaticFieldLeak")
    private void insertNewTranasction() {
        try {
            new AsyncTask<Void,Void,Void>(){
                @RequiresApi(api = Build.VERSION_CODES.N)
                @Override
                protected Void doInBackground(Void... voids) {

                    Transaction transaction = new Transaction(
                            binding.trandetail.getText().toString().trim(),
                            Helper.getCurrentDate(),
                            Double.parseDouble(binding.tranAmount.getText().toString().trim()),
                            1,
                            1,
                            mappedTagList.stream().mapToInt(it-> it.getId()).toArray()
                    );

                    db.transactionDao().insert(transaction);
                    /*HashTag tag = new HashTag(""+tagName.trim(),"",Helper.getCurrentDate(),1);
                    long id = db.hashTagDao().insert(tag);
                    Helper.appendLog("id generated is  "+id);

                    HashTag t = db.hashTagDao().getTag(Integer.parseInt(""+id));
                    Helper.appendLog("Tag created "+new Gson().toJson(t!=null?t:" IS EMPTY"));
                    if (t!=null){
                        mappedTagList.add(t);
                    }*/
                    return null;
                }

                @Override
                protected void onPostExecute(Void unused) {
                    super.onPostExecute(unused);
                    Helper.showToast(context,"Transaction Added Successfully.");
                    finish();
                }
            }.execute();
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

    private void insertNewTag(String tagName) {
        Helper.appendLog("Inserting new tag with name "+tagName);
        new AsyncTask<Void,Void,Void>(){
            @Override
            protected Void doInBackground(Void... voids) {
                HashTag tag = new HashTag(""+tagName.trim(),"",Helper.getCurrentDate(),1);
                long id = db.hashTagDao().insert(tag);
                Helper.appendLog("id generated is  "+id);

                HashTag t = db.hashTagDao().getTag(Integer.parseInt(""+id));
                Helper.appendLog("Tag created "+new Gson().toJson(t!=null?t:" IS EMPTY"));
                if (t!=null){
                    mappedTagList.add(t);
                }
                return null;
            }

            @Override
            protected void onPostExecute(Void unused) {
                super.onPostExecute(unused);
                loadMappedTags();
            }
        }.execute();
    }

    private void getFilteredTags(String query){
        new AsyncTask<Void, Void, Void>() {
            List<HashTag> hashTags = new ArrayList<>();
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            protected Void doInBackground(Void... voids) {
                int[] ints = mappedTagList.stream().mapToInt(it-> it.getId()).toArray();
                Helper.appendLog("Will be searching for "+query);
                //hashTags = db.hashTagDao().getTagsByName(query);
                hashTags = db.hashTagDao().getTagsByNameExclude(query,ints);
                return null;
            }

            @Override
            protected void onPostExecute(Void unused) {
                super.onPostExecute(unused);
                if (hashTags == null){
                    hashTags = new ArrayList<>();
                }
                suggestionTagList = hashTags;
                loadSuggestions();
            }
        }.execute();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void loadSuggestions(){
        try {
            //Helper.appendLog("generateChips "+new Gson().toJson(suggestionTagList));
            suggestionTagAdapter = new HashTagAdapter(context,suggestionTagList,OPERATION_TYPE.SELECT,this);
            binding.tagSuggestions.setLayoutManager(new LinearLayoutManager(context,RecyclerView.HORIZONTAL,false));
            binding.tagSuggestions.setAdapter(suggestionTagAdapter);
            suggestionTagAdapter.notifyDataSetChanged();

            List<Integer> a = suggestionTagList.stream().map(it-> it.getId()).collect(Collectors.toList());
            Helper.appendLog("suggest "+new Gson().toJson(a));
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

    @Override
    public void OnHashTagClicked(OPERATION_TYPE operationType, int position, HashTag tag) {
        Helper.appendLog("136 position "+position+" tag "+new Gson().toJson(tag));
        if (operationType == OPERATION_TYPE.SELECT){
            mappedTagList.add(suggestionTagList.get(position));
            suggestionTagList.remove(position);
            suggestionTagAdapter.notifyDataSetChanged();
            loadMappedTags();
        }else {
            suggestionTagList.add(mappedTagList.get(position));
            mappedTagList.remove(position);
            mappedTagAdapter.notifyDataSetChanged();
            getFilteredTags(binding.searchEd.getText().toString().trim());
        }
    }

    private void loadMappedTags(){
        try {
            mappedTagAdapter = new HashTagAdapter(context,mappedTagList,OPERATION_TYPE.REMOVE,this);
            binding.tagMapped.setLayoutManager(new LinearLayoutManager(context,RecyclerView.HORIZONTAL,false));
            binding.tagMapped.setAdapter(mappedTagAdapter);
            mappedTagAdapter.notifyDataSetChanged();
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }
}
