package com.android.example.realestate.property.details;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import com.android.example.realestate.R;
import com.android.example.realestate.property.contact.ContactDialogFragment;
import com.android.example.realestate.property.list.PropertyListActivity;

public class PropertyDetailsActivity extends AppCompatActivity
{
    private int id;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_property_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null)
        {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        if (savedInstanceState == null)
        {
            id = getIntent().getIntExtra(PropertyDetailsFragment.ARG_ITEM_ID, 0);

            if (id > 0)
            {
                PropertyDetailsFragment fragment = PropertyDetailsFragment.newInstance(id);
                getSupportFragmentManager().beginTransaction()
                        .add(R.id.character_detail_container, fragment)
                        .commit();
            }
        }

        FloatingActionButton fab = (FloatingActionButton)findViewById(R.id.fab);
        if (fab != null)
        {
            fab.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    ContactDialogFragment fragment =
                            ContactDialogFragment.newInstance(id);
                    fragment.show(getSupportFragmentManager(),fragment.getTag());
                }
            });
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        int id = item.getItemId();
        if (id == android.R.id.home)
        {
            Intent intent = new Intent(this, PropertyListActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            NavUtils.navigateUpTo(this, intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
