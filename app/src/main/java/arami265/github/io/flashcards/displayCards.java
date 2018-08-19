package arami265.github.io.flashcards;

import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class displayCards extends AppCompatActivity {

    int currentDid;
    String currentDname;
    String currentClName;
    TextView deckNameForCards;
    dbAdapter dbhelper;
    TextView viewCardsProgress;
    CardView viewCardsCardView;
    ConstraintLayout viewCardButtonsLayout;
    List<cardItem> cardItems;
    CardView displayCardsCardView;

    TextView cardTitleTextView;
    TextView cardTextTextView;

    ImageView backButton, forwardButton, deleteCardImage;

    int currentCard;
    int deckSize;

    @Override
    protected void onResume() {
        overridePendingTransition(0,0);
        super.onResume();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        overridePendingTransition(0,0);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_cards);

        currentDid = getIntent().getIntExtra("DID", -1);
        currentDname = getIntent().getStringExtra("DNAME");
        currentClName = getIntent().getStringExtra("CLNAME");

        Toolbar myToolbar = (Toolbar) findViewById(R.id.appBar);
        myToolbar.setTitle(currentDname);
        myToolbar.setSubtitle(currentClName);
        setSupportActionBar(myToolbar);

        dbhelper = new dbAdapter(this);

        currentCard = 0;

        displayCardsCardView = (CardView) findViewById(R.id.displayCardsCardView);

        viewCardsProgress = (TextView) findViewById(R.id.viewCardsProgress);
        viewCardsProgress.setVisibility(View.GONE);

        viewCardsCardView = (CardView) findViewById(R.id.displayCardsCardView);
        viewCardsCardView.setVisibility(View.GONE);

        forwardButton = (ImageView) findViewById(R.id.forwardButton);
        backButton = (ImageView) findViewById(R.id.backButton);
        forwardButton.setVisibility(View.GONE);
        backButton.setVisibility(View.GONE);

        deleteCardImage = (ImageView) findViewById(R.id.deleteCardImage);
        deleteCardImage.setVisibility(View.GONE);

        cardTitleTextView = (TextView) findViewById(R.id.cardTitleTextView);
        cardTextTextView = (TextView) findViewById(R.id.cardTextTextView);

        boolean isDeckEmpty = dbhelper.isDeckEmpty(currentDid);
        if(!isDeckEmpty)
        {
            deckSize = dbhelper.getDeckSize(currentDid);
            cardItems = dbhelper.getCards(currentDid);

            viewCardsProgress.setText("Card " + (currentCard+1) + "/" + (deckSize));

            if(!cardItems.get(currentCard).getCtitle().isEmpty())
            {
                cardTitleTextView.setText(cardItems.get(currentCard).getCtitle());
                cardTitleTextView.setVisibility(View.VISIBLE);
            }
            else
            {
                cardTitleTextView.setVisibility(View.GONE);
            }

            cardTextTextView.setText(cardItems.get(currentCard).getCtext());

            viewCardsProgress.setVisibility(View.VISIBLE);
            viewCardsCardView.setVisibility(View.VISIBLE);
            forwardButton.setVisibility(View.VISIBLE);
            backButton.setVisibility(View.VISIBLE);
            deleteCardImage.setVisibility(View.VISIBLE);


        }
        else
        {
            Toast.makeText(this, "No cards in this deck...", Toast.LENGTH_SHORT).show();
        }
    }

    public void deleteCard(View view)
    {
        int cid = cardItems.get(currentCard).getCid();

        if(deckSize == 1)
        {
            viewCardsProgress.setVisibility(View.GONE);
            viewCardsCardView.setVisibility(View.GONE);
            forwardButton.setVisibility(View.GONE);
            backButton.setVisibility(View.GONE);
            deleteCardImage.setVisibility(View.GONE);
            Toast.makeText(this, "No cards in this deck...", Toast.LENGTH_SHORT).show();

            dbhelper.deleteCard(cid);
            finish();
        }
        else
        {
            dbhelper.deleteCard(cid);
            cardItems.remove(currentCard);
            if(currentCard != (deckSize-1))
                currentCard++;
            deckSize--;

            prevCard(findViewById(android.R.id.content));
        }
    }

    public void nextCard(View v)
    {
        if(currentCard == (deckSize-1))
            currentCard = 0;
        else
            currentCard++;

        viewCardsProgress.setText("Card " + (currentCard+1) + "/" + (deckSize));

        if(!cardItems.get(currentCard).getCtitle().isEmpty())
        {
            cardTitleTextView.setText(cardItems.get(currentCard).getCtitle());
            cardTitleTextView.setVisibility(View.VISIBLE);
        }
        else
        {
            cardTitleTextView.setVisibility(View.GONE);
        }

        cardTextTextView.setText(cardItems.get(currentCard).getCtext());
    }

    public void prevCard(View v)
    {
        if(currentCard == 0)
            currentCard = (deckSize-1);
        else
            currentCard--;

        viewCardsProgress.setText("Card " + (currentCard+1) + "/" + (deckSize));

        if(!cardItems.get(currentCard).getCtitle().isEmpty())
        {
            cardTitleTextView.setText(cardItems.get(currentCard).getCtitle());
            cardTitleTextView.setVisibility(View.VISIBLE);
        }
        else
        {
            cardTitleTextView.setVisibility(View.GONE);
        }

        cardTextTextView.setText(cardItems.get(currentCard).getCtext());
    }
}
