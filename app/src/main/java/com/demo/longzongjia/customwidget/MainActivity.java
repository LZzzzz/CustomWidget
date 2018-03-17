package com.demo.longzongjia.customwidget;

import android.app.Activity;
import android.app.ActivityManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;

import com.bilibili.magicasakura.utils.ThemeUtils;
import com.demo.longzongjia.customwidget.adaper.FragmentAdapter;
import com.demo.longzongjia.customwidget.commons.Titles;
import com.demo.longzongjia.customwidget.fragment.FourFragment;
import com.demo.longzongjia.customwidget.fragment.OneFragment;
import com.demo.longzongjia.customwidget.fragment.ThreeFragment;
import com.demo.longzongjia.customwidget.fragment.TwoFragment;
import com.demo.longzongjia.customwidget.utils.StatusBarUtil;
import com.demo.longzongjia.customwidget.utils.ThemeHelper;
import com.demo.longzongjia.customwidget.widget.CardPickerDialog;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements CardPickerDialog.ClickListener {

    @BindView(R.id.tab)
    TabLayout mTab;
    @BindView(R.id.view_pager)
    ViewPager viewPager;
    @BindView(R.id.nav_view)
    NavigationView navView;
    @BindView(R.id.drawer_layout)
    DrawerLayout drawer;
    @BindView(R.id.nav_header)
    LinearLayout navHeader;
    private GestureDetector detector;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(null);
        ButterKnife.bind(this);
        initView();
    }


    @Override
    public void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= 21) {
            StatusBarUtil.setColorForDrawerLayout(this, drawer,
                    ThemeUtils.getColorById(this, R.color.theme_color_primary),
                    90, 10.0f);
            mTab.setBackgroundColor(ThemeUtils.getColorById(this, R.color.theme_color_primary));
            navHeader.setBackgroundColor(ThemeUtils.getColorById(this, R.color.theme_color_primary));
            ActivityManager.TaskDescription description = new ActivityManager.TaskDescription(null, null,
                    ThemeUtils.getThemeAttrColor(this, android.R.attr.colorPrimary));
            setTaskDescription(description);
        }
    }

    private void initView() {
        toolbar.setElevation(10.0f);
        mTab.setElevation(10.0f);
        mTab.setTabTextColors(getResources().getColor(R.color.white_trans), getResources().getColor(R.color.white));
        mTab.setSelectedTabIndicatorColor(getResources().getColor(R.color.white));
        String[] mTitles = Titles.MainTitles;
        for (String mTitle : mTitles) {
            mTab.addTab(mTab.newTab().setText(mTitle));
        }
        setupViewPager(viewPager);
        mTab.setupWithViewPager(viewPager);
        detector = new GestureDetector(new GestureDetector.SimpleOnGestureListener() {
            @Override
            public boolean onDown(MotionEvent e) {
                if (myTouchListener != null) {
                    myTouchListener.onDown(e);
                }
                return super.onDown(e);
            }
        });
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

    }

    public void setupViewPager(ViewPager mViewPager) {
        FragmentAdapter adapter = new FragmentAdapter(this.getSupportFragmentManager());
        adapter.addFragment(OneFragment.getInstance(0), Titles.MainTitles[0]);
        adapter.addFragment(TwoFragment.getInstance(1), Titles.MainTitles[1]);
        adapter.addFragment(ThreeFragment.getInstance(2), Titles.MainTitles[2]);
        adapter.addFragment(FourFragment.getInstance(3), Titles.MainTitles[3]);
        mViewPager.setAdapter(adapter);
        mViewPager.setCurrentItem(0);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.change_theme) {
            CardPickerDialog dialog = new CardPickerDialog();
            dialog.setClickListener(this);
            dialog.show(getSupportFragmentManager(), CardPickerDialog.TAG);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onConfirm(int currentTheme) {
        if (ThemeHelper.getTheme(MainActivity.this) != currentTheme) {
            ThemeHelper.setTheme(MainActivity.this, currentTheme);
            ThemeUtils.refreshUI(MainActivity.this, new ThemeUtils.ExtraRefreshable() {
                        @Override
                        public void refreshGlobal(Activity activity) {
                            //for global setting, just do once
                            if (Build.VERSION.SDK_INT >= 21) {
                                final MainActivity context = MainActivity.this;
                                ActivityManager.TaskDescription taskDescription =
                                        new ActivityManager.TaskDescription(null, null,
                                                ThemeUtils.getThemeAttrColor(context, android.R.attr.colorPrimary));
                                setTaskDescription(taskDescription);
                                StatusBarUtil.setColorForDrawerLayout(context, drawer,
                                        ThemeUtils.getColorById(context, R.color.theme_color_primary),
                                        90, 10.0f);
                                mTab.setBackgroundColor(ThemeUtils.getColorById(context, R.color.theme_color_primary));
                                navHeader.setBackgroundColor(ThemeUtils.getColorById(context, R.color.theme_color_primary));
                            }
                        }

                        @Override
                        public void refreshSpecificView(View view) {
                            //TODO: will do this for each traversal
                        }
                    }
            );
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        detector.onTouchEvent(event);
        return super.onTouchEvent(event);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        detector.onTouchEvent(event);
        return super.dispatchTouchEvent(event);
    }

    MyTouchListener myTouchListener;


    // 回调接口
    public interface MyTouchListener {

        void onDown(MotionEvent e);
    }


    public void registerTouchListener(MyTouchListener listener) {
        myTouchListener = listener;
    }
}
