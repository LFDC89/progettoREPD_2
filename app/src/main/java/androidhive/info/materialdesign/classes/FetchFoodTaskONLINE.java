package androidhive.info.materialdesign.classes;

import android.os.AsyncTask;
import android.util.Log;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import androidhive.info.materialdesign.fragments.SearchFragment;


// background task
public class FetchFoodTaskONLINE extends AsyncTask<String, Void, FoodsData>
{
    private final String LOG_TAG = FetchFoodTaskONLINE.class.getSimpleName();

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

            Log.v(LOG_TAG, " +++ Current Food: " + resultStr[i]);
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

        // HTTP REQUEST

        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;

        // will contain the JSON response as a string
        String foodsDataJsonStr = null;

        try
        {
            final String BASE_URL = "http://192.168.3.102:8080//FoodNutrients//FN_Server//foods_list_json.action";
            URL url = new URL(BASE_URL.toString());

            // Create the server request, and open the connection
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            // Read the input stream into a String
            InputStream inputStream = urlConnection.getInputStream();
            StringBuffer buffer = new StringBuffer();

            if (inputStream == null)
                return null;    // Nothing to do.

            reader = new BufferedReader(new InputStreamReader(inputStream));

            String line;
            while ((line = reader.readLine()) != null)
            {
                // New line used for debug
                buffer.append(line + "\n");
            }

            if (buffer.length() == 0)
            {
                // Stream was empty.  No point in parsing.
                foodsDataJsonStr = null;
            }
            foodsDataJsonStr = buffer.toString();

            Log.v(LOG_TAG, " FoodData JSON string: " + foodsDataJsonStr);
        }
        catch (IOException e)
        {
            Log.e(LOG_TAG, "Error ", e);
            // If the code didn't successfully get the data,
            // there's no point in attempting to parse it.
            foodsDataJsonStr = null;
        }
        finally
        {
            if (urlConnection != null)
            {
                urlConnection.disconnect();
            }
            if (reader != null)
            {
                try
                {
                    reader.close();
                }
                catch (final IOException e)
                {
                    Log.e(LOG_TAG, "Error closing stream", e);
                }
            }
        }

        try
        {
            return getFoodsDataFromJSON(foodsDataJsonStr);
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
        // point to all the data
        List<Food> temp = FoodsData.foodsData;

        if (results != null)
        {
            SearchFragment.mFoodsAdapter.clear();

            for(int i=0; i<temp.size(); i++)
            {
                // extract current food object
                Food temp_food   = temp.get(i);

                // extract food description
                String food_desc = temp_food.getDescription();

                // extract the list of nutrients
                List<Nutrient> temp_nut_list = temp_food.getNutList();

                // extract energy nutrient (the number 3 in the list)
                String calories = temp_nut_list.get(3).getValue();

                // final string
                String final_string = food_desc+" - " + calories + " KCal/100gr ";

                // update the ListView
                SearchFragment.mFoodsAdapter.add(final_string);
                SearchFragment.mFoodsAdapter.notifyDataSetChanged();
            }

            // data is back from the server. !!!
        }

    }
}