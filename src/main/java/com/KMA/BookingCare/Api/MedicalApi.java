package com.KMA.BookingCare.Api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.KMA.BookingCare.Api.form.formDelete;
import com.KMA.BookingCare.Service.MedicalExaminationScheduleService;

@Controller
public class MedicalApi {
	@Autowired
	private MedicalExaminationScheduleService medicalServiceImpl;
	
	@PostMapping(value = {"/admin/managerMedical/delete","/doctor/managerMedical/delete"})
	public ResponseEntity<?> deleteMedical(@RequestBody formDelete form) {
		try {
//			handbookServiceImpl.updateHandbookByStatus(form.getIds());
			medicalServiceImpl.updateMedicalByStatus(0, form.getIds());
			System.out.println("oke");
			
		} catch (Exception e ) {
			e.printStackTrace();
			System.out.println("looxi");
		}
		return ResponseEntity.ok("true");
	}
	@PostMapping(value = {"/admin/managerMedical/complete","/doctor/managerMedical/complete"})
	public ResponseEntity<?> completeMedical(@RequestBody formDelete form) {
		try {
			medicalServiceImpl.updateMedicalByStatus(2, form.getIds());
			System.out.println("oke");
			
		} catch (Exception e ) {
			e.printStackTrace();
			System.out.println("looxi");
		}
		return ResponseEntity.ok("true");
	}

}
