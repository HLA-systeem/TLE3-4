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

    private NotificationCompat.Builder noti;
    private int notiID;
    private Uri notiSound;

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

        this.noti = new NotificationCompat.Builder(this);
        this.notiID = 999;
        this.noti.setAutoCancel(true);
        this.notiSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        this.showNotification();
    }

    public void onClick(View v){
        switch (v.getId()){
            case R.id.button_login:
                String username = LoginActivity.this.nameInput.getText().toString();
                String password = LoginActivity.this.passInput.getText().toString();

                Response.Listener<String> responseListener = new Response.Listener<String>(){

                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject json = new JSONObject(response);
                            boolean succes = json.getBoolean("succes");

                            if(succes == true){
                                Intent in = new Intent(LoginActivity.this, TimerActivity.class);
                                LoginActivity.this.startActivity(in);
                            }

                            else{
                                AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                                builder.setMessage("Incorrect username or password.")
                                        .setNegativeButton("Retry", null)
                                        .create()
                                        .show();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };

                LoginRequest request = new LoginRequest(username,password,responseListener);
                RequestQueue queue = Volley.newRequestQueue(LoginActivity.this);
                queue.add(request);
                break;
            case R.id.text_register:
                Intent in = new Intent(this, RegisterActivity.class);
                LoginActivity.this.startActivity(in);
                break;
        }
    }

    public void showNotification(){
        this.noti.setSmallIcon(R.mipmap.ic_launcher);
        this.noti.setTicker("So this is what they call a ticker.");
        this.noti.setWhen(System.currentTimeMillis());
        this.noti.setContentTitle("You're luggage wil arrive soon!");
        this.noti.setContentText("Press this to see the timer.");
        this.noti.setSound(this.notiSound);

        Intent in = new Intent(this, TimerActivity.class);
        PendingIntent inPen = PendingIntent.getActivity(this, 0 ,in, PendingIntent.FLAG_UPDATE_CURRENT);
        this.noti.setContentIntent(inPen);



        NotificationManager nm = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        nm.notify(this.notiID, this.noti.build() );

    }
}
