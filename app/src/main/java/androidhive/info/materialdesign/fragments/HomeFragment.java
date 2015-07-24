package androidhive.info.materialdesign.fragments;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import androidhive.info.materialdesign.classes.DataPreferences;
import androidhive.info.materialdesign.classes.Food;
import androidhive.info.materialdesign.classes.FoodsData;
import androidhive.info.materialdesign.R;
import androidhive.info.materialdesign.activity.FoodDetailsActivityHome;
import androidhive.info.materialdesign.classes.Nutrient;


public class HomeFragment extends Fragment
{
    public Context context = null;
    List<Food> data = null;
    String  start_data_string = null;
    String[] start_data_indexes = null;

    // test variables (to delete as soon as we finish the settings stuff)
    private int kcal_total = 0;
    private int kcal_consumed = 0;
    private double lipids = 0.0;
    private double carbohydrates = 0.0;
    private double proteins = 0.0;


    // adapter for the listView in this fragment
    public static ArrayAdapter<String> mUserFoodAdapter = null;

    public HomeFragment()
    {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        context = getActivity();    // get the current context
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {

        // build starting data for the HomeFragment listView
        List<String> start_data = getUserFoods();

        mUserFoodAdapter = new ArrayAdapter<String>(
                context,                            // the current context (this activity)
                R.layout.list_item_foodslist,       // the name of the layout to apply to each rows
                R.id.list_item_foodslist_textview,  // ID of textview to populate
                start_data                          // data
        );

        // inflate the fragment and setAdapter for listView
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);
        ListView listView = (ListView)rootView.findViewById(R.id.fragment_home_listview);

        listView.setAdapter(mUserFoodAdapter);

        if(!start_data_string.equals("no food added"))
        {
            // listView OnClick method
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                    // start FoodDetailsActivityHome and send the current food position
                    Intent intent = new Intent(getActivity(), FoodDetailsActivityHome.class)
                            .putExtra(Intent.EXTRA_TEXT, start_data_indexes[position]);

                    // start the activity
                    startActivity(intent);
                }
            });
        }


        /* GRAPHIC STUFF */
        // getting text view to apply custom font
        TextView title_home_textView = (TextView) rootView.findViewById(R.id.fragment_home_title);
        Typeface CF_title_home = Typeface.createFromAsset(getActivity().getAssets(), "fonts/a song for jennifer.ttf");
        title_home_textView.setTypeface(CF_title_home);

        // since the nutrients text view have the same font, we define a "general" custom font for nutrients names
        Typeface CF_nutrients_home = Typeface.createFromAsset(getActivity().getAssets(),"fonts/Girls_Have_Many Secrets.ttf");

        // apply custom font to kcal values
        TextView kcal_percentage_textView = (TextView) rootView.findViewById(R.id.fragment_home_percentage);
        TextView kcal_consumed_textView = (TextView) rootView.findViewById(R.id.fragment_home_kcal_consumed);
        TextView kcal_total_textView = (TextView) rootView.findViewById(R.id.fragment_home_kcal_total);
        kcal_percentage_textView.setTypeface(CF_nutrients_home);
        kcal_consumed_textView.setTypeface(CF_nutrients_home);
        kcal_total_textView.setTypeface(CF_nutrients_home);

        // apply nutrients custom font to all nutrients text view
        TextView proteins_title_textView = (TextView) rootView.findViewById(R.id.fragment_home_proteins);
        proteins_title_textView.setTypeface(CF_nutrients_home);

        TextView carboidrats_title_textView = (TextView) rootView.findViewById(R.id.fragment_home_carboidrats);
        carboidrats_title_textView.setTypeface(CF_nutrients_home);

        TextView lipids_title_textView = (TextView) rootView.findViewById(R.id.fragment_home_lipids);
        lipids_title_textView.setTypeface(CF_nutrients_home);

        TextView proteins_value_textView = (TextView) rootView.findViewById(R.id.fragment_home_proteins_value);
        proteins_value_textView.setTypeface(CF_nutrients_home);

        TextView carbohydrates_value_textView = (TextView) rootView.findViewById(R.id.fragment_home_carbohydrates_value);
        carbohydrates_value_textView.setTypeface(CF_nutrients_home);

        TextView lipids_value_textview = (TextView) rootView.findViewById(R.id.fragment_home_lipids_value);
        lipids_value_textview.setTypeface(CF_nutrients_home);


        /* PROGRESS BAR STUFF */
        // for loop in witch I get the foods elements and extract all nutrients value, in order to
        // update the total consumes

        // default case
        if (start_data_string.equals("no food added"));
        else if (data == null);
        else
        {
            for (int i = 0; i < start_data_indexes.length; i++)
            {
                // split info: position & portion
                String[] split_info = start_data_indexes[i].split(":");

                // obtain the index of the current food
                int food_position      = Integer.parseInt(split_info[0]);
                double current_portion = Double.parseDouble(split_info[1]);

                Log.v(" ######  HOME FRAGMENT ---> ", food_position + ":" + current_portion);

                // obtain the i-th food
                Food temp_food = data.get(food_position);

                // obtain the nutrient list
                List<Nutrient> nut_temp_list = temp_food.getNutList();

                for (int j = 0; j < nut_temp_list.size(); j++)
                {
                    if (nut_temp_list.get(j).getName().equals("Protein"))
                        proteins = proteins + (Double.parseDouble(nut_temp_list.get(j).getValue())*current_portion)/100;

                    else if (nut_temp_list.get(j).getName().equals("Total lipid (fat)"))
                        lipids = lipids + (Double.parseDouble(nut_temp_list.get(j).getValue())*current_portion)/100;

                    else if ((nut_temp_list.get(j).getName().equals("Carbohydrate, by difference")) || (nut_temp_list.get(j).getName().equals("Carbohydrate")))
                        carbohydrates = carbohydrates + (Double.parseDouble(nut_temp_list.get(j).getValue())*current_portion)/100;

                    else if (nut_temp_list.get(j).getName().equals("Energy"))
                        kcal_consumed = kcal_consumed + (Integer.parseInt(nut_temp_list.get(j).getValue())*(int)current_portion)/100;
                }
            }
        }

        // getting username string
        String temp_user_info = DataPreferences.readPreference(context, DataPreferences.PREFS_USER_INFO, DataPreferences.PUI_KEY);
        String[] info = temp_user_info.split(",");
        String username = info[0];
        TextView username_textView = (TextView) rootView.findViewById(R.id.fragment_home_welcome);
        username_textView.setTypeface(CF_nutrients_home);
        String welcome = "Benvenuto " + username + "!";
        username_textView.setText(welcome);

        // GET TOTAL USER KCAL
        kcal_total = get_user_kcal().intValue();

        // update text view values
        String proteins_string      = String.valueOf(rounding(proteins));
        String carbohydrates_string = String.valueOf(rounding(carbohydrates));
        String lipids_string        = String.valueOf(rounding(lipids));

        // display only the first 2 decimal values
        proteins_value_textView      .setText(proteins_string);
        carbohydrates_value_textView .setText(carbohydrates_string);
        lipids_value_textview        .setText(lipids_string);

        kcal_consumed_textView .setText(String.valueOf(kcal_consumed));
        kcal_total_textView    .setText(String.valueOf(kcal_total));

        double percentage = (double) kcal_consumed/kcal_total * 100;

        kcal_percentage_textView.setText(String.valueOf(rounding(percentage))+"%");

        // updating progress bar values
        final  ProgressBar progressBar = (ProgressBar) rootView.findViewById(R.id.fragment_home_progressBar);
        progressBar.setMax(kcal_total);
        progressBar.setProgress(kcal_consumed);

        return rootView;
    }


    public List<String> getUserFoods()
    {
        // build starting data for the HomeFragment listView
        List<String> start_data = new ArrayList<String>();


        // get all the foods data in the JSON file and stored in the FoodsData static class
        data = FoodsData.foodsData;

        // retrieve user foods stored with SharedPreferences method (saved just the indexes)
        start_data_string = DataPreferences.readPreference(context, DataPreferences.PREFS_USER_FOODS, DataPreferences.PUF_KEY);

        // split data indexes, because are separated with commas
        start_data_indexes = start_data_string.split(",");

        // default case
        if (start_data_string.equals("no food added"))
            start_data.add("no food added");

        else if (data == null)
            start_data.add("loading data");

        else
        {
            // for each food saved by the user
            for (int i = 0; i < start_data_indexes.length; i++)
            {
                // obtain the index of the current food
                String food_data = start_data_indexes[i];

                // split info: position & portion
                String[] split_info = food_data.split(":");

                // obtain the index of the current food
                int food_position = Integer.parseInt(split_info[0]);

                // add food description in the listView
                start_data.add(data.get(food_position).getDescription());

                // just a LOG
                // Log.d(" Home fragment: ----> ", data.get(food_position).getDescription());
            }
        }

        return start_data;
    }

    public Double get_user_kcal()
    {
        String temp_user_info = DataPreferences.readPreference(context, DataPreferences.PREFS_USER_INFO, DataPreferences.PUI_KEY);

        if (!temp_user_info.equals("no user info"))
        {
            String[] info = temp_user_info.split(",");

            Double temp = Double.valueOf(info[1]);

            return temp;
        }
        else
        {
            Double default_value = 500.3;
            return default_value;
        }
    }

    public static double rounding(double x)
    {
        x = Math.floor(x*100);
        x = x/100;
        return x;
    }

    public void onAttach(Activity activity)
    {
        super.onAttach(activity);
    }

    @Override
    public void onDetach()
    {
        super.onDetach();
    }


}
