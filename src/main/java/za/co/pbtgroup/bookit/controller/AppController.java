package za.co.pbtgroup.bookit.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AppController {

	@GetMapping("/")
	public String index(ModelMap model) {
		model.addAttribute("message", "hello world from the controller");

		return "index";
	}


}
