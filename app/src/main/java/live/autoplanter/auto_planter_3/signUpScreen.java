package live.autoplanter.auto_planter_3;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.Button;
import android.widget.TextView;
import android.view.View;

public class signUpScreen extends AppCompatActivity {

    String firstName, lastName, email, password;


    EditText signUpFirstNameField;
    EditText signUpLastNameField;
    EditText signUpEmailField;
    EditText signUpPasswordField;

    Button signUpScreenButton;

    TextView firstNameDisplay;
    TextView lastNameDisplay;
    TextView emailDisplay;
    TextView passwordDisplay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_up_screen);

        signUpFirstNameField = (EditText) findViewById(R.id.sign_up_first_name_field);
        signUpLastNameField = (EditText) findViewById(R.id.sign_up_last_name_field);
        signUpEmailField = (EditText) findViewById(R.id.sign_up_email_field);
        signUpPasswordField = (EditText) findViewById(R.id.sign_up_password_field);

        emailDisplay = (TextView) findViewById(R.id.log_in_email_display);
        passwordDisplay = (TextView) findViewById(R.id.log_in_password_display);

        signUpScreenButton = (Button) findViewById(R.id.log_in_screen_button);
        signUpScreenButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                firstName = signUpFirstNameField.getText().toString();
                lastName = signUpLastNameField.getText().toString();
                email = signUpEmailField.getText().toString();
                password = signUpPasswordField.getText().toString();
//                firstNameDisplay.setText(firstName);
//                lastNameDisplay.setText(lastName);
//                emailDisplay.setText(email);
//                passwordDisplay.setText(password);
                String task = "sign_in";
                BackgroundTask backgroundTask = new BackgroundTask(signUpScreen.this);
                backgroundTask.execute(task,firstName,lastName, email, password);
                finish();
            }
        });
    }
}