package com.kee.recycler_view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class Otp_Page extends AppCompatActivity {
    private EditText editText1, editText2, editText3, editText4;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp_page);

        editText1 = findViewById(R.id.editText1);
        editText2 = findViewById(R.id.editText2);
        editText3 = findViewById(R.id.editText3);
        editText4 = findViewById(R.id.editText4);

        // Add text change listener to handle OTP verification when all digits are entered
        editText4.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                // Verify OTP when all digits are entered
                if (s.length() == 1) {
                    verifyOTP();
                }
            }
        });
    }

    private void setUpEditTextFocusListener(final EditText currentEditText, final EditText nextEditText) {
        currentEditText.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_DEL && event.getAction() == KeyEvent.ACTION_DOWN) {
                    // Backspace pressed, move focus to previous EditText
                    if (currentEditText.getText().toString().isEmpty()) {
                        nextEditText.requestFocus();
                        return true;
                    }
                }
                return false;
            }
        });
        currentEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                // Move focus to next EditText when current EditText is filled
                if (s.length() == 1) {
                    nextEditText.requestFocus();
                }
            }
        });
    }

    private void verifyOTP() {
        String otp = editText1.getText().toString() +
                editText2.getText().toString() +
                editText3.getText().toString() +
                editText4.getText().toString();

        // Your OTP verification logic here
        if (otp.equals("1234")) { // Change this to your actual OTP
            Toast.makeText(Otp_Page.this, "OTP Verified", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(Otp_Page.this,Home_Page.class);
            startActivity(intent);

        } else {
            Toast.makeText(Otp_Page.this, "Invalid OTP", Toast.LENGTH_SHORT).show();
            // Optionally, clear the OTP fields
            editText1.setText("");
            editText2.setText("");
            editText3.setText("");
            editText4.setText("");
            editText1.requestFocus();
        }
    }
}