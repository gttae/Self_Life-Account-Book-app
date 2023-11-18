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

public class ReportCommentAdapter extends RecyclerView.Adapter<ReportCommentAdapter.ViewHolder> {

    private Context context;
    private List<ReportComment_List> RCList;
    private OnItemClickListener listener;

    public ReportCommentAdapter(Context context, List<ReportComment_List> rcList) {
        this.context = context;
        this.RCList = rcList;
    }

    public interface OnItemClickListener {
        void onItemClick(int position, String postId);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public ReportCommentAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.report_commentlist_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ReportCommentAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        final ReportComment_List RCData = RCList.get(position);
        holder.reportIdTextView.setText(RCData.getReportId());
        holder.nicknameTextView.setText(RCData.getNickcname());
        holder.commentIdTextView.setText(RCData.getCommentId());
        holder.boardIdTextView.setText(RCData.getPostId());
        holder.dateTextView.setText(RCData.getDate());
        holder.contentTextView.setText(RCData.getContent());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (listener != null) {
                    // 수정된 부분: postId를 가져와서 전달
                    String postId = RCData.getPostId();

                    listener.onItemClick(position, postId);
                }
            }
        });
    }

    @Override
    public int getItemCount()  {
        return (RCList != null ? RCList.size() : 0);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView reportIdTextView;
        public TextView nicknameTextView;
        public TextView commentIdTextView;
        public TextView boardIdTextView;
        public TextView dateTextView;
        public TextView contentTextView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            reportIdTextView = itemView.findViewById(R.id.board_ReportId);
            nicknameTextView = itemView.findViewById(R.id.board_Nickname);
            boardIdTextView = itemView.findViewById(R.id.board_boardId);
            commentIdTextView = itemView.findViewById(R.id.board_commentId);
            dateTextView = itemView.findViewById(R.id.board_Date);
            contentTextView = itemView.findViewById(R.id.board_Content);
        }
    }
}
