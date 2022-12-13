package com.shoponline.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.shoponline.helper.Message;
import com.shoponline.model.User;
import com.shoponline.service.UserService;

@RestController
public class UserController {

	@Autowired
    private UserService userService;

	/*
	 * @PostConstruct public void initRoleAndUser() { userService.initRoleAndUser();
	 * }
	 */
	 
    @GetMapping("/register")
	public ModelAndView register() {
		ModelAndView modelView = new ModelAndView();
		modelView.setViewName("register.html");
		modelView.addObject("title", "Register - Online Shopping");
		modelView.addObject("user", new User());
		return modelView;
	}

    @PostMapping({"/registerNewUser"})
    public ModelAndView registerNewUser(@ModelAttribute("user") User user, @RequestParam(value = "agreement", defaultValue = "false") boolean agreement, 
			HttpSession session) {
    	ModelAndView modelView = new ModelAndView();
		modelView.setViewName("register.html");
		try {
			if (!agreement) {
				System.out.println("You have not accepted terms and conditions.");
				throw new Exception("You have not accepted terms and conditions.");
			}
			userService.registerNewUser(user);
			modelView.addObject("user", new User());
			session.setAttribute("message", new Message("Successfully registered !!", "alert-success"));
			return modelView;
		}catch (Exception e) {
			e.printStackTrace();
			modelView.addObject("user", user);
			session.setAttribute("message", new Message("Something went wrong !! " + e.getMessage(), "alert-danger"));
			return modelView;
		}
    }

    @GetMapping({"/forAdmin"})
    @PreAuthorize("hasRole('Admin')")
    public String forAdmin(){
        return "This URL is only accessible to the admin";
    }

    @GetMapping({"/forUser"})
    @PreAuthorize("hasRole('User')")
    public String forUser(){
        return "This URL is only accessible to the user";
    }
}
