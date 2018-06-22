package sfinder.app.Presentacion;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;



import android.support.annotation.NonNull;
import android.widget.EditText;
import android.widget.Toast;


import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import sfinder.app.Negocio.ServicioAplicacion;
import sfinder.app.R;

public class ChangePasswordActivity extends FragmentActivity {


    private EditText oldpassET;
    private EditText newPassET;
    private EditText newPassConfirmET;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_informacion);
    }

    public void editarInformacion(View view)
    {
        ServicioAplicacion sa = ServicioAplicacion.getInstance();
        FirebaseAuth mAuth = sa.getAuth();
        final FirebaseUser user;
        user = mAuth.getCurrentUser();

        final String email = user.getEmail();

        oldpassET = findViewById(R.id.editPass);
        String oldpass = oldpassET.getText().toString();

        newPassET = findViewById(R.id.editContr);
        final String newPass = newPassET.getText().toString();

        newPassConfirmET = findViewById(R.id.editContrConf);
        String newPassConfirm = newPassConfirmET.getText().toString();

        AuthCredential credential = EmailAuthProvider.getCredential(email,oldpass);

        if(newPassConfirm.equals( newPass)) {
            user.reauthenticate(credential).addOnCompleteListener(new OnCompleteListener<Void>() {

                public void onComplete(@NonNull Task<Void> task) {
                    if(task.isSuccessful()){
                        user.updatePassword(newPass).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if(!task.isSuccessful()){
                                    Toast.makeText(ChangePasswordActivity.this,"Se ha producido un error",Toast.LENGTH_SHORT).show();

                                }else {
                                    Toast.makeText(ChangePasswordActivity.this,"La contraseña ha sido modificada",Toast.LENGTH_SHORT).show();
                                    ChangePasswordActivity.this.finish();
                                }
                            }
                        });
                    }else {
                        Toast.makeText(ChangePasswordActivity.this,"Se ha producido un error en la autenticación",Toast.LENGTH_SHORT).show();

                    }
                }
            });
        }else {
            Toast.makeText(ChangePasswordActivity.this,"La confirmación de la contraseña no cohincide",Toast.LENGTH_SHORT).show();

        }

    }
}
