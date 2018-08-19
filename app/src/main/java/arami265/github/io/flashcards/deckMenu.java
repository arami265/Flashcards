package arami265.github.io.flashcards;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class deckMenu extends AppCompatActivity {

    dbAdapter dbhelper;
    int currentDid;
    String currentDname;
    TextView deckNameForMenu;
    TextView takeQuizTextView;
    TextView deckSizeTextView;
    TextView comprehensionTextViewForMenu;
    TextView viewCardsTextView;
    boolean isDeckEmpty;
    String currentComp;
    List<cardItem> cardItems;
    String currentClName;

    @Override
    protected void onResume() {
        overridePendingTransition(0,0);
        super.onResume();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        initializeView();
        super.onActivityResult(requestCode, resultCode, data);
    }

    public void initializeView()
    {
        dbhelper = new dbAdapter(this);

        isDeckEmpty = dbhelper.isDeckEmpty(currentDid);
        if(isDeckEmpty)
        {
            comprehensionTextViewForMenu.setText("Confidence: N/A");
            deckSizeTextView.setText("# of cards in deck: 0");
        }
        else
        {
            cardItems = dbhelper.getCards(currentDid);
            deckSizeTextView.setText("There are " + dbhelper.getDeckSize(currentDid) + " cards in this deck.");

            float avgComp = dbhelper.getDeckComp(currentDid);
            if (avgComp < 0)
            {
                comprehensionTextViewForMenu.setText("Confidence: N/A");
            }
            else
            {
                currentComp = String.valueOf(avgComp);
                comprehensionTextViewForMenu.setText("Confidence: " + currentComp + "%");
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        overridePendingTransition(0,0);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deck_menu);

        takeQuizTextView = (TextView) findViewById(R.id.takeQuizTextView);
        deckSizeTextView = (TextView) findViewById(R.id.deckSizeTextView);
        viewCardsTextView = (TextView) findViewById(R.id.viewCardsTextView);
        comprehensionTextViewForMenu = (TextView) findViewById(R.id.comprehensionTextViewForMenu);

        takeQuizTextView.setTextColor(Color.BLACK);
        viewCardsTextView.setTextColor(Color.BLACK);

        currentDid = getIntent().getIntExtra("DID", -1);
        currentDname = getIntent().getStringExtra("DNAME");
        currentClName = getIntent().getStringExtra("CLNAME");


        Toolbar myToolbar = (Toolbar) findViewById(R.id.appBar);
        myToolbar.setTitle(currentDname);
        myToolbar.setSubtitle(currentClName);
        setSupportActionBar(myToolbar);

        initializeView();

        comprehensionTextViewForMenu.setOnLongClickListener(new View.OnLongClickListener()
        {
            @Override
            public boolean onLongClick(View view)
            {
                //Toast.makeText(getApplicationContext(), "Please enter card text.", Toast.LENGTH_SHORT).show();
                for(int i = 0; i < cardItems.size(); i++)
                {
                    cardItems.get(i).setComp(-1);
                }
                dbhelper.updateComp(cardItems);
                comprehensionTextViewForMenu.setText("Confidence: N/A");
                return true;
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.deck_menu_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_delete_deck)
        {
            dbhelper.deleteDeck(currentDid);
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

    public void addCards(View v)
    {
        Intent myIntent = new Intent(this, addCards.class);
        myIntent.putExtra("DID", currentDid);
        myIntent.putExtra("DNAME", currentDname);
        myIntent.putExtra("CLNAME", currentClName);
        startActivityForResult(myIntent, 0);
    }

    public void viewCards(View v)
    {
        if(!isDeckEmpty)
        {
            Intent myIntent = new Intent(this, displayCards.class);
            myIntent.putExtra("DID", currentDid);
            myIntent.putExtra("DNAME", currentDname);
            myIntent.putExtra("CLNAME", currentClName);
            startActivityForResult(myIntent, 0);
        }
        else
        {
            Toast.makeText(this, "Please add cards first...", Toast.LENGTH_SHORT).show();
        }
    }

    public void takeQuiz(View v)
    {

        if(!isDeckEmpty)
        {
            Intent myIntent = new Intent(this, displayQuiz.class);
            myIntent.putExtra("DID", currentDid);
            myIntent.putExtra("DNAME", currentDname);
            myIntent.putExtra("CLNAME", currentClName);
            startActivityForResult(myIntent, 0);
        }
        else
        {
            Toast.makeText(this, "Please add cards first...", Toast.LENGTH_SHORT).show();
        }
    }
}
