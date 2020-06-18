package com.example.demo.controller;

import java.util.List;

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
import com.example.demo.controller.validation.ProjectValidator;
import com.example.demo.model.Project;
import com.example.demo.model.Task;
import com.example.demo.model.User;
import com.example.demo.services.ProjectService;
import com.example.demo.services.UserService;




@Controller
public class ProjectController {
	
	@Autowired
	ProjectService projectService;
	
	@Autowired
	UserService userService;
	
	@Autowired
	ProjectValidator projectValidator;
	
	
	@Autowired
	SessionData sessionData;
	
	 @RequestMapping(value = { "/projects" }, method = RequestMethod.GET) 
	    public String myOwnedProjects(Model model) {
	        User loggedUser = sessionData.getLoggedUser();
	        List<Project> projectsList = projectService.retrieveProjectsOwnedBy(loggedUser);
	        model.addAttribute("loggedUser", loggedUser);
	        model.addAttribute("projectsList", projectsList);
	        return "myOwnedProjects";
	        }
	 
	 
	 @RequestMapping(value = { "/projects/{projectId}" }, method = RequestMethod.GET) 
		public String project(Model model, 
				@PathVariable Long projectId) {
			Project project = projectService.getProject(projectId);
			User loggedUser = sessionData.getLoggedUser();
			if(project==null) 
				return "redirect:/projects";
			List<User> members = userService.getMembers(project);
			List<Task> tasks = project.getTasks();
			if(!project.getOwner().equals(loggedUser) && members.contains(loggedUser))
				return "redirect:/projects";

	        model.addAttribute("tasks", tasks);
			model.addAttribute("user",loggedUser);
			model.addAttribute("project",project);
			model.addAttribute("members",members);

			return "project";


		}
	 
	 @RequestMapping(value = { "/projects/add" }, method = RequestMethod.GET) 
	    public String createProjectForm(Model model) {
	        User loggedUser = sessionData.getLoggedUser();	   
	        model.addAttribute("loggedUser", loggedUser);
	        model.addAttribute("projectForm", new Project());
	        return "addProject";
	        }
	 
	 
	 @RequestMapping(value= {"/projects/add"}, method = RequestMethod.POST)
		public String createProject(@Valid @ModelAttribute ("projectForm") Project project, 
	             BindingResult projectBindingResult,
				Model model) {
		    User loggedUser = sessionData.getLoggedUser();
		    
		    projectValidator.validate(project, projectBindingResult);
		    if(!projectBindingResult.hasErrors()) {
				project.setOwner(loggedUser);
				this.projectService.saveProject(project);
				return"redirect:/projects/" + project.getId();
			}
		    
			model.addAttribute("loggedUser",loggedUser);
			return "addProject";
		}
	 
	 
	
	
	 @RequestMapping(value = { "/projects/{name}/delete" }, method = RequestMethod.POST)
	    public String removeProject(Model model, @PathVariable String name) {
	        this.projectService.deleteProject(name);
	        return "redirect:/projects";
	}

}
