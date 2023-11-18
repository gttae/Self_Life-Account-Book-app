package com.example.self_life;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ReportPostAdapter extends RecyclerView.Adapter<ReportPostAdapter.ViewHolder> {

    private Context context;
    private List<ReportPost_List> RPList;
    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(int position, String postId);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public ReportPostAdapter(Context context, List<ReportPost_List> rpList) {
        this.context = context;
        this.RPList = rpList;
    }

    @NonNull
    @Override
    public ReportPostAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.report_postlist_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ReportPostAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        final ReportPost_List RPData = RPList.get(position);
        holder.reportIdTextView.setText(RPData.getReportId());
        holder.writerTextView.setText(RPData.getWriter());
        holder.categoryTextView.setText(RPData.getCategory());
        holder.titleTextView.setText(RPData.getTitle());
        holder.postIdTextView.setText(RPData.getPostId());
        holder.dateTextView.setText(RPData.getDate());
        holder.contentTextView.setText(RPData.getContent());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (listener != null) {
                    // 수정된 부분: postId를 가져와서 전달
                    String postId = RPData.getPostId();

                    listener.onItemClick(position, postId);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return (RPList != null ? RPList.size() : 0);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView reportIdTextView;
        public TextView writerTextView;
        public TextView categoryTextView;
        public TextView titleTextView;
        public TextView dateTextView;
        public TextView postIdTextView;
        public TextView contentTextView;

        public ViewHolder(View itemview) {
            super(itemview);
            reportIdTextView = itemview.findViewById(R.id.board_ReportId);
            writerTextView =itemview.findViewById(R.id.board_Writer);
            categoryTextView =itemview.findViewById(R.id.board_category);
            titleTextView =itemview.findViewById(R.id.board_title);
            dateTextView =itemview.findViewById(R.id.board_Date);
            postIdTextView =itemview.findViewById(R.id.board_Id);
            contentTextView =itemview.findViewById(R.id.board_Content);
        }
    }
}
