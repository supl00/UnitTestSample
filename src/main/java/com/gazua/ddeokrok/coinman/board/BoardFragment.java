package com.gazua.ddeokrok.coinman.board;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gazua.ddeokrok.coinman.R;

/**
 * Created by kimju on 2018-02-15.
 */

public class BoardFragment extends Fragment {
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_board_main, null);
        ViewPager pager = view.findViewById(R.id.board_pager);
        pager.setAdapter(new BoardPagerAdapter(getFragmentManager()));
        return view;
    }
}
