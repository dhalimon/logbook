package com.bsteam.logbook.fragments;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.bsteam.logbook.data.*;

import com.bsteam.logbook.R;

public class ShowTasksFragment extends Fragment {

	@Override
	public void onResume() {
		UpdateList(getView());
		super.onResume();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View rootView = inflater.inflate(R.layout.frg_showtasks, container,
				false);

		UpdateList(rootView);
		return rootView;
	}

	
	public void UpdateList(View rootView) {
		SQLiteAdapter db = new SQLiteAdapter(getActivity());

		List<TaskType> tasks = db.getAllTasks();
		if (tasks.size() > 0) {
			ArrayList<String> taskNameList = new ArrayList<String>();

			for (TaskType task : tasks) {

				taskNameList.add(task.TaskType);
			}

			ListView listview = (ListView) rootView
					.findViewById(R.id.listView1);

			ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
					getActivity(), android.R.layout.simple_list_item_1,
					taskNameList);
			listview.setAdapter(arrayAdapter);
		}

	}
}
