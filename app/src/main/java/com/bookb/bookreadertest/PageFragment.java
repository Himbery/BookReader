package com.bookb.bookreadertest;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

public class PageFragment extends Fragment {

    private static final String ARG_TAB_POSITON = "tab_position";

    private OnFragmentReadyListener bookListener;

    public static PageFragment newInstance(int tabPosition) {
        PageFragment fragment = new PageFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_TAB_POSITON, tabPosition);
        fragment.setArguments(args);
        return fragment;
    }

    public PageFragment() {
    }

    public interface OnFragmentReadyListener {
        View onFragmentReady(int position);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            bookListener = (OnFragmentReadyListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()+ "must implements Listener");
        }

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        bookListener = null;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_page, container, false);
        RelativeLayout mainLayout = (RelativeLayout) rootView.findViewById(R.id.fragment_main_layout);
//        assert getArguments() != null;
        View view = bookListener.onFragmentReady(getArguments().getInt(ARG_TAB_POSITON));

        if (view != null) {
            mainLayout.addView(view);
        }

        return rootView;
    }
}