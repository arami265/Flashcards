package arami265.github.io.flashcards;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
//import com.google.firebase.auth.FirebaseAuth;

public class AddUser extends Activity implements View.OnClickListener {

    private Button buttonRegister;
    private EditText editTextEmail;
    private EditText editTextPassword;
    private TextView textViewGoToSignIn;
    private TextView skipRegText;

    private ProgressDialog progressDialog;

//    private FirebaseAuth firebaseAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_user);

//        firebaseAuth = FirebaseAuth.getInstance();

/*        if(firebaseAuth.getCurrentUser() != null)
        {
            finish();
            startActivity(new Intent(getApplicationContext(), displayClasses.class));
        }*/

        progressDialog = new ProgressDialog(this);

        buttonRegister = (Button) findViewById(R.id.buttonRegisterUser);
        editTextEmail = (EditText) findViewById(R.id.editTextEmail);
        editTextPassword = (EditText) findViewById(R.id.editTextPassword);
        skipRegText = (TextView) findViewById(R.id.skipRegText);


        textViewGoToSignIn = (TextView) findViewById(R.id.textViewGoToSignIn);

        buttonRegister.setOnClickListener(this);
        textViewGoToSignIn.setOnClickListener(this);
    }

    private void registerUser()
    {
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();

        if(email.isEmpty())
        {
            Toast.makeText(this, "Please enter an email", Toast.LENGTH_SHORT).show();
            return;
        }

        if(password.isEmpty())
        {
            Toast.makeText(this, "Please enter a password", Toast.LENGTH_SHORT).show();
            return;
        }

        progressDialog.setMessage("Registering...");
        progressDialog.show();

       /* firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful())
                        {
                            Toast.makeText(AddUser.this, "Registration successful", Toast.LENGTH_SHORT).show();

                            finish();
                            //Watch for first argument breaking
                            startActivity(new Intent(getParent(), UserLogin.class));

                        }
                        else
                        {
                            Toast.makeText(AddUser.this, "Unable to register...", Toast.LENGTH_SHORT).show();
                        }
                        progressDialog.dismiss();
                    }
                });*/
    }


    @Override
    public void onClick(View v) {
        if(v == buttonRegister)
        {
            registerUser();
        }

        if(v == textViewGoToSignIn)
        {
            finish();
            startActivity(new Intent(this, UserLogin.class));
        }

        if(v == skipRegText)
        {
            finish();
            startActivity(new Intent(getApplicationContext(), displayClasses.class));
        }

    }
}
