package com.example.madassignment.Expenses;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.madassignment.R;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.atomic.AtomicReference;

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
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    CollectionReference categoriesCollection = db.collection("EXPENSE_CATEGORY");
    FirebaseAuth auth = FirebaseAuth.getInstance();
    FirebaseUser user = auth.getCurrentUser();
    String userID = user.getUid();
    Timestamp timestamp = Timestamp.now();
    Date currentDate = new Date(timestamp.getSeconds() * 1000); // Convert seconds to milliseconds
    SimpleDateFormat dateFormat = new SimpleDateFormat("MMMyyyy", Locale.US);
    String formattedDate = dateFormat.format(currentDate);
    // Access or create the user's document in "USER_DETAILS" collection
    DocumentReference userDocRef = db.collection("USER_DETAILS").document(userID);

    // Access or create the "expenses" subcollection for the current user
    CollectionReference expensesCollection = userDocRef.collection("EXPENSE");

    // Access or create the document for the current month and year
    DocumentReference monthDocRef = expensesCollection.document("EXPENSE_MONTH");

    // Access or create the "expense" subcollection for the current month
    CollectionReference monthExpensesCollection = monthDocRef.collection(formattedDate);
    Firebase_Expenses fbe = new Firebase_Expenses();


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
        ArrayList<Integer> colors = new ArrayList<>();
        ArrayList<String> category = new ArrayList<>();
        colors.add(Color.parseColor("#3498db")); // Blue
        colors.add(Color.parseColor("#2ecc71")); // Green
        colors.add(Color.parseColor("#e74c3c")); // Red
        colors.add(Color.parseColor("#f1c40f")); // Yellow
        colors.add(Color.parseColor("#9b59b6")); // Purple
        colors.add(Color.parseColor("#e67e22")); // Orange
        colors.add(Color.parseColor("#1abc9c")); // Teal
        TextView expenseTV = rootView.findViewById(R.id.expenseTV);
        TextView tvAmount1 = rootView.findViewById(R.id.tvAmountMeal);
        TextView tvAmount2 = rootView.findViewById(R.id.tvAmountDaily);
        TextView tvAmount3 = rootView.findViewById(R.id.tvAmountTransport);
        TextView tvAmount4 = rootView.findViewById(R.id.tvAmountCommunicate);
        TextView tvAmount5 = rootView.findViewById(R.id.tvAmountRecreation);
        TextView tvAmount6 = rootView.findViewById(R.id.tvAmountMedical);
        TextView tvAmount7 = rootView.findViewById(R.id.tvAmountOthers);
        TextView tvPercent1 = rootView.findViewById(R.id.tvPercentMeal);
        TextView tvPercent2 = rootView.findViewById(R.id.tvPercentDaily);
        TextView tvPercent3 = rootView.findViewById(R.id.tvPercentTransport);
        TextView tvPercent4 = rootView.findViewById(R.id.tvPercentCommunicate);
        TextView tvPercent5 = rootView.findViewById(R.id.tvPercentRecreation);
        TextView tvPercent6 = rootView.findViewById(R.id.tvPercentMedical);
        TextView tvPercent7 = rootView.findViewById(R.id.tvPercentOthers);

        fbe.calculateTotalExpense(new Firebase_Expenses.TotalExpenseCallback() {
            double newExpense;

            @Override
            public void onTotalExpenseCalculated(double totalExpense) {
                if (totalExpense % 1 == 0) {
                    // Convert to int if the decimal part is zero
                    newExpense = (int) totalExpense;
                    expenseTV.setText("RM" + Integer.toString((int) newExpense));
                } else {
                    newExpense = totalExpense;
                    expenseTV.setText("RM" + Double.toString(newExpense));
                }

                for (int i=1; i<=7; i++){
                    int finalI = i;
                    fbe.calculateCategoryExpense(i, new Firebase_Expenses.CategoryExpenseCallback() {
                        @Override
                        public void onCategoryExpenseCalculated(double expenseC) {
                            double newExpenseC;
                            if (expenseC % 1 == 0) {
                                // Convert to int if the decimal part is zero
                                newExpenseC = (int) expenseC;
                                if (finalI ==1){
                                    tvAmount1.setText("RM" + Integer.toString((int) newExpenseC));
                                } else if (finalI==2) {
                                    tvAmount2.setText("RM" + Integer.toString((int) newExpenseC));
                                } else if (finalI==3) {
                                    tvAmount3.setText("RM" + Integer.toString((int) newExpenseC));
                                } else if (finalI==4) {
                                    tvAmount4.setText("RM" + Integer.toString((int) newExpenseC));
                                } else if (finalI==5) {
                                    tvAmount5.setText("RM" + Integer.toString((int) newExpenseC));
                                } else if (finalI==6) {
                                    tvAmount6.setText("RM" + Integer.toString((int) newExpenseC));
                                } else {
                                    tvAmount7.setText("RM" + Integer.toString((int) newExpenseC));
                                }

                            } else {
                                newExpenseC = expenseC;
                                if (finalI ==1){
                                    tvAmount1.setText("RM" + Double.toString(newExpenseC));
                                } else if (finalI==2) {
                                    tvAmount2.setText("RM" + Double.toString(newExpenseC));
                                } else if (finalI==3) {
                                    tvAmount3.setText("RM" + Double.toString(newExpenseC));
                                } else if (finalI==4) {
                                    tvAmount4.setText("RM" + Double.toString(newExpenseC));
                                } else if (finalI==5) {
                                    tvAmount5.setText("RM" + Double.toString(newExpenseC));
                                } else if (finalI==6) {
                                    tvAmount6.setText("RM" + Double.toString(newExpenseC));
                                } else {
                                    tvAmount7.setText("RM" + Double.toString(newExpenseC));
                                }
                            }

                            int percent = (int)((newExpenseC/newExpense)*100);
                                if (finalI ==1){
                                    tvPercent1.setText(Integer.toString(percent)+"%");
                                } else if (finalI==2) {
                                    tvPercent2.setText(Integer.toString(percent)+"%");
                                } else if (finalI==3) {
                                    tvPercent3.setText(Integer.toString(percent)+"%");
                                } else if (finalI==4) {
                                    tvPercent4.setText(Integer.toString(percent)+"%");
                                } else if (finalI==5) {
                                    tvPercent5.setText(Integer.toString(percent)+"%");
                                } else if (finalI==6) {
                                    tvPercent6.setText(Integer.toString(percent)+"%");
                                } else {
                                    tvPercent7.setText(Integer.toString(percent)+"%");
                                }

                            if (percent>0){
                                float floatPercent = (float) percent;
                                dataValues.add(floatPercent);
                                if (finalI ==1){
                                    category.add("Meal");
                                } else if (finalI==2) {
                                    category.add("Daily");
                                } else if (finalI==3) {
                                    category.add("Transport");
                                } else if (finalI==4) {
                                    category.add("Communicate");
                                } else if (finalI==5) {
                                    category.add("Recreation");
                                } else if (finalI==6) {
                                    category.add("Medical");
                                } else {
                                    category.add("Others");
                                }
                            }
                            pieChart.setData(dataValues, colors, category);
                            }
                        @Override
                        public void onError(Exception exception) {
                            exception.printStackTrace();
                        }
                    });
                }
            }

            @Override
            public void onError(Exception exception) {
                exception.printStackTrace();
            }
        });


        return rootView;
    }


}