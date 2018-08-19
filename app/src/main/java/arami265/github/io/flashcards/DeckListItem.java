package arami265.github.io.flashcards;

/**
 * Created by arami265 on 4/18/2017.
 */

public class DeckListItem {
    private String deckName;
    private int did;
    private int clid;

    public DeckListItem(String deckName, int did, int clid) {
        this.deckName = deckName;
        this.did = did;
        this.clid = clid;
    }

    public String getDeckName() {
        return deckName;
    }

    public int getDid() {
        return did;
    }

    public int getClid() {
        return clid;
    }
}
