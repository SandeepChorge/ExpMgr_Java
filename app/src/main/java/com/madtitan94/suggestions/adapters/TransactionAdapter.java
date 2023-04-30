package com.madtitan94.suggestions.adapters;

import android.content.Context;
import android.graphics.Typeface;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.google.android.material.shape.ShapeAppearanceModel;
import com.madtitan94.suggestions.R;
import com.madtitan94.suggestions.pojoClasses.TranasctionListItem;
import com.madtitan94.suggestions.pojoClasses.Transaction;
import com.madtitan94.suggestions.utils.Helper;
import com.madtitan94.suggestions.utils.OPERATION_TYPE;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TransactionAdapter extends RecyclerView.Adapter<TransactionAdapter.ViewHolder> {
    Context context;
    List<TranasctionListItem> transactions;
    OPERATION_TYPE operationType;
    TransactionClicked transactionClicked;

    public TransactionAdapter(Context context, List<TranasctionListItem> transactions, TransactionClicked transactionClicked ){
        this.context = context;
        this.transactions = transactions;
        this.operationType = operationType;
        this.transactionClicked = transactionClicked;
    }

    public interface TransactionClicked{
        public void OnTransactionClicked(OPERATION_TYPE operationType, int position, Transaction transaction);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.transaction_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        try{


        holder.amount.setText(Helper.getMoneyFormat(transactions.get(position).getAmount()));
        holder.date.setText(Helper.DT_getDateForDisplay(transactions.get(position).getCreatedAt()));
        generateChipDetails(transactions.get(position).getTags(),holder.tagDetails);
/*
        String det = transactions.get(position).getDetail()!=null?transactions.get(position).getDetail():"";
        holder.tr_detail.setText(det);
*/
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return transactions!=null?transactions.size():0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tr_detail,date,amount;
        ChipGroup tagDetails;
        CardView tagCard;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            date = itemView.findViewById(R.id.date);
            tagDetails = itemView.findViewById(R.id.tagDetails);
            tr_detail = itemView.findViewById(R.id.tr_detail);
            amount = itemView.findViewById(R.id.amount);
            tagCard = itemView.findViewById(R.id.tagCard);
        }
    }

    private void generateChipDetails(String tagString, ChipGroup chipGroup){
        try {
            if (tagString!=null && !tagString.isEmpty()){
                List<String> list = new ArrayList<String>(Arrays.asList(tagString.split(",")));
                for (String str :list){
                    if (str.isEmpty())
                        continue;
                    Chip chip = new Chip(context);

                   /* int paddingDp = (int) TypedValue.applyDimension(
                            TypedValue.COMPLEX_UNIT_DIP, 1,
                            context.getResources().getDisplayMetrics()
                    );
                    chip.setPadding(paddingDp, paddingDp, paddingDp, paddingDp);*/
                    chip.setText("#"+str);
                    //chip.setBackgroundColor(context.getResources().getColor(R.color.color15));
                  /*  chip.setChipBackgroundColorResource(R.color.black_back);
                    chip.setChipStrokeColorResource(R.color.teal_700);
                    chip.setTextColor(context.getResources().getColor(R.color.color19));*/
                    chip.setChipBackgroundColorResource(R.color.black_back);
                    chip.setChipStrokeColorResource(R.color.color19);
                    chip.setTextColor(context.getResources().getColor(R.color.white));
                    chip.setChipStrokeWidth(1.0f);
                    chip.setTypeface(Typeface.create(Typeface.SANS_SERIF,Typeface.BOLD_ITALIC));
                    /*ShapeAppearanceModel.builder()
                            .setBottomLeftCornerSize(0)
                            .setBottomRightCornerSize(10)
                            .setTopRightCornerSize(0).setTopLeftCornerSize(0);
*/
                    chip.setShapeAppearanceModel(ShapeAppearanceModel.builder().setBottomLeftCornerSize(0)
                            .setBottomRightCornerSize(20)
                            .setTopRightCornerSize(0).setTopLeftCornerSize(20).build());
                    /*chip.setCloseIconResource(R.drawable.ic_cancel_2);
                    chip.setCloseIconVisible(true);*/
                    chipGroup.addView(chip);
                }
            }

        }catch (Exception ex){ex.printStackTrace();}
    }
}
