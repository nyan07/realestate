package com.android.example.realestate.property.list;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.example.realestate.data.Property;

import com.android.example.realestate.R;

class PropertyViewHolder extends RecyclerView.ViewHolder
{
    public final View mView;
    public final TextView mNameView;
    public final ImageView mImageView;
    public Property mItem;

    public PropertyViewHolder(View view)
    {
        super(view);
        mView = view;
        mNameView = (TextView) view.findViewById(R.id.name);
        mImageView = (ImageView) view.findViewById(R.id.picture);
    }

    @Override
    public String toString()
    {
        return super.toString() + " '" + mNameView.getText() + "'";
    }
}