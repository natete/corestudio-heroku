package com.OneWingSoft.corestudio.rest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Controller to forward Angular requests to index so routes are managed by Angular ui.routes 
 * @author Ignacio González Bullón <natete981@gmail.com>
 * @since 18 oct. 2015
 */
@Controller
public class CorestudioRestRoutes {

	@RequestMapping({ "/home", "/login", "/professor" })
	public String index() {
		return "forward:/index.html";

	}
}
