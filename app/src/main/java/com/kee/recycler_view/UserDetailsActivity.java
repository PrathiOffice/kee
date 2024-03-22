package com.kee.recycler_view;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class UserDetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_details);

        // Retrieve data passed from the UserInfoActivity
        String name = getIntent().getStringExtra("name");
        String phone = getIntent().getStringExtra("phone");
        String email = getIntent().getStringExtra("email");
        String pincode = getIntent().getStringExtra("pincode");
        String selectedDate = getIntent().getStringExtra("selectedDate");

        // Populate TextViews with the retrieved data
        TextView nameTextView = findViewById(R.id.nameTextView);
        TextView phoneTextView = findViewById(R.id.phoneTextView);
        TextView emailTextView = findViewById(R.id.emailTextView);
//        TextView pincodeTextView = findViewById(R.id.pincodeTextView);
        TextView selectedDateTextView = findViewById(R.id.selectedDateTextView);

        nameTextView.setText(name);
        phoneTextView.setText(phone);
        emailTextView.setText(email);
//        pincodeTextView.setText(pincode);
        selectedDateTextView.setText(selectedDate);
    }

    }

