package com.android.example.realestate.property.list;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.example.realestate.R;
import com.android.example.realestate.data.Property;
import com.squareup.picasso.Picasso;

import java.util.List;

class PropertyAdapter
        extends RecyclerView.Adapter<PropertyViewHolder>
{
    private final List<Property> mValues;
    private OnPropertyClickListener mListener;

    public PropertyAdapter(List<Property> items, OnPropertyClickListener listener)
    {
        mValues = items;
        mListener = listener;
    }

    @Override
    public PropertyViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.property_list_content, parent, false);

        PropertyViewHolder vh = new PropertyViewHolder(view);

        int height = parent.getMeasuredHeight() / 3;
        vh.mImageView.setMinimumHeight(height);

        return vh;
    }

    @Override
    public void onBindViewHolder(final PropertyViewHolder holder, int position)
    {
        holder.mItem = mValues.get(position);
        holder.mNameView.setText(mValues.get(position).subType);

        Context context = holder.itemView.getContext();

        Picasso.with(context)
                .load(holder.mItem.thumbnail)
                .tag(context)
                .into(holder.mImageView);

        holder.mView.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if (mListener != null)
                {
                    mListener.onClick(holder.mItem);
                }
            }
        });
    }

    @Override
    public int getItemCount()
    {
        return mValues.size();
    }

    public interface OnPropertyClickListener
    {
        void onClick(Property property);
    }
}