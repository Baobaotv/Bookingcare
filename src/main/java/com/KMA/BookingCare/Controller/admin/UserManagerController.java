package com.KMA.BookingCare.Controller.admin;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;

import com.KMA.BookingCare.Api.form.UserForm;
import com.KMA.BookingCare.Api.form.searchDoctorForm;
import com.KMA.BookingCare.Dto.HospitalDto;
import com.KMA.BookingCare.Dto.Role;
import com.KMA.BookingCare.Dto.SpecializedDto;
import com.KMA.BookingCare.Dto.User;
import com.KMA.BookingCare.Dto.WorkTimeDto;
import com.KMA.BookingCare.Service.HospitalService;
import com.KMA.BookingCare.Service.RoleService;
import com.KMA.BookingCare.Service.SpecializedService;
import com.KMA.BookingCare.Service.UserService;
import com.KMA.BookingCare.Service.WorkTimeService;

import io.netty.handler.codec.http.HttpResponse;

@Controller
public class UserManagerController {
	
	@Autowired
	private UserService userServiceImpl;
	@Autowired
	private HospitalService hospitalServiceImpl;
	@Autowired
	private SpecializedService specializedServiceImpl;
	@Autowired
	private WorkTimeService workTimeServiceImpl;
	@Autowired
	private RoleService roleSeviceImpl;
	
	@GetMapping(value = "/admin/managerUser")
	public String managerUerPage( Model model,@RequestParam(required = false,name = "page",defaultValue = "1") Integer page,
			@ModelAttribute searchDoctorForm form) {
		Pageable pageable = PageRequest.of(page-1, 3);
		List<User> lstUser = userServiceImpl.searchDoctorAndPageable(form,pageable);
		List<HospitalDto> lstHospital= hospitalServiceImpl.findAll();
		List<SpecializedDto> lstSpecialized= specializedServiceImpl.findAll();
		List<WorkTimeDto> lstWorkTime = workTimeServiceImpl.findAll();
		List<Role> lstRole= roleSeviceImpl.findAll();
//		List<User> lstUser= userServiceImpl.findAllByStatus(1,pageable);
		model.addAttribute("lstRole", lstRole);
		model.addAttribute("lstWorkTime", lstWorkTime);
		model.addAttribute("lstHospital", lstHospital);
		model.addAttribute("lstSpecialized", lstSpecialized);
		model.addAttribute("lstUser", lstUser);
//		Integer curentPage=page;
//		model.addAttribute("curentPage", curentPage);
		model.addAttribute("formSearch",form);
		Integer curentPage=page;
		model.addAttribute("curentPage", curentPage);
		return "admin/views/managerUser";
	}
	@PostMapping(value = {"/admin/editProfile","/doctor/editProfile"})
	public ResponseEntity<?> editProfilePage( Model model, @ModelAttribute UserForm form) {
		System.out.println("test");
		try {
			userServiceImpl.saveDoctor(form);
			
		} catch (IllegalStateException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ResponseEntity.ok("true");
		
	}
	

	

}
