package br.com.fiap.EpicTask.controller;

import java.util.List;
import java.util.Locale;

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

import br.com.fiap.EpicTask.model.User;
import br.com.fiap.EpicTask.repository.UserRepository;

@Controller
@RequestMapping("/user")
public class UserController {
	
	@Autowired
	private UserRepository repository;

	@GetMapping()  
	public ModelAndView users() {
		List<User> users = repository.findAll();
		ModelAndView modelAndView = new ModelAndView("users");
		modelAndView.addObject("users", users);
		return modelAndView;
	}
	
	@PostMapping()  
	public String save(@Valid User user, BindingResult result, RedirectAttributes attributes) {
		if (result.hasErrors()) return "user_new";
		repository.save(user);
		attributes.addFlashAttribute("message", "Usuário cadastrado com sucesso");
		return "redirect:user";
	}
	
	@RequestMapping("new")  
	public String formUser(User user) {
		return "user_new";
	}
	
	@GetMapping("delete/{id}")
	public String deleteUser(@PathVariable("id") Long id) {
		repository.deleteById(id);
		return "redirect:/user";
	}
	
	private MessageSource messageSource = new MessageSource() {
		
		@Override
		public String getMessage(String code, Object[] args, String defaultMessage, Locale locale) {
			String message = messageSource.getMessage("user.add.success" , null, LocaleContextHolder.getLocale());
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
