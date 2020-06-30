package com.konradkowalczyk.fizkey_java_android.menu.kinematyka.Rzuty;

import java.util.ArrayList;

import static java.lang.Math.sqrt;

public class RzutyAtrybuty implements java.io.Serializable {

    private double v0x = 0;
    private double v0y = 0;
    private double y;
    private double aY=9.81;
    private double x;
    private double fSPR = -1; // k*x siła sprężystości
    private double cA = -1; //opór powietrza
     java.util.List<String> lista = new ArrayList<String>();



    public double getV0x()
    {
        return v0x;
    }

    public double getV0y()
    {
        return v0y;
    }

    public double getY()
    {
        return y;
    }

    public double getX()
    {
        return x;
    }

    public double getFSPR()
    {
        return fSPR;
    }

    public double getCa()
    {
        return cA;
    }

    public void setV0x(double v0x)
    {
        this.v0x=v0x;
    }

    public void setV0y(double v0y)
    {
        this.v0y=v0y;
    }

    public void setY(double y)
    {
        this.y=y;
    }

    public void setX(double x)
    {
        this.x=x;
    }

    public void setFSPR(double fSPR)
    {
        this.fSPR=fSPR;
    }

    public void setCa(double cA)
    {
        this.cA=cA;
    }

    public double getAy()
    {
        return aY;
    }

    public void setAy(double ay)
    {
        this.aY=ay;
    }




    void wypelnij()
    {
        double i=0;
        double pos = getY();
        double v=0;
        int licznik = 0;
        int czas = 0;
        lista.add("Wyniki");
        lista.add(bd(czas,v,pos));
        while(i<=sqrt(getY()*2/aY))
        {

            if(licznik==1000) {
                czas++;
                lista.add(bd(czas,v,pos));
                licznik = 0;
            }

            i+=0.001;
            v= (aY*i);
            pos= getY() - (aY*i*i/2);
            licznik++;



        }

        StringBuilder sb = new StringBuilder();
        sb.append("tk = ");
        sb.append((String.format("%.2f", sqrt(getY()*2/aY))));
        sb.append("s | ");
        sb.append("vk = ");
        sb.append((String.format("%.2f", sqrt(2*getY()*aY))));
        sb.append("m/s | ");
        sb.append("y = ");
        sb.append(0);
        sb.append("m");
        lista.add(sb.toString());
    }

    public String[] getList()
    {
        String[] array = lista.toArray(new String[0]);

        return array;
    }

    public String bd(int s, double v, double pos)
    {
        StringBuilder sb = new StringBuilder();
        sb.append("t = ");
        sb.append(s);
        sb.append("s | ");
        sb.append("v = ");
        sb.append((String.format("%.2f", v)));
        sb.append("m/s | ");
        sb.append("y = ");
        sb.append((String.format("%.2f", pos)));
        sb.append("m");

        return sb.toString();
    }


}
