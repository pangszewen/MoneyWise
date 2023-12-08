package com.example.madassignment.Expenses;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.Icon;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.vectordrawable.graphics.drawable.VectorDrawableCompat;

import com.example.madassignment.R;
import com.example.madassignment.databinding.ActivityMainExpensesBinding;
import com.example.madassignment.databinding.ActivityMainExpensesBinding;
import com.example.madassignment.forum.Forum_MainActivity;
import com.example.madassignment.home.HomeActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class MainActivity extends AppCompatActivity {

    ActivityMainExpensesBinding binding;

    BottomNavigationView bottomNavigationView;

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
        int grey = ContextCompat.getColor(this, R.color.grey);

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

        bottomNavigationView = findViewById(R.id.bottomExpenseNavigationView);
        bottomNavigationView.setBackground(null);
        MenuItem menuItemDisable = bottomNavigationView.getMenu().findItem(R.id.iconExpenses);
        menuItemDisable.setEnabled(false);
        menuItemDisable.setIcon(R.drawable.circle);

        Drawable icon = VectorDrawableCompat.create(getResources(), R.drawable.outline_expenses_white, getTheme());
        icon.setTintList(AppCompatResources.getColorStateList(this, R.color.grey));
        menuItemDisable.setIcon(icon);

        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int itemID = item.getItemId();
                if(itemID==R.id.iconHome) {
                    startActivity(new Intent(MainActivity.this, HomeActivity.class));
                    return true;
                }else if(itemID==R.id.iconForum) {
                    startActivity(new Intent(MainActivity.this, Forum_MainActivity.class));
                    return true;
                }else if(itemID==R.id.iconExpenses) {
                    startActivity(new Intent(MainActivity.this, MainActivity.class));
                    return true;
                }else
                    return false;
            }
        });

    }

    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

}