package sfinder.app.Presentacion;

import android.content.Intent;
import android.os.Bundle;

import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import sfinder.app.R;

public class PerfilActivity extends FragmentActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);

        final FirebaseUser user;
        user = FirebaseAuth.getInstance().getCurrentUser();

        TextView userDesc = findViewById(R.id.userDesc);
        userDesc.setText(user.getEmail().toString());

        TextView markersDesc = findViewById(R.id.markersDesc);
        markersDesc.setText(Integer.toString(0));
    }

    public void changePassword(View view)
    {
        Intent intent = new Intent(PerfilActivity.this, ChangePasswordActivity.class);
        PerfilActivity.this.startActivity(intent);
    }
}
