package com.android.example.realestate.property.list;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.android.example.realestate.data.Property;
import com.android.example.realestate.data.PropertyService;
import com.android.example.realestate.property.details.PropertyDetailActivity;
import com.android.example.realestate.property.details.PropertyDetailFragment;
import com.android.example.realestate.ui.GridSpacingItemDecoration;
import com.android.example.realestate.R;

public class PropertyListActivity extends AppCompatActivity
        implements PropertyAdapter.OnPropertyClickListener, PropertyService.OnDataSetChangedListener
{
    private boolean mTwoPane;
    private RecyclerView mRecyclerView;
    private PropertyAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_property_list);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(getTitle());

        View recyclerView = findViewById(R.id.character_list);
        assert recyclerView != null;
        mRecyclerView = (RecyclerView) recyclerView;

        if (findViewById(R.id.character_detail_container) != null)
        {
            mTwoPane = true;
        }

        setupRecyclerView();

        PropertyService.getInstance().setOnDataSetChangedListener(this);
        PropertyService.getInstance().loadAll();
    }

    private void setupRecyclerView()
    {
        int columns = 2;
        int spacingInPixels = getResources().getDimensionPixelSize(R.dimen.grid_spacing);
        mRecyclerView.addItemDecoration(new GridSpacingItemDecoration(columns, spacingInPixels, true));

        mAdapter = new PropertyAdapter(PropertyService.getInstance().getProperties(), this);
        mRecyclerView.setAdapter(mAdapter);

        GridLayoutManager layoutManager = new GridLayoutManager(this, columns);
        mRecyclerView.setLayoutManager(layoutManager);
    }

    @Override
    protected void onDestroy()
    {
        PropertyService.getInstance().setOnDataSetChangedListener(this);
        super.onDestroy();
    }

    @Override
    public void OnDataSetChanged()
    {
        runOnUiThread(new Runnable()
        {
            @Override
            public void run()
            {
                mAdapter.notifyDataSetChanged();
            }
        });
    }

    public void onClick(Property property)
    {
        if (mTwoPane)
        {
            PropertyDetailFragment fragment = PropertyDetailFragment.newInstance(property.id);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.property_detail_container, fragment)
                    .commit();
        }
        else
        {
            Intent intent = new Intent(this, PropertyDetailActivity.class);
            intent.putExtra(PropertyDetailFragment.ARG_ITEM_ID, property.id);
            startActivity(intent);
        }
    }
}
