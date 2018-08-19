package arami265.github.io.flashcards;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;
import java.util.Random;

public class displayQuiz extends AppCompatActivity {

    int currentDid;
    String currentDname;
    TextView deckNameForCards;
    dbAdapter dbhelper;
    TextView viewCardsProgress;
    CardView viewCardCardView;
    LinearLayout viewCardButtonsLayout;
    List<cardItem> cardItems;

    TextView cardTitleTextView;
    TextView cardTextTextView;
    TextView descriptorTextView;
    Button nextButton;
    SeekBar seekBar;

    int currentCard;
    int deckSize;

    int[] cardOrder;

    public int[] randomizeCardOrder(int length)
    {
        int[] cardOrder = new int[length];
        for(int i = 0; i < length; i++)
        {
            cardOrder[i] = i;
        }

        Random random = new Random();

        for (int i = (cardOrder.length)-1; i > 0; i--)
        {

            // Pick a random index from 0 to i
            int j = random.nextInt(i);

            // Swap arr[i] with the element at random index
            int temp = cardOrder[i];
            cardOrder[i] = cardOrder[j];
            cardOrder[j] = temp;
        }

        return cardOrder;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_quiz);

        dbhelper = new dbAdapter(this);
        nextButton = (Button) findViewById(R.id.nextButton);
        seekBar = (SeekBar) findViewById(R.id.seekBar);

        currentDid = getIntent().getIntExtra("DID", -1);
        currentDname = getIntent().getStringExtra("DNAME");

        deckNameForCards = (TextView) findViewById(R.id.deckNameForCards);
        deckNameForCards.setText(currentDname);

        viewCardsProgress = (TextView) findViewById(R.id.viewCardsProgress);
        viewCardsProgress.setVisibility(View.GONE);

        viewCardCardView = (CardView) findViewById(R.id.displayCardsCardView);
        viewCardCardView.setVisibility(View.GONE);

        viewCardButtonsLayout = (LinearLayout) findViewById(R.id.viewCardButtonsLayout);
        viewCardButtonsLayout.setVisibility(View.GONE);

        cardTitleTextView = (TextView) findViewById(R.id.cardTitleTextView);
        cardTextTextView = (TextView) findViewById(R.id.cardTextTextView);

        descriptorTextView = (TextView) findViewById(R.id.descriptorTextView);


        boolean isDeckEmpty = dbhelper.isDeckEmpty(currentDid);
        if(!isDeckEmpty)
        {
            seekBar.setProgress(55);

            descriptorTextView.setText("Somewhat");
            seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                    if(i < 25)
                        descriptorTextView.setText("Not at all");
                    else if(i < 50)
                        descriptorTextView.setText("Not much");
                    else if(i < 66)
                        descriptorTextView.setText("Somewhat");
                    else
                        descriptorTextView.setText("Very well");
                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {

                }

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {

                }
            });

            deckSize = dbhelper.getDeckSize(currentDid);
            cardItems = dbhelper.getCards(currentDid);

            currentCard = 0;

            //Create array of ints to know what order to quiz
            cardOrder = randomizeCardOrder(deckSize);


            viewCardsProgress.setText("Card " + (currentCard+1) + "/" + (deckSize));

            if(!cardItems.get(cardOrder[currentCard]).getCtitle().isEmpty())
            {
                cardTitleTextView.setText(cardItems.get(cardOrder[currentCard]).getCtitle());
                cardTitleTextView.setVisibility(View.VISIBLE);
            }
            else
            {
                cardTitleTextView.setVisibility(View.GONE);
            }

            cardTextTextView.setText(cardItems.get(cardOrder[currentCard]).getCtext());

            viewCardsProgress.setVisibility(View.VISIBLE);
            viewCardCardView.setVisibility(View.VISIBLE);
            viewCardButtonsLayout.setVisibility(View.VISIBLE);


        }
        else
        {
            Toast.makeText(this, "No cards in this deck...", Toast.LENGTH_SHORT).show();
        }
    }

    public void nextCard(View v)
    {
        if(currentCard == (deckSize-1))
        {
            cardItems.get(cardOrder[currentCard]).setComp(seekBar.getProgress());
            dbhelper.updateComp(cardItems);
            finish();
        }
        else
        {
            //Deal with seekbar here
            cardItems.get(cardOrder[currentCard]).setComp(seekBar.getProgress());
            seekBar.setProgress(55);

            currentCard++;

            if(currentCard == (deckSize-1))
                nextButton.setText("Finish");

            viewCardsProgress.setText("Card " + (currentCard+1) + "/" + (deckSize));

            if(!cardItems.get(cardOrder[currentCard]).getCtitle().isEmpty())
            {
                cardTitleTextView.setText(cardItems.get(cardOrder[currentCard]).getCtitle());
                cardTitleTextView.setVisibility(View.VISIBLE);
            }
            else
            {
                cardTitleTextView.setVisibility(View.GONE);
            }

            cardTextTextView.setText(cardItems.get(cardOrder[currentCard]).getCtext());
        }
    }

}
