package com.example.madassignment.Expenses;

import android.content.res.ColorStateList;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.madassignment.R;
import com.example.madassignment.databinding.ActivityMainExpensesBinding;
import com.example.madassignment.databinding.ActivityMainExpensesBinding;

public class MainActivity extends AppCompatActivity {

    ActivityMainExpensesBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        binding = ActivityMainExpensesBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        replaceFragment(new BookFragment());


        binding.bottomNavigationViewExpenses.setOnItemSelectedListener(item -> {
            // Use the NavController for BottomNavigationView items
            if (item.getItemId() == R.id.book) {
                replaceFragment(new BookFragment());
            } else if (item.getItemId() == R.id.analytics) {
                replaceFragment(new AnalyticsFragment());
            } else if (item.getItemId() == R.id.budget) {
                replaceFragment(new BudgetFragment());
            } else if (item.getItemId() == R.id.trend) {
                replaceFragment(new TrendFragment());
            }
            return true;
        });

        // Disable default icon tinting
        binding.bottomNavigationViewExpenses.setItemIconTintList(null);
        binding.bottomNavigationViewExpenses.setItemTextColor(null);

        // Set custom icon tinting
        int[][] states = new int[][]{
                new int[]{android.R.attr.state_checked}, // checked
                new int[]{-android.R.attr.state_checked}  // unchecked
        };

        int selectedIconColor = ContextCompat.getColor(this, R.color.dark_blue);
        int unselectedIconColor = ContextCompat.getColor(this, R.color.white);
        int selectedTextColor = ContextCompat.getColor(this, R.color.dark_blue);
        int unselectedTextColor = ContextCompat.getColor(this, R.color.white);

        int[] colors = new int[]{
                selectedIconColor,
                unselectedIconColor
        };

        int[] textColors = new int[]{
                selectedTextColor,
                unselectedTextColor
        };

        ColorStateList iconColorStateList = new ColorStateList(states, colors);
        ColorStateList textColorStateList = new ColorStateList(states, textColors);

        binding.bottomNavigationViewExpenses.setItemIconTintList(iconColorStateList);
        binding.bottomNavigationViewExpenses.setItemTextColor(textColorStateList);

    }

    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

}