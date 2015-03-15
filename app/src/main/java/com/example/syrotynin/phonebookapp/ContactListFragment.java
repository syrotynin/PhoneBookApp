package com.example.syrotynin.phonebookapp;

import android.annotation.TargetApi;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by Syrotynin on 11.03.2015.
 */
public class ContactListFragment extends ListFragment {
    private ArrayList<Contact> contacts;
    private DBHelper dbHelper;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().setTitle(R.string.contacts_title);

        Log.d("MENU","setHasOptionsMenu(true) in OnCreate");
        setHasOptionsMenu(true);

        // load data
        dbHelper = new DBHelper(getActivity().getApplicationContext());
        Cursor cursor = dbHelper.getAllContacts();
        contacts = dbHelper.cursor_to_contacts(cursor);
        ContactAdapter adapter = new ContactAdapter(contacts);

        setListAdapter(adapter);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = super.onCreateView(inflater, container, savedInstanceState);

        // регистрация контекстного меню
        ListView listView = (ListView)v.findViewById(android.R.id.list);
        registerForContextMenu(listView);

        return v;
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        Contact c = ((ContactAdapter)getListAdapter()).getItem(position);
        Toast.makeText(getActivity().getApplicationContext(),
                "Phone call to " + c.getName(), Toast.LENGTH_LONG).show();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_contact_list, menu);
        Log.d("MENU","inflate in OnCreateOptionsMenu");
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        getActivity().getMenuInflater().inflate(R.menu.context_menu_list, menu);
    }

    @TargetApi(11)
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_item_new_contact:
                Intent i = new Intent(getActivity(), ContactActivity.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }



    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info =
                (AdapterView.AdapterContextMenuInfo)item.getMenuInfo();
        ContactAdapter adapter = (ContactAdapter)getListAdapter();
        Contact contact = adapter.getItem(info.position);

        switch (item.getItemId()){
            case R.id.menu_item_delete_contact:
                dbHelper.deleteContact(contact.getId());
                adapter.remove(contact);
                adapter.notifyDataSetChanged();
                return true;
        }

        return super.onContextItemSelected(item);
    }

    private class ContactAdapter extends ArrayAdapter<Contact>{
        public ContactAdapter(ArrayList<Contact> contacts){
            super(getActivity(), android.R.layout.simple_list_item_1 , contacts);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = getActivity().getLayoutInflater()
                        .inflate(R.layout.list_item_contact, null);
            }

            Contact c = getItem(position);

            TextView nameTextView =
                    (TextView)convertView.findViewById(R.id.contact_list_item_nameTextView);
            nameTextView.setText(c.getName());

            TextView numberTextView =
                    (TextView)convertView.findViewById(R.id.contact_list_item_numberTextView);
            numberTextView.setText(c.getPhoneNumber());

            return convertView;
        }
    }
}
