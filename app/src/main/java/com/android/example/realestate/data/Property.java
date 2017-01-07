package com.android.example.realestate.data;

import java.text.NumberFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class Property
{
    public int id;
    public String type;
    public String subType;
    public String thumbnail;
    public String offerType;
    public Double price;
    public int bedrooms;
    public int suites;
    public int garageSpaces;
    public double buindingArea;
    public double landSize;
    public Date updatedAt;
    public Address address;

    public String notes;
    public String extraInfo;
    public String condoFee;
    public List<String> generalFeatures;
    public List<String> condoFeatures;
    public List<String> pictures;
    public Client client;
    public boolean isLoaded;
}


