package nl.hr.tle4_mobile;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class RegisterRequest extends StringRequest{ //gets a string response from the server after a request.
    private static final String REQUEST_URL = "https://brusque-millimeters.000webhostapp.com/register.php";
    private Map<String, String> params;

    public RegisterRequest(String username, String password, Response.Listener<String> listener){
        super(Method.POST, REQUEST_URL, listener, null);
        this.params = new HashMap<>();
        this.params.put("username",username);
        this.params.put("password",password);
    }

    @Override
    public Map<String, String> getParams(){
        return this.params;
    }
}
