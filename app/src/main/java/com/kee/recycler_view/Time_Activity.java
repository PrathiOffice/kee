    package com.kee.recycler_view;

    import androidx.appcompat.app.AppCompatActivity;
    import androidx.cardview.widget.CardView;

    import android.app.Dialog;
    import android.content.Intent;
    import android.graphics.Color;
    import android.graphics.drawable.ColorDrawable;
    import android.os.Bundle;
    import android.view.View;
    import android.widget.Button;
    import android.widget.LinearLayout;
    import android.widget.TextView;
    import android.widget.Toast;

    public class Time_Activity extends AppCompatActivity {
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_time);
            // Find all CardViews by their IDs
            CardView card3AM = findViewById(R.id.card_3_am);
            CardView card4AM = findViewById(R.id.card_4_am);

            // Set click listeners for each CardView
            card3AM.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    navigateToAnotherPage("3:00 AM");
                }
            });
            card4AM.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    navigateToAnotherPage("4:00 AM");
                }
            });

            // Add listeners for other cards as needed
        }

        private void navigateToAnotherPage(String selectedTime) {
            // For demonstration, simply display a toast with the selected time
            Toast.makeText(this, "Selected time: " + selectedTime, Toast.LENGTH_SHORT).show();
            String selectedDate = getIntent().getStringExtra("selectedDate");

            // Example of navigating to another activity
             Intent intent = new Intent(this, UserInfoActivity.class);
            // Pass the selected date and time as extras to the UserInfoActivity
            intent.putExtra("selectedDate", selectedDate);
            intent.putExtra("selectedTime", selectedTime);

            // Start the UserInfoActivity
            startActivity(intent);

            // Example of navigating to another fragment
            // FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            // transaction.replace(R.id.container, new AnotherFragment());
            // transaction.addToBackStack(null);
            // transaction.commit();
        }


    }