package ba.sum.fpmoz.my2fullauth;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import ba.sum.fpmoz.my2fullauth.databinding.ActivityProfilBinding;

public class ProfilActivity extends AppCompatActivity {
FirebaseAuth mAuth;
private ActivityProfilBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityProfilBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        mAuth=FirebaseAuth.getInstance();
        checkUserStatus();

        binding.logoutBtn.setOnClickListener(new View.OnClickListener() {
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