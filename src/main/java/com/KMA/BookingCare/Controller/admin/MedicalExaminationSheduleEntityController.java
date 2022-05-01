package com.KMA.BookingCare.Controller.admin;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.KMA.BookingCare.Dto.MedicalExaminationScheduleDto;
import com.KMA.BookingCare.Dto.MyUser;
import com.KMA.BookingCare.Service.MedicalExaminationScheduleService;

@Controller
public class MedicalExaminationSheduleEntityController {
	
	@Autowired
	private MedicalExaminationScheduleService medicalServiceImpl;
	
	@GetMapping(value = {"/admin/managerMedical","/doctor/managerMedical"})
	public String medicalPage(Model model,HttpSession session) {
		MyUser user = (MyUser) session.getAttribute("userDetails");
//		List<Medical> lstHandbook= new ArrayList<HandbookDto>();
		List<MedicalExaminationScheduleDto> lstMedical= new ArrayList<MedicalExaminationScheduleDto>();
		if(user.getRoles().contains("ROLE_DOCTOR")) {
//			 lstHandbook= handbookSeviceImpl.findAllByStatusAndUserId(1, user.getId());
			lstMedical= medicalServiceImpl.findAllByDoctorIdAndStatus(user.getId(), 1);
		}else {
			lstMedical= medicalServiceImpl.findAllByStatus(1);
		}

		model.addAttribute("lstMedical", lstMedical);
		return "admin/views/managerMedical";
	}
	
	@GetMapping(value = {"/admin/managerMedicalComplete","/doctor/managerMedicalComplete"})
	public String medicalConpletePage(Model model,HttpSession session) {
		MyUser user = (MyUser) session.getAttribute("userDetails");

		List<MedicalExaminationScheduleDto> lstMedical= new ArrayList<MedicalExaminationScheduleDto>();
		if(user.getRoles().contains("ROLE_DOCTOR")) {
//			 lstHandbook= handbookSeviceImpl.findAllByStatusAndUserId(1, user.getId());
			lstMedical= medicalServiceImpl.findAllByDoctorIdAndStatus(user.getId(), 2);
		}else {
			lstMedical= medicalServiceImpl.findAllByStatus(2);
		}

		model.addAttribute("lstMedical", lstMedical);
		return "admin/views/managerMedicalComplete";
	}
}
