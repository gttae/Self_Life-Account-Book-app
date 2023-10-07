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

public class BoardAdapter extends RecyclerView.Adapter<BoardAdapter.ViewHolder> {

    private Context context;
    private List<Board_List> boardList;
    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(int position, String postId);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public BoardAdapter(Context context, List<Board_List> boardList) {
        this.context = context;
        this.boardList = boardList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") final int position) {
        final Board_List boardData = boardList.get(position);
        holder.categoryTextView.setText(boardData.getCategory());
        holder.titleTextView.setText(boardData.getTitle());
        holder.postIdTextView.setText(boardData.getPostId());
        int sequenceNumber = position + 1;
        holder.numberTextView.setText(String.valueOf(sequenceNumber));

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (listener != null) {
                    // 수정된 부분: postId를 가져와서 전달
                    String postId = boardData.getPostId();

                    listener.onItemClick(position, postId);
                }
            }
        });
    }


    @Override
    public int getItemCount() {
        return Math.min(boardList.size(), 10);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView categoryTextView;
        public TextView titleTextView;
        public TextView numberTextView;
        public TextView postIdTextView;

        public ViewHolder(View itemView) {
            super(itemView);
            categoryTextView = itemView.findViewById(R.id.board_category);
            titleTextView = itemView.findViewById(R.id.board_title);
            numberTextView = itemView.findViewById(R.id.board_number);
            postIdTextView = itemView.findViewById(R.id.board_Id);
        }
    }
}
