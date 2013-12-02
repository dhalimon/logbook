package com.bsteam.logbook.fragments;

import com.bsteam.logbook.R;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.bsteam.logbook.data.*;

public class AddTaskFragment extends Fragment implements View.OnClickListener {

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View rootView = inflater
				.inflate(R.layout.frg_addtask, container, false);

		Button b = (Button) rootView.findViewById(R.id.TaskSave);
		b.setOnClickListener(this);
		return rootView;
	}

	@Override
	public void onClick(View v) {

		EditText taskNameBox = (EditText) getView().findViewById(R.id.TaskName);

		String taskName = taskNameBox.getText().toString();

		TaskType taskType = new TaskType(taskName);

		SQLiteAdapter db = new SQLiteAdapter(getActivity());

		if (db.insertTask(taskType)) {
			Toast.makeText(getActivity(), "Saved!", Toast.LENGTH_LONG).show();

		} else {
			Toast.makeText(getActivity(), "Some thing's wrong!",
					Toast.LENGTH_LONG).show();
		}

	}

}