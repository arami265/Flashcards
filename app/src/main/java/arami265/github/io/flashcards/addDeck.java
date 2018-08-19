package arami265.github.io.flashcards;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class addDeck extends AppCompatActivity {

    dbAdapter dbhelper;
    int currentClid;
    String currentClname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        overridePendingTransition(0,0);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_deck);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.appBar);
        myToolbar.setTitle("Add a deck:");
        setSupportActionBar(myToolbar);

        dbhelper = new dbAdapter(this);
        currentClname = getIntent().getStringExtra("CLNAME");
        currentClid = getIntent().getIntExtra("CLID", -1);

        /*List<String> spinnerArray =  dbhelper.getFieldNamesAsStrings();

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this, android.R.layout.simple_spinner_item, spinnerArray);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Spinner sItems = (Spinner) findViewById(R.id.fieldSpinner);
        sItems.setAdapter(adapter);*/

    }

    public void addDeckMethod(View v)
    {
        EditText deckNameInput   = (EditText)findViewById(R.id.deckNameInput);
        String deckName = deckNameInput.getText().toString();

        EditText deckDescriptionInput   = (EditText)findViewById(R.id.deckDescriptionInput);
        String description = deckDescriptionInput.getText().toString();


        if(!deckName.toString().isEmpty())
        {
            long id = dbhelper.insertDeck(deckName, description, currentClid);
            if (id < 0) {
                Message.message(this, "Unsuccessful.");
            } else {
                //Message.message(this, "Successfully inserted a deck.");

                Intent myIntent = new Intent(this, displayDecks.class);
                myIntent.putExtra("CLNAME", currentClname);
                myIntent.putExtra("CLID", currentClid);
                finish();
                startActivity(myIntent);
            }
        }
        else
        {
            Toast.makeText(this, "Please enter a deck name.", Toast.LENGTH_SHORT).show();
        }


    }
}