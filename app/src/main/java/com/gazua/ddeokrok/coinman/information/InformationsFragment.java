package com.gazua.ddeokrok.coinman.information;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ListView;
import android.widget.Toast;

import com.gazua.ddeokrok.coinman.R;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterAuthException;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.models.Tweet;
import com.twitter.sdk.android.tweetui.SearchTimeline;
import com.twitter.sdk.android.tweetui.TimelineResult;
import com.twitter.sdk.android.tweetui.TweetTimelineListAdapter;

/**
 * Created by kimju on 2018-02-01.
 */

public class InformationsFragment extends ListFragment {
    private static final String TAG = "InformationsFragment";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_informations_main, container, false);
        final SwipeRefreshLayout swipeLayout = v.findViewById(R.id.swipe_layout);
        final View emptyView = v.findViewById(android.R.id.empty);
        final ListView listView = v.findViewById(android.R.id.list);
        listView.setEmptyView(emptyView);

        // launch the app login activity when a guest user tries to favorite a Tweet
        final Callback<Tweet> actionCallback = new Callback<Tweet>() {
            @Override
            public void success(Result<Tweet> result) {
                // Intentionally blank
            }
            @Override
            public void failure(TwitterException exception) {

            }
        };

        final SearchTimeline timeline = new SearchTimeline.Builder().query("from:bitCoin OR from:OKEx_ OR from:QtumOfficial OR from:monaco_card OR from:NEO_Blockchain OR from:red_pulse_china OR from:Tronfoundation OR from:LiskHQ OR from:Cryptocoins4all").build();
        final TweetTimelineListAdapter adapter = new TweetTimelineListAdapter.Builder(getContext())
                .setTimeline(timeline)
                .setViewStyle(R.style.tw__TweetLightWithActionsStyle)
                .setOnActionCallback(actionCallback)
                .build();
        listView.setAdapter(adapter);

        swipeLayout.setColorSchemeResources(R.color.twitter_blue, R.color.twitter_dark);

        // set custom scroll listener to enable swipe refresh layout only when at list top
        listView.setOnScrollListener(new AbsListView.OnScrollListener() {
            boolean enableRefresh = false;
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {}
            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount,
                                 int totalItemCount) {
                if (listView != null && listView.getChildCount() > 0) {
                    // check that the first item is visible and that its top matches the parent
                    enableRefresh = listView.getFirstVisiblePosition() == 0 &&
                            listView.getChildAt(0).getTop() >= 0;
                } else {
                    enableRefresh = false;
                }
                swipeLayout.setEnabled(enableRefresh);
            }
        });

        // specify action to take on swipe refresh
        swipeLayout.setOnRefreshListener(() -> {
            swipeLayout.setRefreshing(true);
            adapter.refresh(new Callback<TimelineResult<Tweet>>() {
                @Override
                public void success(Result<TimelineResult<Tweet>> result) {
                    swipeLayout.setRefreshing(false);
                }

                @Override
                public void failure(TwitterException exception) {
                    swipeLayout.setRefreshing(false);
                }
            });
        });

        return v;
    }
}
