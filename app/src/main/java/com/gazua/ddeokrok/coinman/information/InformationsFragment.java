package com.gazua.ddeokrok.coinman.information;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gazua.ddeokrok.coinman.R;

/**
 * Created by kimju on 2018-02-01.
 */

public class InformationsFragment extends Fragment {
    private static final String TAG = "InformationsFragment";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_informations_main, null);
        return view;
    }
}
