package br.com.fiap.EpicTask.controller;

import java.util.List;
import java.util.Locale;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceResolvable;
import org.springframework.context.NoSuchMessageException;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.fiap.EpicTask.model.Task;
import br.com.fiap.EpicTask.repository.TaskRepository;

@Controller
@RequestMapping("/task")
public class TaskController {
	
	@Autowired
	private TaskRepository repository;

	@GetMapping()  
	public ModelAndView tasks() {
		List<Task> tasks = repository.findAll();
		ModelAndView modelAndView = new ModelAndView("tasks");
		modelAndView.addObject("tasks", tasks);
		return modelAndView;
	}
	
	@PostMapping()  
	public String save(@Valid Task task, BindingResult result, RedirectAttributes attributes) {
		if (result.hasErrors()) return "task_new";
		repository.save(task);
		attributes.addFlashAttribute("message", "Task cadastrada com sucesso");
		return "redirect:task";
	}
	
	@RequestMapping("new")  
	public String formTask(Task task) {
		return "task_new";
	}
	
	@GetMapping("/delete/{id}")
	public String deleteTask(@PathVariable Long id, RedirectAttributes redirect) {
		repository.deleteById(id);
		redirect.addFlashAttribute("message", "Task apagada com sucesso");
		return "redirect:/task";
	}
	
	@GetMapping("/{id}")
	public ModelAndView editTaskForm(@PathVariable Long id) {
		Optional<Task> task = repository.findById(id);
		ModelAndView modelAndView = new ModelAndView("task_edit");
		modelAndView.addObject("task", task);
		return modelAndView;		
	}
	
	@PostMapping("/update")
	public String updateTask(Task task) {
		repository.save(task);
		return "redirect:/task"; 
	}
	
	private MessageSource messageSource = new MessageSource() {
		
		@Override
		public String getMessage(String code, Object[] args, String defaultMessage, Locale locale) {
			String message = messageSource.getMessage("task.add.success" , null, LocaleContextHolder.getLocale());
			return message;
		}
		
		@Override
		public String getMessage(String code, Object[] args, Locale locale) throws NoSuchMessageException {
			// TODO Auto-generated method stub
			return null;
		}
		
		@Override
		public String getMessage(MessageSourceResolvable resolvable, Locale locale) throws NoSuchMessageException {
			// TODO Auto-generated method stub
			return null;
		}
	};
	
	private String gitHubUser;
	
	public String getAvatar() {
		return "https://avatars.githubbusercontent.com/" + gitHubUser;
	}
	
}
