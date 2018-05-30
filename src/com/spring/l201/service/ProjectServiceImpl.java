package com.spring.l201.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.spring.l201.dao.ProjectDao;
import com.spring.l201.model.Employee2;
import com.spring.l201.model.Project;
import com.spring.l201.model.Task;

@Service
@Transactional
public class ProjectServiceImpl implements ProjectService {

	@Autowired
	ProjectDao projectDao;

	@Override
	public void init() {
		System.out.println("Initializing project/employee information");
		Project proj1 = new Project();

		proj1.setDescription("Push Notifications");
		proj1.setStartDate("05-05-2018");
		proj1.setDueDate("05-06-2018");
		proj1.setProjectName("iPhone app UI");

		Project proj2 = new Project();

		proj2.setDescription("Graphics slicing");
		proj2.setStartDate("05-05-2018");
		proj2.setDueDate("05-06-2018");
		proj2.setProjectName("iPad Bugs");

		List<Employee2> project1Employees = new ArrayList<>();
		List<Employee2> project2Employees = new ArrayList<>();

		Employee2 emp1 = new Employee2();
		emp1.setMid("M10440XX");
		emp1.setEmployeeName("Varun V");

		Employee2 emp2 = new Employee2();
		emp2.setMid("M10440XX");
		emp2.setEmployeeName("Vivek PV");

		Employee2 emp3 = new Employee2();
		emp3.setMid("M10440XX");
		emp3.setEmployeeName("Vivian P");

		Employee2 emp4 = new Employee2();
		emp4.setMid("M10440XX");
		emp4.setEmployeeName("Apoorva R");

		Employee2 emp5 = new Employee2();
		emp5.setMid("M10440XX");
		emp5.setEmployeeName("Rahul Dev");

		project1Employees.add(emp1);
		project1Employees.add(emp2);
		project1Employees.add(emp3);
		project2Employees.add(emp4);
		project2Employees.add(emp5);

		proj1.setEmployees(project1Employees);

		proj2.setEmployees(project2Employees);

		projectDao.save(proj1);
		projectDao.save(proj2);

		System.out.println("Initialization complete");

	}

	@Override
	public void save(Project proj) {
		projectDao.save(proj);
	}

	@Override
	public List<Employee2> getEmpByProjId(int id) {
		return projectDao.findEmpByProjId(id);
	}

	@Override
	public Collection<String> getAllDistinctProjects() {
		return projectDao.getAllDistinctProjects();
	}

	@Override
	public List<Employee2> getAllEmployees() {
		return projectDao.getAllEmployees();
	}

	@Override
	public void persist(Project proj) {
		projectDao.persist(proj);
	}

	@Override
	public List<Project> getProjectByName(String pName) {
		return projectDao.findProjectByName(pName);
	}

	@Override
	public Employee2 getEmpById(Long id) {
		return projectDao.findEmpByMid(id);
	}

	@Override
	public void deleteEmp(Employee2 empObj) {
		projectDao.deleteEmp(empObj);
	}

	@Override
	public List<Project> getAllProjects() {
		return projectDao.getAllProjects();
	}

	@Override
	public boolean validateInputDates(String startDate, String dueDate) {
		System.out.println("Validating format of input dates");
		SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
		df.setLenient(false);
		try {
			df.parse(startDate);
			df.parse(dueDate);
			if (dueDate.compareTo(startDate) < 0 || dueDate.compareTo(startDate) == 0)
				return false;
			return true;
		} catch (ParseException e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public void populateProjectDetails(List<Project> projectList, List<String> descriptionList, List<Task> taskList,
			List<String> projectNames) {
		System.out.println("Populate project details ");
		for (Project project : projectList) {
			Task taskObj = new Task();
			taskObj.setProjectName(project.getProjectName());
			taskObj.setDescription(project.getDescription());
			taskObj.setDueDate(project.getDueDate());
			taskObj.setStartDate(project.getStartDate());
			taskObj.setEmployeeList(getEmpByProjId(project.getId().intValue()));
			descriptionList.add(project.getDescription());
			taskList.add(taskObj);
		}

		projectNames.add("All Projects");
		for (String project : (List<String>) getAllDistinctProjects()) {
			System.out.println("Added project to list: " + project);
			projectNames.add(project);
		}

	}

	@Override
	public List<String> getEmployeesAssociatedWithProject(String projectName) {
		System.out.println("Populate employees associated with selected Project " + projectName);
		List<Project> projDetails = getProjectByName(projectName);
		List<String> projNames = new ArrayList<>();
		List<Employee2> empAssoProject = new ArrayList<>();
		List<String> empNames = new ArrayList<>();

		for (Project project : projDetails) {
			projNames.add(project.getProjectName());
			empAssoProject.addAll(getEmpByProjId(project.getId().intValue()));
		}
		for (Employee2 emp : empAssoProject) {
			empNames.add(emp.getEmployeeName());
			System.out.println("Employee added to list: " + emp.getEmployeeName());
		}

		return empNames;
	}
}
