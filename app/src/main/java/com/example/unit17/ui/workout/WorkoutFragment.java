package com.example.unit17.ui.workout;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.unit17.databinding.FragmentWorkoutBinding;

public class WorkoutFragment extends Fragment {

    private FragmentWorkoutBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        WorkoutViewModel workoutViewModel =
                new ViewModelProvider(this).get(WorkoutViewModel.class);

        binding = FragmentWorkoutBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        // Set title
        workoutViewModel.getText().observe(getViewLifecycleOwner(), text ->
                binding.textWorkout.setText(text));

        // Observe workout list
        workoutViewModel.getWorkoutList().observe(getViewLifecycleOwner(), workouts -> {
            binding.workoutList.removeAllViews(); // clear old items

            for (int i = 0; i < workouts.size(); i++) {
                Workout workout = workouts.get(i);
                final int index = i;

                // Horizontal layout
                android.widget.LinearLayout row = new android.widget.LinearLayout(requireContext());
                row.setOrientation(android.widget.LinearLayout.HORIZONTAL);
                row.setPadding(0, 8, 0, 8);

                // Workout text
                TextView workoutView = new TextView(requireContext());
                workoutView.setText("• " + workout.getName() + " - " + workout.getTime());
                workoutView.setTextSize(16);
                workoutView.setTextColor(0xFFFFFFFF);
                workoutView.setLayoutParams(new android.widget.LinearLayout.LayoutParams(
                        0, ViewGroup.LayoutParams.WRAP_CONTENT, 1)); // Let text take most of the space
                workoutView.setOnClickListener(v -> showEditDialog(index, workoutViewModel, workout));

// Delete icon
                android.widget.ImageView deleteIcon = new android.widget.ImageView(requireContext());
                deleteIcon.setImageResource(android.R.drawable.ic_menu_delete); // Use system delete icon
                deleteIcon.setColorFilter(0xFFFF4444); // Optional: red tint
                deleteIcon.setPadding(16, 0, 0, 0);
                deleteIcon.setOnClickListener(v -> workoutViewModel.removeWorkout(index));

// Add both to the row
                row.addView(workoutView);
                row.addView(deleteIcon);
                binding.workoutList.addView(row);
            }

            if (workouts.isEmpty()) {
                binding.placeholderText.setText("No workouts yet");
            } else {
                binding.placeholderText.setText(""); // Clear the placeholder message
            }
        });

        // Add workout button
        binding.btnAddWorkout.setOnClickListener(v -> showAddDialog(workoutViewModel));

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void showAddDialog(WorkoutViewModel viewModel) {
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(requireContext());
        builder.setTitle("Add Workout");

        android.widget.LinearLayout layout = new android.widget.LinearLayout(requireContext());
        layout.setOrientation(android.widget.LinearLayout.VERTICAL);
        layout.setPadding(50, 20, 50, 20);

        final android.widget.EditText nameInput = new android.widget.EditText(requireContext());
        nameInput.setHint("Workout Name");
        layout.addView(nameInput);

        final android.widget.EditText timeInput = new android.widget.EditText(requireContext());
        timeInput.setHint("Duration (e.g. 15 mins)");
        layout.addView(timeInput);

        builder.setView(layout);

        builder.setPositiveButton("Add", (dialog, which) -> {
            String name = nameInput.getText().toString().trim();
            String time = timeInput.getText().toString().trim();

            if (!name.isEmpty() && !time.isEmpty()) {
                viewModel.addWorkout(new Workout(name, time));
                binding.placeholderText.setText("Workout added!");
            }
        });

        builder.setNegativeButton("Cancel", null);
        builder.show();
    }
    private void showEditDialog(int index, WorkoutViewModel viewModel, Workout currentWorkout) {
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(requireContext());
        builder.setTitle("Edit Workout");

        android.widget.LinearLayout layout = new android.widget.LinearLayout(requireContext());
        layout.setOrientation(android.widget.LinearLayout.VERTICAL);
        layout.setPadding(50, 20, 50, 20);

        final android.widget.EditText nameInput = new android.widget.EditText(requireContext());
        nameInput.setText(currentWorkout.getName());
        nameInput.setHint("Workout Name");
        layout.addView(nameInput);

        final android.widget.EditText timeInput = new android.widget.EditText(requireContext());
        timeInput.setText(currentWorkout.getTime());
        timeInput.setHint("Duration");
        layout.addView(timeInput);

        builder.setView(layout);

        builder.setPositiveButton("Update", (dialog, which) -> {
            String newName = nameInput.getText().toString().trim();
            String newTime = timeInput.getText().toString().trim();

            if (!newName.isEmpty() && !newTime.isEmpty()) {
                viewModel.updateWorkout(index, new Workout(newName, newTime));
            }
        });

        builder.setNegativeButton("Cancel", null);
        builder.show();
    }
}
