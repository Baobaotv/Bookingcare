package com.KMA.BookingCare.Api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.KMA.BookingCare.Api.form.HospitalForm;
import com.KMA.BookingCare.Api.form.SpecializedForm;
import com.KMA.BookingCare.Api.form.formDelete;
import com.KMA.BookingCare.Service.HospitalService;
import com.KMA.BookingCare.Service.SpecializedService;

@Controller
public class SpecialzedApi {
	
	@Autowired
	private SpecializedService specializedServiceImpl;;
	@PostMapping(value = "/admin/api/managerSpecialized/add")
	public ResponseEntity<?> addHospital(@ModelAttribute SpecializedForm form) {
		try {
			specializedServiceImpl.saveOrUpdateSpecialized(form);
			System.out.println("oke");
			
		} catch (Exception e ) {
			e.printStackTrace();
			System.out.println("looxi");
		}
		return ResponseEntity.ok("true");
	}
	
	@PostMapping(value = "/admin/api/managerSpecialized/delete")
	public ResponseEntity<?> deleteHospital(@RequestBody formDelete form) {
		try {
//			HospitalServiceImpl.updateHospitalByStatus(form.getIds());
			System.out.println("oke");
			
		} catch (Exception e ) {
			e.printStackTrace();
			System.out.println("ERROR: không thể xoá chuyên khoa");
		}
		return ResponseEntity.ok("true");
	}
	
	@PostMapping(value = "/admin/api/managerSpecialized/edit")
	public ResponseEntity<?> editHospital(@ModelAttribute SpecializedForm form) {
		try {
			specializedServiceImpl.saveOrUpdateSpecialized(form);
			System.out.println("oke");
			
		} catch (Exception e ) {
			e.printStackTrace();
			System.out.println("ERROR: Không thể cập nhập chuyên ngành");
		}
		return ResponseEntity.ok("true");
	}
}
