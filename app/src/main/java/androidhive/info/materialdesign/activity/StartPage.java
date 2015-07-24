package androidhive.info.materialdesign.activity;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidhive.info.materialdesign.classes.FetchFoodTaskOFFLINE;
import androidhive.info.materialdesign.classes.FoodsData;
import androidhive.info.materialdesign.R;


/** This is the first activity displayed when the app starts. **/
public class StartPage extends ActionBarActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_page);    // load the xml

        // start a background task to read foods data in the JSON file. After this all the data
        // are linked in the List<Food> FoodsData.foodsData.
        FetchFoodTaskOFFLINE foodTask = new FetchFoodTaskOFFLINE(getApplicationContext());
        foodTask.execute("");

        // linkage the button in the xml
        ImageButton startButton = (ImageButton) findViewById(R.id.activity_start_page_button);

        // onClick method for the startButton
        startButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                // wait loading data from the xml
                while(FoodsData.foodsData == null);

                // start the Activity
                Intent openActivity = new Intent(StartPage.this, MainActivity.class);
                startActivity(openActivity);

            }
        });

        TextView subtitle_start_page = (TextView) findViewById(R.id.activity_start_page_subtitle);
        Typeface CF_start_page = Typeface.createFromAsset(getAssets(),"fonts/uwch.ttf");
        subtitle_start_page.setTypeface(CF_start_page);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_start_page, menu);
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
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
