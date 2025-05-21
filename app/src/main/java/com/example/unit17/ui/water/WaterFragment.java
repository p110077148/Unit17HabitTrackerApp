package com.example.unit17.ui.water;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.unit17.databinding.FragmentWaterBinding;

public class WaterFragment extends Fragment {

    private FragmentWaterBinding binding;
    private WaterViewModel waterViewModel;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        waterViewModel = new ViewModelProvider(this).get(WaterViewModel.class);
        binding = FragmentWaterBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        // Update text
        waterViewModel.getWaterCount().observe(getViewLifecycleOwner(), count ->
                binding.textWater.setText(count + " glasses"));

        // Button actions
        binding.btnIncrement.setOnClickListener(v -> waterViewModel.increment());
        binding.btnDecrement.setOnClickListener(v -> waterViewModel.decrement());
        binding.btnReset.setOnClickListener(v -> waterViewModel.reset());

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
