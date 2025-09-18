package com.example.listycitylab3;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;



public class AddCityFragment extends DialogFragment {
    interface AddCityDialogListener {
        void addCity(City city);
        void editCity(int position, City city);
    }

    private static final String ARG_CITY_NAME = "city_name";
    private static final String ARG_PROVINCE_NAME = "province_name";
    private static final String ARG_POSITION = "position";
    private AddCityDialogListener listener;
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof AddCityDialogListener) {
            listener = (AddCityDialogListener) context;
        } else {
            throw new RuntimeException(context + " must implement AddCityDialogListener");
        }
    }
    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_add_city, null);
        EditText editCityName = view.findViewById(R.id.edit_text_city_text);
        EditText editProvinceName = view.findViewById(R.id.edit_text_province_text);

        // Read arguments for edit mode and if present replace what was there before
        Bundle args = getArguments();
        final int position = (args != null && args.containsKey("position")) ? args.getInt("position") : -1;
        if (args != null) {
            if (args.containsKey("city_name")) {
                editCityName.setText(args.getString("city_name", ""));
            }

            if (args.containsKey("province_name")) {
                editProvinceName.setText(args.getString("province_name", ""));
            }
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        return builder
                .setView(view)
                .setTitle("Add/Edit a city")
                .setNegativeButton("Cancel", null)
                // Change button label if editing, otherwise its Add
                .setPositiveButton(position >= 0 ? "Save" : "Add", (dialog, which) -> {
                    String cityName = editCityName.getText().toString();
                    String provinceName = editProvinceName.getText().toString();
                    if (position >= 0) {
                        listener.editCity(position, new City(cityName, provinceName));
                    } else {
                        listener.addCity(new City(cityName, provinceName));
                    }
                })
                .create();
    }
}