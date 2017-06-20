package nl.hr.tle4_mobile;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class RegisterActivity extends AppCompatActivity {
    private String registerResults;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_register);

        final EditText nameInput = (EditText) findViewById(R.id.input_username);
        final EditText passInput = (EditText) findViewById(R.id.input_password);
        Button registerButton = (Button) findViewById(R.id.button_register);

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = nameInput.getText().toString();
                String password = passInput.getText().toString();

                RegisterRequest req = new RegisterRequest(RegisterActivity.this);
                req.execute(username,password);
            }
        });
    }

    public void loginSucces(){
        Toast.makeText(this,"Registration successful. Please log in with your new account", Toast.LENGTH_LONG).show();
    }

    public void setRegisterResults(String results){
        this.registerResults = results;
    }
}
