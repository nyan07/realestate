package com.android.example.realestate.data;

import android.util.Log;

import com.android.example.realestate.BuildConfig;
import com.android.example.realestate.utils.DateUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class PropertyService
{
    private static PropertyService INSTANCE;
    private final List<Property> items = new ArrayList<>();
    private final Map<String, Property> itemMap = new HashMap<String, Property>();
    private OnDataSetChangedListener dataSetChangedListener;
    private OnPropertyUpdateListener propertyUpdateListener;
    private OnSendUserInfoListener sendUserInfoListener;

    private OkHttpClient client = new OkHttpClient();

    private PropertyService()
    {
    }

    public static PropertyService getInstance()
    {
        if (INSTANCE == null)
        {
            INSTANCE = new PropertyService();
        }

        return INSTANCE;
    }

    public void setOnDataSetChangedListener(OnDataSetChangedListener listener)
    {
        dataSetChangedListener = listener;
    }

    public void setOnPropertyUpdateListener(OnPropertyUpdateListener listener)
    {
        propertyUpdateListener = listener;
    }

    public void setOnSendUserInfoListener(OnSendUserInfoListener listener)
    {
        sendUserInfoListener = listener;
    }

    private void addItem(Property property)
    {
        String key = String.valueOf(property.id);
        if (!itemMap.containsKey(key))
        {
            items.add(property);
            itemMap.put(key, property);
        }
    }

    public Property getProperty(int id)
    {
        Property property = itemMap.get(String.valueOf(id));

        if (!property.isLoaded)
        {
            loadPropertyDetails(property);
        }

        return property;
    }

    private void loadPropertyDetails(final Property property)
    {
        HttpUrl.Builder urlBuilder = HttpUrl.parse(BuildConfig.API_BASE_URL).newBuilder();
        urlBuilder
                .addEncodedPathSegment("imoveis")
                .addEncodedPathSegment(String.valueOf(property.id));
        String url = urlBuilder.build().toString();

        Request request = new Request.Builder().url(url).build();
        client.newCall(request).enqueue(new Callback() {

            @Override
            public void onFailure(Call call, IOException e)
            {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException
            {
                try
                {
                    final String PROPERTY = "Imovel";

                    final String CLIENT = "Cliente";
                    final String CLIENT_ID = "CodCliente";
                    final String CLIENT_NAME = "NomeFantasia";

                    final String NOTES = "Observacao";
                    final String GENERAL_FEATURES = "Caracteristicas";
                    final String CONDO_FEE = "PrecoCondominio";
                    final String CONDO_FEATURES = "CaracteristicasComum";
                    final String EXTRA_INFO = "InformacoesComplementares";
                    final String PICTURES = "Fotos";

                    String responseData = response.body().string();
                    JSONObject json = new JSONObject(responseData);
                    JSONObject propertyJson = json.getJSONObject(PROPERTY);

                    JSONObject clientJson = propertyJson.getJSONObject(CLIENT);
                    Client client = new Client();
                    client.id = clientJson.getInt(CLIENT_ID);
                    client.name = clientJson.getString(CLIENT_NAME);
                    property.client = client;

                    if (propertyJson.has(NOTES))
                        property.notes = propertyJson.getString(NOTES);

                    if (propertyJson.has(GENERAL_FEATURES))
                    {
                        JSONArray generalFeaturesJson = propertyJson.getJSONArray(GENERAL_FEATURES);
                        List<String> list = new ArrayList<>();
                        for (int i = 0; i < generalFeaturesJson.length(); i++)
                        {
                            list.add( generalFeaturesJson.getString(i));
                        }

                        property.generalFeatures = list;
                    }

                    if (propertyJson.has(CONDO_FEE))
                        property.condoFee = propertyJson.getDouble(CONDO_FEE);

                    if (propertyJson.has(CONDO_FEATURES))
                    {
                        JSONArray condoFeaturesJson = propertyJson.getJSONArray(CONDO_FEATURES);
                        List<String> list = new ArrayList<>();
                        for (int i = 0; i < condoFeaturesJson.length(); i++)
                        {
                            list.add(condoFeaturesJson.getString(i));
                        }

                        property.condoFeatures = list;
                    }

                    if (propertyJson.has(EXTRA_INFO))
                        property.extraInfo = propertyJson.getString(EXTRA_INFO);

                    if (propertyJson.has(PICTURES))
                    {
                        JSONArray picturesJson = propertyJson.getJSONArray(PICTURES);
                        List<String> list = new ArrayList<>();
                        for (int i = 0; i < picturesJson.length(); i++)
                        {
                            list.add(picturesJson.getString(i));
                        }

                        property.pictures = list;
                    }

                    property.isLoaded = true;
                    if (propertyUpdateListener != null)
                    {
                        propertyUpdateListener.OnPropertyUpdate(property);
                    }
                }
                catch (JSONException e)
                {
                    Log.e("json", e.toString());
                }
            }
        });
    }

    public void loadAll()
    {
        HttpUrl.Builder urlBuilder = HttpUrl.parse(BuildConfig.API_BASE_URL).newBuilder();
        urlBuilder.addEncodedPathSegment("imoveis");
        String url = urlBuilder.build().toString();

        Request request = new Request.Builder().url(url).build();
        client.newCall(request).enqueue(new Callback() {

            @Override
            public void onFailure(Call call, IOException e)
            {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException
            {
                try
                {
                    final String PROPERTIES = "Imoveis";

                    final String PROPERTY_ID = "CodImovel";
                    final String PROPERTY_TYPE = "TipoImovel";
                    final String PROPERTY_SUBTYPE = "SubtipoImovel";
                    final String OFFER_TYPE = "SubTipoOferta";
                    final String THUMBNAIL = "UrlImagem";

                    final String PRICE = "PrecoVenda";
                    final String BEDROOMS = "Dormitorios";
                    final String SUITES = "Suites";
                    final String GARAGE_SPACES = "Vagas";
                    final String BUILDING_AREA = "AreaUtil";
                    final String LAND_SIZE = "AreaTotal";
                    final String UPDATED_ON = "DataAtualizacao";

                    final String ADDRESS = "Endereco";
                    final String PUBLIC_SPACE = "Logradouro";
                    final String ADDRESS_NUMBER = "Numero";
                    final String ADDRESS_DETAILS = "Complemento";
                    final String ZIPCODE = "03591010";
                    final String NEIGHBORHOOD = "Bairro";
                    final String CITY = "Cidade";
                    final String STATE = "Estado";
                    final String ZONE = "Zona";

                    String responseData = response.body().string();
                    JSONObject json = new JSONObject(responseData);

                    JSONArray jsonArray = json.getJSONArray(PROPERTIES);

                    for (int i = 0; i < jsonArray.length(); i++)
                    {
                        JSONObject propertyJson = jsonArray.getJSONObject(i);

                        Property property = new Property();

                        property.id = propertyJson.getInt(PROPERTY_ID);
                        property.type = propertyJson.getString(PROPERTY_TYPE);
                        property.subType = propertyJson.getString(PROPERTY_SUBTYPE);
                        property.offerType = propertyJson.getString(OFFER_TYPE);
                        property.thumbnail = propertyJson.getString(THUMBNAIL);

                        property.price = propertyJson.getDouble(PRICE);
                        property.bedrooms = propertyJson.getInt(BEDROOMS);
                        property.suites = propertyJson.getInt(SUITES);
                        property.garageSpaces = propertyJson.getInt(GARAGE_SPACES);
                        property.buindingArea = propertyJson.getInt(BUILDING_AREA);
                        property.landSize = propertyJson.getInt(LAND_SIZE);

                        property.updatedOn = DateUtil.parseMsTimestampToMilliseconds(
                                propertyJson.getString(UPDATED_ON));

                        JSONObject addressJson = propertyJson.getJSONObject(ADDRESS);
                        Address address = new Address();

                        if (addressJson.has(PUBLIC_SPACE))
                            address.publicSpace = addressJson.getString(PUBLIC_SPACE);

                        if (addressJson.has(ADDRESS_NUMBER))
                            address.number = addressJson.getString(ADDRESS_NUMBER);

                        if (addressJson.has(ADDRESS_DETAILS))
                            address.details = addressJson.getString(ADDRESS_DETAILS);

                        if (addressJson.has(ZIPCODE))
                            address.zipcode = addressJson.getString(ZIPCODE);

                        if (addressJson.has(NEIGHBORHOOD))
                            address.neighborhood = addressJson.getString(NEIGHBORHOOD);

                        if (addressJson.has(CITY))
                            address.city = addressJson.getString(CITY);

                        if (addressJson.has(STATE))
                            address.state = addressJson.getString(STATE);

                        if (addressJson.has(ZONE))
                            address.zone = addressJson.getString(ZONE);

                        property.address = address;
                        addItem(property);
                    }

                    if (dataSetChangedListener != null)
                    {
                        dataSetChangedListener.OnDataSetChanged();
                    }
                }
                catch (JSONException e)
                {
                    Log.e("json", e.toString());
                }
            }
        });
    }

    public List<Property> getProperties()
    {
        return items;
    }


    public void sendUserInfo(int propertyId, String name, String email, String phone)
    {
        HttpUrl.Builder urlBuilder = HttpUrl.parse(BuildConfig.API_BASE_URL).newBuilder();
        urlBuilder.addEncodedPathSegment("contato");
        String url = urlBuilder.build().toString();

        RequestBody formBody = new FormBody.Builder()
                .add("CodImovel", String.valueOf(propertyId))
                .add("Nome", name)
                .add("Email", email)
                .add("Telefone", phone)
                .build();

        Request request = new Request.Builder()
                .url(url)
                .post(formBody)
                .build();

        client.newCall(request).enqueue(new Callback()
        {

            @Override
            public void onFailure(Call call, IOException e)
            {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException
            {
                if (sendUserInfoListener != null)
                {
                    if (response.isSuccessful())
                    {
                        sendUserInfoListener.OnSuccess();
                    }
                    else
                    {
                        sendUserInfoListener.OnError(response.body().string());
                    }
                }
            }
        });
    }

    public interface OnDataSetChangedListener
    {
        void OnDataSetChanged();
    }

    public interface OnPropertyUpdateListener
    {
        void OnPropertyUpdate(Property property);
    }

    public interface OnSendUserInfoListener
    {
        void OnSuccess();
        void OnError(String message);
    }
}