package com.bsteam.logbook.data;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class SQLiteAdapter extends SQLiteOpenHelper {

	static final String dbName = "TaskDb";
	static final String tblTask = "TaskTable";
	static final String colId = "_id";
	static final String colName = "effortName";
	static final String colDate = "effortDate";

	public SQLiteAdapter(Context con) {
		super(con, dbName, null, 1);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {

		String createTableQuery = "CREATE TABLE " + tblTask + " (" + colId
				+ " integer primary key autoincrement ," + colName + " TEXT, "
				+ colDate + " datetime default current_timestamp)";

		db.execSQL(createTableQuery);

	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

	}

	public boolean insertTask(TaskType task) {

		try {

			SQLiteDatabase db = this.getWritableDatabase();

			ContentValues values = new ContentValues();

			values.put(colName, task.TaskType);

			db.insert(tblTask, null, values);
			return true;
		} catch (Exception ex) {
			return false;
		}
	}

	public List<TaskType> getAllTasks() {

		List<TaskType> taskList = new LinkedList<TaskType>();

		SQLiteDatabase db = this.getReadableDatabase();

		Cursor cursor;

		try {
			String query = "SELECT * FROM " + tblTask + "order by " + colDate
					+ " desc";
			cursor = db.rawQuery(query, null);

			TaskType t = null;
			if (cursor != null) {
				if (cursor.moveToFirst()) {
					do {

						t = new TaskType();

						t.TaskId = Integer.parseInt(cursor.getString(cursor
								.getColumnIndex(colId)));
						t.TaskType = cursor.getString(cursor
								.getColumnIndex(colName));

						SimpleDateFormat formatter = new SimpleDateFormat(
								"yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);
						Date date = formatter.parse(cursor.getString(cursor
								.getColumnIndex(colDate)));
						t.TaskDate = date;
						t.TaskDate = new Date();
						taskList.add(t);
					} while (cursor.moveToNext());
				}
			}

		} catch (SQLException e) {
			Log.e("DB Error", e.toString());
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return taskList;
	}
}