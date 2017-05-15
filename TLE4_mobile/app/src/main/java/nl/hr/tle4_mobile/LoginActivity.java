package nl.hr.tle4_mobile;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    EditText username;
    EditText password;
    Button login;
    //TODO add remember me
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();

        setContentView(R.layout.activity_login);

        this.username = (EditText)findViewById(R.id.username);
        this.password = (EditText)findViewById(R.id.password);
        this.login = (Button)findViewById(R.id.button_login);
        this.login.setOnClickListener(this);
    }

    public void onClick(View v){
        switch (v.getId()) {
            case R.id.button_login:
                if (this.username.getText().toString().equals("test") && this.password.getText().toString().equals("123")) {
                    Intent in = new Intent(this, TimerActivity.class);
                    LoginActivity.this.startActivity(in);
                }
                /*else {
                    // password
                }*/
            break;
        }
    }
}
