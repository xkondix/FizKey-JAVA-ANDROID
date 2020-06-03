package com.konradkowalczyk.fizkey_java_android;

public class Vector2D {

    private double x,y;

    public Vector2D(double x, double y)
    {
        this.x=x;
        this.y=y;
    }

    public double getX()
    {
        return x;
    }

    public void setX(double x)
    {
        this.x=x;
    }

    public double getY()
    {
        return y;
    }

    public void setY(double y)
    {
        this.y=y;
    }

    public double getLength()
    {
        return Math.sqrt((x*x) + (y*y));
    }

    public Vector2D colne()
    {
        return new Vector2D(x,y);
    }

    public void negate()
    {
        this.x = -x;
        this.y = -y;
    }

    public double normalize()
    {
        double len = getLength();
        if(len>0) {
            this.x = this.x / len;
            this.x = this.x / len;
        }

        return getLength();

    }

    public Vector2D add(Vector2D v)
    {
        return new Vector2D(this.x+v.x,this.y+v.y);
    }

    public void increment(Vector2D v)
    {
        this.x=x+v.x;
        this.y=y+v.y;
    }

    public void increment(double dx, double dy)
    {
        this.x+=dx;
        this.y+=dy;
    }

    public Vector2D sub(Vector2D v)
    {
        return new Vector2D(this.x-v.x,this.y-v.y);
    }

    public void decrement(Vector2D v)
    {
        this.x=x-v.x;
        this.y=y-v.y;
    }

    public void decrement(double dx, double dy)
    {
        this.x-=dx;
        this.y-=dy;
    }

    public void scale(int k)
    {
        this.x*=k;
        this.y*=k;
    }

    public double product(Vector2D v)
    {
        return (x*v.x)+(y*v.y);
    }

    public double distance(Vector2D v1, Vector2D v2)
    {
        return (v1.sub(v2)).getLength();
    }

    public int angle(Vector2D v1, Vector2D v2)
    {
        return (int)Math.acos((v1.product(v2))/(v1.getLength()*v2.getLength()));
    }
}


