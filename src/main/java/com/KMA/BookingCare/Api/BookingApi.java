package com.KMA.BookingCare.Api;

import java.util.List;

import javax.servlet.http.HttpSession;

import com.KMA.BookingCare.Mapper.UserMapper;
import com.KMA.BookingCare.ServiceImpl.UserDetailsImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.KMA.BookingCare.Api.form.BookingForm;
import com.KMA.BookingCare.Api.form.ChangeDateForm;
import com.KMA.BookingCare.Api.form.HandbookForm;
import com.KMA.BookingCare.Dto.MyUser;
import com.KMA.BookingCare.Dto.User;
import com.KMA.BookingCare.Dto.WorkTimeDto;
import com.KMA.BookingCare.Entity.UserEntity;
import com.KMA.BookingCare.Service.MedicalExaminationScheduleService;
import com.KMA.BookingCare.Service.WorkTimeService;

//@Controller
@RestController()
@RequestMapping("/api")
public class BookingApi {

	private final Logger log = LoggerFactory.getLogger(BookingApi.class);

	@Autowired
	private MedicalExaminationScheduleService medicalServiceImpl;
	
	@Autowired
	private WorkTimeService workTimeserviceImpl;
	
	@PostMapping(value = "/booking")
	public ResponseEntity<?>  booking(@ModelAttribute BookingForm form,HttpSession session) {
		try {
			if(session.getAttribute("userDetails")!=null) {
				MyUser user =(MyUser) session.getAttribute("userDetails");
				form.setUserId(user.getId());
			}
		}catch (Exception e){
			log.error("login by app");
			UserDetailsImpl user = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication()
					.getPrincipal();
			MyUser userDetails = UserMapper.convertToMyUser(user);
			form.setUserId(userDetails.getId());
		}

		medicalServiceImpl.save(form);
		return ResponseEntity.ok("true");
	}
	
	@PostMapping(value = "/changedate")
	public ResponseEntity<?> changeDate(@RequestBody ChangeDateForm form) {
	List<WorkTimeDto> lstDtos = workTimeserviceImpl.findByDateAndDoctorId(form.getDate(), form.getIdDoctor());
	 return new ResponseEntity<Object>(lstDtos, HttpStatus.OK);
	}

}
