package com.spring.l201.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "PROJECT")
public class Project {
	@Id
	@GeneratedValue
	private Long id;
	@Column(name = "NAME")
	private String projectName;
	@Column(name = "DESCRIPTION")
	private String description;
	@Column(name = "START_DATE")
	private String startDate;
	@Column(name = "DUE_DATE")
	private String dueDate;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name="PROJECT_ID")
	private List<Employee2> employees = new ArrayList<>();

	public Project() {

	}

	public Project(String projectName, String description, String startDate, String dueDate) {
		this.projectName = projectName;
		this.description = description;
		this.startDate = startDate;
		this.dueDate = dueDate;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
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

	public List<Employee2> getEmployees() {
		return employees;
	}

	public void setEmployees(List<Employee2> employees) {
		this.employees = employees;
	}

	@Override
	public String toString() {
		return "Project [id=" + id + ", name=" + projectName + ", description=" + description + ", startDate="
				+ startDate + "dueDate=" + dueDate + "]";
	}
}
