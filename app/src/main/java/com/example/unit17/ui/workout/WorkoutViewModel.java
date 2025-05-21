package com.example.unit17.ui.workout;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;

public class WorkoutViewModel extends ViewModel {

    private final MutableLiveData<String> mText = new MutableLiveData<>("Workout Tracker");

    // list of workouts
    private final MutableLiveData<List<Workout>> workoutList = new MutableLiveData<>(new ArrayList<>());

    public LiveData<String> getText() {
        return mText;
    }

    public LiveData<List<Workout>> getWorkoutList() {
        return workoutList;
    }

    public void updateWorkout(int index, Workout updatedWorkout) {
        List<Workout> currentList = workoutList.getValue();
        if (currentList != null && index >= 0 && index < currentList.size()) {
            currentList.set(index, updatedWorkout);
            workoutList.setValue(new ArrayList<>(currentList)); // Trigger LiveData
        }
    }

    public void addWorkout(Workout workout) {
        List<Workout> currentList = workoutList.getValue();
        if (currentList != null) {
            currentList.add(workout);
            workoutList.setValue(new ArrayList<>(currentList));
        }
    }

    public void removeWorkout(int index) {
        List<Workout> currentList = workoutList.getValue();
        if (currentList != null && index >= 0 && index < currentList.size()) {
            currentList.remove(index);
            workoutList.setValue(new ArrayList<>(currentList)); // Update LiveData
        }
    }

}
