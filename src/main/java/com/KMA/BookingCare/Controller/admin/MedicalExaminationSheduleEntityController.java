package com.KMA.BookingCare.Controller.admin;

import java.util.List;

import javax.servlet.http.HttpSession;

import com.KMA.BookingCare.Dto.WorkTimeDto;
import com.KMA.BookingCare.Service.WorkTimeService;
import com.KMA.BookingCare.common.Constant;
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
    @Autowired
    private WorkTimeService workTimeServiceImpl;

    @GetMapping(value = {"/admin/managerMedical", "/doctor/managerMedical"})
    public String medicalPage(Model model, HttpSession session) {
        MyUser user = (MyUser) session.getAttribute("userDetails");
        List<WorkTimeDto> lstWorkTime = workTimeServiceImpl.findAll();
        List<MedicalExaminationScheduleDto> lstMedical;
        if (user.getRoles().contains("ROLE_DOCTOR")) {
            lstMedical = medicalServiceImpl.findAllByDoctorIdAndStatus(user.getId(), Constant.MEDICAL_SCHEDULE_IS_WAITING);
        } else {
            lstMedical = medicalServiceImpl.findAllByStatus(Constant.MEDICAL_SCHEDULE_IS_WAITING);
        }

        model.addAttribute("lstMedical", lstMedical);
        model.addAttribute("lstWorkTime", lstWorkTime);
        return "admin/views/managerMedical";
    }

    @GetMapping(value = {"/admin/managerMedicalComplete", "/doctor/managerMedicalComplete"})
    public String medicalConpletePage(Model model, HttpSession session) {
        MyUser user = (MyUser) session.getAttribute("userDetails");

        List<MedicalExaminationScheduleDto> lstMedical;
        if (user.getRoles().contains("ROLE_DOCTOR")) {
            lstMedical = medicalServiceImpl.findAllByDoctorIdAndStatus(user.getId(), 2);
        } else {
            lstMedical = medicalServiceImpl.findAllByStatus(2);
        }

        model.addAttribute("lstMedical", lstMedical);
        return "admin/views/managerMedicalComplete";
    }

    @GetMapping(value = {"/admin/managerMedicalCancel", "/doctor/managerMedicalCancel"})
    public String medicalCancelPage(Model model, HttpSession session) {
        MyUser user = (MyUser) session.getAttribute("userDetails");
        List<MedicalExaminationScheduleDto> lstMedical;
        if (user.getRoles().contains("ROLE_DOCTOR")) {
            lstMedical = medicalServiceImpl.findAllByDoctorIdAndStatus(user.getId(), Constant.MEDICAL_SCHEDULE_IS_CANCEL);
        } else {
            lstMedical = medicalServiceImpl.findAllByStatus(Constant.MEDICAL_SCHEDULE_IS_CANCEL);
        }

        model.addAttribute("lstMedical", lstMedical);
        return "admin/views/managerMedicalCancel";
    }
}
