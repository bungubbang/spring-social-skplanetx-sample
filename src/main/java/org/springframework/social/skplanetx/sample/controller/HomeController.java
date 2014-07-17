package org.springframework.social.skplanetx.sample.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.skplanetx.api.Skplanetx;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/")
public class HomeController {

    @Autowired
    private Skplanetx skplanetx;

	@RequestMapping(method = RequestMethod.GET)
	public String home(ModelMap model) {
		model.addAttribute("profile", skplanetx.getUser().getProfile());
		return "home";
	}
}