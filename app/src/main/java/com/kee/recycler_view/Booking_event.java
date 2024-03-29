package com.kee.recycler_view;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Layout;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.kee.recycler_view.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.datepicker.CalendarConstraints;
import com.google.android.material.datepicker.DateValidatorPointForward;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

public class Booking_event extends AppCompatActivity {
    private Button bookButton;
    private CardView dateCardView;
    private TextView dateTextView;
    private Set<Long> bookedDates = new HashSet<>();
    private RecyclerView recyclerView;
    private List<String> timingList = new ArrayList<>();
    private MaterialDatePicker<Long> picker;
    private LinearLayout timing_card;
    private BottomNavigationView bottomNavigationView;
    private Map<Date, Button> dateButtonMap;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking_event);

//        bookButton = findViewById(R.id.bookButton);
//        dateTextView = findViewById(R.id.dateTextView);
//        dateCardView = findViewById(R.id.dateCardView);
//        bottomNavigationView = findViewById(R.id.bottomNavigationView);
////        timing_card = findViewById(R.id.timing_card);


        dateButtonMap = new HashMap<>();

        // Get current date
        Calendar calendar = Calendar.getInstance();
        Date currentDate = calendar.getTime();

        // Get next month's date
        calendar.add(Calendar.MONTH, 1);
        Date nextMonthDate = calendar.getTime();

        // Set the start date as the current date
        Date startDate = currentDate;

        // Set the end date as the last date of the next month
        calendar.setTime(nextMonthDate);
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        Date endDate = calendar.getTime();

        // Generate buttons for each month
        Map<String, List<Date>> monthMap = groupDatesByMonth(startDate, endDate);
        generateMonthButtons(monthMap, currentDate);
    }

    // Group dates by month
    private Map<String, List<Date>> groupDatesByMonth(Date startDate, Date endDate) {
        Map<String, List<Date>> monthMap = new HashMap<>();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(startDate);
        SimpleDateFormat monthFormat = new SimpleDateFormat("MMMM yyyy", Locale.getDefault());

        while (calendar.getTime().before(endDate)) {
            Date result = calendar.getTime();
            String monthYear = monthFormat.format(result);

            if (!monthMap.containsKey(monthYear)) {
                monthMap.put(monthYear, new ArrayList<Date>());
            }
            monthMap.get(monthYear).add(result);
            calendar.add(Calendar.DATE, 1);
        }
        return monthMap;
    }

    // Generate buttons for each month
    private void generateMonthButtons(Map<String, List<Date>> monthMap, Date currentDate) {
        // Change the format of the date to display only the abbreviated day of the week
        final SimpleDateFormat sdf = new SimpleDateFormat("d-EEE", Locale.getDefault());

        // Find the layout where you want to add buttons
        final LinearLayout layout = findViewById(R.id.buttonContainer);

        // Reorder monthMap to ensure March and April are displayed first
        Map<String, List<Date>> reorderedMonthMap = new LinkedHashMap<>();
        for (Map.Entry<String, List<Date>> entry : monthMap.entrySet()) {
            String monthYear = entry.getKey();
            if (monthYear.startsWith("March")) {
                reorderedMonthMap.put(monthYear, entry.getValue());
            }
        }
        for (Map.Entry<String, List<Date>> entry : monthMap.entrySet()) {
            String monthYear = entry.getKey();
            if (monthYear.startsWith("April")) {
                reorderedMonthMap.put(monthYear, entry.getValue());
            }
        }
        for (Map.Entry<String, List<Date>> entry : monthMap.entrySet()) {
            String monthYear = entry.getKey();
            if (!monthYear.startsWith("March") && !monthYear.startsWith("April")) {
                reorderedMonthMap.put(monthYear, entry.getValue());
            }
        }

        // Generate buttons for each month
        for (Map.Entry<String, List<Date>> entry : reorderedMonthMap.entrySet()) {
            String monthYear = entry.getKey();
            List<Date> dates = entry.getValue();

            TextView textView = new TextView(this);
            textView.setText(monthYear);
            textView.setTextSize(18); // Adjust text size for better readability
            layout.addView(textView);

            LinearLayout rowLayout = null;
            int dayCount = 0;
            for (final Date date : dates) {
                if (dayCount % 4 == 0) { // Start new row every 4 days
                    rowLayout = new LinearLayout(this);
                    rowLayout.setLayoutParams(new LinearLayout.LayoutParams(
                            ViewGroup.LayoutParams.MATCH_PARENT,
                            ViewGroup.LayoutParams.WRAP_CONTENT
                    ));
                    layout.addView(rowLayout);
                }

                Button button = new Button(this);
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                        0,
                        ViewGroup.LayoutParams.WRAP_CONTENT,
                        1
                );
                layoutParams.setMargins(8, 8, 8, 8); // Add margins for spacing
                button.setLayoutParams(layoutParams);
                button.setText(sdf.format(date));
                button.setTextSize(16);
                button.setBackgroundResource(R.drawable.available_date); // Set background resource for the current date
                button.setTextColor(Color.GREEN); // Set text color to green
                button.setGravity(Gravity.CENTER);
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(Booking_event.this, Time_Activity.class
                        );
                        intent.putExtra("selectedDate", sdf.format(date));
                        startActivityForResult(intent, 1); // Start the Time_activity
                    }
                });

                if (isSameDay(date, currentDate)) {
                    button.setTextColor(Color.GRAY); // Disable text color for the current date
                    button.setEnabled(false); // Disable the button for the current date
                }

                if (isBooked(date)) {
                    // Change text color to red for booked dates
                    button.setTextColor(Color.RED);
                }

                rowLayout.addView(button);
                dateButtonMap.put(date, button); // Add button to map with corresponding date
                dayCount++;
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if (resultCode == RESULT_OK && data != null) {
                String selectedDate = data.getStringExtra("selectedDate");
                if (selectedDate != null) {
                    Button selectedButton = dateButtonMap.get(parseDate(selectedDate));
                    if (selectedButton != null) {
                        selectedButton.setTextColor(Color.RED); // Change text color to red for booked date
                    }
                }
            }
        }
    }

    private Date parseDate(String dateString) {
        try {
            return new SimpleDateFormat("d-EEE", Locale.getDefault()).parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    private boolean isBooked(Date date) {
        // Implement your logic to check if the date is booked
        return false;
    }

    // Check if two dates are the same day
    private boolean isSameDay(Date date1, Date date2) {
        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(date1);
        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(date2);
        return cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR) &&
                cal1.get(Calendar.MONTH) == cal2.get(Calendar.MONTH) &&
                cal1.get(Calendar.DAY_OF_MONTH) == cal2.get(Calendar.DAY_OF_MONTH);
    }
}






















//        timingAdapter = new TimingAdapter(); // Initialize timingAdapter
//
//        // Set click listener for bookButton
//        bookButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                // Show MaterialDatePicker
//                showDatePicker();
//            }
//        });
//    }
//
//    private void showDatePicker() {
//        Calendar calendar = Calendar.getInstance();
//        MaterialDatePicker.Builder<Long> builder = MaterialDatePicker.Builder.datePicker();
//        builder.setTitleText("Select Date");
//        builder.setSelection(calendar.getTimeInMillis());
//        final MaterialDatePicker<Long> picker = builder.build();
//
//        // Customize the calendar constraints to disable past dates
//        CalendarConstraints.Builder constraintsBuilder = new CalendarConstraints.Builder();
//        constraintsBuilder.setValidator(DateValidatorPointForward.now());
//
//        picker.addOnPositiveButtonClickListener(new MaterialPickerOnPositiveButtonClickListener<Long>() {
//            @Override
//            public void onPositiveButtonClick(Long selection) {
//                // Determine the status of the selected date
//                DateStatus status = getDateStatus(selection);
//
//                // Handle the status accordingly
//                switch (status) {
//                    case AVAILABLE:
//                        handleAvailableDate(selection);
//                        break;
//                    case BOOKED:
//                        handleBookedDate();
//                        break;
//                    case PAST:
//                        handlePastDate();
//                        break;
//                }
//            }
//        });
//
//        picker.show(getSupportFragmentManager(), picker.toString());
//    }
//
//    private void showAvailableTimings() {
//        // Populate timingList with available timings based on the selected date
//        // For example, you can fetch available timings from a database or calculate them based on some logic
//
//        // Clear the existing timing list
//        timingList.clear();
//
//        // Add example timings for demonstration
//        timingList.add("9:00 AM");
//        timingList.add("10:00 AM");
//        timingList.add("11:00 AM");
//        timingList.add("12:00 PM");
//
//        // Notify adapter about data changes
//        timingAdapter.notifyDataSetChanged();
//    }
//
//    private boolean isDateAlreadyBooked(Long selection) {
//        // Check if the selected date is already booked by another user
//        return bookedDates.contains(selection);
//    }
//
//    private DateStatus getDateStatus(Long selection) {
//        // Implement logic to determine the status of the selected date
//        // For simplicity, let's assume all dates in the past are considered past dates
//        Calendar selectedDate = Calendar.getInstance();
//        selectedDate.setTimeInMillis(selection);
//
//        Calendar currentDate = Calendar.getInstance();
//
//        if (selectedDate.before(currentDate)) {
//            return DateStatus.PAST;
//        } else if (bookedDates.contains(selection)) {
//            return DateStatus.BOOKED;
//        } else {
//            return DateStatus.AVAILABLE;
//        }
//    }
//
//    private void handleAvailableDate(Long selection) {
//        // Start UserInfoActivity and pass the selected date as an extra
//        Intent intent = new Intent(Booking_event.this, UserInfoActivity.class);
//        intent.putExtra("selectedDate", selection);
//        startActivity(intent);
//    }
//
//    private void handleBookedDate() {
//        // Handle logic for booked date (e.g., show information)
//        // For demonstration, let's just show a message
//        showToast("This date is already booked.");
//    }
//
//    private void handlePastDate() {
//        // Handle logic for past date (e.g., disable selection)
//        // For demonstration, let's just show a message
//        showToast("You cannot select a past date.");
//    }
//
//    private void showToast(String message) {
//        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
//    }
//
//    @Override
//    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
//        return false;
//    }
//
//    private enum DateStatus {
//        AVAILABLE,
//        BOOKED,
//        PAST
//    }
//
//    // RecyclerView Adapter
//    // RecyclerView Adapter
//    private class TimingAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
//
//        @NonNull
//        @Override
//        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//            // Inflate layout for timing card
//            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_time, parent, false);
//            return new TimingViewHolder(view);
//        }
//
//        @Override
//        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
//            // Bind data to the views inside the ViewHolder
//            // Implement this method according to your requirements
//        }
//
//        @Override
//        public int getItemCount() {
//            // Return the size of your timing list
//            return timingList.size();
//        }
//
//        // ViewHolder class for timing card
//        private class TimingViewHolder extends RecyclerView.ViewHolder {
//            TextView timingTextView;
//
//            TimingViewHolder(@NonNull View itemView) {
//                super(itemView);
////                timing_card = itemView.findViewById(R.id.timing_card);
//            }
//        }
//    }
//
//    }
//}