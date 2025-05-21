package com.example.unit17.ui.timer;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.unit17.databinding.FragmentTimerBinding;

public class TimerFragment extends Fragment {

    private FragmentTimerBinding binding;
    private TimerViewModel timerViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        timerViewModel = new ViewModelProvider(this).get(TimerViewModel.class);
        binding = FragmentTimerBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        // Update timer display
        final TextView timerText = binding.textTimer;
        timerViewModel.getTimeString().observe(getViewLifecycleOwner(), timerText::setText);

        // Button actions
        binding.btnStart.setOnClickListener(v -> timerViewModel.startTimer());
        binding.btnPause.setOnClickListener(v -> timerViewModel.pauseTimer());
        binding.btnReset.setOnClickListener(v -> timerViewModel.resetTimer());

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
