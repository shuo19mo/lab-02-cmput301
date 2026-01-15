package com.example.listycity;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    ListView cityList;
    ArrayAdapter<String> cityAdapter;
    ArrayList<String> dataList;

    EditText cityInput;
    Button addButton;
    Button deleteButton;

    int selectedIndex = -1; // -1 means nothing selected

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("ListyCity");

        cityList = findViewById(R.id.city_list);
        cityInput = findViewById(R.id.city_input);
        addButton = findViewById(R.id.add_button);
        deleteButton = findViewById(R.id.delete_button);

        // Allow tapping an item to select it (Android will highlight selection)
        cityList.setChoiceMode(ListView.CHOICE_MODE_SINGLE);

        String[] cities = {
                "Edmonton", "Vancouver", "Moscow", "Sydney",
                "Berlin", "Vienna", "Tokyo", "Beijing",
                "Osaka", "New Delhi"
        };

        dataList = new ArrayList<>();
        dataList.addAll(Arrays.asList(cities));

        cityAdapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_activated_1,
                dataList
        );
        cityList.setAdapter(cityAdapter);

        // Tap to select
        cityList.setOnItemClickListener((parent, view, position, id) -> {
            selectedIndex = position;
        });

        // Add City
        addButton.setOnClickListener(v -> {
            String newCity = cityInput.getText().toString().trim();

            if (TextUtils.isEmpty(newCity)) {
                cityInput.setError("City name cannot be empty");
                return;
            }

            dataList.add(newCity);
            cityAdapter.notifyDataSetChanged();
            cityInput.setText("");
        });

        // Delete City (must select first)
        deleteButton.setOnClickListener(v -> {
            if (selectedIndex < 0 || selectedIndex >= dataList.size()) {
                Toast.makeText(this, "Select a city to delete", Toast.LENGTH_SHORT).show();
                return;
            }

            dataList.remove(selectedIndex);
            cityAdapter.notifyDataSetChanged();

            // Clear selection after deletion
            cityList.clearChoices();
            selectedIndex = -1;
        });
    }
}
