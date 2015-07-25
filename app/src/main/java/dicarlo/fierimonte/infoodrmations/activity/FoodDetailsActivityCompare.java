package dicarlo.fierimonte.infoodrmations.activity;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import dicarlo.fierimonte.infoodrmations.R;
import dicarlo.fierimonte.infoodrmations.classes.DataPreferences;
import dicarlo.fierimonte.infoodrmations.classes.Food;
import dicarlo.fierimonte.infoodrmations.classes.FoodsData;
import dicarlo.fierimonte.infoodrmations.classes.Nutrient;

public class FoodDetailsActivityCompare extends ActionBarActivity
{
    static int position;
    static int check = 0;
    String[] indexes = new String[2];

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_food_details_compare);

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
            String food_name_home = temp_food.getDescription();

            // putting food's name into text view
            TextView foodNameHome = (TextView) findViewById(R.id.activity_food_details_compare_textview_foodsName);
            Typeface future_font = Typeface.createFromAsset(getAssets(),"fonts/a song for jennifer.ttf");
            foodNameHome.setTypeface(future_font);

            foodNameHome.setText(food_name_home);

            String food_nutrients_home = new String();

            for (int i = 0; i < temp_nut_list.size(); i++)
            {
                if (!temp_nut_list.get(i).getName().equals("Carbohydrate, by difference"))
                {
                    // build the texts
                    food_nutrients_home += ""//"         "
                            + temp_nut_list.get(i).getName()
                            + ":  " + temp_nut_list.get(i).getValue()
                            + "\n\n";
                }
                else
                {
                    // build the texts
                    food_nutrients_home += ""//"         "
                            + "Carbohydrate"
                            + ":    " + temp_nut_list.get(i).getValue()
                            + "\n\n";
                }

            }

            ((TextView)findViewById(R.id.activity_food_details_compare_textview_foodsNutrients)).setText(food_nutrients_home);

            TextView nutrients_home = (TextView) findViewById(R.id.activity_food_details_compare_textview_foodsNutrients);
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

    public void addFood_toCompare(View view)
    {
        String compare_indexes = DataPreferences.readPreference(view.getContext(), DataPreferences.COMP_FOODS, DataPreferences.COMP_KEY);

        String position_string = Integer.toString(position);

        // check variable needs to verify that it's added only one food or two
        if(check == 1)
        {
            Intent start_compare_activity = new Intent(FoodDetailsActivityCompare.this, Compare.class);
            startActivity(start_compare_activity);
            check = 0;
        }

        else if(check == 0)
        {
            String message = "Primo cibo aggiunto. Torna indietro e seleziona un altro cibo!";
            Toast msg = Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG);
            msg.show();
            check = 1;
        }

        DataPreferences.writePreference(getApplicationContext(), DataPreferences.COMP_FOODS, DataPreferences.COMP_KEY, position_string);

    }

}
