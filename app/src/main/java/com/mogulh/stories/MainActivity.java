package com.mogulh.stories;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

public class MainActivity extends AppCompatActivity {

    private Spinner spinner;
    private EditText editText;
    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        spinner=findViewById(R.id.CountrySpinner);
        editText=findViewById(R.id.phone);
        button=findViewById(R.id.btn_continue);

        spinner.setAdapter(new ArrayAdapter<String>
                (this, android.R.layout.simple_spinner_dropdown_item, CountryData.countryNames));

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String code = CountryData.countryAreaCodes[spinner.getSelectedItemPosition()];
                String number = editText.getText().toString().trim();

                if (number.isEmpty() || number.length()<10){
                    editText.setError("Valid number is required");
                    editText.requestFocus();
                    return;
                }

                String PhoneNumber = "+" + code + number;

                Intent intent = new Intent(MainActivity.this, OTP.class);
                intent.putExtra("phonenumber", PhoneNumber);
                startActivity(intent);


            }
        });


    }
}
