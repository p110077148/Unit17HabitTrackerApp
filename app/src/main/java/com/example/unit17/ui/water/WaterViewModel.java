package com.example.unit17.ui.water;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class WaterViewModel extends ViewModel {

    private final MutableLiveData<Integer> waterCount = new MutableLiveData<>(0);

    public LiveData<Integer> getWaterCount() {
        return waterCount;
    }

    public void increment() {
        waterCount.setValue(waterCount.getValue() + 1);
    }

    public void decrement() {
        int current = waterCount.getValue();
        if (current > 0) {
            waterCount.setValue(current - 1);
        }
    }

    public void reset() {
        waterCount.setValue(0);
    }
}
