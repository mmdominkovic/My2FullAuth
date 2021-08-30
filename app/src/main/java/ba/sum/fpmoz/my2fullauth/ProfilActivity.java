package ba.sum.fpmoz.my2fullauth;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import ba.sum.fpmoz.my2fullauth.databinding.ActivityProfilBinding;

public class ProfilActivity extends AppCompatActivity {
FirebaseAuth mAuth;
private Button authBtn;
private Button logoutBtn;
private ActivityProfilBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityProfilBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        mAuth=FirebaseAuth.getInstance();
        this.logoutBtn=findViewById(R.id.logoutBtn);
        this.authBtn=findViewById(R.id.authBtn);
        checkUserStatus();

        this.authBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), BioAuth.class);
                startActivity(i);
            }
        });

      this.logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth.signOut();
                checkUserStatus();
            }
        });
    }

    private void checkUserStatus() {
        FirebaseUser firebaseUser =mAuth.getCurrentUser();
        if (firebaseUser !=null){
            String phone = firebaseUser.getPhoneNumber();
            binding.phoneTv.setText(phone);
        } else{
            finish();
        }
    }
}