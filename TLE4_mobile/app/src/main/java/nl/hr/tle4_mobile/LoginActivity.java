package nl.hr.tle4_mobile;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.NotificationCompat;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

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
        getSupportActionBar().hide();

        setContentView(R.layout.activity_login);

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
                Intent i = new Intent(this, SearchActivity.class);
                startActivity(i);
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
