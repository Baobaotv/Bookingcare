package com.KMA.BookingCare.Api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.KMA.BookingCare.Api.form.HospitalForm;
import com.KMA.BookingCare.Api.form.formDelete;
import com.KMA.BookingCare.Service.HospitalService;

@Controller
public class HospitalApi {
	
	@Autowired
	private HospitalService HospitalServiceImpl;
	@PostMapping(value = "/admin/api/managerHospital/add")
	public ResponseEntity<?> addHospital(@ModelAttribute HospitalForm form) {
		try {
			HospitalServiceImpl.saveHospital(form);
			System.out.println("oke");
			
		} catch (Exception e ) {
			e.printStackTrace();
			System.out.println("looxi");
		}
		return ResponseEntity.ok("true");
	}
	
	@PostMapping(value = "/admin/api/managerHospital/delete")
	public ResponseEntity<?> deleteHospital(@RequestBody formDelete form) {
		try {
//			HospitalServiceImpl.updateHospitalByStatus(form.getIds());
			System.out.println("oke");
			
		} catch (Exception e ) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("looxi");
//			return ResponseEntity.status(HttpStatus.)
		}
		return ResponseEntity.ok("true");
	}
	
	@PostMapping(value = "/admin/api/managerHospital/edit")
	public ResponseEntity<?> editHospital(@ModelAttribute HospitalForm form) {
		try {
			HospitalServiceImpl.saveHospital(form);
			System.out.println("oke");
			
		} catch (Exception e ) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("looxi");
//			return ResponseEntity.status(HttpStatus.)
		}
		return ResponseEntity.ok("true");
	}
}
