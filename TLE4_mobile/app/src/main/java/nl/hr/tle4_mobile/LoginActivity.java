package nl.hr.tle4_mobile;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText nameInput;
    private EditText passInput;
    private Button login;
    private TextView register;

    private String username;
    private String password;
    private String loginResults;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_login);

        SharedPreferences getPrefs = PreferenceManager.getDefaultSharedPreferences(this);
        RelativeLayout bg = (RelativeLayout)findViewById(R.id.main_login);
        bg.setBackgroundColor(Color.parseColor(getPrefs.getString("appColor","#9370DB")));

        this.nameInput = (EditText)findViewById(R.id.username);
        this.passInput = (EditText)findViewById(R.id.password);
        this.login = (Button)findViewById(R.id.button_login);
        this.register = (TextView)findViewById(R.id.text_register);

        this.login.setOnClickListener(this);
        this.register.setOnClickListener(this);
    }

    public void onClick(View v){
        switch (v.getId()){
            case R.id.button_login:
                LoginActivity.this.username = LoginActivity.this.nameInput.getText().toString();
                LoginActivity.this.password = LoginActivity.this.passInput.getText().toString();

                LoginRequest req = new LoginRequest(LoginActivity.this);
                req.execute(LoginActivity.this.username,LoginActivity.this.password);

                break;

            case R.id.text_register:
                Intent in = new Intent(this, RegisterActivity.class);
                LoginActivity.this.startActivity(in);
                break;
        }
    }



    public void setLoginResults(String results){
        this.loginResults = results;
    }

    public String getUsername(){
        return this.username;
    }

}
