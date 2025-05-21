package com.example.unit17.ui.timer;

import android.os.Handler;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class TimerViewModel extends ViewModel {

    private final MutableLiveData<String> timeString = new MutableLiveData<>("00:00");
    private final Handler handler = new Handler();
    private long secondsElapsed = 0;
    private boolean isRunning = false;

    private final Runnable timerRunnable = new Runnable() {
        @Override
        public void run() {
            secondsElapsed++;
            updateTimeText();
            handler.postDelayed(this, 1000);
        }
    };

    public LiveData<String> getTimeString() {
        return timeString;
    }

    public void startTimer() {
        if (!isRunning) {
            handler.postDelayed(timerRunnable, 1000);
            isRunning = true;
        }
    }

    public void pauseTimer() {
        handler.removeCallbacks(timerRunnable);
        isRunning = false;
    }

    public void resetTimer() {
        pauseTimer();
        secondsElapsed = 0;
        updateTimeText();
    }

    private void updateTimeText() {
        int minutes = (int) (secondsElapsed / 60);
        int seconds = (int) (secondsElapsed % 60);
        timeString.postValue(String.format("%02d:%02d", minutes, seconds));
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        handler.removeCallbacks(timerRunnable);
    }
}
