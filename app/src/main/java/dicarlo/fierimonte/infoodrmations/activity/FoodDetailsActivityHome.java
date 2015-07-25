package dicarlo.fierimonte.infoodrmations.activity;

import android.content.Intent;

import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.content.Context;


import java.util.List;

import dicarlo.fierimonte.infoodrmations.classes.DataPreferences;
import dicarlo.fierimonte.infoodrmations.classes.Food;
import dicarlo.fierimonte.infoodrmations.classes.FoodsData;
import dicarlo.fierimonte.infoodrmations.classes.Nutrient;
import dicarlo.fierimonte.infoodrmations.R;

public class FoodDetailsActivityHome extends ActionBarActivity
{
    static int position;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_food_details_home);

        // The detail Activity called via intent.  Inspect the intent for forecast data.
        Intent intent = getIntent();

        if (intent != null && intent.hasExtra(Intent.EXTRA_TEXT))
        {
            // split info: position & portion
            String food_received_info = intent.getStringExtra(Intent.EXTRA_TEXT);

            // split info: position & portion
            String[] split_info = food_received_info.split(":");

            position               = Integer.parseInt(split_info[0]);
            double current_portion = Double.parseDouble(split_info[1]);

            // point to all the data
            List<Food> temp = FoodsData.foodsData;

            // extract food object in position
            Food temp_food = temp.get(position);

            // extract the list of nutrients
            List<Nutrient> temp_nut_list = temp_food.getNutList();

            // food's name
            String food_name_home = temp_food.getDescription();

            // putting food's name into text view
            TextView foodNameHome = (TextView) findViewById(R.id.activity_food_details_home_textview_foodsName);
            Typeface future_font = Typeface.createFromAsset(getAssets(),"fonts/a song for jennifer.ttf");
            foodNameHome.setTypeface(future_font);
            foodNameHome.setText(food_name_home);


            String food_nutrients_home = new String();

            int kcal             = 0;
            double lipids        = 0.0;
            double carbohydrates = 0.0;
            double current_nutrient      = 0.0;

            for (int i = 0; i<temp_nut_list.size(); i++)
            {
                current_nutrient = rounding((Double.parseDouble(temp_nut_list.get(i).getValue())*current_portion)/100);
                if (temp_nut_list.get(i).getName().equals("Carbohydrate, by difference"))
                {
                    // build the texts
                    food_nutrients_home += ""
                            + "Carbohydrate"

                            + ":  " + current_nutrient
                            + "\n\n";
                }
                else if (temp_nut_list.get(i).getName().equals("Energy"))
                {
                    // build the texts
                    food_nutrients_home += ""
                            + "Kcal"
                            + ":    " + current_nutrient
                            + "\n\n";
                }
                else
                {
                        // build the texts
                        food_nutrients_home += ""
                                + temp_nut_list.get(i).getName()
                                + ":    " + current_nutrient
                                + "\n\n";
                }

            }

            ((TextView)findViewById(R.id.activity_food_details_home_textview_foodsNutrients)).setText(food_nutrients_home);

            TextView nutrients_home = (TextView) findViewById(R.id.activity_food_details_home_textview_foodsNutrients);
            Typeface custom_font_home_nutrients = Typeface.createFromAsset(getAssets(),"fonts/Girls_Have_Many Secrets.ttf");
            nutrients_home.setTypeface(custom_font_home_nutrients);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_food_details, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings)
        {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    public void deleteFood(View view)
    {
        List<Food> temp = FoodsData.foodsData;

        // getting food object to delete
        Food to_delete = temp.get(position);

        // reading start data string
        String start_data_string = DataPreferences.readPreference(view.getContext(), DataPreferences.PREFS_USER_FOODS, DataPreferences.PUF_KEY);

        // getting data indexes
        final String[] start_data_indexes = start_data_string.split(",");

        // vector containing new indexes
        final String[] new_indexes = new String[start_data_indexes.length - 1];

        // relative index of delete object
        int delete_index = 0;

        String position_string = String.valueOf(position);

        String[] real_indexes = new String[start_data_indexes.length];
        for(int i=0;i<start_data_indexes.length;i++)
        {
            String[] appo = start_data_indexes[i].split(":");
            real_indexes[i] = appo[0];
        }

        // founding the relative index corresponding to global index passed by argument
        for(int i=0;i<start_data_indexes.length;i++)
            if(real_indexes[i].equals(position_string))
                delete_index = i;

        // deleting the selected object
        for(int i=delete_index;i<start_data_indexes.length-1;i++)
        {
            start_data_indexes[i] = start_data_indexes[i+1];
        }

        // reset the user food data
        SharedPreferences settings = getApplicationContext().getSharedPreferences(DataPreferences.PREFS_USER_FOODS, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();

        editor.clear();
        editor.commit();

        // rebuilding user food data with new indexes array
        for(int i=0; i<start_data_indexes.length-1; i++)
           DataPreferences.writePreference(view.getContext(), DataPreferences.PREFS_USER_FOODS, DataPreferences.PUF_KEY, start_data_indexes[i]);

        // starting HomeFragment
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
    }

    public static double rounding(double x)
    {
        x = Math.floor(x*100);
        x = x/100;
        return x;
    }
}
