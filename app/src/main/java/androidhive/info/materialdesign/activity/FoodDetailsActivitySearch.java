package androidhive.info.materialdesign.activity;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.List;

import androidhive.info.materialdesign.R;
import androidhive.info.materialdesign.classes.DataPreferences;
import androidhive.info.materialdesign.classes.Food;
import androidhive.info.materialdesign.classes.FoodsData;
import androidhive.info.materialdesign.classes.Nutrient;

public class FoodDetailsActivitySearch extends ActionBarActivity
{
    static int position;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        final Button prova = (Button) findViewById(R.id.activity_food_details_search_tryButton);
        final Button button = (Button) findViewById(R.id.addButton);

        setContentView(R.layout.activity_food_details_search);

        // The detail Activity called via intent.  Inspect the intent for forecast data.
        Intent intent = getIntent();

        if (intent != null && intent.hasExtra(Intent.EXTRA_TEXT))
        {

            position = Integer.parseInt(intent.getStringExtra(Intent.EXTRA_TEXT));

            // point to all the data
            List<Food> temp = FoodsData.foodsData;

            // extract food object in position
            Food temp_food = temp.get(position);

            // extract the list of nutrients
            List<Nutrient> temp_nut_list = temp_food.getNutList();

            // food's name
            String food_name = temp_food.getDescription();

            // putting food's name into text view
            TextView food_name_search_textView = (TextView) findViewById(R.id.activity_food_details_search_textview_foodsName);
            food_name_search_textView.setText(food_name);

            String food_nutrients = new String();

            for (int i = 0; i < temp_nut_list.size(); i++)
            {
                if (!temp_nut_list.get(i).getName().equals("Carbohydrate, by difference"))
                {
                    // build the texts
                    food_nutrients += ""//"         "
                            + temp_nut_list.get(i).getName()
                            + ":  " + temp_nut_list.get(i).getValue()
                            + "\n\n";
                }
                else
                {
                    // build the texts
                    food_nutrients += ""//"         "
                            + "Carbohydrate"
                            + ":    " + temp_nut_list.get(i).getValue()
                            + "\n\n";
                }

            }

            ((TextView)findViewById(R.id.activity_food_details_search_textview_foodsNutrients)).setText(food_nutrients);


            // setting custom fonts
            Typeface CF_food_name_search = Typeface.createFromAsset(getAssets(),"fonts/a song for jennifer.ttf");
            food_name_search_textView.setTypeface(CF_food_name_search);

            TextView nutrients_search_textView = (TextView) findViewById(R.id.activity_food_details_search_textview_foodsNutrients);
            Typeface CF_food_nutrient_search = Typeface.createFromAsset(getAssets(),"fonts/Girls_Have_Many Secrets.ttf");
            nutrients_search_textView.setTypeface(CF_food_nutrient_search);

            TextView info_search_textView = (TextView) findViewById(R.id.activity_food_details_search_info);
            Typeface CF_info_search = Typeface.createFromAsset(getAssets(), "fonts/Girls_Have_Many Secrets.ttf");
            info_search_textView.setTypeface(CF_info_search);
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

    public void addFood(View view)
    {
        Log.v("Button", " ++++++++++++++++++++++++++++++++++++++++++ ");

        // point to all the data
        List<Food> temp = FoodsData.foodsData;

        // add the position of the food
        String new_position = Integer.toString(position);

        DataPreferences.writePreference(getApplicationContext(), DataPreferences.PREFS_USER_FOODS, DataPreferences.PUF_KEY, new_position);

        // starting HomeFragment
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
    }

    public void prova(View view)
    {
         /* CUSTOM PORTION STUFF */
        final EditText portion_input = (EditText) findViewById(R.id.activity_food_details_search_insertPortion);
        double newPortion = 0.0;
        double standardPortion = 100.0;
        double oldNutrient = 0.0;
        double newNutrient = 0.0;

        // read value from editText and parsing to double
        // if the user don't modify portion, this it's setted at the default value 100 in order to obtain a
        // coefficient equals to 1
        if(portion_input.length()<=0)
            newPortion = 100.0;
        else
            newPortion = Double.parseDouble(portion_input.getText().toString());

        // point to all the data
        List<Food> temp = FoodsData.foodsData;

        // add the position of the food
        Food tmp_food = temp.get(position);

        // extract the list of nutrients
        List<Nutrient> tmp_nut_list = tmp_food.getNutList();

        int nutListSize = tmp_nut_list.size();

        double coefficient[] = new double[nutListSize];

        String displayString = new String();
        String nutrientName[] = new String[nutListSize];
        String nutrientValue[] = new  String[nutListSize];

        for(int i=0; i<tmp_nut_list.size();i++)
        {
            // shortcuts
            nutrientName[i] = tmp_nut_list.get(i).getName();
            nutrientValue[i] = tmp_nut_list.get(i).getValue();

            // parsing string to double
            oldNutrient = Double.parseDouble(nutrientValue[i]);

            // coefficient calculation
            coefficient[i] = oldNutrient / standardPortion;
        }
    }
}
