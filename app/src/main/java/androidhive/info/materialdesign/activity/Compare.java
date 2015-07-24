package androidhive.info.materialdesign.activity;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import androidhive.info.materialdesign.R;

public class Compare extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compare);

        TextView prova_textView = (TextView) findViewById(R.id.prova);
        TextView prova2_textView = (TextView) findViewById(R.id.prova2);

        Intent recieve_intent = getIntent();

        String recived_string[] = recieve_intent.getExtras().getStringArray("Food_positions");


        prova_textView.setText(String.valueOf(recived_string[0]));
        prova2_textView.setText(String.valueOf(recived_string[1]));

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
