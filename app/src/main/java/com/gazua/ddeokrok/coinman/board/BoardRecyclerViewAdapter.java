package com.gazua.ddeokrok.coinman.board;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.gazua.ddeokrok.coinman.R;
import com.gazua.ddeokrok.coinman.board.data.BoardData;

import java.util.List;

/**
 * Created by kimju on 2018-02-22.
 */

public class BoardRecyclerViewAdapter extends RecyclerView.Adapter<BoardRecyclerViewAdapter.BoardViewHoler> {
    private List<BoardData> datas;

    public BoardRecyclerViewAdapter(List<BoardData> datas) {
        this.datas = datas;
    }

    @Override
    public BoardViewHoler onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.board_recycler_view_item, parent, false);
        BoardViewHoler holder = new BoardViewHoler(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(BoardViewHoler holder, int position) {
        holder.update(this.datas.get(position));
    }

    @Override
    public int getItemCount() {
        return this.datas.size();
    }

    static class BoardViewHoler extends RecyclerView.ViewHolder {
        TextView title;
        TextView userName;
        ImageView userImage;
        TextView count;
        TextView date;

        BoardViewHoler(View itemView) {
            super(itemView);
            this.title = itemView.findViewById(R.id.title);
            this.userName = itemView.findViewById(R.id.userName);
            this.userImage = itemView.findViewById(R.id.userImage);
            this.count = itemView.findViewById(R.id.count);
            this.date = itemView.findViewById(R.id.date);
        }

        void update(@NonNull BoardData data) {
            this.title.setText(data.getTitle());
            this.userName.setText(data.getUserName());
            this.count.setText(data.getCount());
            this.date.setText(data.getDate());
        }
    }
}
