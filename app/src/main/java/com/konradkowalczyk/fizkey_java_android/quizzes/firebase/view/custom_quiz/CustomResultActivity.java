package com.konradkowalczyk.fizkey_java_android.quizzes.firebase.view.custom_quiz;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.konradkowalczyk.fizkey_java_android.R;
import com.konradkowalczyk.fizkey_java_android.quizzes.firebase.help_class.TaskRecycler;
import com.konradkowalczyk.fizkey_java_android.quizzes.menu.phenomenon.AbstractQuestion;

import java.util.ArrayList;
import java.util.List;

public class CustomResultActivity extends AppCompatActivity {


    private TaskRecycler taskRecycler;
    private int numberOfFields;
    private List<EditText> anwsersEditText = new ArrayList<>();
    private List<ImageButton> imageButtons = new ArrayList<>();




    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_result);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if(getIntent().getAction().equals(CreateCustomQuizActivity.ACTION_NEW)) {
            taskRecycler = null;
            numberOfFields = (Integer) getIntent().getIntExtra(
                    CreateCustomQuizActivity.NUMBER_OF_FIELDS, 2);
        }
        else {
            taskRecycler = (TaskRecycler) getIntent().getParcelableExtra(CreateCustomQuizActivity.TASK_RECYCLER);
            numberOfFields = (Integer) getIntent().getIntExtra(
                    CreateCustomQuizActivity.NUMBER_OF_FIELDS, 2);
        }




        GridLayout gridLayout = findViewById(R.id.gridlayout_custom_result_activity);
        gridLayout.setAlignmentMode(GridLayout.ALIGN_BOUNDS);
        gridLayout.setColumnCount(3);
        gridLayout.setRowCount(numberOfFields + 1);

        //inital view

        final TextView textViewInital = new TextView(this);
        textViewInital.setText(getResources().getString(R.string.card_view_good_answer));
        final EditText editTextInital = new EditText(this);
        editTextInital.setMaxWidth(300);
        anwsersEditText.add(editTextInital);

        gridLayout.addView(textViewInital);
        gridLayout.addView(editTextInital);

        textViewInital.setLayoutParams(createLayout(0,0));
        editTextInital.setLayoutParams(createLayout(0,1));

        Drawable dr = getResources().getDrawable(R.drawable.dices);
        Bitmap bitmap = ((BitmapDrawable) dr).getBitmap();
        Drawable d = new BitmapDrawable(getResources(), Bitmap.createScaledBitmap(bitmap, 100, 100, true));

        for (int i = 1; i < numberOfFields; i++) {

            final TextView textView = new TextView(this);
            textView.setText(getResources().getString(R.string.answer)+" "+ (i+1));

            final EditText editText = new EditText(this);
            editText.setMaxWidth(300);
            anwsersEditText.add(editText);

            final ImageButton button = new ImageButton(this);
            button.setImageDrawable(d);
            button.setScaleType(ImageView.ScaleType.CENTER_CROP);
            button.setPadding(0,0,0,0);
            button.setEnabled(false);
            imageButtons.add(button);
            final int index = i;
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int value = Integer.parseInt(anwsersEditText.get(0).getText().toString());
                    anwsersEditText.get(index).setText(
                            AbstractQuestion.createFakeAnswer(value)+"");
                }
            });

            gridLayout.addView(textView);
            gridLayout.addView(editText);
            gridLayout.addView(button);

            GridLayout.LayoutParams param1 = createLayout(i,0);
            GridLayout.LayoutParams param2 = createLayout(i,1);
            GridLayout.LayoutParams param3 = createLayout(i,2);



            textView.setLayoutParams(param1);
            editText.setLayoutParams(param2);
            button.setLayoutParams(param3);

        }

        editTextInital.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(s.equals("")){
                    for(ImageButton button : imageButtons){
                        button.setEnabled(false);
                    }
                }
                else {
                    for(ImageButton button : imageButtons){
                        button.setEnabled(true);
                    }
                }

            }
        });



    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private GridLayout.LayoutParams createLayout(int row, int column)
    {
        GridLayout.LayoutParams layoutParams = new GridLayout.LayoutParams();
        layoutParams.height = GridLayout.LayoutParams.WRAP_CONTENT;
        layoutParams.width = GridLayout.LayoutParams.WRAP_CONTENT;

        layoutParams.bottomMargin = 5;
        layoutParams.leftMargin = 5;
        layoutParams.rightMargin = 5;
        layoutParams.topMargin = 5;

        layoutParams.columnSpec = GridLayout.spec(column);
        layoutParams.rowSpec = GridLayout.spec(row);

        layoutParams.setGravity(Gravity.CENTER);

        return layoutParams;
    }
}





