package dicarlo.fierimonte.infoodrmations.fragments;

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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import dicarlo.fierimonte.infoodrmations.R;
import dicarlo.fierimonte.infoodrmations.activity.FoodDetailsActivityHome;
import dicarlo.fierimonte.infoodrmations.activity.MainActivity;
import dicarlo.fierimonte.infoodrmations.classes.DataPreferences;


public class UserInformationsFragment extends Fragment
{
    String user_info_default;

    Context context = null;
    View rootView   = null;

    EditText eT_username = null,
            eT_age      = null,
            eT_weight   = null,
            eT_height   = null;

    Spinner sp_gender  = null,
            sp_psy_act = null,
            sp_work    = null;

    Button btn_calculate = null;

    String username = null;
    int height = 0;
    int weight = 0;
    int age    = 0;
    String work     = null;
    String phy_act  = null;
    String gender   = null;

    public UserInformationsFragment()
    {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        context = getActivity().getApplicationContext();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        // inflate the fragment
        rootView = inflater.inflate(R.layout.fragment_insert_informations, container, false);

        // set custom font on activity title
        TextView InsertInformations_title_textView = (TextView) rootView.findViewById(R.id.activity_insert_informations_title);
        Typeface CF_insert_informations_title = Typeface.createFromAsset(context.getAssets(), "fonts/a song for jennifer.ttf");
        InsertInformations_title_textView.setTypeface(CF_insert_informations_title);

        final String user_info_insert = DataPreferences.readPreference(rootView.getContext(),DataPreferences.PREFS_USER_INFO,DataPreferences.PUI_KEY);

        // variable that represent if are present user informations or not
        int profile_values = 0;

        if(user_info_insert.equals("no user info"))
            profile_values = 0;
        else
            profile_values = 1;

        if(profile_values == 0)
        {
            // set spinners values
            setSpinners();

            // BUTTON
            btn_calculate = (Button) rootView.findViewById(R.id.insert_info_submit_button);

            btn_calculate.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    int check;

                    // check the edits text values
                    check = getEditsTextValues();
                    getSpinnerValues();

                    Log.d(" CHECK ------------------------> ", Integer.toString(check));

                    if (check == 0)
                    {
                        double total_calories = calculate();
                        Toast.makeText(context, "TOTAL CALORIES: " + Double.toString(total_calories), Toast.LENGTH_SHORT).show();

                        // store user informations with SharedPreferences
                        String user_info = username + ","
                                + total_calories + ","
                                + gender + ","
                                + work + ","
                                + phy_act + ","
                                + Integer.toString(age) + ","
                                + Integer.toString(height) + ","
                                + Integer.toString(weight);
                        DataPreferences.writePreference(context, DataPreferences.PREFS_USER_INFO, DataPreferences.PUI_KEY, user_info);


                        // start the MainActivity
                        Intent openMainActivity = new Intent(context, MainActivity.class);
                        startActivity(openMainActivity);
                    }
                }
            });
        }

        else if(profile_values == 1)
        {
            // EDITS TEXT
            eT_username = (EditText) rootView.findViewById(R.id.insert_info_editText_username);
            eT_age      = (EditText) rootView.findViewById(R.id.insert_info_editText_age);
            eT_height   = (EditText) rootView.findViewById(R.id.insert_info_editText_height);
            eT_weight   = (EditText) rootView.findViewById(R.id.insert_info_editText_weight);

            // Set edit text values
            final String[] info = user_info_insert.split(",");

            // set spinners values
            setSpinners2(info[2], info[4], info[3]);

            // BUTTON
            btn_calculate = (Button) rootView.findViewById(R.id.insert_info_submit_button);

            btn_calculate.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    int check;

                    // get edit text values
                    String temp_username  = eT_username.getText().toString();
                    String temp_height    = eT_height.getText().toString();
                    String temp_weight    = eT_weight.getText().toString();
                    String temp_age       = eT_age.getText().toString();

                    // check edit text values
                    if(temp_username.length() <= 0)
                    {
                        temp_username = info[0];
                    }
                    if(temp_height.length() <= 0 || Integer.parseInt(temp_height)<20)
                    {
                        temp_height = info[6];
                    }
                    if(temp_weight.length() <= 0 || Integer.parseInt(temp_weight)<5)
                    {
                        temp_weight = info[7];
                    }
                    if(temp_age.length() <= 0 || Integer.parseInt(temp_age)<=0)
                    {
                        temp_age = info[5];
                    }

                    age    = Integer.valueOf(temp_age);
                    height = Integer.valueOf(temp_height);
                    weight = Integer.valueOf(temp_weight);

                    getSpinnerValues();

                    double total_calories = calculate();

                    total_calories = FoodDetailsActivityHome.rounding(total_calories);

                    // store user informations with SharedPreferences
                    String user_info = temp_username + ","
                            + total_calories + ","
                            + gender + ","
                            + work + ","
                            + phy_act + ","
                            + Integer.toString(age) + ","
                            + Integer.toString(height) + ","
                            + Integer.toString(weight);

                    DataPreferences.writePreference(context, DataPreferences.PREFS_USER_INFO, DataPreferences.PUI_KEY, user_info);

                    // start the MainActivity
                    Intent openMainActivity = new Intent(context, MainActivity.class);
                    startActivity(openMainActivity);
                    }

            });
        }

        return rootView;
    }

    private void setSpinners()
    {
        // SPINNERS (find and fill)
        sp_gender  = (Spinner) rootView.findViewById(R.id.insert_info_spinner_gender);
        sp_psy_act = (Spinner) rootView.findViewById(R.id.insert_info_spinner_phys_act);
        sp_work    = (Spinner) rootView.findViewById(R.id.insert_info_spinner_work);

        /*************************** SPINNER PHYSICAL ACTIVITY ***************************/
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> phy_act_adapter = ArrayAdapter.createFromResource(getActivity(), R.array.physical_activity, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        phy_act_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        sp_psy_act.setAdapter(phy_act_adapter);

        /*************************** SPINNER GENDER ***************************/
        ArrayAdapter<CharSequence> gender_adapter = ArrayAdapter.createFromResource(getActivity(), R.array.gender, android.R.layout.simple_spinner_item);
        gender_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp_gender.setAdapter(gender_adapter);
        /*************************** SPINNER WORK ***************************/
        ArrayAdapter<CharSequence> work_adapter = ArrayAdapter.createFromResource(getActivity(), R.array.work_type, android.R.layout.simple_spinner_item);
        work_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp_work.setAdapter(work_adapter);
    }

    private void setSpinners2(String gender_value, String psy_act_value, String work_value)
    {
        // SPINNERS (find and fill)
        sp_gender  = (Spinner) rootView.findViewById(R.id.insert_info_spinner_gender);
        sp_psy_act = (Spinner) rootView.findViewById(R.id.insert_info_spinner_phys_act);
        sp_work    = (Spinner) rootView.findViewById(R.id.insert_info_spinner_work);

        /*************************** SPINNER PHYSICAL ACTIVITY ***************************/
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> phy_act_adapter = ArrayAdapter.createFromResource(getActivity(), R.array.physical_activity, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        phy_act_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        sp_psy_act.setAdapter(phy_act_adapter);

        ArrayAdapter psy_actAdapt = (ArrayAdapter) sp_psy_act.getAdapter(); //cast to an ArrayAdapter

        int psy_actPosition = psy_actAdapt.getPosition(psy_act_value);
        sp_psy_act.setSelection(psy_actPosition);


        /*************************** SPINNER GENDER ***************************/
        ArrayAdapter<CharSequence> gender_adapter = ArrayAdapter.createFromResource(getActivity(), R.array.gender, android.R.layout.simple_spinner_item);
        gender_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp_gender.setAdapter(gender_adapter);

        ArrayAdapter genderAdapt = (ArrayAdapter) sp_gender.getAdapter(); //cast to an ArrayAdapter

        int genderPosition = genderAdapt.getPosition(gender_value);
        sp_gender.setSelection(genderPosition);


        /*************************** SPINNER WORK ***************************/
        ArrayAdapter<CharSequence> work_adapter = ArrayAdapter.createFromResource(getActivity(), R.array.work_type, android.R.layout.simple_spinner_item);
        work_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp_work.setAdapter(work_adapter);

        ArrayAdapter workAdapt = (ArrayAdapter) sp_work.getAdapter(); //cast to an ArrayAdapter

        int workPosition = workAdapt.getPosition(work_value);
        sp_work.setSelection(workPosition);

    }


    private int getEditsTextValues()
    {
        // EDITS TEXT
        eT_username = (EditText) rootView.findViewById(R.id.insert_info_editText_username);
        eT_age      = (EditText) rootView.findViewById(R.id.insert_info_editText_age);
        eT_height   = (EditText) rootView.findViewById(R.id.insert_info_editText_height);
        eT_weight   = (EditText) rootView.findViewById(R.id.insert_info_editText_weight);

        int check = 0;


        // get edit text values
        username             = eT_username.getText().toString();
        String temp_height   = eT_height.getText().toString();
        String temp_weight   = eT_weight.getText().toString();
        String temp_age      = eT_age.getText().toString();


        // check edit text values
        if(username.length() <= 0)
        {
                check = 1;
                Toast.makeText(context, "Enter username", Toast.LENGTH_SHORT).show();
        }
        else if(temp_height.length() <= 0 || Integer.parseInt(temp_height)<20)
        {
            check = 1;
            Toast.makeText(context, "Enter correct height", Toast.LENGTH_SHORT).show();
        }
        else if(temp_weight.length() <= 0 || Integer.parseInt(temp_weight)<5)
        {
            check = 1;
            Toast.makeText(context, "Enter correct weight", Toast.LENGTH_SHORT).show();
        }
        else if(temp_age.length() <= 0 || Integer.parseInt(temp_age)<=0)
        {
            check = 1;
            Toast.makeText(context, "Enter correct age", Toast.LENGTH_SHORT).show();
        }

        Log.d(" CHECK ------------------------>", temp_height + "-" + Integer.toString(temp_height.length()));
        Log.d( " CHECK ------------------------>", temp_weight + "-" + Integer.toString(temp_weight.length()) );
        Log.d(" CHECK ------------------------>", temp_age + "-" + Integer.toString(temp_age.length()));

        if (check == 0)
        {
            age    = Integer.valueOf(temp_age);
            height = Integer.valueOf(temp_height);
            weight = Integer.valueOf(temp_weight);
        }

        return check;
    }

    private void getSpinnerValues()
    {
        gender  = sp_gender.getSelectedItem().toString();
        work    = sp_work.getSelectedItem().toString();
        phy_act = sp_psy_act.getSelectedItem().toString();

        //Log.d( " SPINNER------------->", gender + "-" + work + "-" + phy_act);
    }


    // TO COMPUTE BASAL METABOLISM
    // WOMEN: 655 + (9.6*weight(kg)) + (1.8*height(cm)) - (4.7*years)
    // MEN:   66 + (13.7*weight(kg)) + (5*height(cm)) - (6.8*years)
    private double calculate()
    {
        double a, b, c, phy_act_share;
        double basal_metabolism = 0;
        double daily_calories = 0;
        double calories = 0;
        double total_calories_needed = 0;


        if (gender.equals("Female"))
        {
            // female
            a = 9.6 * weight;
            b = 1.8 * height;
            c = 4.7 * age;

            basal_metabolism = 655 + a + b - c;
        }
        else if (gender.equals("Male"))
        {
            // man
            a = 13.7 * weight;
            b = 5 * height;
            c = 6.8 * age;

            basal_metabolism = 66 + a + b - c;
        }

        // compute the daily calories needed
        // Termogenesi Indotta Dieta (TID)
        daily_calories = basal_metabolism * 0.10;

        // add the works informations
        if (work.equals("Sedentary"))
        {
            calories = (basal_metabolism * 1.25) + daily_calories;
        }
        else if (work.equals("Light"))
        {
            calories = (basal_metabolism * 1.45) + daily_calories;
        }
        else if (work.equals("Moderate"))
        {
            calories = (basal_metabolism * 1.65) + daily_calories;
        }
        else if (work.equals("Weighty"))
        {
            calories = (basal_metabolism * 1.85) + daily_calories;
        }

        // add the physical activity informations
        if (phy_act.equals("Less than 3 hours a week"))
        {
            phy_act_share = basal_metabolism * 6.5/100;
            total_calories_needed = calories + phy_act_share;
        }
        else if (phy_act.equals("Less than 4 hours a week"))
        {
            phy_act_share = basal_metabolism * 11/100;
            total_calories_needed = calories + phy_act_share;
        }
        else if (phy_act.equals("Less than 5 hours a week"))
        {
            phy_act_share = basal_metabolism * 15/100;
            total_calories_needed = calories + phy_act_share;
        }
        else if (phy_act.equals("Less than 6 hours a week"))
        {
            phy_act_share = basal_metabolism * 19/100;
            total_calories_needed = calories + phy_act_share;
        }
        else if (phy_act.equals("Less than 7 hours a week"))
        {
            phy_act_share = basal_metabolism * 23.5/100;
            total_calories_needed = calories + phy_act_share;
        }
        else if (phy_act.equals("Less than 8 hours a week"))
        {
            phy_act_share = basal_metabolism * 27.5/100;
            total_calories_needed = calories + phy_act_share;
        }
        else if (phy_act.equals("Less than 9 hours a week"))
        {
            phy_act_share = basal_metabolism * 31.5/100;
            total_calories_needed = calories + phy_act_share;
        }
        else if (phy_act.equals("Less than 10 hours a week"))
        {
            phy_act_share = basal_metabolism * 36/100;
            total_calories_needed = calories + phy_act_share;
        }

        return total_calories_needed;
    }


    @Override
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
