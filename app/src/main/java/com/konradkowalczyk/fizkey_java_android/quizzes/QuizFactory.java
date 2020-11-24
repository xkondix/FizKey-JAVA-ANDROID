package com.konradkowalczyk.fizkey_java_android.quizzes;

import android.content.Context;
import android.os.Build;

import androidx.annotation.RequiresApi;

import com.konradkowalczyk.fizkey_java_android.quizzes.mainly.FallDown;
import com.konradkowalczyk.fizkey_java_android.quizzes.mainly.Question;
import com.konradkowalczyk.fizkey_java_android.quizzes.mainly.ThrowUp;

import java.util.Random;

public class QuizFactory {

    private Random random;
    private Context context;

    public QuizFactory(Context context)
    {
        this.context=context;
        random = new Random();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public Question getQuestion()
    {
        switch(random.nextInt(2))
        {
            case 0:
            {
                return new FallDown(context);
            }
            case 1:
            {
                return new ThrowUp(context);
            }
            default:
                return new FallDown(context);

        }

    }

}
