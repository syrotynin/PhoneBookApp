package com.example.syrotynin.phonebookapp;


import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by Syrotynin on 13.03.2015.
 */
public class ContactFragment extends Fragment {
    private DBHelper dbHelper;

    private EditText nameField;
    private EditText phoneField;
    private Button addButton;
    private Button clearButton;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        dbHelper = new DBHelper(getActivity().getApplicationContext());
    }

    @Override
    @TargetApi(11)
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_contact, parent, false);

        nameField = (EditText)v.findViewById(R.id.contactNameField);
        phoneField = (EditText)v.findViewById(R.id.phoneNumberField);

        addButton = (Button)v.findViewById(R.id.buttonAdd);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(nameField.getText().toString().isEmpty()) {
                    Toast.makeText(getActivity().getApplicationContext(),
                            "Name field shouldn't be empty!", Toast.LENGTH_LONG).show();
                }
                else if(phoneField.getText().toString().isEmpty()){
                    Toast.makeText(getActivity().getApplicationContext(),
                            "Phone field shouldn't be empty!", Toast.LENGTH_LONG).show();
                }
                else{
                    dbHelper.createNewContact(nameField.getText().toString(),
                            phoneField.getText().toString());
                    // to list activity
                    Intent i = new Intent(getActivity(), ContactListActivity.class);
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(i);
                }
            }
        });

        clearButton = (Button)v.findViewById(R.id.buttonClear);
        clearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nameField.setText("");
                phoneField.setText("");
            }
        });

        return v;
    }
}
