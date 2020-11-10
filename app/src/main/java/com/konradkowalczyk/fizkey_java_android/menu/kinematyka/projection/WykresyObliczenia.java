package com.konradkowalczyk.fizkey_java_android.menu.kinematyka.projection;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.sqrt;

public class WykresyObliczenia implements Serializable {

    private List<Double> listTime;
    private List<Double> listAcceleration;
    private List<Double> listVelocity;
    private List<Double> listHeight;
    private List<Double> listPotentialEnergy;
    private List<Double> listKineticEnergy;
    private List<Double> listTotalEnergy;
    private double y0;
    private double v0;
    private double g;
    private double cX;
    private double dt;
    private long scalaY,scalaX;
    private int width,heigh,changeX,changeY,lenX,lenY;


    private double countVy(double vY)
    {
        return vY - (g * dt) - (cX * vY * dt);
    }

    private double countY(double y, double vY)
    {
        return y + (vY *dt);
    }


    public WykresyObliczenia(double h, double v0, double g, double cX)
    {
        this.y0 = h;
        this.v0 = v0;
        this.g = g;
        this.cX = cX;
        listTime = new ArrayList<>();
        listAcceleration = new ArrayList<>();
        listVelocity = new ArrayList<>();
        listHeight = new ArrayList<>();
        listPotentialEnergy = new ArrayList<>();
        listKineticEnergy = new ArrayList<>();
        listTotalEnergy = new ArrayList();
        wypelnij();
    }


    void wypelnij()
    {
        int n=1000;
        dt = 2.1/n;
        double licznik = 1;
        double t=0;
        double ep= 1 * g * y0;
        double ek = (1* v0*v0)/2;
        double ec = ep + ek;
        listAcceleration.add(g);
        listVelocity.add(v0);
        listHeight.add(y0);
        listKineticEnergy.add(ek);
        listPotentialEnergy.add(ep);
        listTotalEnergy.add(ec);
        listTime.add(t);
        System.out.println("ep - "+ep+"; ek - "+ek+"; ec - "+ec);




        while(y0>=0){
            v0 = countVy(v0);
            y0 = countY(y0,v0);
            ep= 10 * g * y0;
            ek = (10* v0*v0)/2;
            ec = ep + ek;
            t+=dt;
            if(t>licznik)
            {
                listAcceleration.add(g);
                listVelocity.add(v0);
                listHeight.add(y0);
                listKineticEnergy.add(ek);
                listPotentialEnergy.add(ep);
                listTotalEnergy.add(ec);
                listTime.add(licznik);
                licznik++;

            }


        }



    }



    public List<Double> getListTime() {
        return listTime;
    }

    public List<Double> getListAcceleration() {
        return listAcceleration;
    }

    public List<Double> getListVelocity() {
        return listVelocity;
    }

    public List<Double> getListHeight() {
        return listHeight;
    }

    public List<Double> getListPotentialEnergy() {
        return listPotentialEnergy;
    }

    public List<Double> getListKineticEnergy() {
        return listKineticEnergy;
    }

    public List<Double> getListTotalEnergy() {
        return listTotalEnergy;
    }



}

