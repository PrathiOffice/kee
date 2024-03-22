package com.kee.recycler_view;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;
import com.kee.recycler_view.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.datepicker.CalendarConstraints;
import com.google.android.material.datepicker.DateValidatorPointForward;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

public class Booking_event extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {
    private Button bookButton;
    private CardView dateCardView;
    private TextView dateTextView;
    private Set<Long> bookedDates = new HashSet<>();
    private MaterialDatePicker<Long> picker;
    private BottomNavigationView bottomNavigationView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking_event);

        bookButton = findViewById(R.id.bookButton);
        dateTextView = findViewById(R.id.dateTextView);
        dateCardView = findViewById(R.id.dateCardView);
        bottomNavigationView = findViewById(R.id.bottomNavigationView);

        // Set click listener for bookButton
        bookButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Show MaterialDatePicker
                showDatePicker();
            }
        });
    }

    //Bottom Navigation




    //    private void showDatePicker() {
//        Calendar calendar = Calendar.getInstance();
//        MaterialDatePicker.Builder<Long> builder = MaterialDatePicker.Builder.datePicker();
//        builder.setTitleText("Select Date");
//        builder.setSelection(calendar.getTimeInMillis());
//        picker = builder.build();
//        picker.addOnPositiveButtonClickListener(new MaterialPickerOnPositiveButtonClickListener<Long>() {
//            @Override
//            public void onPositiveButtonClick(Long selection) {
//                // Check if the selected date is already booked by another user
//                if (isDateAlreadyBooked(selection)) {
//                    handleBookedDate();
//                } else {
//                    // Determine the status of the selected date and handle accordingly
//                    DateStatus status = getDateStatus(selection);
//                    switch (status) {
//                        case AVAILABLE:
//                            handleAvailableDate(selection);
//                            break;
//                        case BOOKED:
//                            handleBookedDate();
//                            break;
//                        case PAST:
//                            handlePastDate();
//                            break;
//                    }
//                }
//            }
//        });
//        picker.show(getSupportFragmentManager(), picker.toString());
//    }
private void showDatePicker() {
    Calendar calendar = Calendar.getInstance();
    MaterialDatePicker.Builder<Long> builder = MaterialDatePicker.Builder.datePicker();
    builder.setTitleText("Select Date");
    builder.setSelection(calendar.getTimeInMillis());
    final MaterialDatePicker<Long> picker = builder.build();

    // Customize the calendar constraints to disable past dates
    CalendarConstraints.Builder constraintsBuilder = new CalendarConstraints.Builder();
    constraintsBuilder.setValidator(DateValidatorPointForward.now());

    picker.addOnPositiveButtonClickListener(new MaterialPickerOnPositiveButtonClickListener<Long>() {
        @Override
        public void onPositiveButtonClick(Long selection) {
            // Determine the status of the selected date
            DateStatus status = getDateStatus(selection);

            // Handle the status accordingly
            switch (status) {
                case AVAILABLE:
                    handleAvailableDate(selection);
                    break;
                case BOOKED:
                    handleBookedDate();
                    break;
                case PAST:
                    handlePastDate();
                    break;
            }
        }
    });

    // Set a custom date selector callback to customize date view appearance

    picker.show(getSupportFragmentManager(), picker.toString());
}

    private boolean isDateAlreadyBooked(Long selection) {
        // Check if the selected date is already booked by another user
        return bookedDates.contains(selection);
    }

    private DateStatus getDateStatus(Long selection) {
        // Implement logic to determine the status of the selected date
        // For simplicity, let's assume all dates in the past are considered past dates
        Calendar selectedDate = Calendar.getInstance();
        selectedDate.setTimeInMillis(selection);

        Calendar currentDate = Calendar.getInstance();

        if (selectedDate.before(currentDate)) {
            return DateStatus.PAST;
        } else if (bookedDates.contains(selection)) {
            return DateStatus.BOOKED;
        } else {
            return DateStatus.AVAILABLE;
        }
    }

    private void handleAvailableDate(Long selection) {
        // Start UserInfoActivity and pass the selected date as an extra
        Intent intent = new Intent(Booking_event.this, UserInfoActivity.class);
        intent.putExtra("selectedDate", selection);
        startActivity(intent);
    }

    private void handleBookedDate() {
        // Handle logic for booked date (e.g., show information)
        // For demonstration, let's just show a message
        showToast("This date is already booked.");
    }

    private void handlePastDate() {
        // Handle logic for past date (e.g., disable selection)
        // For demonstration, let's just show a message
        showToast("You cannot select a past date.");
    }

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        return false;
    }

    private enum DateStatus {
        AVAILABLE,
        BOOKED,
        PAST
    }
}
