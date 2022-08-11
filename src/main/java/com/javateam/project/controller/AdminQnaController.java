package com.javateam.project.controller;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.javateam.project.domain.MemberVO;
import com.javateam.project.domain.PageVO;
import com.javateam.project.domain.QnaVO;
import com.javateam.project.service.QnaService;

import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping("admin")
@Slf4j
public class AdminQnaController {

	@Inject
	QnaService qnaService;
	
	@RequestMapping("qna/qna_list")
	public String qnaList(@RequestParam(value = "page", defaultValue = "1") int page,
						  @RequestParam(value = "limit", defaultValue = "5") int limit,
						  Model model) {
		
		log.info("admin.qnaList");
		
		List<QnaVO> qnaList = qnaService.listQnaByPaging(page, limit);
		
		int totalQnasCount = qnaService.getTotalQnasCount();
		
		int startPage = 1;
		int maxPage = (int)((double)totalQnasCount/limit + 0.95); 
		
		PageVO pageVO = new PageVO();
		pageVO.setCurrPage(page);
		pageVO.setStartPage(startPage);		
		pageVO.setEndPage(maxPage);
		pageVO.setLimit(limit);
		pageVO.setMaxPage(maxPage);
		
		model.addAttribute("qnaList", qnaList);
		model.addAttribute("pageVO", pageVO);
		
		return "/admin/qna/qna_list";
	}
	
	@RequestMapping("qna/qna_detail")
	public String qnaDetialForm(@RequestParam("qseq") int qseq, Model model) {
		
		log.info("qnaDetialForm");
		
		model.addAttribute("qnaVO", qnaService.getQna(qseq));
		
		return "/admin/qna/qna_detail";
	}
	
	@RequestMapping("qna/qna_repsave")
	public String qnaRepsave(@RequestParam("qseq") int qseq,
							 @RequestParam("reply") String reply,
							 Model model) {
		
		log.info("qnaRepsave");
		
		qnaService.updateQna(qseq, reply);
		
		String msg = "";
		String movePath = "";
		
		if(qnaService.updateQna(qseq, reply) == true) {
			
			msg = "답글 저장 성공";
			movePath = "/admin/qna/qna_list";
			
		} else {
			
			msg = "답글 저장  실패";
			movePath = "/admin/qna/qna_write_form";
		}
 		
		model.addAttribute("err_msg", msg);
		model.addAttribute("move_path", movePath);
		
		return "/error/error";
	}
	
}
