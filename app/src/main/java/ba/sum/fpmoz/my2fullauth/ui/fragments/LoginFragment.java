package ba.sum.fpmoz.my2fullauth.ui.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import ba.sum.fpmoz.my2fullauth.PhoneAuthActivity;
import ba.sum.fpmoz.my2fullauth.ProfilActivity;
import ba.sum.fpmoz.my2fullauth.R;


public class LoginFragment extends Fragment {
    private FirebaseAuth mAuth;
    private EditText emailTxt;
    private EditText passTxt;
    private Button loginBtn;
    private Button loginwithphonebtn;

    public LoginFragment() {}
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
       View loginView = inflater.inflate(R.layout.fragment_login, container, false);
        mAuth=FirebaseAuth.getInstance();

        this.emailTxt =loginView.findViewById(R.id.emailTxt);
        this.passTxt=loginView.findViewById(R.id.passTxt);
        this.loginBtn =loginView.findViewById(R.id.loginBtn);
        this.loginwithphonebtn=loginView.findViewById(R.id.loginwithphonebtn);
        this.loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email =emailTxt.getText().toString();
                String password =passTxt.getText().toString();

                mAuth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()){
                                    Intent i = new Intent(getContext(), ProfilActivity.class);
                                    startActivity(i);
                                }
                                else {

                                }
                            }
                        });

            }
        });
        this.loginwithphonebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getContext(), PhoneAuthActivity.class);
                startActivity(i);
            }
        });
        return loginView;
    }
}