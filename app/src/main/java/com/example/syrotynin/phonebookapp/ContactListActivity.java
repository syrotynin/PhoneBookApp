package com.example.syrotynin.phonebookapp;

import android.provider.Settings;
import android.support.v4.app.Fragment;
import android.util.Log;

/**
 * Created by Syrotynin on 11.03.2015.
 */
public class ContactListActivity extends SingleFragmentActivity {
    @Override
    protected Fragment createFragment() {
        return new ContactListFragment();
    }
}
