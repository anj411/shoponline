package com.shoponline.controller;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.shoponline.model.JwtRequest;
import com.shoponline.model.JwtResponse;
import com.shoponline.service.JwtServiceImpl;

@RestController
@CrossOrigin
public class JwtController {
	
	private Logger log = LoggerFactory.getLogger(JwtController.class.getName());
	
	@Autowired
	private JwtServiceImpl jwtService;
	
	/**
	 * This api when called returns login Page.
	 * @return
	 */
	@RequestMapping("/signin")
	public ModelAndView login()
	{
		log.info("login: api Started");
		ModelAndView modelView = new ModelAndView();
		modelView.setViewName("login.html");
		modelView.addObject("title", "Login - Online Shopping");
		modelView.addObject("jwtRequest", new JwtRequest());
		log.info("login: api Stopped");
		return modelView;
	}
	
	/**
	 * This api when called returns JwtResponse token.
	 * @param jwtRequest
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/authenticate", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public JwtResponse createJwtToken(@RequestBody JwtRequest jwtRequest) throws Exception {

		log.info("createJwtToken: api Started");
		return jwtService.createJwtToken(jwtRequest);
	}

}
