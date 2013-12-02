package com.bsteam.logbook.fragments;

import java.util.ArrayList;
import java.util.List;

import com.bsteam.logbook.R;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.bsteam.logbook.data.*;

public class AddTaskFragment extends Fragment implements View.OnClickListener {

	private static final int REQUEST_CODE = 1234;
	private static final int RESULT_OK = 0;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View rootView = inflater
				.inflate(R.layout.frg_addtask, container, false);

		Button b = (Button) rootView.findViewById(R.id.TaskSaveBtn);

		b.setOnClickListener(this);

		Button rB = (Button) rootView.findViewById(R.id.RecordSndBtn);

		rB.setOnClickListener(this);

		// Disable button if no recognition service is present
		PackageManager pm = getActivity().getPackageManager();
		List<ResolveInfo> activities = pm.queryIntentActivities(new Intent(
				RecognizerIntent.ACTION_RECOGNIZE_SPEECH), 0);
		if (activities.size() == 0) {
			rB.setEnabled(false);
		}

		return rootView;
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == REQUEST_CODE && resultCode == RESULT_OK) {
			// Populate the wordsList with the String values the recognition
			// engine thought it heard
			ArrayList<String> matches = data
					.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);

			EditText taskNameBox = (EditText) getView().findViewById(
					R.id.saveTextBox);

			taskNameBox.setText(matches.get(0));
			
			/*
			 * wordsList.setAdapter(new ArrayAdapter<String>(this,
			 * android.R.layout.simple_list_item_1, matches)); wordsList
			 * .setOnItemClickListener(new ListView.OnItemClickListener() {
			 * 
			 * @Override public void onItemClick(AdapterView<?> a, View v, int
			 * pos, long l) { try { String UserExpenditure = wordsList
			 * .getItemAtPosition(pos).toString();
			 * Toast.makeText(getApplicationContext(), UserExpenditure,
			 * Toast.LENGTH_LONG) .show(); } catch (Exception e) { System.out
			 * .println("cannot get the selected index"); } } });
			 */
		}
		super.onActivityResult(requestCode, resultCode, data);
	}

	@Override
	public void onClick(View v) {
		// unified click event for all event handlers in Frag 1
		switch (v.getId()) {
		// for Save Button
		case R.id.TaskSaveBtn:
			EditText taskNameBox = (EditText) getView().findViewById(
					R.id.saveTextBox);

			String taskName = taskNameBox.getText().toString();

			TaskType taskType = new TaskType(taskName);

			SQLiteAdapter db = new SQLiteAdapter(getActivity());

			if (db.insertTask(taskType)) {
				Toast.makeText(getActivity(), "Saved!", Toast.LENGTH_LONG)
						.show();

			} else {
				Toast.makeText(getActivity(), "Some thing's wrong!",
						Toast.LENGTH_LONG).show();
			}
			break;
		// for Rec Button
		case R.id.RecordSndBtn:
			Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
			intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
					RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
			intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Add An Entry...");
			startActivityForResult(intent, REQUEST_CODE);
			break;
		}

	}

}