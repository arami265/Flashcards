package arami265.github.io.flashcards;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

/**
 * Created by arami265 on 4/11/2017.
 */

public class DeckAdapter extends RecyclerView.Adapter<DeckAdapter.ViewHolder> {

    private List<DeckListItem> deckListItems;
    private Context context;

    dbAdapter dbhelper = new dbAdapter(context);
    displayDecks act;
    String currentClName;



    public DeckAdapter(List<DeckListItem> deckListItems, Context context, displayDecks act, String currentClName) {
        this.deckListItems = deckListItems;
        this.context = context;
        this.act = act;
        this.currentClName = currentClName;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.deck_list_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        DeckListItem deckListItem = deckListItems.get(position);

        holder.deckTextView.setText(deckListItem.getDeckName());
        holder.deckIDview.setText("Deck ID: " + deckListItem.getDid());
    }

    @Override
    public int getItemCount() {
        return deckListItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {

        public TextView deckTextView;
        public TextView deckIDview;

        public ViewHolder(final View itemView) {
            super(itemView);

            deckTextView = (TextView) itemView.findViewById(R.id.deckTextView);
            deckIDview = (TextView) itemView.findViewById(R.id.deckIDview);

            itemView.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View view)
                {
                    //Toast.makeText(context, "Deck clicked is: "+String.valueOf(deckTextView.getText()), Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(context, deckMenu.class);
                    intent.putExtra("DID", deckListItems.get(getAdapterPosition()).getDid());
                    intent.putExtra("DNAME", deckListItems.get(getAdapterPosition()).getDeckName());
                    intent.putExtra("CLNAME", currentClName);
                    act.startActivityForResult(intent,0);
                }
            });
        }
    }

}
