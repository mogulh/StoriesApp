package com.mogulh.stories;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskExecutors;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.concurrent.TimeUnit;

public class OTP extends AppCompatActivity {

    private EditText editText;
    private String verificationId;
    private FirebaseAuth mAuth;

    DatabaseReference reference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_o_t_p);

        Button button = findViewById(R.id.btn_confirm);
        editText=findViewById(R.id.code);
        mAuth= FirebaseAuth.getInstance();



        String phonenumber = getIntent().getStringExtra("phonenumber");
        sendVerificationCode(phonenumber);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String code = editText.getText().toString().trim();

                if (code.isEmpty() || code.length()<6){
                    editText.setError("Code is required!");
                    editText.requestFocus();
                    return;
                }

                verifyCode(code);

            }
        });
    }


    private void verifyCode(String code){
        Toast.makeText(OTP.this, "Verifying...", Toast.LENGTH_SHORT).show();
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationId, code);
        signInWithCredential(credential);



    }

    private void signInWithCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                            if (FirebaseDatabase.getInstance().getReference().child("Users").getKey() != null) {
                                Intent intent = new Intent(OTP.this, Home.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

                                startActivity(intent);
                            }
                            FirebaseUser firebaseUser = mAuth.getCurrentUser();
                            String userID = firebaseUser.getUid();
                            String phone = getIntent().getStringExtra("phonenumber");

                            reference = FirebaseDatabase.getInstance().getReference().child("Users").child(userID);
                            HashMap<String, Object> map = new HashMap<>();
                            map.put("id", userID);
                            map.put("username", "");
                            map.put("fullname", "");
                            map.put("imageurl", "gs://stories-5293.appspot.com/icons8-customer-100.png");
                            map.put("bio", "");
                            map.put("website", "");
                            map.put("phone", phone);

                            reference.setValue(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        Intent intent = new Intent(OTP.this, Home.class);
                                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                        startActivity(intent);

                                        Toast.makeText(OTP.this, "Verified...", Toast.LENGTH_SHORT).show();


                                    } else {
                                        Toast.makeText(OTP.this, task.getException()
                                                .getMessage(), Toast.LENGTH_LONG).show();

                                    }

                                }
                            });
                        }
                    }
                });
    }

    private void sendVerificationCode(String number){
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                number,
                60,
                TimeUnit.SECONDS,
                TaskExecutors.MAIN_THREAD,
                mCallBack);
    }

    private PhoneAuthProvider.OnVerificationStateChangedCallbacks
            mCallBack = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
        @Override
        public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            super.onCodeSent(s, forceResendingToken);
            verificationId = s;
        }

        @Override
        public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {
            String code = phoneAuthCredential.getSmsCode();
            if (code != null){
                verifyCode(code);
                editText.setText(code);
            }

        }

        @Override
        public void onVerificationFailed(FirebaseException e) {
            Toast.makeText(OTP.this, e.getMessage(), Toast.LENGTH_LONG).show();

        }
    };


}
