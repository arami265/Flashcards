package arami265.github.io.flashcards;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
//import com.google.firebase.auth.FirebaseAuth;

public class UserLogin extends Activity implements View.OnClickListener {

    private Button buttonSignIn;
    private EditText editTextEmail;
    private EditText editTextPassword;
    private TextView textViewGoToSignUp;
    private ProgressDialog progressDialog;
//    private FirebaseAuth firebaseAuth;
    private TextView userLoginSkip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_login);
//        firebaseAuth = FirebaseAuth.getInstance();

        /*if(firebaseAuth.getCurrentUser() != null)
        {
            finish();
            startActivity(new Intent(getApplicationContext(), displayClasses.class));
        }*/

        progressDialog = new ProgressDialog(this);

        buttonSignIn = (Button) findViewById(R.id.buttonSignIn);
        editTextEmail = (EditText) findViewById(R.id.editTextEmail);
        editTextPassword = (EditText) findViewById(R.id.editTextPassword);
        userLoginSkip = (TextView) findViewById(R.id.userLoginSkip);

        textViewGoToSignUp = (TextView) findViewById(R.id.textViewGoToSignUp);

        buttonSignIn.setOnClickListener(this);
        textViewGoToSignUp.setOnClickListener(this);


    }

    private void userLogin()
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

        progressDialog.setMessage("Signing in...");
        progressDialog.show();

       /* firebaseAuth.signInWithEmailAndPassword(email, password)
        .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful())
                {
                    finish();
                    startActivity(new Intent(getApplicationContext(), displayClasses.class));
                }
                else
                {
                    Toast.makeText(UserLogin.this, "Unable to sign in...", Toast.LENGTH_SHORT).show();
                }
                progressDialog.dismiss();
            }
        });*/
    }

    @Override
    public void onClick(View v) {
        if(v == buttonSignIn)
        {
            userLogin();
        }

        if (v == textViewGoToSignUp)
        {
            finish();
            startActivity(new Intent(this, AddUser.class));
        }

        if(v == userLoginSkip)
        {
            finish();
            startActivity(new Intent(getApplicationContext(), displayClasses.class));
        }
    }
}
