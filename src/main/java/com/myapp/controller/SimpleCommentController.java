package com.myapp.controller;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.myapp.jpa.SimpleComment;
import com.myapp.jpa.SimpleCommentRepository;

@Controller
public class SimpleCommentController
{
	@Autowired
	private SimpleCommentRepository simpleCommentRepository;

	@RequestMapping("/SimpleComment/")
	public String view(Model model)
	{
		List<SimpleComment> commentList = simpleCommentRepository.findAll();
		model.addAttribute("CommentList", commentList);
		
		return "SimpleComment/simple-comment";
	}

	@RequestMapping("/SimpleComment/Insert")
	public String insert(Model model, HttpServletRequest request, @RequestParam("text") String text){
		
		SimpleComment simpleComment = new SimpleComment();
		Date datetime = new Date();
		
		simpleComment.setText(text);
		simpleComment.setDatetime(datetime);
		simpleCommentRepository.save(simpleComment);

		return "redirect:/SimpleComment/";
	}
}