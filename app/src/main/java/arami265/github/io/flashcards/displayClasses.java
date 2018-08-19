package arami265.github.io.flashcards;

import android.content.Intent;
import android.os.Bundle;
import android.support.transition.Fade;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import android.support.v7.widget.Toolbar;

//import com.google.firebase.auth.FirebaseAuth;

import java.util.List;

public class displayClasses extends AppCompatActivity {

    private RecyclerView recyclerView;
    private TextView emptyFieldsView;
    private RecyclerView.Adapter adapter;
    dbAdapter dbhelper;
//    private FirebaseAuth firebaseAuth;

    private List<ClassListItem> classListItems;

    public void initializeRecyclerView()
    {
        dbhelper = new dbAdapter(this);

        //fieldListItems = new ArrayList<>();
        classListItems = dbhelper.getClasses();
        if(!classListItems.isEmpty())
        {
            adapter = new ClassAdapter(classListItems, this, this);

            recyclerView = (RecyclerView) findViewById(R.id.classRecyclerView);
            recyclerView.setHasFixedSize(true);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));

            recyclerView.setAdapter(adapter);
        }
        else
        {
            Toast.makeText(this, "No classes in the database...", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onResume() {
        overridePendingTransition(0,0);
        super.onResume();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Intent intent = new Intent(this, displayClasses.class);
        startActivity(intent);
        finish();
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        overridePendingTransition(0,0);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_classes);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.appBar);
        myToolbar.setTitle("Classes");
        setSupportActionBar(myToolbar);

        //firebaseAuth = FirebaseAuth.getInstance();

        /*if(firebaseAuth.getCurrentUser() == null)
        {
            finish();
            startActivity(new Intent(getApplicationContext(), AddUser.class));
        }*/

        initializeRecyclerView();
    }

    public void addClass(View view)
    {
        //String fields = dbhelper.getFieldNames();
        //Message.message(this, fields);

        Fade fade = new Fade();
        fade.setDuration(150);
        //TransitionManager.beginDelayedTransition(R, fade);
        Intent myIntent = new Intent(this, addClass.class);
        startActivityForResult(myIntent, 0);
    }
}
