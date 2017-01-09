package com.android.example.realestate.property.list;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.example.realestate.data.Property;
import com.android.example.realestate.data.PropertyService;
import com.android.example.realestate.property.details.PropertyDetailsActivity;
import com.android.example.realestate.property.details.PropertyDetailsFragment;
import com.android.example.realestate.ui.GridSpacingItemDecoration;
import com.android.example.realestate.R;

public class PropertyListActivity extends AppCompatActivity
        implements PropertyAdapter.OnPropertyClickListener, PropertyService.OnChangeDataSetListener
{
    private boolean mTwoPane;
    private RecyclerView mRecyclerView;
    private PropertyAdapter mAdapter;
    private View mEmptyView;
    private ProgressBar mProgressBar;

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
        mEmptyView = findViewById(R.id.empty_list);
        mProgressBar = (ProgressBar) findViewById(R.id.progressbar);

        if (findViewById(R.id.character_detail_container) != null)
        {
            mTwoPane = true;
        }

        setupRecyclerView();

        PropertyService.getInstance().setOnChangeDataSetListener(this);
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
        PropertyService.getInstance().setOnChangeDataSetListener(null);
        super.onDestroy();
    }

    @Override
    public void onDataSetChanged()
    {
        mProgressBar.setVisibility(View.GONE);

        if (PropertyService.getInstance().getProperties().size() > 0)
        {
            mEmptyView.setVisibility(View.GONE);
        }
        mAdapter.notifyDataSetChanged();


    }

    @Override
    public void onFailure(String message)
    {
        mProgressBar.setVisibility(View.GONE);
        mEmptyView.setVisibility(View.VISIBLE);

        Toast.makeText(this, "Não foi possível carregar os imóveis.", Toast.LENGTH_LONG).show();
    }

    public void onClick(Property property)
    {
        if (mTwoPane)
        {
            PropertyDetailsFragment fragment = PropertyDetailsFragment.newInstance(property.id);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.property_detail_container, fragment)
                    .commit();
        }
        else
        {
            Intent intent = new Intent(this, PropertyDetailsActivity.class);
            intent.putExtra(PropertyDetailsFragment.ARG_ITEM_ID, property.id);
            startActivity(intent);
        }
    }
}
