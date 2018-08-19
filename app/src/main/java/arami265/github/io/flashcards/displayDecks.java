package arami265.github.io.flashcards;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class displayDecks extends AppCompatActivity {

    private RecyclerView recyclerView;
    private TextView emptyFieldsView;
    private RecyclerView.Adapter adapter;
    dbAdapter dbhelper;
    int currentClid;
    String currentClname;

    private List<DeckListItem> deckListItems;

    @Override
    protected void onResume() {
        overridePendingTransition(0,0);
        super.onResume();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Intent myIntent = new Intent(this, displayDecks.class);
        myIntent.putExtra("CLID", currentClid);
        myIntent.putExtra("CLNAME", currentClname);
        startActivity(myIntent);
        finish();
        super.onActivityResult(requestCode, resultCode, data);
    }

    public void initializeRecyclerView()
    {
        dbhelper = new dbAdapter(this);

        currentClid = getIntent().getIntExtra("CLID", -1);
        currentClname = getIntent().getStringExtra("CLNAME");

        Toolbar myToolbar = (Toolbar) findViewById(R.id.appBar);
        myToolbar.setTitle("Decks for " + currentClname);
        setSupportActionBar(myToolbar);

        final Context context = this;

        //fieldListItems = new ArrayList<>();
        deckListItems = dbhelper.getDecks(currentClid);
        if(!deckListItems.isEmpty())
        {
            adapter = new DeckAdapter(deckListItems, this, this, currentClname);

            recyclerView = (RecyclerView) findViewById(R.id.deckRecyclerView);
            recyclerView.setHasFixedSize(true);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));

            recyclerView.setAdapter(adapter);
        }
        else
        {
            Toast.makeText(this, "No decks for this course...", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        overridePendingTransition(0,0);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_decks);

        initializeRecyclerView();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.display_decks_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_delete_class)
        {
            dbhelper.deleteClass(currentClid);
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

    /*public void moreDetails(View view)
    {
        Intent intent = new Intent(this, displayClassInfo.class);
        intent.putExtra("CLID", currentClid);
        intent.putExtra("CLNAME", currentClname);
        startActivity(intent);
    }*/

    public void addDeck(View view)
    {
        //String fields = dbhelper.getFieldNames();
        //Message.message(this, fields);

        Intent myIntent = new Intent(this, addDeck.class);
        myIntent.putExtra("CLNAME", currentClname);
        myIntent.putExtra("CLID", currentClid);
        startActivityForResult(myIntent, 0);
    }
}
