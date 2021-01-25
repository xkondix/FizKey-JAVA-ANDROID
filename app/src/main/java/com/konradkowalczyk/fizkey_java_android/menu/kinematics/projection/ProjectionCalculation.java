package com.konradkowalczyk.fizkey_java_android.menu.kinematics.projection;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


public class ProjectionCalculation implements Serializable {

    private List<Double> times;
    private List<Double> accelerations;
    private List<Double> velocityies;
    private List<Double> velocityiesX;
    private List<Double> velocityiesY;

    private List<Double> yPostions;
    private List<Double> xPositions;

    private List<Double> degrees;
    private List<Double> potentialEnergies;
    private List<Double> kineticEnergies;
    private List<Double> totalEnergies;

    private double y0, v0y, x0, v0x, v;
    private double g, cX, dt, counter, angle;
    private double mass;




    public static class Builder
    {

        //required varibles
        private final double  h, v0, counter;

        //optional varibles
        private double angle = 90;
        private double g = 9.81;
        private double cX = 0;
        private double mass = 1;

        public Builder(double h, double v0, double counter){
            this.h = h;
            this.v0 = v0;
            this.counter = counter;
        }

        public Builder angle(double angle)
        {
            this.angle = angle;
            return this;
        }
        public Builder acceleration(double g)
        {
            this.g = g;
            return this;
        }

        public Builder resistance(double cX)
        {
            this.cX = cX;
            return this;
        }

        public Builder mass(double mass)
        {
            this.mass = mass;
            return this;
        }


        public ProjectionCalculation build()
        {
            return new ProjectionCalculation(this);
        }

    }

    private ProjectionCalculation(Builder builder)
    {
        this.y0 = builder.h;
        this.x0 = 0;
        this.angle = builder.angle;
        this.v0y = builder.v0 * Math.sin((Math.PI* angle)/180);
        this.v0x = builder.v0 * Math.cos((Math.PI* angle)/180);
        this.v = builder.v0;
        this.g = builder.g;
        this.cX = builder.cX;
        this.mass = builder.mass;
        this.counter = builder.counter;


        times = new ArrayList<>();
        accelerations = new ArrayList<>();

        velocityies = new ArrayList<>();
        velocityiesX = new ArrayList<>();
        velocityiesY = new ArrayList<>();

        yPostions = new ArrayList<>();
        xPositions = new ArrayList<>();

        potentialEnergies = new ArrayList<>();
        kineticEnergies = new ArrayList<>();
        totalEnergies = new ArrayList<>();
        degrees = new ArrayList<>();

        calculate();

    }


    private double countVy(double vY)
    {
        return vY - (g * dt) - (cX * vY * dt);
    }

    private double countY(double y, double vY)
    {
        return y + (vY *dt);
    }

    private double countVx(double vX)
    {
        return vX - (cX * vX * dt);
    }

    private double countX(double x, double vX)
    {
        return x + (vX *dt);
    }


    private void calculate()
    {
        int n=1000;
        dt = 2.1/n;

        double licznik = counter;
        double t=0;
        double ep= 1 * g * y0;
        double ek = (1* v0y * v0y)/2;
        double ec = ep + ek;

        accelerations.add(g);
        velocityiesY.add(v0y);
        velocityiesX.add(v0x);
        velocityies.add(v);
        yPostions.add(y0);
        xPositions.add(x0);
        kineticEnergies.add(ek);
        potentialEnergies.add(ep);
        totalEnergies.add(ec);
        degrees.add(round(Math.atan2(v0y,v0x)));
        times.add(t);



        while(countY(y0,v0y)>0.0){
            v0y = countVy(v0y);
            v0x = countVx(v0x);
            y0 = countY(y0, v0y);
            x0 = countX(x0, v0x);
            v = Math.sqrt(Math.pow(v0x,2) + Math.pow(v0y,2));
            angle = Math.atan2(v0y, v0x);
            ep= 1 * g * y0;
            ek = (1* v0y * v0y)/2;
            ec = ep + ek;
            t+=dt;


            if(t>licznik)
            {
                accelerations.add(g);
                velocityiesY.add(round(v0y));
                velocityiesX.add(round(v0x));
                velocityies.add(round(v));
                yPostions.add(round(y0));
                xPositions.add(round(x0));
                kineticEnergies.add(round(ek));
                potentialEnergies.add(round(ep));
                totalEnergies.add(round(ec));
                degrees.add(round(Math.atan2(v0y,v0x)));
                times.add(round(t));

                licznik += counter;

            }

        }

        accelerations.add(g);
        velocityiesY.add(round(v0y));
        velocityiesX.add(round(v0x));
        velocityies.add(round(v));
        yPostions.add(round(0));
        xPositions.add(round(x0));
        kineticEnergies.add(round(ek));
        potentialEnergies.add(round(ep));
        totalEnergies.add(round(ec));
        degrees.add(round(Math.atan2(v0y,v0x)));
        times.add(round(t));


    }


    private double round(double value)
    {
        return Math.round(value * 100.0) / 100.0;
    }



    public String getVelocityScore(double time, double velocity, double acceleration)
    {
        StringBuilder sb = new StringBuilder();
        sb.append("t = ");
        sb.append((String.format("%.2f", time)));
        sb.append("s | ");
        sb.append("v = ");
        sb.append((String.format("%.2f", velocity)));
        sb.append("m/s | ");
        sb.append("y = ");
        sb.append((String.format("%.2f", acceleration)));
        sb.append("m");

        return sb.toString();
    }


//    public String[] getListOfPhenomeno()
//    {
//        String[] phenomenos = new String[times.size()];
//
//        for(int i = 0; i < times.size(); i++)
//        {
//            phenomenos[i] = getString(listTime.get(i),listVelocity.get(i),listHeight.get(i));
//        }
//
//        return phenomenos;
//    }


    public List<Double> getTimes() {
        return times;
    }

    public List<Double> getAccelerations() {
        return accelerations;
    }

    public List<Double> getVelocityies() {
        return velocityies;
    }

    public List<Double> getVelocityiesX() {
        return velocityiesX;
    }

    public List<Double> getVelocityiesY() {
        return velocityiesY;
    }

    public List<Double> getPostionsY() {
        return yPostions;
    }

    public List<Double> getPositionsX() {
        return xPositions;
    }

    public List<Double> getPotentialEnergies() {
        return potentialEnergies;
    }

    public List<Double> getKineticEnergies() {
        return kineticEnergies;
    }

    public List<Double> getTotalEnergies() {
        return totalEnergies;
    }

    public List<Double> getDegrees() {
        return degrees;
    }
}

