package ba.sum.fpmoz.my2fullauth.ui.fragments;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import ba.sum.fpmoz.my2fullauth.R;
import ba.sum.fpmoz.my2fullauth.databinding.ActivityPhoneAuthBinding;
import ba.sum.fpmoz.my2fullauth.model.Users;


public class RegisterFragment extends Fragment {

    private FirebaseAuth mAuth;
    private FirebaseDatabase db;
    private EditText nameRegTxt;
    private EditText emailRegTxt;
    private EditText passRegTxt;
    private EditText rePassRegTxt;
    DatabaseReference ref;
    private EditText regPhoneNumber;
    private Button registerBtn;
    Boolean isDataValid=false;
    private ActivityPhoneAuthBinding binding;

    private PhoneAuthProvider.ForceResendingToken forceResendingToken;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallBacks;
    private static final String TAG="MAIN TAG";
    private ProgressDialog pd;


    public RegisterFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,  Bundle savedInstanceState) {
        View registerView = inflater.inflate(R.layout.fragment_register, container, false);
        mAuth=FirebaseAuth.getInstance();
        this.db =FirebaseDatabase.getInstance();


        this.nameRegTxt =registerView.findViewById(R.id.nameRegTxt);
        this.emailRegTxt =registerView.findViewById(R.id.emailRegTxt);
        this.passRegTxt=registerView.findViewById(R.id.passRegTxt);
        this.rePassRegTxt=registerView.findViewById(R.id.rePassRegTxt);

        this.regPhoneNumber=registerView.findViewById(R.id.regPhoneNumber);

        validateData(nameRegTxt);
        validateData(emailRegTxt);
        validateData(passRegTxt);
        validateData(rePassRegTxt);
        validateData(regPhoneNumber);

      
        this.registerBtn=registerView.findViewById(R.id.registerBtn);
        this.registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name=nameRegTxt.getText().toString();
                String email=emailRegTxt.getText().toString();
                String password=passRegTxt.getText().toString();
                String passCnf = rePassRegTxt.getText().toString();
                String phone= regPhoneNumber.getText().toString();

                if (!password.equals(passCnf)){
                                    rePassRegTxt.setError("Password don't match");
                }else {




                    mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                           if (task.isSuccessful()){
                               final FirebaseUser user =FirebaseAuth.getInstance().getCurrentUser();
                               UserProfileChangeRequest changeRequest = new UserProfileChangeRequest
                                       .Builder()
                                       .build();
                               user.updateProfile(changeRequest).addOnCompleteListener(new OnCompleteListener<Void>() {
                                   @Override
                                   public void onComplete(@NonNull Task<Void> task) {
                                       if(task.isSuccessful()){
                                           nameRegTxt.setText("");
                                           emailRegTxt.setText("");
                                           passRegTxt.setText("");
                                           rePassRegTxt.setText("");
                                           regPhoneNumber.setText("");
                                           Log.d("Poruka","Profil je ažuriran");
                                           db.getReference("korisnici").child(user.getUid()).setValue(new Users(name,email,password));
                                           Toast.makeText(getActivity(),"User account is created", Toast.LENGTH_SHORT).show();

                                       }
                                   }
                               });
                           } else {
                               Toast.makeText(getContext(), "Nastala je greška s registracijom na sustav:"+ task.getException().getMessage().toString(), Toast.LENGTH_LONG).show();
                           }
                        }
                    });

                }
            }
        });
        return  registerView;
    }
    public void validateData(EditText field){
        if (field.getText().toString().isEmpty()){
            isDataValid=false;
            field.setError("Required field");
        }else {
            isDataValid=true;
        }
    }
}