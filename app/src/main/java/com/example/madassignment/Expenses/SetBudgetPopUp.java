package com.example.madassignment.Expenses;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.example.madassignment.R;

public class SetBudgetPopUp {
    private Context context;
    private PopupWindow popupWindow;

    public SetBudgetPopUp(Context context) {
        this.context = context;
        initPopup();
    }

    private void initPopup() {
        // Inflate the layout for the pop-up
        View popupView = LayoutInflater.from(context).inflate(R.layout.pop_up, null);

        // Reference the TextView and EditText
        TextView textView = popupView.findViewById(R.id.titleTV);
        EditText editText = popupView.findViewById(R.id.setBudgetET);


        // Create the PopupWindow
        popupWindow = new PopupWindow(
                popupView,
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT,
                true  // Set this to true to allow the pop-up to close when touched outside
        );

        Button cancelBtn = popupView.findViewById(R.id.buttonCancel);
        cancelBtn.setOnClickListener(v -> {
            // Close the pop-up
            dismiss();
        });

        Button okBtn = popupView.findViewById(R.id.buttonOK);
        okBtn.setOnClickListener(v -> {
            // Close the pop-up
            dismiss();
        });


        // Set other properties of the PopupWindow if needed
        // For example, you can set animations, background, etc.

        // Set click listener or perform any other initialization steps
        // ...

    }

    public void show(View anchorView) {
        // Show the pop-up at the specified location
        popupWindow.showAtLocation(anchorView, Gravity.CENTER, 0, 0);
    }

    public void dismiss() {
        // Dismiss the pop-up
        popupWindow.dismiss();
    }
}
