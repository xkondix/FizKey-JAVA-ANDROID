package com.konradkowalczyk.fizkey_java_android.plot;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class PlotData implements Serializable {

    private Map<Float,Float> data;
    private List<Float> xList;
    private List<Float> yList;

    public PlotData()
    {
        data = new TreeMap<>();
        xList = new ArrayList<>();
        yList = new ArrayList<>();

    }

    public PlotData(List xList, List yList)
    {
        this.xList=xList;
        this.yList=yList;
    }

    private void setData()
    {
        if(!checkInterval())
        {

        }
    }

    private int getInerval()
    {
        return xList.size()/10;
    }

    private boolean checkInterval()
    {
        return getInerval()>1;
    }


    public Map<Float, Float> getData() {
        return data;
    }




}
