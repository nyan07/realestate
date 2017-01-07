package com.android.example.realestate.data;

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

        if (publicSpace != null)
        {
            stringBuilder.append(publicSpace);

            if (number != null)
            {
                stringBuilder.append(", ");
                stringBuilder.append(number);
            }

            if (details != null)
            {
                stringBuilder.append(" ");
                stringBuilder.append(details);
            }
        }

        if (neighborhood != null)
        {
            if (stringBuilder.length() > 0)
            {
                stringBuilder.append(" - ");
            }

            stringBuilder.append(neighborhood);
        }

        if (city != null)
        {
            if (stringBuilder.length() > 0)
            {
                stringBuilder.append(" - ");
            }

            stringBuilder.append(city);
        }

        if (state != null)
        {
            if (stringBuilder.length() > 0)
            {
                stringBuilder.append(" - ");
            }

            stringBuilder.append(state);
        }

        if (zipcode != null)
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
