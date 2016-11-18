package com.astapley.buster;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ParallaxActivity extends AppCompatActivity {

    @BindView(R.id.view_pager) ViewPager viewPager;

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parallax);

        ButterKnife.bind(this);

        ParallaxAdapter parallaxAdapter = new ParallaxAdapter(getSupportFragmentManager());
        viewPager.setAdapter(parallaxAdapter);
        viewPager.setPageTransformer(true, new ZoomOutPageTransformer());
    }

    private class ParallaxAdapter extends FragmentStatePagerAdapter {

        ParallaxAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override public Fragment getItem(int position) {
            return ParallaxFragment.newInstance(Integer.toString(position + 1));
        }

        @Override public int getCount() {
            return 10;
        }

        @Override public CharSequence getPageTitle(int position) {
            return "Page " + (position + 1);
        }
    }

    public class ZoomOutPageTransformer implements ViewPager.PageTransformer {
        private static final float MIN_SCALE = 0.85f;
        private static final float MIN_ALPHA = 0.5f;

        public void transformPage(View view, float position) {
            int pageWidth = view.getWidth();
            int pageHeight = view.getHeight();

            if (position < -1) { // [-Infinity,-1)
                // This page is way off-screen to the left.
                view.setAlpha(0);

            }
            else if (position <= 1) { // [-1,1]
                // Modify the default slide transition to shrink the page as well
                float scaleFactor = Math.max(MIN_SCALE, 1 - Math.abs(position));
                float vertMargin = pageHeight * (1 - scaleFactor) / 2;
                float horzMargin = pageWidth * (1 - scaleFactor) / 2;
//                if (position < 0) {
//                    view.setTranslationX(horzMargin - vertMargin / 2);
//                }
//                else {
//                    view.setTranslationX(-horzMargin + vertMargin / 2);
//                }

                // Scale the page down (between MIN_SCALE and 1)
//                view.setScaleX(scaleFactor);
//                view.setScaleY(scaleFactor);

                // Fade the page relative to its size.
//                view.setAlpha(MIN_ALPHA +
//                        (scaleFactor - MIN_SCALE) /
//                                (1 - MIN_SCALE) * (1 - MIN_ALPHA));

                TextView count = (TextView) view.findViewById(R.id.count);
                count.setTranslationX(position * (pageWidth * 1.2f));
                count.setRotationY(360 * position);

                TextView rotate = (TextView) view.findViewById(R.id.rotate);
                rotate.setTranslationX(position * (pageWidth * 1.75f));
//                rotate.setRotationX(360 * position);
                rotate.setRotationY(360 * position);
//                rotate.setRotation(360 * position);

                ImageView android = (ImageView) view.findViewById(R.id.android);
                android.setRotation(360 * position);
                android.setScaleX(1 + Math.abs(position * 5));
                android.setScaleY(1 + Math.abs(position * 5));
                android.setTranslationX(position * (pageWidth * 3));
                android.setTranslationY(position * (pageWidth * 3));
//                android.setPivotX(0);
//                android.setPivotY(0);

                ImageView imageView = (ImageView) view.findViewById(R.id.image_view);
                imageView.setTranslationX(position * (pageWidth * -1));
//                imageView.setScaleX(1 + Math.abs(position * 1));
//                imageView.setScaleY(1 + Math.abs(position * 1));
            }
            else { // (1,+Infinity]
                // This page is way off-screen to the right.
                view.setAlpha(0);
            }
        }
    }

    public class DepthPageTransformer implements ViewPager.PageTransformer {
        private static final float MIN_SCALE = 0.75f;

        public void transformPage(View view, float position) {
            int pageWidth = view.getWidth();

            if (position < -1) { // [-Infinity,-1)
                // This page is way off-screen to the left.
                view.setAlpha(0);

            }
            else if (position <= 0) { // [-1,0]
                // Use the default slide transition when moving to the left page
                view.setAlpha(1);
                view.setTranslationX(0);
                view.setScaleX(1);
                view.setScaleY(1);

            }
            else if (position <= 1) { // (0,1]
                // Fade the page out.
                view.setAlpha(1 - position);

                // Counteract the default slide transition
                view.setTranslationX(pageWidth * -position);

                // Scale the page down (between MIN_SCALE and 1)
                float scaleFactor = MIN_SCALE
                        + (1 - MIN_SCALE) * (1 - Math.abs(position));
                view.setScaleX(scaleFactor);
                view.setScaleY(scaleFactor);

            }
            else { // (1,+Infinity]
                // This page is way off-screen to the right.
                view.setAlpha(0);
            }
        }
    }
}
