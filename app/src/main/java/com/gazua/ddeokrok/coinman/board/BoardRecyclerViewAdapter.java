package com.gazua.ddeokrok.coinman.board;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.gazua.ddeokrok.coinman.R;
import com.gazua.ddeokrok.coinman.board.data.BoardData;
import com.gazua.ddeokrok.coinman.board.url.builder.UrlBuilder;
import com.gazua.ddeokrok.coinman.board.url.builder.server.BaseServer;
import com.thefinestartist.finestwebview.FinestWebView;

import java.util.List;

import io.reactivex.Maybe;
import io.reactivex.Single;

/**
 * Created by kimju on 2018-02-22.
 */

public class BoardRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private  static final String TAG = "BoardRecyclerViewAdapter";

    private static final int TYPE_VIEW_HOLDER_CARD = 0;
    private static final int TYPE_VIEW_HOLDER_AD = 1;

    private List<BoardData> datas;

    public BoardRecyclerViewAdapter(List<BoardData> datas) {
        this.datas = datas;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return Single.just(viewType)
                .map(type -> createHolder(parent.getContext(), parent, type))
                .blockingGet();
    }

    @Override
    public int getItemViewType(int position) {
        return Single.just(position)
                .map(integer -> TYPE_VIEW_HOLDER_CARD)
                .blockingGet();
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        switch (holder.getItemViewType()) {
            case TYPE_VIEW_HOLDER_CARD: {
                final CardViewHolder h = (CardViewHolder) holder;
                BoardData data = this.datas.get(position);
                h.update(data);
                h.setOnItemClickListener(v -> new FinestWebView.Builder(holder.itemView.getContext()).show(data.getLinkUrl()));
                h.setOnClickFavoriteListener(v -> {

                });
            }
            break;
            case TYPE_VIEW_HOLDER_AD: {
            }
            break;
        }
    }

    @Override
    public int getItemCount() {
        return this.datas.size();
    }

    static class CardViewHolder extends RecyclerView.ViewHolder {
        TextView boardName;
        TextView title;
        TextView body;
        TextView userName;
        ImageView userImage;
        TextView viewCount;
        TextView commentCount;
        TextView time;
        View favorite;
        View userMargin;

        public CardViewHolder(View itemView) {
            super(itemView);
            this.boardName = itemView.findViewById(R.id.board_name);
            this.title = itemView.findViewById(R.id.board_title);
            this.body = itemView.findViewById(R.id.board_body);
            this.userName = itemView.findViewById(R.id.board_user_name);
            this.userImage = itemView.findViewById(R.id.board_user_image);
            this.viewCount = itemView.findViewById(R.id.text_view);
            this.commentCount = itemView.findViewById(R.id.text_comment);
            this.time = itemView.findViewById(R.id.board_text_time);
            this.favorite = itemView.findViewById(R.id.favorite);
            this.userMargin = itemView.findViewById(R.id.user_margin);
        }

        void update(@NonNull BoardData data) {
            this.boardName.setText(BaseServer.getBoardName(itemView.getContext(), data.getTarget()));
            this.title.setText(data.getTitle());
            this.body.setText(data.getBody());
            this.body.requestLayout();
            this.userName.setText(data.getUserName());
            this.userName.requestLayout();
            this.userImage.setImageDrawable(null);
            this.userImage.setVisibility(TextUtils.isEmpty(data.getUserImage()) ? View.GONE : View.VISIBLE);
            this.userMargin.setVisibility(!TextUtils.isEmpty(data.getUserName()) && !TextUtils.isEmpty(data.getUserImage()) ? View.VISIBLE : View.GONE);
            this.viewCount.setText(data.getViewCount());
            this.commentCount.setText(data.getCommentCount());
            this.time.setText(data.getDate());

            Maybe.fromCallable(data::getUserImage)
                    .filter(s -> !TextUtils.isEmpty(s))
                    .subscribe(imageUri -> Glide.with(itemView.getContext())
                            .load(imageUri)
                            .listener(new RequestListener<Drawable>() {
                                @Override
                                public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                                    return false;
                                }

                                @Override
                                public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                                    float ratio = resource.getIntrinsicWidth() / resource.getIntrinsicHeight();
                                    userImage.getLayoutParams().width = (int) (userImage.getMeasuredHeight() * ratio);
                                    userImage.requestLayout();
                                    return false;
                                }
                            })
                            .into(this.userImage));
            Single.just(data)
                    .filter(boardData -> TextUtils.isEmpty(boardData.getBody()))
                    .filter(boardData -> !TextUtils.isEmpty(boardData.getLinkUrl()))
                    .subscribe(boardData -> UrlBuilder.target(data.getTarget())
                            .loadBody(data.getLinkUrl())
                            .listener(boardData::setBody)
                            .into(body));
//
//            PageService pageService = ApiUtils.getRpJsoupService();
//            String content = pageService.selectContentGetSubList(url).execute().body().getContent();
//            return Maybe.just(content)
//                    .filter(Objects::nonNull)
//                    .map(Jsoup::parse)
//                    .map(document -> document.select(server.bodyContentsTag()))
//                    .map(elements -> elements.select(server.bodyContentsTextTag()).text())
//                    .blockingGet("");
//            Maybe.just(data)
//                    .filter(boardData -> Objects.nonNull(boardData.getLinkUrl()))
//                    .filter(boardData -> Objects.isNull(boardData.getBody()))
//                    .map(boardData -> new Pair<BoardData, >())
        }

        void setOnItemClickListener(View.OnClickListener listener) {
            this.itemView.setOnClickListener(listener);
        }

        void setOnClickFavoriteListener(View.OnClickListener listener) {
            this.favorite.setOnClickListener(listener);
        }
    }

    private static final RecyclerView.ViewHolder createHolder(@NonNull Context context, ViewGroup parent, int type) {
        RecyclerView.ViewHolder holder;
        switch (type) {
            case TYPE_VIEW_HOLDER_CARD:
                holder = new CardViewHolder(LayoutInflater.from(context).inflate(R.layout.board_item_card, parent, false));
                break;
            case TYPE_VIEW_HOLDER_AD:
                holder = new CardViewHolder(LayoutInflater.from(context).inflate(R.layout.board_item_card, parent, false));
                break;
            default:
                holder = new CardViewHolder(LayoutInflater.from(context).inflate(R.layout.board_item_card, parent, false));
        }
        return holder;
    }
}
