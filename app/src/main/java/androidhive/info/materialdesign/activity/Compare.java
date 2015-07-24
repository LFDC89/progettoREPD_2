package androidhive.info.materialdesign.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import androidhive.info.materialdesign.R;
import androidhive.info.materialdesign.classes.DataPreferences;
import androidhive.info.materialdesign.classes.Food;
import androidhive.info.materialdesign.classes.FoodsData;
import androidhive.info.materialdesign.classes.Nutrient;

public class Compare extends ActionBarActivity {

    private String compare_indexes_string = new String();

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compare);

        // Setting custom font to title Text View
        TextView title_textView = (TextView) findViewById(R.id.activity_compare_title);
        Typeface custom_font = Typeface.createFromAsset(getAssets(),"fonts/a song for jennifer.ttf");
        title_textView.setTypeface(custom_font);


        compare_indexes_string = DataPreferences.readPreference(getApplicationContext(), DataPreferences.COMP_FOODS, DataPreferences.COMP_KEY);

        String[] indexes = compare_indexes_string.split(",");

        int food_A_position = Integer.parseInt(indexes[0]);
        int food_B_position = Integer.parseInt(indexes[1]);

        // list of all foods
        List<Food> tmp = FoodsData.foodsData;

        // getting the foods to compare
        Food food_A = tmp.get(food_A_position);
        Food food_B = tmp.get(food_B_position);

        // getting nutrients list relative to the foods to compare
        List<Nutrient> temp_nut_list_food_A = food_A.getNutList();
        List<Nutrient> temp_nut_list_food_B = food_B.getNutList();

        // getting nutrients from nutrients lists
        double proteins_food_A = Double.parseDouble(temp_nut_list_food_A.get(0).getValue());
        double proteins_food_B = Double.parseDouble(temp_nut_list_food_B.get(0).getValue());
        double lipids_food_A = Double.parseDouble(temp_nut_list_food_A.get(1).getValue());
        double lipids_food_B = Double.parseDouble(temp_nut_list_food_B.get(1).getValue());
        double carbohydrate_food_A = Double.parseDouble(temp_nut_list_food_A.get(2).getValue());
        double carbohydrate_food_B = Double.parseDouble(temp_nut_list_food_B.get(2).getValue());
        double kcal_food_A = Double.parseDouble(temp_nut_list_food_A.get(3).getValue());
        double kcal_food_B = Double.parseDouble(temp_nut_list_food_B.get(3).getValue());

        // getting Text View items
        TextView Food_A_name_textView = (TextView) findViewById(R.id.activity_compare_food_A);
        TextView Food_B_name_textView = (TextView) findViewById(R.id.activity_compare_food_B);
        TextView vs_textView = (TextView) findViewById(R.id.activity_compare_vs);

        // setting custom font
        Typeface custom_font_body = Typeface.createFromAsset(getAssets(), "fonts/Girls_Have_Many Secrets.ttf");

        // setting custom fonts
        Food_A_name_textView.setTypeface(custom_font_body);
        Food_B_name_textView.setTypeface(custom_font_body);
        vs_textView.setTypeface(custom_font_body);

        // getting foods names
        String Food_A_description = food_A.getDescription();
        String Food_B_description = food_B.getDescription();

        // putting values into text views
        Food_A_name_textView.setText(Food_A_description);
        Food_B_name_textView.setText(Food_B_description);

        // getting kcal progress bar title text view
        TextView kcal_progress_bar_title_textView = (TextView) findViewById(R.id.activity_compare_kcal_progress_bar_title);
        kcal_progress_bar_title_textView.setTypeface(custom_font_body);

        // getting kcal progress bar
        ProgressBar progressBar_kcal_food_A = (ProgressBar) findViewById(R.id.activity_compare_kcal_foodA_progressBar);
        ProgressBar progressBar_kcal_food_B = (ProgressBar) findViewById(R.id.activity_compare_kcal_foodB_progressBar);

        // setting kcal progress bar values
        progressBar_kcal_food_A.setProgress((int) kcal_food_A);
        progressBar_kcal_food_B.setProgress((int) kcal_food_B);

        // getting lipids progress bar title text view
        TextView lipids_progress_bar_title_textView = (TextView) findViewById(R.id.activity_compare_lipids_progress_bar_title);
        lipids_progress_bar_title_textView.setTypeface(custom_font_body);

        // getting lipids progress bar
        ProgressBar progressBar_lipids_food_A = (ProgressBar) findViewById(R.id.activity_compare_lipids_foodA_progressBar);
        ProgressBar progressBar_lipids_food_B = (ProgressBar) findViewById(R.id.activity_compare_lipids_foodB_progressBar);

        // setting lipids progress bar values
        progressBar_lipids_food_A.setProgress((int)lipids_food_A);
        progressBar_lipids_food_B.setProgress((int)lipids_food_B);

        // getting lipids progress bar title text view
        TextView proteins_progress_bar_title_textView = (TextView) findViewById(R.id.activity_compare_proteins_progress_bar_title);
        proteins_progress_bar_title_textView.setTypeface(custom_font_body);

        // getting lipids progress bar
        ProgressBar progressBar_proteins_food_A = (ProgressBar) findViewById(R.id.activity_compare_proteins_foodA_progressBar);
        ProgressBar progressBar_proteins_food_B = (ProgressBar) findViewById(R.id.activity_compare_proteins_foodB_progressBar);

        // setting lipids progress bar values
        progressBar_proteins_food_A.setProgress((int)proteins_food_A);
        progressBar_proteins_food_B.setProgress((int)proteins_food_B);

        // getting lipids progress bar title text view
        TextView carbohydrates_progress_bar_title_textView = (TextView) findViewById(R.id.activity_compare_carbohydrates_progress_bar_title);
        carbohydrates_progress_bar_title_textView.setTypeface(custom_font_body);

        // getting lipids progress bar
        ProgressBar progressBar_carbohydrates_food_A = (ProgressBar) findViewById(R.id.activity_compare_carbohydrates_foodA_progressBar);
        ProgressBar progressBar_carbohydrates_food_B = (ProgressBar) findViewById(R.id.activity_compare_carbohydrates_foodB_progressBar);

        // setting lipids progress bar values
        progressBar_carbohydrates_food_A.setProgress((int)carbohydrate_food_A);
        progressBar_carbohydrates_food_B.setProgress((int)carbohydrate_food_B);

        // reset the compare list
        SharedPreferences settings = getApplicationContext().getSharedPreferences(DataPreferences.COMP_FOODS, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();

        editor.clear();
        editor.commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_compare, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings)
        {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
