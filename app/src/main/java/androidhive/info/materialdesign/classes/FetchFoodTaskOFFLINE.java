package androidhive.info.materialdesign.classes;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;


// background task
public class FetchFoodTaskOFFLINE extends AsyncTask<String, Void, FoodsData>
{
    private final String LOG_TAG = FetchFoodTaskOFFLINE.class.getSimpleName();
    private Context mCtx; // declare a Context reference

    public FetchFoodTaskOFFLINE(Context context)
    {
        mCtx = context; // fill it with the Context you are passed
    }

    private FoodsData getFoodsDataFromJSON(String foodDataStr) throws JSONException
    {
        Log.v(LOG_TAG, " ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");

        // these are the names of the JSON objects that need to be extracted. (FN = FoodNutrients
        final String FN_Description = "description";
        final String FN_FoodsList   = "foodsList";
        final String FN_NutList     = "nutList";

        // convert string in a JSON object
        JSONObject foodDataJson = new JSONObject(foodDataStr);

        // extract the foodList array from JSON object
        JSONArray foodsArray = foodDataJson.getJSONArray(FN_FoodsList);

        // this array will have the results
        String[] resultStr = new String[foodsArray.length()];

        // the list of foods objects
        List<Food> foodList = new ArrayList<Food>();

        for(int i=0; i<foodsArray.length(); i++)
        {
            String food_name;
            String nut_name;
            String nut_value;

            // get the current Food with his fields
            JSONObject currentFood = foodsArray.getJSONObject(i);

            // get the description for the current object
            food_name = currentFood.getString(FN_Description);

            // get the nutrients JSONArray for the current object
            JSONArray  nutList    = currentFood.getJSONArray(FN_NutList);

            resultStr[i] = food_name;

            List<Nutrient> nut_list =  new ArrayList<Nutrient>();

            // extract the nutrients
            for (int j=0; j<nutList.length(); j++)
            {

                nut_name  = nutList.getJSONObject(j).getString("name");
                nut_value = nutList.getJSONObject(j).getString("value");

                nut_list.add( new Nutrient(nut_name,nut_value) );

                resultStr[i] += " - " + nut_name + " - " + nut_value;
            }

            // add the current food to the list
            foodList.add(new Food(food_name, nut_list));

            //Log.v(LOG_TAG, " +++ Current Food: " + resultStr[i]);
        }

        // Object with all the data
        FoodsData  foodsData = new FoodsData(foodList);

        return foodsData;
    }

    @Override
    protected FoodsData doInBackground(String... params)
    {
        if(params.length == 0)
            return null;


        // will contain the JSON data as a string

        StringBuilder foodsDataJsonStr = null;
        try
        {
            foodsDataJsonStr = new StringBuilder();
            InputStream json     = mCtx.getAssets().open("foods_list_json.json");
            BufferedReader in    = new BufferedReader(new InputStreamReader(json, "UTF-8"));

            String str;
            while ((str = in.readLine()) != null)
            {
                foodsDataJsonStr.append(str);
            }

            in.close();


            // Log.v(LOG_TAG, " *** FoodData JSON string: " + foodsDataJsonStr);
        }
        catch (IOException e)
        {
            Log.e(LOG_TAG, "Error ", e);
            // If the code didn't successfully get the data,
            // there's no point in attempting to parse it.
            foodsDataJsonStr = null;
        }


        try
        {
            return getFoodsDataFromJSON(foodsDataJsonStr.toString());
        }
        catch (JSONException e)
        {
            Log.e(LOG_TAG, e.getMessage(), e);
            e.printStackTrace();
        }

        return null;
    }

    @Override
    protected void onPostExecute(FoodsData results)
    {
        if (results == null)
        {
            for (int i = 0; i < 10; i++)
                Log.d(" ERROR in FetchFoodTaskOFFLINE ", "error error error error error");
        }
        else
        {
        }
    }
}