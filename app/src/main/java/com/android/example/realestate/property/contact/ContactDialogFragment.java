package com.android.example.realestate.property.contact;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.design.widget.CoordinatorLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.example.realestate.R;

public class ContactDialogFragment extends BottomSheetDialogFragment
{
    public static final String ARG_ITEM_ID = "item_id";
    private int propertyId;
    private BottomSheetBehavior.BottomSheetCallback mBottomSheetBehaviorCallback =
            new BottomSheetBehavior.BottomSheetCallback() {

                @Override
                public void onStateChanged(@NonNull View bottomSheet, int newState)
                {
                    if (newState == BottomSheetBehavior.STATE_HIDDEN)
                    {
                        dismiss();
                    }
                }

                @Override
                public void onSlide(@NonNull View bottomSheet, float slideOffset)
                {
                }
            };

    private BottomSheetBehavior mBehavior;

    public ContactDialogFragment()
    {
    }

    public static ContactDialogFragment newInstance(int propertyId)
    {
        ContactDialogFragment fragment = new ContactDialogFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_ITEM_ID, propertyId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        if (getArguments() != null)
        {
            propertyId = getArguments().getInt(ARG_ITEM_ID);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_contact, container, false);

        return view;
    }

    @Override
    public void onStart()
    {
        super.onStart();
        mBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
    }

    @Override
    public void setupDialog(Dialog dialog, int style)
    {
        super.setupDialog(dialog, style);
        View contentView = View.inflate(getContext(), R.layout.fragment_contact, null);
        dialog.setContentView(contentView);

        CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams) ((View) contentView.getParent()).getLayoutParams();
        CoordinatorLayout.Behavior behavior = params.getBehavior();

        if( behavior != null && behavior instanceof BottomSheetBehavior )
        {
            mBehavior = (BottomSheetBehavior) behavior;
            mBehavior.setBottomSheetCallback(mBottomSheetBehaviorCallback);
        }
    }
}
