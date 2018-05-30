package com.spring.l201.model;

import java.util.List;
import org.hibernate.validator.constraints.NotEmpty;

public class Task {
	@NotEmpty(message = "Please select a project")
	private List<String> project;
	@NotEmpty(message = "Please enter a description")
	private String description;
	@NotEmpty(message = "Please enter a start date")
	private String startDate;
	@NotEmpty(message = "Please enter a due date")
	private String dueDate;
	@NotEmpty(message = "Please select employee(s)")
	private List<String> employee;
	private List<Employee2> employeeList;
	private List<Project> projectList;
	private String projectName;
	
	public List<String> getProject() {
		return project;
	}
	public void setProject(List<String> project) {
		this.project = project;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	public String getDueDate() {
		return dueDate;
	}
	public void setDueDate(String dueDate) {
		this.dueDate = dueDate;
	}
	public List<String> getEmployee() {
		return employee;
	}
	public void setEmployee(List<String> employee) {
		this.employee = employee;
	}
	public List<Employee2> getEmployeeList() {
		return employeeList;
	}
	public void setEmployeeList(List<Employee2> employeeList) {
		this.employeeList = employeeList;
	}
	public List<Project> getProjectList() {
		return projectList;
	}
	public void setProjectList(List<Project> projectList) {
		this.projectList = projectList;
	}
	public String getProjectName() {
		return projectName;
	}
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
}
