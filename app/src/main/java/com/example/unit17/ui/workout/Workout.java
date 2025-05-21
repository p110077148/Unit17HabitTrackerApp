package com.example.unit17.ui.workout;

public class Workout {
    private final String name;
    private final String time;

    public Workout(String name, String time) {
        this.name = name;
        this.time = time;
    }

    public String getName() {
        return name;
    }

    public String getTime() {
        return time;
    }
}
