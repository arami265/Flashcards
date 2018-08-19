package arami265.github.io.flashcards;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.List;

public class addClass extends AppCompatActivity {

    dbAdapter dbhelper;

    @Override
    protected void onResume() {
        super.onResume();
        overridePendingTransition(0,0);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        overridePendingTransition(0,0);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_class);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.appBar);
        myToolbar.setTitle("Add a class:");
        setSupportActionBar(myToolbar);

        dbhelper = new dbAdapter(this);

        List<String> spinnerArray =  dbhelper.getFieldNamesAsStrings();

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this, android.R.layout.simple_spinner_item, spinnerArray);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Spinner sItems = (Spinner) findViewById(R.id.fieldSpinner);
        sItems.setAdapter(adapter);

    }

    public void addClassMethod(View v)
    {
        EditText classNameInput   = (EditText)findViewById(R.id.classNameInput);
        String className = classNameInput.getText().toString();

        Spinner fieldSpinner   = (Spinner)findViewById(R.id.fieldSpinner);
        String field = fieldSpinner.getSelectedItem().toString();

        EditText timeInput   = (EditText)findViewById(R.id.timeInput);
        String time = timeInput.getText().toString();

        EditText roomInput   = (EditText)findViewById(R.id.roomInput);
        String room = roomInput.getText().toString();

        EditText instructorInput   = (EditText)findViewById(R.id.instructorInput);
        String instructor = instructorInput.getText().toString();

        EditText descriptionInput   = (EditText)findViewById(R.id.classDescriptionInput);
        String description = descriptionInput.getText().toString();


        if(!className.toString().isEmpty())
        {
            long id = dbhelper.insertClass(className, field, time, room, instructor, description);
            if (id < 0) {
                Message.message(this, "Unsuccessful.");
            } else {
                //Message.message(this, "Successfully inserted a class.");

                Intent myIntent = new Intent(this, displayClasses.class);
                startActivity(myIntent);
                finish();
            }
        }
        else
        {
            Toast.makeText(this, "Please enter a class name.", Toast.LENGTH_SHORT).show();
        }


    }
}
