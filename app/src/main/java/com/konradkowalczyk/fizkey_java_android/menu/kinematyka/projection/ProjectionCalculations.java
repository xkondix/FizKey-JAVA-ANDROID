package com.konradkowalczyk.fizkey_java_android.menu.kinematyka.projection;

import android.graphics.Point;

import com.konradkowalczyk.fizkey_java_android.physics.Vector2D;

public class ProjectionCalculations {

    private Vector2D force;
    private Vector2D velocity;
    private Vector2D acceleration;
    private Vector2D resistance;
    private Vector2D distance;
    private double mass;
    private double kineticEnergy;
    private double potentialEnergy;
    private double time;



    public static class Builder
    {

        //optional varibles
        private Vector2D force;
        private Vector2D velocity;
        private Vector2D acceleration;
        private Vector2D distance;
        private Vector2D resistance;
        private double mass;
        private double kineticEnergy;
        private double potentialEnergy;
        private double time;

        public Builder(){}

        public Builder velocity(double velocityX, double velocityY)
        {
            this.velocity = new Vector2D(velocityX,velocityY);
            return this;
        }
        public Builder acceleration(double accelerationX, double accelerationY)
        {
            this.acceleration = new Vector2D(accelerationX,accelerationY);
            return this;
        }

        public Builder resistance(double resistanceX, double resistanceY)
        {
            this.resistance = new Vector2D(resistanceX,resistanceY);
            return this;
        }

        public Builder force(double forceX, double forceY)
        {
            this.force = new Vector2D(forceX,forceY);
            return this;
        }

        public Builder distance(double distanceX, double disstanceY)
        {
            this.force = new Vector2D(distanceX,disstanceY);
            return this;
        }

        public Builder mass(double mass)
        {
            this.mass=mass;
            return this;
        }

        public Builder kineticEnergy(double mass)
        {
            this.kineticEnergy=kineticEnergy;
            return this;
        }


        public Builder potentialEnergy(double mass)
        {
            this.potentialEnergy=potentialEnergy;
            return this;
        }

        public Builder time(double time)
        {
            this.time=time;
            return this;
        }




        public ProjectionCalculations build()
        {
            return new ProjectionCalculations(this);
        }

    }

    private ProjectionCalculations(Builder builder)
    {
        this.force=builder.force;
        this.velocity=builder.velocity;
        this.acceleration=builder.acceleration;
        this.resistance=builder.resistance;
        this.distance=builder.distance;
        this.mass=builder.mass;
        this.kineticEnergy=builder.kineticEnergy;
        this.potentialEnergy=builder.potentialEnergy;

    }



}
