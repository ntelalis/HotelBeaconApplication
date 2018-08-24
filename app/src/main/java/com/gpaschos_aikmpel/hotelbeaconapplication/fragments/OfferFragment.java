package com.gpaschos_aikmpel.hotelbeaconapplication.fragments;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gpaschos_aikmpel.hotelbeaconapplication.R;

public class OfferFragment extends Fragment {

    public static final String TAG = "OfferFragment";
    private ViewPager viewPager;
    public static final int OFFER_GENERAL = 0;
    public static final int OFFER_EXCLUSIVE = 1;

    public ViewPager getViewpager() {
        return viewPager;
    }

    public OfferFragment() {
        // Required empty public constructor
    }

    public static OfferFragment newInstance() {
        OfferFragment fragment = new OfferFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_offer, container, false);
        viewPager = v.findViewById(R.id.vpOffer);
        TabLayout tabLayout = v.findViewById(R.id.tlOffer);
        tabLayout.setupWithViewPager(viewPager);
        viewPager.setAdapter(new OfferPagerAdapter(getFragmentManager()));
        return v;
    }


    private class OfferPagerAdapter extends FragmentStatePagerAdapter {

        private final int NUM_PAGES = 2;

        OfferPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case OFFER_GENERAL:
                    return OfferGeneralFragment.newInstance();
                case OFFER_EXCLUSIVE:
                    return OfferExclusiveFragment.newInstance();
                default:
                    return null;
            }
        }

        @Override
        public int getCount() {
            return NUM_PAGES;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case OFFER_GENERAL:
                    return "General";
                case OFFER_EXCLUSIVE:
                    return "Exclusive";
                default:
                    return "";
            }
        }
    }
}
