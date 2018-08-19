package arami265.github.io.flashcards;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class displayClassInfo extends Activity {

    TextView classNameDetail;
    TextView fieldDetail;
    TextView classDescriptionDetail;
    TextView roomDetail;
    TextView timeDetail;
    TextView instructorDetail;
    dbAdapter dbhelper;

    String currentClname;
    int currentClid;
    classDetailItem currentClassDetailItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_class_info);
        dbhelper = new dbAdapter(this);

        currentClid = getIntent().getIntExtra("CLID", -1);
        currentClname = getIntent().getStringExtra("CLNAME");

        //currentClassDetailItem = dbhelper.getClassDetail(currentClid);

        classNameDetail = (TextView) findViewById(R.id.classNameDetail);
        fieldDetail = (TextView) findViewById(R.id.fieldDetail);
        classDescriptionDetail = (TextView) findViewById(R.id.classDescriptionDetail);
        roomDetail = (TextView) findViewById(R.id.roomDetail);
        timeDetail = (TextView) findViewById(R.id.timeDetail);
        instructorDetail = (TextView) findViewById(R.id.instructorDetail);
    }
}
