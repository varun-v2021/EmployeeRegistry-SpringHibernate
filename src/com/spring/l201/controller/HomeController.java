package com.spring.l201.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.spring.l201.model.Employee2;
import com.spring.l201.model.Project;
import com.spring.l201.model.Task;
import com.spring.l201.service.ProjectService;

@Controller
public class HomeController {

	@Autowired
	private ProjectService projectService;

	@RequestMapping(method = RequestMethod.GET)
	public String initPage(ModelMap model) {
		projectService.init();
		return "HomePage";
	}

	// Handler for displaying task assignment information
	@RequestMapping(value = "/viewSelectedTasks", method = RequestMethod.GET)
	public String viewTasks(@RequestParam("project") String projectName, ModelMap model) {
		ModelAndView mav = new ModelAndView("HomePage");
		Task task = new Task();
		model.put("userForm", task);
		List<Project> projectList = projectService.getProjectByName(projectName);

		if (projectName.equalsIgnoreCase("All Projects"))
			projectList = projectService.getAllProjects();

		List<String> descriptionList = new ArrayList<>();
		List<Task> taskList = new ArrayList<>();
		List<String> projectNames = new ArrayList<>();

		// populate project details
		projectService.populateProjectDetails(projectList, descriptionList, taskList, projectNames);

		// populate web elements based on project details
		model.addAttribute("projectNames", projectNames);
		model.addAttribute("descriptionList", descriptionList);
		model.addAttribute("taskList", taskList);
		model.addAttribute("message", "");
		return "ViewTasks";

	}

	// Handler for home page/index page
	@RequestMapping(value = "/Home", method = RequestMethod.GET)
	public ModelAndView cancelPage(ModelMap model) {
		ModelAndView mav = new ModelAndView("HomePage");
		model.addAttribute("message", "");
		return mav;
	}

	// handler for assignment of tasks to employees
	@RequestMapping(value = "/assign", method = RequestMethod.GET)
	public String assignEmployeeToTask(ModelMap model) {
		Task task = new Task();
		model.put("userForm", task);

		// obtain all the unique projects from DB
		List<String> projectList = (List<String>) projectService.getAllDistinctProjects();
		List<String> projectNames = new ArrayList<>();
		List<String> employeeNames = new ArrayList<>();

		// populate project details
		for (String projectName : projectList) {
			projectNames.add(projectName);
		}
		task.setProject(projectNames);
		model.addAttribute("projectList", projectNames);

		// populate employee details from each project
		Collections.sort(projectNames);
		// Default employee population in drop down
		try {
			employeeNames = projectService.getEmployeesAssociatedWithProject(projectNames.get(projectNames.size() - 1));
		} catch (Exception ex) {
			ex.printStackTrace();
			return "HomePage";
		}

		// set the web element based on project and employee information
		task.setEmployee(employeeNames);
		model.addAttribute("employeeList", employeeNames);
		model.addAttribute("message", "");
		return "AssignTasks";
	}

	// handler for displaying employee-project mapping information
	@RequestMapping(value = "/view", method = RequestMethod.GET)
	public String viewAssignedEmployeeTasks(ModelMap model) {
		Task task = new Task();
		model.put("userForm", task);
		List<String> projectNames = (List<String>) projectService.getAllDistinctProjects();
		List<Project> projectList = new ArrayList<>();
		List<String> descriptionList = new ArrayList<>();
		List<Task> taskList = new ArrayList<>();

		// obtain all project details from DB
		projectList = projectService.getAllProjects();

		for (Project project : projectList) {
			// create a new task object and populate project, employee info
			Task taskObj = new Task();
			taskObj.setProjectName(project.getProjectName());
			taskObj.setDescription(project.getDescription());
			taskObj.setDueDate(project.getDueDate());
			taskObj.setStartDate(project.getStartDate());
			taskObj.setEmployeeList(projectService.getEmpByProjId(project.getId().intValue()));
			descriptionList.add(project.getDescription());
			taskList.add(taskObj);
		}

		// populate view elements
		model.addAttribute("projectNames", projectNames);
		model.addAttribute("descriptionList", descriptionList);
		model.addAttribute("taskList", taskList);
		model.addAttribute("message", "");
		return "ViewTasks";
	}

	// handler for adding an employee a new task for a specific project
	@RequestMapping(value = "/addEmployee", method = RequestMethod.POST)
	public ModelAndView processForm(@RequestParam("button") String buttonValue, @RequestParam("project") String pName,
			@ModelAttribute("userForm") @Validated Task task, BindingResult result) {
		ModelAndView mav = new ModelAndView("HomePage");

		if (buttonValue.equalsIgnoreCase("Add a task")) {
			// validate whether all details are available
			if (result.hasErrors()) {
				return handleInvalidInput(task, "", pName);
			} else {
				// check whether the selected project is a valid one
				if (task.getProject().get(0).equalsIgnoreCase("select")) {
					return handleInvalidInput(task, "", pName);
				}

				// check dates for their format and validity of the same
				if (!projectService.validateInputDates(task.getStartDate(), task.getDueDate())) {
					return handleInvalidInput(task, "Enter valid date(s)", pName);
				}

				List<Employee2> allEmpPresent = projectService.getAllEmployees();

				List<Employee2> saveEmpList = new ArrayList<>();

				Project projObj = new Project();
				// populate project object from the user input
				projObj.setProjectName(task.getProject().get(0));
				projObj.setDescription(task.getDescription());
				projObj.setStartDate(task.getStartDate());
				projObj.setDueDate(task.getDueDate());

				// create a employee object for each of the employee user
				// selects
				for (String employeeNameToAdd : task.getEmployee()) {
					Employee2 empObj = new Employee2();
					for (Employee2 presentEmployee : allEmpPresent) {
						if (presentEmployee.getEmployeeName().compareTo(employeeNameToAdd) == 0) {
							// remove the stale information of employee
							Employee2 emp = projectService.getEmpById(presentEmployee.getId());
							System.out.println("deleting emp with MID: " + emp.getMid());
							projectService.deleteEmp(emp);
							// persist new employee information
							saveEmpList.add(new Employee2(presentEmployee.getMid(), employeeNameToAdd));
						}
					}
				}

				// persist new employee(s) information into DB
				projObj.setEmployees(saveEmpList);
				projectService.persist(projObj);

				mav.addObject("message", "Successfully added employee");
			}
		}
		return mav;
	}

	// handler for redirection on invalid inputs from user
	public ModelAndView handleInvalidInput(Task task, String message, String userSelectProject) {
		ModelAndView mav_err = new ModelAndView("AssignTasks");

		Task task_err = new Task();
		mav_err.addObject("userForm", task);

		// reset the project list by obtaining distinct projects
		List<String> projectList = (List<String>) projectService.getAllDistinctProjects();
		List<String> projectNames = new ArrayList<>();
		List<String> employeeNames = new ArrayList<>();

		for (String projectName : projectList) {
			projectNames.add(projectName);
		}
		task_err.setProject(projectNames);
		mav_err.addObject("projectList", projectNames);

		// populate all employees associated with single project irrespective of
		// tasks
		employeeNames = projectService.getEmployeesAssociatedWithProject(userSelectProject);
		task_err.setEmployee(employeeNames);

		// populate view elements
		mav_err.addObject("employeeList", employeeNames);
		mav_err.addObject("message", message);
		return mav_err;
	}

	// handle change in project selection from view
	@RequestMapping(value = "/populateEmployees", method = RequestMethod.POST)
	public @ResponseBody List<String> ajaxHandler(@RequestParam("projectName") String projectName,
			@ModelAttribute("userForm") @Validated Task task, BindingResult result) {
		ModelAndView mav = new ModelAndView("AssignTasks");
		// populate all employees associated with single project irrespective of
		// tasks
		List<String> empNames = projectService.getEmployeesAssociatedWithProject(projectName);
		// populate view elements
		task.setEmployee(empNames);
		return empNames;
	}
}
