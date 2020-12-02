package com.konradkowalczyk.fizkey_java_android.quizzes.menu.phenomenon.kinematics;

import com.konradkowalczyk.fizkey_java_android.quizzes.menu.phenomenon.AbstractQuestion;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class AbstractKinematics extends AbstractQuestion {

    protected static final double ACCELERATION = 9.81;


    private List<String> unitsBaseName = new ArrayList<>(Arrays.asList(new String[]{"m","m/s","s","m/s^2"}));

    private static final Map<Integer,Map<String,Double>> UNITS_CONVERT = new HashMap<Integer,Map<String,Double>>() {{
        put(0, new HashMap<String,Double>() {{
            put("cm", 100.0);
            put("mm", 1000.0);
            put("km", 0.001);
            }});

        put(1, new HashMap<String,Double>() {{
            put("km/h", 3.6);
            put("km/s", 0.001);
            put("km/min", 0.06);
            put("cm/s", 100.0);
            put("cm/min", 6000.0);
            put("cm/h", 360000.0);
        }});

        put(2, new HashMap<String,Double>() {{
            put("min", 0.0166666667);
            put("h", 0.000277777778);
        }});

    }};


    public AbstractKinematics(int countQuestion) {
        super(countQuestion);
    }

    protected Map<String,Double> getConverts(int index)
    {
        return  UNITS_CONVERT.get(index);
    }

    protected List<String> getUnitBase()
    {
        return unitsBaseName;
    }
}
