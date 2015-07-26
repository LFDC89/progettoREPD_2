package dicarlo.fierimonte.infoodrmations.fragments;


import android.content.Intent;
import android.graphics.Typeface;
import android.inputmethodservice.Keyboard;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import dicarlo.fierimonte.infoodrmations.R;
import dicarlo.fierimonte.infoodrmations.activity.MainActivity;


public class CreditsFragment extends android.support.v4.app.Fragment {


    public CreditsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_credits, container, false);

        TextView title_credits_textView = (TextView) rootView.findViewById(R.id.fragment_credits_title);
        Typeface custom_font_credits_title = Typeface.createFromAsset(getActivity().getAssets(), "fonts/a song for jennifer.ttf");
        title_credits_textView.setTypeface(custom_font_credits_title);

        TextView text_credits_textView = (TextView) rootView.findViewById(R.id.fragment_credits_text);
        Typeface custom_font_credits_text = Typeface.createFromAsset(getActivity().getAssets(), "fonts/Girls_Have_Many Secrets.ttf");
        text_credits_textView.setTypeface(custom_font_credits_text);

        TextView signature_credits_textView = (TextView) rootView.findViewById(R.id.fragment_credits_signatures);
        Typeface CF_credits_signatures = Typeface.createFromAsset(getActivity().getAssets(),"fonts/Jennifer Lynne.ttf");
        signature_credits_textView.setTypeface(CF_credits_signatures);


        return rootView;
    }

}
