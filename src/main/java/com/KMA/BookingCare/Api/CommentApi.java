package com.KMA.BookingCare.Api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.KMA.BookingCare.Api.form.formDelete;
import com.KMA.BookingCare.Service.CommentService;

@Controller
public class CommentApi {
	
	@Autowired
	private CommentService commentService;

	
	@PostMapping(value = {"/comment/delete"})
	public ResponseEntity<?> deleteHandbook(@RequestBody Long id) {
		try {
			commentService.delete(id);
			System.out.println("đã xoá cmt");
			
		} catch (Exception e ) {
			e.printStackTrace();
			System.out.println("không thể xoá cmt");
		}
		return ResponseEntity.ok("true");
	}
}
