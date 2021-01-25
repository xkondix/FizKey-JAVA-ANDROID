package com.konradkowalczyk.fizkey_java_android.menu.kinematics.projection;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ProjectionViewModel extends ViewModel {

    private MutableLiveData<Double> positionXMutableLiveData;
    private MutableLiveData<Double> positionYMutableLiveData;
    private MutableLiveData<Double> velocityXMutableLiveData;
    private MutableLiveData<Double> velocityYMutableLiveData;
    private MutableLiveData<Double> timeMutableLiveData;
    private MutableLiveData<Double> angleMutableLiveData;



    public ProjectionViewModel() {
        super();
    }

    public void init()
    {
        if(positionXMutableLiveData == null) {
            positionXMutableLiveData = new MutableLiveData<>() ;
        }
        if(positionYMutableLiveData == null) {
            positionYMutableLiveData = new MutableLiveData<>();
        }
        if(velocityXMutableLiveData == null) {
            velocityXMutableLiveData = new MutableLiveData<>();
        }
        if(velocityYMutableLiveData == null) {
            velocityYMutableLiveData = new MutableLiveData<>();
        }
        if(timeMutableLiveData == null) {
            timeMutableLiveData = new MutableLiveData<>();
        }
        if(angleMutableLiveData == null) {
            angleMutableLiveData = new MutableLiveData<>();
        }
    }


    public LiveData getPositionXMutableLiveData() {
        return positionXMutableLiveData;
    }

    public void setPositionXMutableLiveData(double positionX) {
        this.positionXMutableLiveData.postValue(positionX);
    }

    public LiveData getPositionYMutableLiveData() {
        return positionYMutableLiveData;
    }

    public void setPositionYMutableLiveData(double positionY) {
        this.positionYMutableLiveData.postValue(positionY);
    }


    public LiveData getVelocityXMutableLiveData() {
        return velocityXMutableLiveData;
    }

    public void setVelocityXMutableLiveData(double velocityX) {
        this.velocityXMutableLiveData.postValue(velocityX);
    }

    public LiveData getVelocityYMutableLiveData() {
        return velocityYMutableLiveData;
    }

    public void setVelocityYMutableLiveData(double velocityY) {
        this.velocityYMutableLiveData.postValue(velocityY);
    }

    public LiveData getTimeMutableLiveData() {
        return timeMutableLiveData;
    }

    public void setTimeMutableLiveData(double time) {
        this.timeMutableLiveData.postValue(time);
    }

    public LiveData getAngleMutableLiveData() {
        return angleMutableLiveData;
    }

    public void setAngleMutableLiveData(double angle) {
        this.angleMutableLiveData.postValue(angle);
    }
}
