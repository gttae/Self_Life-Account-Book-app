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

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.ViewHolder> {
    private Context context;
    private List<Comment_List> comment_list;
    private OnItemClickListener listener;
    private OnItemLongClickListener longClickListener;


    public interface OnItemClickListener {
        void onItemClick(int position, String commentId);
    }

    public interface OnItemLongClickListener {
        void onItemLongClick(int position, String commentId);
    }

    public void setOnItemClickLister(CommentAdapter.OnItemClickListener listener) {this.listener = listener;}

    public void setOnItemLongClickListener(OnItemLongClickListener longClickListener) {
        this.longClickListener = longClickListener;
    }

    public CommentAdapter(Context context , List<Comment_List> comment_list) {
        this.context = context;
        this.comment_list = comment_list;
    }


    @NonNull
    @Override
    public CommentAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.comment_list_item, parent, false);
        return new CommentAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CommentAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        final Comment_List commentData = comment_list.get(position);
        holder.nameTextView.setText(commentData.getName());
        holder.dateTextView.setText(commentData.getDate());
        holder.contentTextView.setText(commentData.getContent());
        holder.commentIdTextView.setText(commentData.getCommentId());
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                if (longClickListener != null) {
                    // 롱클릭 이벤트 처리
                    String commentId = commentData.getCommentId();
                    longClickListener.onItemLongClick(position, commentId);
                }
                return true; // 롱클릭 이벤트를 소비함
            }
        });
    }

    @Override
    public int getItemCount() {
        return (comment_list != null ? comment_list.size() : 0);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView nameTextView;
        public TextView dateTextView;
        public TextView contentTextView;
        public TextView commentIdTextView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.CommentNameTv);
            dateTextView = itemView.findViewById(R.id.CommentDateTv);
            contentTextView = itemView.findViewById(R.id.CommentWordTv);
            commentIdTextView = itemView.findViewById(R.id.CommentIdTv);
        }
    }


}
