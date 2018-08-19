package arami265.github.io.flashcards;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class addCards extends AppCompatActivity {

    int currentDid;
    String currentDname, currentClName;
    TextView deckNameForAddCards;
    dbAdapter dbhelper;
    TextView currentCardToAddText;

    void initializeView()
    {
        currentCardToAddText.setText("This will be card #" + (dbhelper.getDeckSize(currentDid)+1) + " in this deck.");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_cards);

        dbhelper = new dbAdapter(this);

        currentDid = getIntent().getIntExtra("DID", -1);
        currentDname = getIntent().getStringExtra("DNAME");
        currentClName = getIntent().getStringExtra("CLNAME");

        Toolbar myToolbar = (Toolbar) findViewById(R.id.appBar);
        myToolbar.setTitle("Add cards to deck: " + currentDname);
        myToolbar.setSubtitle(currentClName);
        setSupportActionBar(myToolbar);

        currentCardToAddText = (TextView) findViewById(R.id.currentCardToAddText);

        initializeView();



    }

    public void addNewCard(View v)
    {
        EditText cardTitleInput   = (EditText)findViewById(R.id.cardTitleInput);
        String cardTitle = cardTitleInput.getText().toString();

        EditText cardTextInput   = (EditText)findViewById(R.id.cardTextInput);
        String cardText = cardTextInput.getText().toString();

        if(!cardText.isEmpty())
        {
            long id = dbhelper.insertCard(currentDid, cardTitle, cardText);
            if (id < 0) {
                Message.message(this, "Unsuccessful.");
            } else {
                //Message.message(this, "Successfully inserted a deck.");

                /*Intent myIntent = new Intent(this, displayDecks.class);
                myIntent.putExtra("CLID", currentClid);
                startActivity(myIntent);*/

                cardTitleInput.setText("");
                cardTextInput.setText("");

                /*Intent myIntent = new Intent(this, addCards.class);
                myIntent.putExtra("DID", currentDid);
                myIntent.putExtra("DNAME", currentDname);
                finish();
                startActivity(myIntent);*/
                initializeView();
            }
        }
        else
        {
            Toast.makeText(this, "Please enter card text.", Toast.LENGTH_SHORT).show();
        }
    }

    public void doneAddingCards(View v)
    {
        finish();
    }
}
