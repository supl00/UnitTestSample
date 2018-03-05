package com.gazua.ddeokrok.coinman.board;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.gazua.ddeokrok.coinman.R;
import com.gazua.ddeokrok.coinman.board.data.BoardData;
import com.gazua.ddeokrok.coinman.common.WebViewActivity;

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
        BoardData data = this.datas.get(position);
        holder.update(data);
        holder.setOnItemClickListener(v -> {
            Intent intent = new Intent(holder.itemView.getContext(), WebViewActivity.class);
            intent.putExtra("url", data.getLinkUrl());
            holder.itemView.getContext().startActivity(intent);
        });
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
        TextView time;

        BoardViewHoler(View itemView) {
            super(itemView);
            this.title = itemView.findViewById(R.id.title);
            this.userName = itemView.findViewById(R.id.writer);
            this.userImage = itemView.findViewById(R.id.icon_profile);
            this.count = itemView.findViewById(R.id.count);
            this.time = itemView.findViewById(R.id.time);
        }

        void update(@NonNull BoardData data) {
            this.title.setText(data.getTitle());
            this.userName.setText(data.getUserName());
            this.count.setText(data.getCount());
            this.time.setText(data.getDate());
        }

        void setOnItemClickListener(View.OnClickListener listener) {
            this.itemView.setOnClickListener(listener);
        }
    }
}
