package androidhive.info.materialdesign.fragments;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import androidhive.info.materialdesign.R;
import androidhive.info.materialdesign.classes.Food;
import androidhive.info.materialdesign.classes.FoodsData;

public class CompareFragment extends Fragment {


    public CompareFragment()
    {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {super.onCreate(savedInstanceState);}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View rootView = inflater.inflate(R.layout.fragment_compare, container, false);

        // Getting food list
        List<Food> tmp = FoodsData.foodsData;

        return rootView;
    }


    @Override
    public void onAttach(Activity activity) {super.onAttach(activity);}

    @Override
    public void onDetach() {super.onDetach();}
}