package com.bsteam.logbook.data;

import java.util.Date;

public class TaskType {
	public Integer TaskId = 0;
	public String TaskType = "";
	public Date TaskDate;

	public TaskType() {
	}

	public TaskType(String TaskType) {
		this.TaskId = 0;
		this.TaskDate = new Date();
		this.TaskType = TaskType;
	}
}
