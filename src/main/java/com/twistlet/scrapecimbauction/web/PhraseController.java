package com.twistlet.scrapecimbauction.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class PhraseController {

	@ResponseBody
	public String execute() {
		return "ok";
	}
}
