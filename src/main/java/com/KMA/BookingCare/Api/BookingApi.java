package com.KMA.BookingCare.Api;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.KMA.BookingCare.Api.form.BookingForm;
import com.KMA.BookingCare.Api.form.ChangeDateForm;
import com.KMA.BookingCare.Api.form.HandbookForm;
import com.KMA.BookingCare.Dto.MyUser;
import com.KMA.BookingCare.Dto.User;
import com.KMA.BookingCare.Dto.WorkTimeDto;
import com.KMA.BookingCare.Entity.UserEntity;
import com.KMA.BookingCare.Service.MedicalExaminationScheduleService;
import com.KMA.BookingCare.Service.WorkTimeService;

@Controller
public class BookingApi {
	@Autowired
	private MedicalExaminationScheduleService medicalServiceImpl;
	
	@Autowired
	private WorkTimeService workTimeserviceImpl;
	
	@PostMapping(value = "/api/booking")
	public ResponseEntity<?>  booking(@ModelAttribute BookingForm form,HttpSession session) {
		if(session.getAttribute("userDetails")!=null) {
			MyUser user =(MyUser) session.getAttribute("userDetails");
			form.setUserId(user.getId());
		}
		System.out.println("test");
		medicalServiceImpl.save(form);
		return ResponseEntity.ok("true");
	}
	
	@PostMapping(value = "/api/changedate")
	public ResponseEntity<?> changeDate(@RequestBody ChangeDateForm form) {
	List<WorkTimeDto> lstDtos = workTimeserviceImpl.findByDateAndDoctorId(form.getDate(), form.getIdDoctor());
	 return new ResponseEntity<Object>(lstDtos, HttpStatus.OK);
	}
}
