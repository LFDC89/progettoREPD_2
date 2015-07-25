package androidhive.info.materialdesign.fragments;

import android.app.Activity;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidhive.info.materialdesign.R;
import androidhive.info.materialdesign.classes.DataPreferences;


public class PrintUserInformations extends android.support.v4.app.Fragment {

    public PrintUserInformations() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {super.onCreate(savedInstanceState);}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View rootView = inflater.inflate(R.layout.fragment_print_user_informations, container, false);

        // Text Views definition
        TextView title_texView     = (TextView) rootView.findViewById(R.id.fragment_print_user_informations_title);
        TextView username_textView = (TextView) rootView.findViewById(R.id.fragment_print_user_informations_username);
        TextView age_textView      = (TextView) rootView.findViewById(R.id.fragment_print_user_informations_age);
        TextView height_textView   = (TextView) rootView.findViewById(R.id.fragment_print_user_informations_height);
        TextView weight_textView   = (TextView) rootView.findViewById(R.id.fragment_print_user_informations_weight);
        TextView gender_textView   = (TextView) rootView.findViewById(R.id.fragment_print_user_informations_gender);
        TextView work_textView     = (TextView) rootView.findViewById(R.id.fragment_print_user_informations_work);
        TextView phys_act_textView = (TextView) rootView.findViewById(R.id.fragment_print_user_informations_physical_activity);
        TextView kcal_tot_textView = (TextView) rootView.findViewById(R.id.fragment_print_user_informations_kcal_total);


        // getting data from shared preferences
        String temp_user_info = DataPreferences.readPreference(rootView.getContext(), DataPreferences.PREFS_USER_INFO, DataPreferences.PUI_KEY);
        String[] info = temp_user_info.split(",");

        if(!temp_user_info.equals("no user info"))
        {
            // putting data into strings
            String username = info[0];
            String kcal_tot = info[1];
            String gender = info[2];
            String work = info[3];
            String phy_act = info[4];
            String age = info[5];
            String height = info[6];
            String weight = info[7];

            // rounding kcal tot at two decimal digits
            double kcalTot = Double.parseDouble(kcal_tot);
            kcalTot = rounding(kcalTot);
            kcal_tot = String.valueOf(kcalTot);

            // putting data into Text Views
            username_textView.setText("USERNAME:  " + username);
            kcal_tot_textView.setText("\nDAILY KCAL REQUIREMENTS\n\n" + kcal_tot + " kcal");
            gender_textView.setText("GENDER:  " + gender);
            work_textView.setText("WORK:  " + work);
            phys_act_textView.setText("PHYS. ACTIVITY:  " + phy_act);
            age_textView.setText("AGE:  " + age);
            height_textView.setText("HEIGHT:  " + height + " cm");
            weight_textView.setText("WEIGHT:  " + weight + " kg");
        }
        // custom font definition
        Typeface custom_font = Typeface.createFromAsset(getActivity().getAssets(),"fonts/Girls_Have_Many Secrets.ttf");
        Typeface custom_font_title = Typeface.createFromAsset(getActivity().getAssets(),"fonts/a song for jennifer.ttf");

        // custom font application
        title_texView.setTypeface(custom_font_title);
        username_textView.setTypeface(custom_font);
        kcal_tot_textView.setTypeface(custom_font);
        gender_textView.setTypeface(custom_font);
        work_textView.setTypeface(custom_font);
        phys_act_textView.setTypeface(custom_font);
        age_textView.setTypeface(custom_font);
        height_textView.setTypeface(custom_font);
        weight_textView.setTypeface(custom_font);

        return rootView;
    }

    @Override
    public void onAttach(Activity activity) {super.onAttach(activity);}

    @Override
    public void onDetach() {super.onDetach();}

    public static double rounding(double x)
    {
        x = Math.floor(x*100);
        x = x/100;
        return x;
    }
}
