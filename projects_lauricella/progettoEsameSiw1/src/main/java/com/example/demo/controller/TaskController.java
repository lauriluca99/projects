package com.example.demo.controller;


import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.example.demo.controller.session.SessionData;
import com.example.demo.controller.validation.TaskValidator;
import com.example.demo.model.Project;
import com.example.demo.model.Task;
import com.example.demo.model.User;
import com.example.demo.services.ProjectService;
import com.example.demo.services.TaskService;



@Controller
public class TaskController {

	@Autowired
	TaskService taskService;

	@Autowired
	ProjectService projectService;

	@Autowired
	TaskValidator taskValidator;


	@Autowired
	SessionData sessionData;

	@RequestMapping(value = { "/projects/{projectId}/addTask" }, method = RequestMethod.GET) 
	public String createTaskForm(Model model, @PathVariable Long projectId ) {
		User loggedUser = sessionData.getLoggedUser();
		Project project = (this.projectService.getProject(projectId));
		
		model.addAttribute("project",project);
		model.addAttribute("loggedUser",loggedUser);
		model.addAttribute("taskForm", new Task());


		return "createTask";

	}
	@RequestMapping(value= {"/projects/{projectId}/addTask"}, method = RequestMethod.POST)
	public String createTask(@Valid @ModelAttribute ("taskForm") Task task, 
			BindingResult taskBindingResult, @PathVariable Long projectId, 
			Model model) {
		User loggedUser = sessionData.getLoggedUser();
		Project project = this.projectService.getProject(projectId);
		this.taskValidator.validate(task, taskBindingResult);
		if(project != null) {
			if(!taskBindingResult.hasErrors()) {
				taskService.saveTask(task);
				project.addTask(task);
				projectService.saveProject(project);
		 	return"redirect:/projects/" + projectId.toString() ;
			}

			model.addAttribute("loggedUser",loggedUser);
			return "createTask";

	}
		return "redirect:/projects/" + projectId.toString();
}
}