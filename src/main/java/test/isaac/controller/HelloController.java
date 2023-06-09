//Isaac 測試mvc helloworld

package test.isaac.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class HelloController {
	String message = "Welcome to Spring MVC!";
	
	@RequestMapping("/hello")
	public ModelAndView showMessage(
			@RequestParam(value="name", required=false, defaultValue="World") String name) {
			System.out.println("in HelloController");
			
			//導到helloworld.jsp中
			ModelAndView mv = new ModelAndView("helloworld");
			mv.addObject("message", message);
			mv.addObject("name", name);
			return mv;
	}
}
