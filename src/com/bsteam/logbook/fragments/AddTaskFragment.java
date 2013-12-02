package com.bsteam.logbook.fragments;

import java.util.ArrayList;
import java.util.List;

import com.bsteam.logbook.R;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.bsteam.logbook.data.*;

public class AddTaskFragment extends Fragment implements View.OnClickListener,
		OnItemClickListener {

	private static final int REQUEST_CODE = 1234;
	private static final int RESULT_OK = -1;

	ListView wordList;
	Dialog listDialog;

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

			

			listDialog = new Dialog(getActivity());
			listDialog.setTitle("Did you mean this? ");
			LayoutInflater li = (LayoutInflater) getActivity()
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			View v = li.inflate(R.layout.list, null, false);
			listDialog.setContentView(v);
			listDialog.setCancelable(true);
			// there are a lot of settings, for dialog, check them all out!

			wordList = (ListView) listDialog.findViewById(R.id.listview);
			wordList.setOnItemClickListener(this);
			wordList.setAdapter(new ArrayAdapter<String>(getActivity(),
					android.R.layout.simple_list_item_1, matches));
			// now that the dialog is set up, it's time to show it
			listDialog.show();

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
			intent.putExtra(RecognizerIntent.EXTRA_PROMPT,
					"What did you do today?");
			startActivityForResult(intent, REQUEST_CODE);
			break;
		}

	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int pos, long arg3) {
		// TODO Auto-generated method stub
		String UserExpenditure = wordList.getItemAtPosition(pos).toString();
		EditText taskNameBox = (EditText) getView().findViewById(
				R.id.saveTextBox);
		taskNameBox.setText(UserExpenditure);
	}

}