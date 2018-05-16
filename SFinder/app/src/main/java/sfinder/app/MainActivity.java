package sfinder.app;


import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import com.android.volley.Response;
import android.support.v7.app.AppCompatActivity;

import com.android.volley.RequestQueue;
import android.support.v7.app.AlertDialog;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    // Used to load the 'native-lib' library on application startup.
    static {
        System.loadLibrary("native-lib");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Example of a call to a native method
//        TextView tv = (TextView) findViewById(R.id.sample_text);
//        tv.setText(stringFromJNI());
    }
    public void iniciarSesion(View view){
        Intent intent = new Intent(MainActivity.this, MapsActivity.class);
        MainActivity.this.startActivity(intent);
       /* Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject json = new JSONObject(response);
                    boolean success = json.getBoolean("success");

                    if (success) {
                        String email = json.getString("email");
                        String user = json.getString("username");
                        String password = json.getString("password");

                        Intent intent = new Intent(MainActivity.this, MapsActivity.class);
                        intent.putExtra("email" , email);
                        intent.putExtra("username", user);
                        MainActivity.this.startActivity(intent);

                    } else {
                        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                        builder.setMessage("Login Failed")
                                .setNegativeButton("Retry", null)
                                .create()
                                .show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };

        /*LoginRequest loginRequest = new LoginRequest(usuario, responseListener);
        RequestQueue queue = Volley.newRequestQueue(MainActivity.this);
        queue.add(loginRequest);*/

    }

}
