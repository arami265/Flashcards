package arami265.github.io.flashcards;

/**
 * Created by arami265 on 4/11/2017.
 */

public class ClassListItem {

    private String className;
    private int clid;

    public ClassListItem(String className, int clid) {
        this.className = className;
        this.clid = clid;
    }

    public String getClassName() {
        return className;
    }

    public int getClid() {
        return clid;
    }
}