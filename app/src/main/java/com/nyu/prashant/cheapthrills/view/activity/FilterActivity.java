package com.nyu.prashant.cheapthrills.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import com.nyu.prashant.cheapthrills.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by rohit on 20/12/18.
 */

public class FilterActivity extends AppCompatActivity {

    private static final String TAG = "Filter Print";
    private Button applyFilter;
    private List<CheckBox> filters = new ArrayList<>();
    private final List<Integer> categories =  Arrays.asList(
                                                            R.id.fashion_accessories,
                                                            R.id.kids,
                                                            R.id.home_goods,
                                                            R.id.food_alcohol,
                                                            R.id.massage,
                                                            R.id.bars_clubs,
                                                            R.id.life_skills_classes,
                                                            R.id.restaurants,
                                                            R.id.baby,
                                                            R.id.unknown,
                                                            R.id.electronics,
                                                            R.id.clothing,
                                                            R.id.automotive_services,
                                                            R.id.hair_removal);

    private Map<Integer, String> idToValue = new HashMap<>();

    private void initWidgets() {

        for (int category : categories) {
            CheckBox checkBox = findViewById(category);
            checkBox.setId(category);
            filters.add(checkBox);
        }
        applyFilter = findViewById(R.id.applyFilter);
    }

    private void initMap() {

        idToValue.put(R.id.fashion_accessories, "fashion_accessories");
        idToValue.put(R.id.kids, "kids");
        idToValue.put(R.id.home_goods, "home_goods");
        idToValue.put(R.id.food_alcohol, "food_alcohol");
        idToValue.put(R.id.massage, "massage");
        idToValue.put(R.id.bars_clubs, "bars-clubs");
        idToValue.put(R.id.life_skills_classes, "life-skills-classes");
        idToValue.put(R.id.restaurants, "restaurants");
        idToValue.put(R.id.baby, "baby");
        idToValue.put(R.id.unknown, "unknown");
        idToValue.put(R.id.electronics, "electronics");
        idToValue.put(R.id.clothing, "clothing");
        idToValue.put(R.id.automotive_services, "automotive-services");
        idToValue.put(R.id.hair_removal, "hair-removal");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);
        initMap();
        initWidgets();
//        addListenerOnFilters();
        addListenerOnButton();
    }

    /*private void addListenerOnFilters() {

        for (int category : categories) {
            CheckBox check = findViewById(category);
            check.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    // is checkbox checked?
                    // if (((CheckBox) v).isChecked()) {}
                }
            });
        }
    }*/

    public void addListenerOnButton() {

        applyFilter.setOnClickListener(new View.OnClickListener() {

            //Run when button is clicked
            @Override
            public void onClick(View v) {

                List<String> categories = new ArrayList<>();
                for (CheckBox check : filters) {
                    if (check.isChecked()) {
                        categories.add(idToValue.get(check.getId()));
                    }
                }
                final EditText radius = findViewById(R.id.radius);
                String radiusVal;
                if (radius.getText() == null) {
                    radiusVal = "1";
                } else {
                    radiusVal = String.valueOf(radius.getText());
                }
                openDealsActivity(categories, radiusVal);
            }

            private void openDealsActivity(List<String> categories, String radius) {

                StringBuilder csv = new StringBuilder();
                for (String category : categories) {
                    csv.append(category).append(",");
                }
                csv.append(radius);
                Intent dealsIntent = new Intent(FilterActivity.this, DealsActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("params", csv.toString());
                dealsIntent.putExtras(bundle);
                FilterActivity.this.startActivity(dealsIntent);
            }
        });
    }
}
