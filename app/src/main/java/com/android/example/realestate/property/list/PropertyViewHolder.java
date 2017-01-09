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
    public final TextView mTitleView;
    public final TextView mSubTitleView;
    public final TextView mPriceView;
    public final ImageView mImageView;
    public Property mItem;

    public PropertyViewHolder(View view)
    {
        super(view);
        mView = view;
        mTitleView = (TextView) view.findViewById(R.id.title);
        mImageView = (ImageView) view.findViewById(R.id.picture);
        mSubTitleView = (TextView) view.findViewById(R.id.subtitle);
        mPriceView = (TextView) view.findViewById(R.id.price);
    }

    @Override
    public String toString()
    {
        return super.toString() + " '" + mTitleView.getText() + "'";
    }
}