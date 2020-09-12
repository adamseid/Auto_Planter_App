package live.autoplanter.auto_planter_3;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.Button;
import android.widget.TextView;
import android.view.View;

public class logInScreen extends AppCompatActivity {

    String email, password;

    EditText logInEmailField;
    EditText logInPasswordField;

    Button logInScreenButton;

    TextView emailDisplay;
    TextView passwordDisplay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.log_in_screen);

        logInEmailField = (EditText) findViewById(R.id.log_in_email_field);
        logInPasswordField = (EditText) findViewById(R.id.log_in_password_field);

        emailDisplay = (TextView) findViewById(R.id.log_in_email_display);
        passwordDisplay = (TextView) findViewById(R.id.log_in_password_display);

        logInScreenButton = (Button) findViewById(R.id.log_in_screen_button);
        logInScreenButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                email = logInEmailField.getText().toString();
                password = logInPasswordField.getText().toString();

//                emailDisplay.setText(email);
//                passwordDisplay.setText(password);
                String task = "log_in";
                BackgroundTask backgroundTask = new BackgroundTask(logInScreen.this);

                logInEmailField.setText("");
                logInPasswordField.setText("");

                backgroundTask.execute(task,email,password);
            }
        });
    }
}