package com.astapley.buster;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.astapley.buster.R.id.count;

public class ParallaxFragment extends Fragment {

    private static final String KEY_COUNT = "COUNT";

    @BindView(count) TextView countTextView;
    @BindView(R.id.image_view) ImageView imageView;

    public static ParallaxFragment newInstance(String count) {
        ParallaxFragment fragment = new ParallaxFragment();
        Bundle args = new Bundle();
        args.putString(KEY_COUNT, count);
        fragment.setArguments(args);
        return fragment;
    }

    @Override public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_parallax, container, false);
        ButterKnife.bind(this, view);
        updateUi();
        return view;
    }

    private void updateUi() {
        countTextView.setText(getCount());
        Picasso.with(getContext())
                .load("http://content.internetvideoarchive.com/content/photos/10283/94740_017.jpg")
                .into(imageView);
    }

    private String getCount() {
        return getArguments().getString(KEY_COUNT);
    }
}
