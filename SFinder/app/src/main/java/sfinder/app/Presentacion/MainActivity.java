package sfinder.app.Presentacion;


import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.google.android.gms.maps.MapsInitializer;
import com.google.firebase.auth.FirebaseAuth;

import android.support.annotation.NonNull;
import android.text.TextUtils;

import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;

import com.google.firebase.auth.FirebaseUser;

import sfinder.app.Negocio.ServicioAplicacion;
import sfinder.app.R;


public class MainActivity extends AppCompatActivity {

    private EditText mEmailField;
    private EditText mPasswordField;

    static {
        System.loadLibrary("native-lib");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ServicioAplicacion.getInstance();

        mEmailField = findViewById(R.id.etemail);
        mPasswordField = findViewById(R.id.etPassword);
    }

    public void onStart(){
        super.onStart();
        ServicioAplicacion sa = ServicioAplicacion.getInstance();
        FirebaseUser currentUser = sa.getAuth().getCurrentUser();
    }


    public void registro(String email, String password){

        if (!validateForm()) {
            return;
        }
        ServicioAplicacion sa = ServicioAplicacion.getInstance();
        FirebaseAuth mAuth = sa.getAuth();
        mAuth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            ServicioAplicacion sa = ServicioAplicacion.getInstance();
                            FirebaseAuth mAuth = sa.getAuth();
                            FirebaseUser user = mAuth.getCurrentUser();
                            enviaEmailVerificacion();

                        }else{
                            Toast.makeText(MainActivity.this,"La contraseña debe contener al menos 6 caracteres.",Toast.LENGTH_SHORT).show();

                        }


                    }
                });
    }

    private boolean validateForm() {
        boolean valid = true;

        String email = mEmailField.getText().toString();
        if (TextUtils.isEmpty(email)) {
            mEmailField.setError("Required.");
            valid = false;
        } else {
            mEmailField.setError(null);
        }

        String password = mPasswordField.getText().toString();
        if (TextUtils.isEmpty(password)) {
            mPasswordField.setError("Required.");
            valid = false;
        } else {
            mPasswordField.setError(null);
        }

        return valid;
    }
    public void iniciarSesion(String email,String password){

        if(!validateForm()){
            return;
        }
        ServicioAplicacion sa = ServicioAplicacion.getInstance();
        FirebaseAuth mAuth = sa.getAuth();
        mAuth.signInWithEmailAndPassword(email,password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            ServicioAplicacion sa = ServicioAplicacion.getInstance();
                            FirebaseAuth mAuth = sa.getAuth();
                            FirebaseUser user = mAuth.getCurrentUser();
                            Intent intent = new Intent(MainActivity.this, MapsActivity.class);
                            MainActivity.this.startActivity(intent);

                        }else{
                            Toast.makeText(MainActivity.this,"Error de autentificación",Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    public void onClick(View v){
        int i = v.getId();

        if(i == R.id.bInicioSesion){
            iniciarSesion(mEmailField.getText().toString(), mPasswordField.getText().toString());

        }else if(i == R.id.bRegistro){
            registro(mEmailField.getText().toString(), mPasswordField.getText().toString());
        }
    }

    private void enviaEmailVerificacion(){
        ServicioAplicacion sa = ServicioAplicacion.getInstance();
        FirebaseAuth mAuth = sa.getAuth();
        final FirebaseUser user = mAuth.getCurrentUser();
        user.sendEmailVerification()
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        // [START_EXCLUDE]
                        // Re-enable button


                        if (task.isSuccessful()) {
                            Toast.makeText(MainActivity.this,
                                    "Email de verificación enviado a " + user.getEmail(),
                                    Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(MainActivity.this,
                                    "Se produjo un error al mandar el email de verificación",
                                    Toast.LENGTH_SHORT).show();
                        }
                        // [END_EXCLUDE]
                    }
                });
    }
}
