package ba.sum.fpmoz.my2fullauth;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.concurrent.TimeUnit;

import ba.sum.fpmoz.my2fullauth.databinding.ActivityPhoneAuthBinding;
import ba.sum.fpmoz.my2fullauth.model.Users;


public class PhoneAuthActivity extends AppCompatActivity {

    private FirebaseDatabase db;
    private EditText phoneEt;
    DatabaseReference ref;
    private ActivityPhoneAuthBinding binding;
    private PhoneAuthProvider.ForceResendingToken forceResendingToken;

    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallBacks;

    private  String mVerificationId;
    private static final String TAG ="MAIN_TAG";
    private FirebaseAuth firebaseAuth;
    private ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPhoneAuthBinding.inflate(getLayoutInflater());
        this.phoneEt=findViewById(R.id.phoneEt);
        setContentView(binding.getRoot());
        binding.phoneLl.setVisibility(View.VISIBLE);
        binding.codeLl.setVisibility(View.GONE);
        db=FirebaseDatabase.getInstance();
        firebaseAuth =FirebaseAuth.getInstance();
        pd = new ProgressDialog(this);
        pd.setTitle("Please wait..");
        pd.setCanceledOnTouchOutside(false);

        mCallBacks= new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                signInWithPhoneAuthCredential(phoneAuthCredential);
            }

            @Override
            public void onVerificationFailed(@NonNull FirebaseException e) {
                pd.dismiss();
                Toast.makeText(PhoneAuthActivity.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();

            }
            public void onCodeSent (@NonNull String verificationId, @NonNull PhoneAuthProvider.ForceResendingToken  token){
                super.onCodeSent(verificationId, forceResendingToken);
                Log.d(TAG,"onCodeSent"+verificationId);

                mVerificationId =verificationId;
                forceResendingToken = token;
                pd.dismiss();

                binding.phoneLl.setVisibility(View.GONE);
                binding.codeLl.setVisibility(View.VISIBLE);

                Toast.makeText(PhoneAuthActivity.this, "Verification code sent", Toast.LENGTH_SHORT).show();
                binding.codeSentDescription.setText("Please type the verification code we sent \nto"+binding.phoneEt.getText().toString().trim());
            }
        };
        binding.phoneContinueBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phone = binding.phoneEt.getText().toString().trim();
                if (TextUtils.isEmpty(phone)){
                    Toast.makeText(PhoneAuthActivity.this, "Please enter Phone number..", Toast.LENGTH_SHORT).show();
                }
                else {


                    startPhoneNumberVerification(phone);
                }
            }


        });
        binding.resendCodeTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phone = binding.phoneEt.getText().toString().trim();
                if (TextUtils.isEmpty(phone)){
                    Toast.makeText(PhoneAuthActivity.this, "Please enter Phone number..", Toast.LENGTH_SHORT).show();
                }
                else {
                    resendVerificationCode(phone, forceResendingToken);
                }
            }


        });
        binding.codeSubmitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phone=binding.phoneEt.getText().toString().trim();
                String code = binding.codeEt.getText().toString().trim();
                DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("korisnik");
                if (TextUtils.isEmpty(code)) {
                    Toast.makeText(PhoneAuthActivity.this, "Please enter verification code...", Toast.LENGTH_SHORT).show();
                } else{
                    verifyPhoneNumberWithCode(mVerificationId, code);
                       //problem
                    ref.orderByChild("phonenumber").equalTo(String.valueOf(phoneEt)).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            final FirebaseUser user =FirebaseAuth.getInstance().getCurrentUser();

                            if(snapshot.exists()){
                                startActivity(new Intent(String.valueOf(ProfilActivity.class)));

                            }else
                            {
                                db.getReference("korisnik").child(user.getUid()).setValue(new Users(phone));

                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }
            }

        });

    }


    private void resendVerificationCode(String phone, PhoneAuthProvider.ForceResendingToken token) {
        pd.setMessage("Resending Code..");
        pd.show();
        PhoneAuthOptions options  =
                PhoneAuthOptions.newBuilder(firebaseAuth)
                        .setPhoneNumber(phone)
                        .setTimeout(60L, TimeUnit.SECONDS)
                        .setActivity(this)
                        .setCallbacks(mCallBacks)
                        .setForceResendingToken(token)
                        .build();
        PhoneAuthProvider.verifyPhoneNumber(options);
    }
    private void startPhoneNumberVerification(String phone) {
        pd.setMessage("Verifying Phone Number");
        pd.show();
        PhoneAuthOptions options  =
                PhoneAuthOptions.newBuilder(firebaseAuth)
                        .setPhoneNumber(phone)
                        .setTimeout(60L, TimeUnit.SECONDS)
                        .setActivity(this)
                        .setCallbacks(mCallBacks)
                        .build();
        PhoneAuthProvider.verifyPhoneNumber(options);
    }
    private void verifyPhoneNumberWithCode(String verificationId, String code) {
        pd.setMessage("Verifying Code");
        pd.show();

        PhoneAuthCredential credential =PhoneAuthProvider.getCredential(verificationId, code);
        signInWithPhoneAuthCredential(credential);
    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        pd.setMessage("Logging In");

        firebaseAuth.signInWithCredential(credential)
                .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        pd.dismiss();
                        String phone = firebaseAuth.getCurrentUser().getPhoneNumber();
                        Toast.makeText(PhoneAuthActivity.this, "Logged in as"+phone, Toast.LENGTH_SHORT).show();

                        startActivity(new Intent(PhoneAuthActivity.this, ProfilActivity.class));
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        pd.dismiss();
                        Toast.makeText(PhoneAuthActivity.this, ""+e.getMessage(),Toast.LENGTH_SHORT).show();
                    }
                });
    }
}