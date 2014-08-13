package com.twistlet.scrapecimbauction.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.twistlet.scrapecimb.model.service.PhraseService;

@Controller
public class PhraseController {

	private final PhraseService phraseService;

	@Autowired
	public PhraseController(final PhraseService phraseService) {
		this.phraseService = phraseService;
	}

	@ResponseBody
	@RequestMapping("/phrase-execute")
	public String execute() {
		phraseService.execute();
		return "ok";
	}
}
