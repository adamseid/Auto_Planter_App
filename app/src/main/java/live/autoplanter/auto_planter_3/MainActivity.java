package live.autoplanter.auto_planter_3;

import androidx.appcompat.app.AppCompatActivity;
import android.widget.Button;
import android.os.Bundle;
import android.view.View;
import android.content.Intent;

public class MainActivity extends AppCompatActivity {

    private Button log_in_button;
    private Button sign_up_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        log_in_button = (Button) findViewById(R.id.main_log_in_button);
        sign_up_button = (Button) findViewById(R.id.main_sign_up_button);

        log_in_button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                openLogInScreen();
            }
        });

        sign_up_button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                openSignUpScreen();
            }

        });
    }

    public void openLogInScreen(){
        Intent intent = new Intent(this, logInScreen.class);
        startActivity(intent);
    }

    public void openSignUpScreen(){
        Intent intent = new Intent(this, signUpScreen.class);
        startActivity(intent);
    }
}
