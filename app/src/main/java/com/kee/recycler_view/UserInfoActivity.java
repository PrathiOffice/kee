package com.kee.recycler_view;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class UserInfoActivity extends AppCompatActivity {
    private TextView selectedDateTextView;
    private EditText nameEditText;
    private EditText phoneEditText;
    private EditText emailEditText;
    private EditText pincodeEditText;
    private Button submitButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);

        // Initialize views
        selectedDateTextView = findViewById(R.id.selectedDateTextView);
        nameEditText = findViewById(R.id.nameEditText);
        phoneEditText = findViewById(R.id.phoneEditText);
        emailEditText = findViewById(R.id.emailEditText);
        pincodeEditText = findViewById(R.id.pincodeEditText);
        submitButton = findViewById(R.id.submitButton);

        // Retrieve selected date from Intent extras
        long selectedDate = getIntent().getLongExtra("selectedDate", 0);

        // Display the selected date
        selectedDateTextView.setText(formatDate(selectedDate));

        // Set onClickListener for the submit button
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Check if any field is empty
                if (TextUtils.isEmpty(nameEditText.getText().toString()) ||
                        TextUtils.isEmpty(phoneEditText.getText().toString()) ||
                        TextUtils.isEmpty(emailEditText.getText().toString()) ||
                        TextUtils.isEmpty(pincodeEditText.getText().toString())) {
                    showToast("Please fill in all fields.");
                } else {
                    // Get user details
                    String name = nameEditText.getText().toString().trim();
                    String phone = phoneEditText.getText().toString().trim();
                    String email = emailEditText.getText().toString().trim();
                    String pincode = pincodeEditText.getText().toString().trim();

                    // Retrieve selected date from Intent extras
                    long selectedDate = getIntent().getLongExtra("selectedDate", 0);
                    String formattedDate = formatDate(selectedDate);

                    // Start UserDetailsActivity and pass user details
                    Intent intent = new Intent(UserInfoActivity.this, UserDetailsActivity.class);
                    intent.putExtra("name", name);
                    intent.putExtra("phone", phone);
                    intent.putExtra("email", email);
                    intent.putExtra("pincode", pincode);
                    intent.putExtra("selectedDate", formattedDate);
                    startActivity(intent);
                }
            }
        });
    }

    // Method to format date
    private String formatDate(long millis) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd - MMM - yyyy", Locale.getDefault());
        return sdf.format(new Date(millis));
    }

    // Method to send email and WhatsApp messages
//    private void sendMessages() {
//        // Get user details
//        String name = nameEditText.getText().toString().trim();
//        String phone = phoneEditText.getText().toString().trim();
//        String email = emailEditText.getText().toString().trim();
//        String pincode = pincodeEditText.getText().toString().trim();
//
//        // Send email
//        sendEmail(name, phone, email, pincode);
//
//        // Send WhatsApp message
//        sendWhatsAppMessage(name, phone, email, pincode);
//    }

    // Method to send email
//    private void sendEmail(String name, String phone, String email, String pincode) {
//        // Recipient email address
//        String recipientEmail = "prathiyuman4452@gmail.com"; // Replace with your email address
//
//        // Email intent
//        Intent emailIntent = new Intent(Intent.ACTION_SENDTO);
//        emailIntent.setData(Uri.parse("mailto:" + recipientEmail));
//        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "User Details");
//        emailIntent.putExtra(Intent.EXTRA_TEXT, "Name: " + name + "\n" +
//                "Phone: " + phone + "\n" +
//                "Email: " + email + "\n" +
//                "Pincode: " + pincode);
//
//        // Check if the device has an app that can handle the email intent
//        if (emailIntent.resolveActivity(getPackageManager()) != null) {
//            startActivity(emailIntent);
//        } else {
//            showToast("No email app found.");
//        }
//    }

    // Method to send WhatsApp message
//    private void sendWhatsAppMessage(String name, String phone, String email, String pincode) {
//        // WhatsApp intent
//        Intent whatsappIntent = new Intent(Intent.ACTION_SEND);
//        whatsappIntent.setType("text/plain");
//        whatsappIntent.putExtra(Intent.EXTRA_TEXT, "Name: " + name + "\n" +
//                "Phone: " + phone + "\n" +
//                "Email: " + email + "\n" +
//                "Pincode: " + pincode);
//
//        // Set WhatsApp package name
//        whatsappIntent.setPackage("com.whatsapp");
//
//        // Check if WhatsApp is installed on the device
//        if (whatsappIntent.resolveActivity(getPackageManager()) != null) {
//            startActivity(whatsappIntent);
//        } else {
//            showToast("WhatsApp is not installed.");
//        }
//    }

    // Method to show toast message
    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
