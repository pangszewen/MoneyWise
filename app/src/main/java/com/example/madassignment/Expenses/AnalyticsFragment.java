package com.example.madassignment.Expenses;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.example.madassignment.R;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AnalyticsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AnalyticsFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public AnalyticsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AnalyticsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AnalyticsFragment newInstance(String param1, String param2) {
        AnalyticsFragment fragment = new AnalyticsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_analytics, container, false);

        // Find the PieChart view
        PieChart pieChart = rootView.findViewById(R.id.pieChart);

        ArrayList<Float> dataValues = new ArrayList<>();
        dataValues.add(15f); // Slice 1
        dataValues.add(20f); // Slice 2
        dataValues.add(10f); // Slice 3
        dataValues.add(15f); // Slice 4
        dataValues.add(10f); // Slice 5
        dataValues.add(15f); // Slice 6
        dataValues.add(15f); // Slice 7

        ArrayList<Integer> colors = new ArrayList<>();
        colors.add(Color.parseColor("#3498db")); // Blue
        colors.add(Color.parseColor("#2ecc71")); // Green
        colors.add(Color.parseColor("#e74c3c")); // Red
        colors.add(Color.parseColor("#f1c40f")); // Yellow
        colors.add(Color.parseColor("#9b59b6")); // Purple
        colors.add(Color.parseColor("#e67e22")); // Orange
        colors.add(Color.parseColor("#1abc9c")); // Teal

        pieChart.setData(dataValues, colors);

        return rootView;
    }
}