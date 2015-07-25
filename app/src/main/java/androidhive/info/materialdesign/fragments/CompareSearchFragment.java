package androidhive.info.materialdesign.fragments;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.app.Fragment;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import androidhive.info.materialdesign.R;
import androidhive.info.materialdesign.activity.FoodDetailsActivityCompare;
import androidhive.info.materialdesign.activity.FoodDetailsActivitySearch;
import androidhive.info.materialdesign.classes.Food;
import androidhive.info.materialdesign.classes.FoodsData;
import androidhive.info.materialdesign.classes.Nutrient;

public class CompareSearchFragment extends android.support.v4.app.Fragment
{
    public static ArrayAdapter<String> mFoodsAdapter = null;
    private List<String> start_data = null;

    private static Map<String, String> real_position_food = new HashMap<>();

    // empty constructor
    public CompareSearchFragment() {}

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {

        // listView start data
        start_data = new ArrayList<String>();
        start_data.add(" loading data...");

        mFoodsAdapter = new ArrayAdapter<String>(
                getActivity(),                      // the current context (this activity)
                R.layout.list_item_foodslist,       // the name of the layout to apply to each rows
                R.id.list_item_foodslist_textview,  // ID of textview to populate
                start_data                          // data
        );

        View rootView = inflater.inflate(R.layout.fragment_compare, container, false);

        // getting explanation text view
        TextView fragment_compare_explanation_textView = (TextView) rootView.findViewById(R.id.fragment_compare_explanation);
        Typeface custom_font = Typeface.createFromAsset(getActivity().getAssets(), "fonts/Girls_Have_Many Secrets.ttf");
        fragment_compare_explanation_textView.setTypeface(custom_font);

        // input search EditText id and method for SEARCH FOOD
        EditText inputSearch = (EditText) rootView.findViewById(R.id.search_compare_edit_text);
        inputSearch.addTextChangedListener(new TextWatcher()
        {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after)
            {
                //
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count)
            {
                // When user changed the Text
                CompareSearchFragment.this.mFoodsAdapter.getFilter().filter(s);
            }

            @Override
            public void afterTextChanged(Editable s)
            {
                //
            }
        });

        // listview
        ListView listView = (ListView)rootView.findViewById(R.id.listview_compare_foodslist);


        listView.setAdapter(mFoodsAdapter);

        // listView onClick method
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id)
            {
                // get the REAL indexes of food in the listView
                String real_index = real_position_food.get(CompareSearchFragment.mFoodsAdapter.getItem(position));

                //Log.v(" POSITION : ", " =====================> " + real_index);

                // start the FoodDetailsActivitySearch and send the food position in the listView
                Intent intent = new Intent(getActivity(), FoodDetailsActivityCompare.class)
                        .putExtra(Intent.EXTRA_TEXT, real_index);
                startActivity(intent);
            }
        });

        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);

        // point to all the data
        List<Food> temp = FoodsData.foodsData;
        CompareSearchFragment.mFoodsAdapter.clear();

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
            String final_string = food_desc;

            // update the ListView
            CompareSearchFragment.mFoodsAdapter.add(final_string);
            CompareSearchFragment.mFoodsAdapter.notifyDataSetChanged();
        }

        for(int i=0; i<temp.size(); i++)
        {
            // create a key-value association where key is listView text and value is the real index of the item
            real_position_food.put(CompareSearchFragment.mFoodsAdapter.getItem(i), Integer.toString(i));
        }

    }

    @Override
    public void onDetach() {
        super.onDetach();
    }


}
