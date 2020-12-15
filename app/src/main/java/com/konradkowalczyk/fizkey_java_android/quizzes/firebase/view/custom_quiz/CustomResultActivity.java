package com.konradkowalczyk.fizkey_java_android.quizzes.firebase.view.custom_quiz;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.konradkowalczyk.fizkey_java_android.R;
import com.konradkowalczyk.fizkey_java_android.quizzes.firebase.help_class.TaskRecycler;
import com.konradkowalczyk.fizkey_java_android.quizzes.menu.QuizFactory;
import com.konradkowalczyk.fizkey_java_android.quizzes.menu.phenomenon.AbstractQuestion;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CustomResultActivity extends AppCompatActivity {

    public final static String RESULTS = "result";

    private TaskRecycler taskRecycler;
    private int numberOfFields;
    private List<EditText> anwsersEditText = new ArrayList<>();
    private List<ImageButton> imageButtons = new ArrayList<>();

    private Spinner randForcesSpinner;
    private EditText questionEditText;
    private Button backButton, saveButton;




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


        questionEditText = findViewById(R.id.topic_custom_result_activity);
        randForcesSpinner = findViewById(R.id.forces_custom_result_activity);
        backButton = findViewById(R.id.back_custom_result_activity);
        saveButton = findViewById(R.id.save_custom_result_activity);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(checkAnswers() && checkQuestion())
                {
                    taskRecycler = new TaskRecycler(getQuestion(),getGoodAnswer(),getAnswers());
                    Intent intent = new Intent();
                    intent.putExtra(RESULTS, taskRecycler);
                    setResult(RESULT_OK, intent);
                    finish();

                }

            }
        });


        randForcesSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                setEditTextsFromRandForce(
                        getResources().getStringArray(R.array.force_for_quizzes_result)[position]);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }});

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
            imageButtons.add(button);
            final int index = i;
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(!anwsersEditText.get(0).getText().toString().equals("")
                            && anwsersEditText.get(0).getText().toString().length() > 0) {
                        int value = fromStringToIntValue(anwsersEditText.get(0).getText().toString());
                        if (value != -1){
                            anwsersEditText.get(index).setText(
                                AbstractQuestion.createFakeAnswer(value) + "");
                        }
                    }
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


        setEditTextsFromLastCreate();

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

    private void setEditTextsFromLastCreate() {
        if (getIntent().getAction().equals(CreateCustomQuizActivity.CHANGE_RESULTS_REQUEST)) {

            final Handler handler = new Handler();
            handler.post(new Runnable() {
                @Override
                public void run() {
                    questionEditText.setText(taskRecycler.getQuestion());
                    for (int i = 0; i < anwsersEditText.size(); i++) {
                        anwsersEditText.get(i).setText(taskRecycler.getListAnswers().get(i));
                    }
                }
            });
        }
    }

    private void setEditTextsFromRandForce(final String force)
    {
        final Handler handler = new Handler();
        handler.post(new Runnable() {
            @Override
            public void run() {
                if(force.equals(getResources().getStringArray(R.array.force_for_quizzes_result)[0]))
                {
                    questionEditText.setText("");
                    for (int i = 0; i < anwsersEditText.size(); i++) {
                        anwsersEditText.get(i).setText("");
                    }
                }
                else {
                    QuizFactory quizFactory = new QuizFactory(getApplicationContext(),numberOfFields);
                    quizFactory.setLevel(QuizFactory.Level.NORMAL);
                    quizFactory.acceptForces(Arrays.asList(force));
                    quizFactory.generateQuestions(1);

                    questionEditText.setText(quizFactory.getDataQuestionAnwser().get(0).getQuestion());
                    List<String> factoryAnswers = quizFactory.getDataQuestionAnwser().get(0).getAnswers();

                    int positiveNumber = quizFactory.getDataQuestionAnwser().get(0).getPositiveNumber();
                    String goodAnswer = factoryAnswers.get(positiveNumber);
                    factoryAnswers.set(positiveNumber,factoryAnswers.get(0));
                    factoryAnswers.set(0,goodAnswer);

                    for (int i = 0; i < anwsersEditText.size(); i++) {
                        anwsersEditText.get(i).setText(factoryAnswers.get(i));
                    }
                }
            }
        });
    }

    private int fromStringToIntValue(String words)
    {
        String value = "";
        int convert;
        String array[] = words.split("");
        for(int i = 0; i < array.length; i++)
        {
            try {
                convert = Integer.valueOf(array[i]);
                value += array[i];
            }catch(NumberFormatException e) {
                if(!value.equals("")) {
                    break;
                }
            }
        }

        if(value.equals("")) {
            return -1;
        }
        else {
            return Integer.parseInt(value);
        }
    }


    private String getQuestion()
    {
        return questionEditText.getText().toString();
    }
    private boolean checkQuestion()
    {
        if(questionEditText.getText().toString().equals("")) {
            questionEditText.setError(getResources().getString(R.string.empty_field));
            return false;
        }
        else {
            return true;
        }
    }

    private List<String> getAnswers()
    {
        List<String> answers = new ArrayList<>();
        for(EditText editText : anwsersEditText)
        {
            answers.add(editText.getText().toString());
        }

        return answers;
    }

    private boolean checkAnswers() {
        boolean result = true;
        List<String> answers = new ArrayList<>();

        for (EditText editText : anwsersEditText) {
            if (editText.getText().toString().equals("")) {
                editText.setError(getResources().getString(R.string.empty_field));
                result = false;
            }

            for (EditText editTextMain : anwsersEditText) {
                if(editTextMain.getText().toString().equals(editText.getText().toString())
                    && !editText.equals(editTextMain)) {
                    result = false;
                    editText.setError(getResources().getString(R.string.values_the_same));
                }
            }

        }
        return result;

    }

    private String getGoodAnswer()
    {
        return anwsersEditText.get(0).getText().toString();
    }


}





