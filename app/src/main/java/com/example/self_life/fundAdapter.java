package com.example.self_life;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class fundAdapter extends RecyclerView.Adapter<fundAdapter.ViewHoler> {

    private Context context;
    private List<Fund_List> fundlist;
    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(int position, String postId);
    }

    public void setOnItemClickListener(fundAdapter.OnItemClickListener listener) {
        this.listener = listener;
    }

    public fundAdapter(Context context, List<Fund_List> fundlist){
        this.context = context;
        this.fundlist = fundlist;
    }


    @NonNull
    @Override
    public fundAdapter.ViewHoler onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.fundlist_item, parent, false);
        return new fundAdapter.ViewHoler(view);
    }

    @Override
    public void onBindViewHolder(@NonNull fundAdapter.ViewHoler holder, int position) {
        final Fund_List fundData = fundlist.get(position);
        holder.fundId.setText(fundData.getFundId());
        holder.fundKind.setText(fundData.getFundKind());
        holder.fundDivision.setText(fundData.getFundDivision());
        holder.fundMemo.setText(fundData.getFundMemo());
        holder.fundValue.setText(fundData.getFundValue());
        holder.fundDay.setText(fundData.getFundDay());
    }

    @Override
    public int getItemCount() {
        return (fundlist != null ? fundlist.size() : 0);
    }

    public class ViewHoler extends RecyclerView.ViewHolder {
        public TextView fundId;
        public TextView fundKind;
        public TextView fundDivision;
        public TextView fundMemo;
        public TextView fundValue;
        public TextView fundDay;

        public ViewHoler(View itemView) {
            super(itemView);
            fundId = itemView.findViewById(R.id.fundId);
            fundKind = itemView.findViewById(R.id.fundKind);
            fundDivision = itemView.findViewById(R.id.fundDivision);
            fundMemo = itemView.findViewById(R.id.fundMeno);
            fundValue = itemView.findViewById(R.id.fundValue);
            fundDay = itemView.findViewById(R.id.fundDay);
        }
    }
}
