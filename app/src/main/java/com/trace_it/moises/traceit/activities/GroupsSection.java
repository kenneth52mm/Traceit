package com.trace_it.moises.traceit.activities;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.trace_it.moises.traceit.R;

/**
 * Created by Daniel on 03/07/2015.
 */
public class GroupsSection extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.lay_group_section, container, false);
        return rootView;
    }
}
