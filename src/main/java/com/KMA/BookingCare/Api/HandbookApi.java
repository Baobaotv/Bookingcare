package com.KMA.BookingCare.Api;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.KMA.BookingCare.Api.form.HandbookForm;
import com.KMA.BookingCare.Api.form.formDelete;
import com.KMA.BookingCare.Api.form.searchHandbookForm;
import com.KMA.BookingCare.Dto.HandbookDto;
import com.KMA.BookingCare.Dto.SpecializedDto;
import com.KMA.BookingCare.Service.HandbookService;
import com.KMA.BookingCare.Service.SpecializedService;

@Controller
public class HandbookApi {
	
	@Autowired
	private HandbookService handbookServiceImpl;
	
	@Autowired
	private SpecializedService specializeServiceImpl;
	@PostMapping(value = {"/admin/managerHandbook/add","/doctor/managerHandbook/add"})
	public ResponseEntity<?> addHandbook(@ModelAttribute HandbookForm form) {
		try {
			handbookServiceImpl.saveHandbook(form);
			System.out.println("oke");
			
		} catch (Exception e ) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("looxi");
//			return ResponseEntity.status(HttpStatus.)
		}
		return ResponseEntity.ok("true");
	}
	
	@PostMapping(value = {"/admin/managerHandbok/delete","/doctor/managerHandbook/delete"})
	public ResponseEntity<?> deleteHandbook(@RequestBody formDelete form) {
		try {
			handbookServiceImpl.updateHandbookByStatus(form.getIds());
			System.out.println("oke");
			
		} catch (Exception e ) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("looxi");
//			return ResponseEntity.status(HttpStatus.)
		}
		return ResponseEntity.ok("true");
	}
	
	@PostMapping(value = {"/admin/managerHandbok/edit","/doctor/managerHandbook/edit"})
	public ResponseEntity<?> editHandbook(@ModelAttribute HandbookForm form) {
		try {
			handbookServiceImpl.saveHandbook(form);
			System.out.println("oke");
			
		} catch (Exception e ) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("looxi");
//			return ResponseEntity.status(HttpStatus.)
		}
		return ResponseEntity.ok("true");
	}
	@PostMapping(value = {"/searchHandbook"})
	public String searchHandbook(@ModelAttribute searchHandbookForm form,Model model) {
			List<HandbookDto> lstDto = handbookServiceImpl.searchHandbook(form);
			System.out.println("oke");
			model.addAttribute("lstDto", lstDto);
			List<SpecializedDto> lstChuyenKhoa = specializeServiceImpl.findAll();
			model.addAttribute("lstChuyenKhoa",lstChuyenKhoa);
		
		return "/client/views/handbook";
	}
}
