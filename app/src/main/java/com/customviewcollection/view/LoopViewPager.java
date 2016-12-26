package com.customviewcollection.view;

import android.content.Context;
import android.database.DataSetObserver;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Magina on 12/7/16.
 * 类功能介绍:  无限循环的VP
 * 由原本的ABC变为CABCA，当移动到边界时，将其转至中间项
 */

public class LoopViewPager extends ViewPager {

    private PagerAdapter mRealAdapter;
    private WrapPagerAdapter mWrapAdapter;

    public LoopViewPager(Context context) {
        super(context);
    }

    public LoopViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void setAdapter(PagerAdapter adapter) {
        mWrapAdapter = wrapAdapter(adapter);
        super.setAdapter(mWrapAdapter);
    }

    @Override
    public PagerAdapter getAdapter() {
        return mWrapAdapter;
    }

    @Override
    public int getCurrentItem() {
        return super.getCurrentItem();
    }


    @Override
    public void setCurrentItem(int item) {
        super.setCurrentItem(item);
    }

    @Override
    public void setCurrentItem(int item, boolean smoothScroll) {
        super.setCurrentItem(item, smoothScroll);
    }

    private WrapPagerAdapter wrapAdapter(PagerAdapter adapter) {
        mRealAdapter = adapter;
        return new WrapPagerAdapter();
    }

    private class WrapPagerAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return mRealAdapter.getCount() + 2;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return mRealAdapter.isViewFromObject(view, object);
        }

        @Override
        public void startUpdate(ViewGroup container) {
            mRealAdapter.startUpdate(container);
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            return mRealAdapter.instantiateItem(container, position);
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            mRealAdapter.destroyItem(container, position, object);
        }

        @Override
        public void setPrimaryItem(ViewGroup container, int position, Object object) {
            mRealAdapter.setPrimaryItem(container, position, object);
        }

        @Override
        public void finishUpdate(ViewGroup container) {
            mRealAdapter.finishUpdate(container);
        }

        @Override
        public Parcelable saveState() {
            return mRealAdapter.saveState();
        }

        @Override
        public void restoreState(Parcelable state, ClassLoader loader) {
            mRealAdapter.restoreState(state, loader);
        }

        @Override
        public int getItemPosition(Object object) {
            return super.getItemPosition(object);
        }

        @Override
        public void notifyDataSetChanged() {
            mRealAdapter.notifyDataSetChanged();
        }

        @Override
        public void registerDataSetObserver(DataSetObserver observer) {
            mRealAdapter.registerDataSetObserver(observer);
        }

        @Override
        public void unregisterDataSetObserver(DataSetObserver observer) {
            mRealAdapter.unregisterDataSetObserver(observer);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mRealAdapter.getPageTitle(position);
        }

        @Override
        public float getPageWidth(int position) {
            return mRealAdapter.getPageWidth(position);
        }
    }

}
