package arami265.github.io.flashcards;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by arami265 on 4/2/2017.
 */

public class Message {
    public static void message(Context context, String message)
    {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show();
    }
}
