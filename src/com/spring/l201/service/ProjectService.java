package com.spring.l201.service;

import java.util.Collection;
import java.util.List;

import com.spring.l201.model.Employee2;
import com.spring.l201.model.Project;
import com.spring.l201.model.Task;

public interface ProjectService {
	public void init();

	public void save(Project proj);

	public void persist(Project proj);

	public List<Employee2> getEmpByProjId(int id);

	public List<Project> getProjectByName(String pName);

	public Employee2 getEmpById(Long id);

	public Collection<String> getAllDistinctProjects();

	public List<Employee2> getAllEmployees();

	public void deleteEmp(Employee2 empObj);

	public List<Project> getAllProjects();

	public boolean validateInputDates(String startDate, String dueDate);

	public void populateProjectDetails(List<Project> projectList, List<String> descriptionList, List<Task> taskList,
			List<String> projectNames);

	public List<String> getEmployeesAssociatedWithProject(String projectName);

}
