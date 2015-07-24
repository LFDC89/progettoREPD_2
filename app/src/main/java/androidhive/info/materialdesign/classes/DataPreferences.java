package androidhive.info.materialdesign.classes;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Marco on 13/07/2015.
 */
public class DataPreferences
{
    private static SharedPreferences settings = null;

    public static final String PREFS_USER_FOODS = "user_food_list";
    public static final String PUF_KEY = "ufl_key";

    public static final String PREFS_USER_INFO = "user_info";
    public static final String PUI_KEY = "ui_key";

    public static final String COMP_FOODS = "compare_food_list";
    public static final String COMP_KEY = "compare_key";

    private DataPreferences()
    {
    }

    public static void writePreference(Context context, String preference_name, String key, String value)
    {
        settings = context.getSharedPreferences(preference_name, Context.MODE_PRIVATE);

        // Writing data to SharedPreferences
        SharedPreferences.Editor editor = settings.edit();


        if (preference_name.equals(PREFS_USER_FOODS))
        {
            // Reading old data with SharedPreferences
            String user_foods_string = settings.getString(key, "no food added");

            if (user_foods_string.equals("no food added"))
            {
                editor.putString(key, value + ",");
                editor.commit();
            }
            else
            {
                editor.putString(key, user_foods_string + value + ",");
                editor.commit();
            }
        }
        else if (preference_name.equals(PREFS_USER_INFO))
        {
            // save user informations
            editor.putString(key,  value);
            editor.commit();
        }
        else if(preference_name.equals(COMP_FOODS) )
        {
            String compare_food_list = settings.getString(key,"no food on compare list");

            // save food indexes to compare
            if(compare_food_list.equals("no food on compare list"))
            {
                editor.putString(key, value + ",");
                editor.commit();
            }
            else
            {
                editor.putString(key, compare_food_list + value + ",");
                editor.commit();
            }

        }

    }

    public static String readPreference(Context context, String preference_name, String key)
    {
        settings = context.getSharedPreferences(preference_name, Context.MODE_PRIVATE);

        if (preference_name.equals(PREFS_USER_FOODS))
        {
            // Reading from SharedPreferences
            String user_foods_string = settings.getString(key, "no food added");
            return user_foods_string;
        }
        else if (preference_name.equals(PREFS_USER_INFO))
        {
            // Reading from SharedPreferences
            String user_info_string = settings.getString(key, "no user info");
            return user_info_string;
        }
        else if (preference_name.equals(COMP_FOODS))
        {
            // Reading from shared preferences
            String compare_foods_string = settings.getString(key, "no food on compare list");
            return compare_foods_string;
        }


        return null;
    }


}