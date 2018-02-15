package com.gazua.ddeokrok.coinman.board;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;


import java.util.ArrayList;
import java.util.List;

/**
 * Created by kimju on 2018-02-01.
 */

public class BoardPagerAdapter extends FragmentPagerAdapter {
    private static final String TAG = "BoardPagerAdapter";

//    static final Class<?> FRAGMENT_CHART = ChartFragment.class;
    static final Class<?> BOARD_CLIEN = ClienFragment.class;

    private List<Class<?>> boards = new ArrayList<>();

    public BoardPagerAdapter(FragmentManager fm) {
        super(fm);
//        boards.add(FRAGMENT_CHART);
        boards.add(BOARD_CLIEN);
    }

    @Override
    public Fragment getItem(int position) {
        Fragment f = null;
        try {
            f = (Fragment) boards.get(position).newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return f;
    }

    @Override
    public int getCount() {
        return boards.size();
    }
}
