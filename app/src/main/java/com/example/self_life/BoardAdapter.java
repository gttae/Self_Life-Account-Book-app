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
    private OnItemLongClickListener longClickListener;

    public interface OnItemClickListener {
        void onItemClick(int position, String postId);

    }

    public interface OnItemLongClickListener {
        void onItemLongClick(int position, String postId);
    }


    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public void setOnItemLongClickListener(OnItemLongClickListener longClickListener) {
        this.longClickListener = (OnItemLongClickListener) longClickListener;
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

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                if (longClickListener != null) {
                    // postId를 가져와서 전달
                    String postId = boardData.getPostId();
                    longClickListener.onItemLongClick(position, postId);
                }
                return true; // 롱클릭 이벤트를 소비함
            }
        });
    }


    @Override
    public int getItemCount(){
        return (boardList != null ? boardList.size() : 0);
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
