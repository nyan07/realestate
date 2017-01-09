package com.android.example.realestate.property.details;

import android.app.Activity;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.example.realestate.R;
import com.android.example.realestate.data.Property;
import com.android.example.realestate.data.PropertyService;
import com.android.example.realestate.property.contact.ContactDialogFragment;
import com.android.example.realestate.utils.FormatterUtil;
import com.squareup.picasso.Picasso;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Currency;
import java.util.Locale;

public class PropertyDetailFragment extends Fragment implements PropertyService.OnPropertyUpdateListener
{
    public static final String ARG_ITEM_ID = "item_id";
    private Property mItem;

    private TextView title;
    private TextView subtitle;
    private TextView price;
    private ViewGroup generalFeaturesItems;

    private ViewGroup notesContainer;
    private TextView notes;

    private ViewGroup condoFeeContainer;
    private TextView condoFee;

    private ViewGroup condoFeaturesContainer;
    private ViewGroup condoFeaturesItems;

    private ViewGroup clientContainer;
    private TextView client;

    private ViewGroup extraInfoContainer;
    private TextView extraInfo;

    private TextView updatedOn;

    public PropertyDetailFragment()
    {
    }

    public static PropertyDetailFragment newInstance(int propertyId)
    {
        PropertyDetailFragment fragment = new PropertyDetailFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_ITEM_ID, propertyId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        if (getArguments().containsKey(ARG_ITEM_ID))
        {
            int id = getArguments().getInt(ARG_ITEM_ID);
            PropertyService.getInstance().setOnPropertyUpdateListener(this);
            mItem = PropertyService.getInstance().getProperty(id);

            Activity activity = this.getActivity();
            CollapsingToolbarLayout appBarLayout = (CollapsingToolbarLayout) activity.findViewById(R.id.collapsing_toolbar);
            if (appBarLayout != null)
            {
                appBarLayout.setTitle(mItem.subType);
                appBarLayout.setTitleEnabled(false);

                ImageView imageView = (ImageView) appBarLayout.findViewById(R.id.picture);
                Picasso.with(getContext()).load(mItem.thumbnail).into(imageView);
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        View rootView = inflater.inflate(R.layout.property_detail, container, false);

        title = (TextView) rootView.findViewById(R.id.subtype);
        subtitle = (TextView) rootView.findViewById(R.id.location);
        price = (TextView) rootView.findViewById(R.id.price);
        generalFeaturesItems = (ViewGroup) rootView.findViewById(R.id.property_features_items);

        notesContainer = (ViewGroup) rootView.findViewById(R.id.notes_container);
        notes = (TextView) rootView.findViewById(R.id.notes);

        condoFeeContainer = (ViewGroup) rootView.findViewById(R.id.condo_fee_container);
        condoFee = (TextView) rootView.findViewById(R.id.condo_fee_value);

        condoFeaturesContainer = (ViewGroup) rootView.findViewById(R.id.condo_features_container);
        condoFeaturesItems = (ViewGroup) rootView.findViewById(R.id.condo_features_items);

        clientContainer = (ViewGroup) rootView.findViewById(R.id.client_container);
        client = (TextView) rootView.findViewById(R.id.client_value);

        extraInfoContainer = (ViewGroup) rootView.findViewById(R.id.extra_info_container);
        extraInfo = (TextView) rootView.findViewById(R.id.extra_info);

        updatedOn = (TextView) rootView.findViewById(R.id.updated_on);

        Button button = (Button) rootView.findViewById(R.id.show_form);
        button.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                ContactDialogFragment fragment = ContactDialogFragment.newInstance(mItem.id);
                fragment.show(getActivity().getSupportFragmentManager(),fragment.getTag());
            }
        });

        bindView();

        return rootView;
    }


    public void OnPropertyUpdate(final Property property)
    {
        if (getActivity() != null)
        {
            getActivity().runOnUiThread(new Runnable()
            {
                @Override
                public void run()
                {
                    bindView();
                }
            });
        }
    }


    private View addPropertyFeature(ViewGroup rootView, String label, String value, int icon)
    {
        View view = LayoutInflater.from(getActivity())
                .inflate(R.layout.property_feature_content, null);

        TextView labelText = (TextView) view.findViewById(R.id.property_feature_label);
        labelText.setText(label);

        if (value != null)
        {
            TextView valueText = (TextView) view.findViewById(R.id.property_feature_value);
            valueText.setText(value);
            valueText.setVisibility(View.VISIBLE);
        }

        if (icon > 0)
        {
            ImageView iconView = (ImageView) view.findViewById(R.id.property_feature_icon);
            iconView.setImageResource(icon);
            iconView.setVisibility(View.VISIBLE);
        }

        rootView.addView(view);
        return view;
    }


    private void bindView()
    {
        if (mItem != null)
        {
            title.setText(mItem.subType);

            if (mItem.address != null)
                subtitle.setText(mItem.address.getFullAddress());

            if (mItem.price > 0)
            {
                price.setText(FormatterUtil.getFormattedCurrencyString("BRL", mItem.price));
            }
            else
            {
                price.setText(R.string.on_request);
            }

            generalFeaturesItems.removeAllViews();

            Resources res = getResources();

            if (mItem.bedrooms > 0)
            {
                String value = res.getQuantityString(R.plurals.bedrooms, mItem.bedrooms, mItem.bedrooms);
                addPropertyFeature(generalFeaturesItems, value, null, R.drawable.ic_local_hotel_black_24dp);
            }

            if (mItem.suites > 0)
            {
                String value = res.getQuantityString(R.plurals.suites, mItem.suites, mItem.suites);
                addPropertyFeature(generalFeaturesItems, value, null, R.drawable.ic_suite_black_24dp);
            }

            if (mItem.garageSpaces > 0)
            {
                String value = res.getQuantityString(R.plurals.garage_spaces, mItem.garageSpaces, mItem.garageSpaces);
                addPropertyFeature(generalFeaturesItems, value, null, R.drawable.ic_directions_car_black_24dp);
            }

            if (mItem.buindingArea > 0)
            {
                String value = res.getString(R.string.building_area, mItem.buindingArea);
                addPropertyFeature(generalFeaturesItems, value, null, R.drawable.ic_building_area_black_24dp);
            }

            if (mItem.landSize > 0)
            {
                String value = res.getString(R.string.total_area, mItem.landSize);
                addPropertyFeature(generalFeaturesItems, value, null, R.drawable.ic_total_area_black_24dp);
            }

            if(mItem.generalFeatures != null && mItem.generalFeatures.size() > 0)
            {
                for (String feature : mItem.generalFeatures)
                {
                    addPropertyFeature(generalFeaturesItems, feature, null, -1);
                }
            }

            if (mItem.notes != null)
            {
                notesContainer.setVisibility(View.VISIBLE);
                notes.setText(mItem.notes);
            }
            else
            {
                notesContainer.setVisibility(View.GONE);
            }

            if (mItem.condoFee > 0)
            {
                condoFee.setText(FormatterUtil.getFormattedCurrencyString("BRL", mItem.condoFee));
                condoFeeContainer.setVisibility(View.VISIBLE);
            }
            else
            {
                condoFeeContainer.setVisibility(View.GONE);
            }

            if (mItem.condoFeatures != null && mItem.condoFeatures.size() > 0)
            {
                condoFeaturesContainer.setVisibility(View.VISIBLE);
                condoFeaturesItems.removeAllViews();
                for (String feature : mItem.condoFeatures)
                {
                    addPropertyFeature(condoFeaturesItems, feature, null, -1);
                }
            }
            else
            {
                condoFeaturesContainer.setVisibility(View.GONE);
            }

            if (mItem.client != null)
            {
                clientContainer.setVisibility(View.VISIBLE);
                client.setText(mItem.client.name);
            }
            else
            {
                clientContainer.setVisibility(View.GONE);
            }

            if (mItem.extraInfo != null && mItem.extraInfo.length() > 0)
            {
                extraInfoContainer.setVisibility(View.VISIBLE);
                extraInfo.setText(mItem.extraInfo);
            }
            else
            {
                extraInfoContainer.setVisibility(View.GONE);
            }

            if (mItem.updatedOn > 0)
            {
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMM HH:mm", Locale.getDefault());
                updatedOn.setText(getResources().getString(R.string.updated_on,
                        dateFormat.format(mItem.updatedOn)));
            }
        }
    }
}

