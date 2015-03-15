package com.example.syrotynin.phonebookapp;

import android.support.v4.app.Fragment;

/**
 * Created by Syrotynin on 13.03.2015.
 */
public class ContactActivity extends SingleFragmentActivity {
    @Override
    protected Fragment createFragment() {
        return new ContactFragment();
    }
}
