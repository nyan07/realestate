package com.android.example.realestate.property.contact;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetBehavior;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.example.realestate.R;
import com.android.example.realestate.data.PropertyService;
import com.android.example.realestate.property.details.PropertyDetailsFragment;

public class ContactActivity extends AppCompatActivity implements
        PropertyService.OnSendUserDataListener
{
    public static final String ARG_ITEM_ID = "item_id";
    private int propertyId;

    private TextView name;
    private TextView email;
    private TextView phone;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final ActionBar actionBar = getSupportActionBar();
        if (actionBar != null)
        {
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.ic_close_white_24dp);
        }

        name = (TextView) findViewById(R.id.name);
        email = (TextView) findViewById(R.id.email);
        phone = (TextView) findViewById(R.id.phone);

        Button button = (Button) findViewById(R.id.send_button);
        button.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                sendMessage();
            }
        });

        phone.setOnEditorActionListener(new TextView.OnEditorActionListener()
        {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event)
            {
                if (actionId == EditorInfo.IME_ACTION_SEND)
                {
                    sendMessage();
                    return true;
                }
                return false;
            }
        });

        if (savedInstanceState == null)
        {
            propertyId = getIntent().getIntExtra(PropertyDetailsFragment.ARG_ITEM_ID, 0);
        }
    }

    public void onUserDataSent()
    {
        Toast.makeText(this, R.string.contact_success, Toast.LENGTH_SHORT).show();
    }

    public void onFailure(String message)
    {
        Toast.makeText(this, R.string.contact_error, Toast.LENGTH_SHORT).show();
    }

    private void sendMessage()
    {
        PropertyService.getInstance().sendUserInfo(propertyId,
                name.getText().toString(),
                email.getText().toString(),
                phone.getText().toString());
    }

    public void showSoftKeyboard(View view)
    {
        if (view.requestFocus())
        {
            InputMethodManager imm = (InputMethodManager)
                    getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home)
        {
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }



    @Override
    public void onStart()
    {
        super.onStart();
        PropertyService.getInstance().setOnSendUserDataListener(this);
    }

    @Override
    public void onStop()
    {
        super.onStop();
        PropertyService.getInstance().setOnSendUserDataListener(null);
    }
}
