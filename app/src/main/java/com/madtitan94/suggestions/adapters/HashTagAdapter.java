package com.madtitan94.suggestions.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.madtitan94.suggestions.R;
import com.madtitan94.suggestions.pojoClasses.HashTag;
import com.madtitan94.suggestions.utils.OPERATION_TYPE;

import java.util.List;

public class HashTagAdapter extends RecyclerView.Adapter<HashTagAdapter.ViewHolder> {
    Context context;
    List<HashTag> tagList;
    OPERATION_TYPE operationType;
    HashTagClicked hashTagClicked;

    public HashTagAdapter(Context context, List<HashTag> tagList,OPERATION_TYPE operationType,HashTagClicked hashTagClicked ){
        this.context = context;
        this.tagList = tagList;
        this.operationType = operationType;
        this.hashTagClicked = hashTagClicked;
    }

    public interface HashTagClicked{
        public void OnHashTagClicked(OPERATION_TYPE operationType, int position, HashTag tag);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.hashtag_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.tagName.setText(tagList.get(position).getTagName());
        if (operationType == OPERATION_TYPE.SELECT){
            holder.operation.setBackground(context.getResources().getDrawable(R.drawable.ic_baseline_add));
        }else {
            holder.operation.setBackground(context.getResources().getDrawable(R.drawable.ic_cancel_2));
        }
        holder.tagCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (hashTagClicked!=null){
                    hashTagClicked.OnHashTagClicked(operationType,position,tagList.get(position));
                }
            }
        });
        holder.operation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (hashTagClicked!=null){
                    hashTagClicked.OnHashTagClicked(operationType,position,tagList.get(position));
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return tagList!=null?tagList.size():0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tagName;
        Button operation;
        CardView tagCard;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tagName = itemView.findViewById(R.id.tagName);
            operation = itemView.findViewById(R.id.tagOperation);
            tagCard = itemView.findViewById(R.id.tagCard);
        }
    }
}
