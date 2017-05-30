package nl.hr.tle4_mobile;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


public class RegisterActivity extends AppCompatActivity {
    private String registerResults;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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

    public void setRegisterResults(String results){
        this.registerResults = results;
    }
}
