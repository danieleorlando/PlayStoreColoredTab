package me.danieleorlando.playstorecoloredtab;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ArgbEvaluator;
import android.databinding.DataBindingUtil;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewGroup;
import java.util.ArrayList;
import java.util.List;

import me.danieleorlando.playstorecoloredtab.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        setSupportActionBar(binding.toolbar);
        setupViewPager(binding.viewPager);
        binding.tabLayout.setupWithViewPager(binding.viewPager);

        initToolbarDimension();

        View tab1 = ((ViewGroup) binding.tabLayout.getChildAt(0)).getChildAt(0);
        tab1.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    reveal(0, event.getRawX(), event.getRawY());
                }
                return false;
            }
        });

        View tab2 = ((ViewGroup) binding.tabLayout.getChildAt(0)).getChildAt(1);
        tab2.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    reveal(1, event.getRawX(), event.getRawY());
                }
                return false;
            }
        });

        binding.viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {
                binding.backgroundView.setBackgroundColor(getBackgroundColor(i, v));
            }

            @Override
            public void onPageSelected(int i) {

            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(TabFragment.newInstance("Tab 1"), "Tab 1");
        adapter.addFragment(TabFragment.newInstance("Tab 2"), "Tab 2");
        viewPager.setAdapter(adapter);
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }

    private void initToolbarDimension() {
        ConstraintLayout.LayoutParams params = (ConstraintLayout.LayoutParams)binding.toolbar.getLayoutParams();
        params.setMargins(0, getStatusBarHeight(), 0, 0);
        binding.toolbar.setLayoutParams(params);
    }

    public int getStatusBarHeight() {
        int result = 0;
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }


    public void reveal(final int position, float x, float y) {
        binding.revealView.setVisibility(View.VISIBLE);
        int revealViewX = binding.revealView.getWidth();
        int revealViewY = binding.revealView.getHeight();

        float radius = Math.max(revealViewX, revealViewY) * 1.2f;
        Animator reveal = ViewAnimationUtils
                .createCircularReveal(binding.revealView, (int) x, (int) y, 0f, radius);

        reveal.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animator) {
                if (position == 0) {
                    binding.backgroundView.setBackgroundColor(ResourcesCompat.getColor(getResources(), R.color.colorPrimary, getTheme()));
                } else {
                    binding.backgroundView.setBackgroundColor(ResourcesCompat.getColor(getResources(), R.color.purple_500, getTheme()));
                }
                binding.revealView.setVisibility(View.INVISIBLE);
            }
        });

        if (position == 0) {
            binding.revealView.setBackgroundColor(ResourcesCompat.getColor(getResources(), R.color.colorPrimary, getTheme()));
        } else {
            binding.revealView.setBackgroundColor(ResourcesCompat.getColor(getResources(), R.color.purple_500, getTheme()));
        }
        reveal.start();
    }

    private int getBackgroundColor(int position, float positionOffset) {
        int startColor;
        int endColor;

        if (position==0) {
            startColor = ResourcesCompat.getColor(getResources(), R.color.colorPrimary, getTheme());
            endColor = ResourcesCompat.getColor(getResources(), R.color.purple_500, getTheme());
        } else {
            startColor = ResourcesCompat.getColor(getResources(), R.color.purple_500, getTheme());
            endColor = ResourcesCompat.getColor(getResources(), R.color.colorPrimary, getTheme());
        }
        ArgbEvaluator argbEvaluator = new ArgbEvaluator();
        int color = (int) argbEvaluator.evaluate(positionOffset, startColor, endColor);
        return color;
    }

}

