package com.android.example.realestate.data;

import com.android.example.realestate.utils.StringUtil;

public class Address
{
    public String publicSpace;
    public String number;
    public String details;
    public String zipcode;
    public String neighborhood;
    public String city;
    public String state;
    public String zone;

    public String getFullAddress()
    {
        StringBuilder stringBuilder = new StringBuilder();

        if (!StringUtil.isNullOrEmpty(publicSpace))
        {
            stringBuilder.append(publicSpace);

            if (!StringUtil.isNullOrWhiteSpace(number))
            {
                stringBuilder.append(", ");
                stringBuilder.append(number);
            }

            if (!StringUtil.isNullOrWhiteSpace(details))
            {
                stringBuilder.append(" ");
                stringBuilder.append(details);
            }
        }

        if (!StringUtil.isNullOrWhiteSpace(neighborhood))
        {
            if (stringBuilder.length() > 0)
            {
                stringBuilder.append(" - ");
            }

            stringBuilder.append(neighborhood);
        }

        if (!StringUtil.isNullOrWhiteSpace(city))
        {
            if (stringBuilder.length() > 0)
            {
                stringBuilder.append(" - ");
            }

            stringBuilder.append(city);
        }

        if (!StringUtil.isNullOrWhiteSpace(state))
        {
            if (stringBuilder.length() > 0)
            {
                stringBuilder.append(" - ");
            }

            stringBuilder.append(state);
        }

        if (!StringUtil.isNullOrWhiteSpace(zipcode))
        {
            if (stringBuilder.length() > 0)
            {
                stringBuilder.append(" - ");
            }

            stringBuilder.append(zipcode);
        }

        return stringBuilder.toString();
    }
}
