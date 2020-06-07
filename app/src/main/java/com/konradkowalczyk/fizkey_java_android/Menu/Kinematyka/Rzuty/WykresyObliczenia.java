package com.konradkowalczyk.fizkey_java_android.Menu.Kinematyka.Rzuty;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.sqrt;

public class WykresyObliczenia implements Serializable {

    private List<Float> listTime;
    private List<Float> listA;
    private List<Float> listV;
    private List<Float> listH;
    private float h;
    private float v0;
    private float g;

    public List<Float> getListTime() {
        return listTime;
    }

    public void setListTime(List<Float> listTime) {
        this.listTime = listTime;
    }

    public List<Float> getListA() {
        return listA;
    }

    public void setListA(List<Float> listA) {
        this.listA = listA;
    }

    public List<Float> getListV() {
        return listV;
    }

    public void setListV(List<Float> listV) {
        this.listV = listV;
    }

    public List<Float> getListH() {
        return listH;
    }

    public void setListH(List<Float> listH) {
        this.listH = listH;
    }

    public float getH() {
        return h;
    }

    public void setH(float h) {
        this.h = h;
    }

    public float getV0() {
        return v0;
    }

    public void setV0(float v0) {
        this.v0 = v0;
    }

    public float getG() {
        return g;
    }

    public void setG(float g) {
        this.g = g;
    }

    public WykresyObliczenia(float h, float v0, float g)
    {
        this.h=h;
        this.v0=v0;
        this.g=g;
        listTime = new ArrayList<>();
        listA = new ArrayList<>();
        listV = new ArrayList<>();
        listH = new ArrayList<>();
        wypelnij();
    }


    void wypelnij() {
        float i = 0;
        float pos = h;
        float v = v0;
        int licznik = 0;
        int czas = 0;


        while (i <= sqrt(h * 2 / g)) {

            if (licznik == 1000) {
                czas++;
                listTime.add((float) czas);
                listV.add(v);
                listH.add(pos);
                listA.add(g);
                licznik = 0;
            }

            i += 0.001;
            v = (g * i);
            pos = h - (g * i * i / 2);
            licznik++;


        }

        listTime.add(i);
        listV.add(v);
        listH.add(0f);
        listA.add(g);
    }



}
