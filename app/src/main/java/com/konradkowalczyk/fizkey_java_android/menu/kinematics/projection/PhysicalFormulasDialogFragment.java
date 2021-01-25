package com.konradkowalczyk.fizkey_java_android.menu.kinematics.projection;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.DialogFragment;

import com.konradkowalczyk.fizkey_java_android.R;

public class PhysicalFormulasDialogFragment extends DialogFragment {

    private TextView okTextView,cancelTextView,formulasTextView;
    private String[] formulas;

    public PhysicalFormulasDialogFragment() {
        // Required empty public constructor
    }

    public PhysicalFormulasDialogFragment(String[] formulasVertical) {
        this.formulas=formulasVertical;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_dialog_physical_formulas, container, false);

        cancelTextView = view.findViewById(R.id.cancel);
        okTextView = view.findViewById(R.id.ok);
        formulasTextView = view.findViewById(R.id.formulas_fragment_text);

        String forms = "";
        for(String formula : formulas)
        {
            forms += formula;
            forms += System.getProperty("line.separator");

        }

        formulasTextView.setText(forms);


        cancelTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDialog().dismiss();
            }
        });

        okTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDialog().dismiss();
            }
        });

        return view;
    }
}