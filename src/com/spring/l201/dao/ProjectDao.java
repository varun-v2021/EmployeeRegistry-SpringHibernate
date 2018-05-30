package com.spring.l201.dao;

import java.util.Collection;
import java.util.List;
import com.spring.l201.model.Employee2;
import com.spring.l201.model.Project;

public interface ProjectDao {
	public void save(Project proj);

	public void persist(Project proj);

	public List<Project> findProjectByName(String pName);

	public List<Employee2> findEmpByProjId(int projId);

	public Employee2 findEmpByMid(Long id);

	public Collection<String> getAllDistinctProjects();

	public List<Employee2> getAllEmployees();
	
	public List<Project> getAllProjects();

	public void deleteEmp(Employee2 empObj);
}
