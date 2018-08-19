package arami265.github.io.flashcards;

/**
 * Created by arami265 on 4/11/2017.
 */

public class cardItem {

    private int cid;
    private String ctitle;
    private String ctext;
    private float comp;

    public cardItem(int cid, String ctitle, String ctext, float comp) {
        this.cid = cid;
        this.ctitle = ctitle;
        this.ctext = ctext;
        this.comp = comp;
    }

    public int getCid() { return cid; }

    public String getCtitle() { return ctitle; }

    public String getCtext() { return ctext; }

    public float getComp() {
        return comp;
    }

    public void setComp(float comp)
    {
        if(this.comp < 0 || comp < 0)
            this.comp = comp;
        else
        {
            this.comp = (this.comp + comp) / 2;
        }
    }
}