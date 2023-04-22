package com.KMA.BookingCare.Controller.admin;

import java.util.List;

import javax.servlet.http.HttpSession;

import com.KMA.BookingCare.Mapper.UserMapper;
import com.KMA.BookingCare.ServiceImpl.UserDetailsImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.KMA.BookingCare.Dto.HospitalDto;
import com.KMA.BookingCare.Dto.MyUser;
import com.KMA.BookingCare.Dto.Role;
import com.KMA.BookingCare.Dto.SpecializedDto;
import com.KMA.BookingCare.Dto.User;
import com.KMA.BookingCare.Dto.WorkTimeDto;
import com.KMA.BookingCare.Service.HospitalService;
import com.KMA.BookingCare.Service.SpecializedService;
import com.KMA.BookingCare.Service.UserService;
import com.KMA.BookingCare.Service.WorkTimeService;

@Controller
public class HomeController {

    Logger logger = LoggerFactory.getLogger(HomeController.class);

    @Autowired
    private HospitalService hospitalServiceImpl;
    @Autowired
    private UserService userServiceImpl;

    @Autowired
    private SpecializedService specializedServiceImpl;
    @Autowired
    private WorkTimeService workTimeServiceImpl;

    @GetMapping(value = {"/admin/home", "/doctor/home"})
    public String homePage(HttpSession session) {
        UserDetailsImpl user = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
        MyUser userDetails = UserMapper.convertToMyUser(user);
        session.setAttribute("userDetails", userDetails);
        return "admin/views/home";
    }

    @GetMapping(value = "/admin/managerHospital")
    public String hospitalPage(Model model, @RequestParam(required = false, name = "page", defaultValue = "1") Integer page) {
        Pageable pageable = PageRequest.of(page - 1, 3);
        List<HospitalDto> lstHospital = hospitalServiceImpl.findAllByStatus(1, pageable);
        model.addAttribute("lstHospital", lstHospital);
        Integer curentPage = page;
        model.addAttribute("curentPage", curentPage);
        return "admin/views/managerHospital";
    }

    @GetMapping(value = "/admin/managerSpecialized")
    public String specializedPage(Model model, @RequestParam(required = false, name = "page", defaultValue = "1") Integer page) {
        Pageable pageable = PageRequest.of(page - 1, 3);
        List<SpecializedDto> lstSpecialized = specializedServiceImpl.findAllByStatus(1, pageable);
        model.addAttribute("lstSpecialized", lstSpecialized);
        Integer curentPage = page;
        model.addAttribute("curentPage", curentPage);
        return "admin/views/managerSpecialized";
    }


    @GetMapping(value = {"/admin/editProfile", "/doctor/editProfile"})
    public String profilePage(Model model, HttpSession session) {
        MyUser userDetails = (MyUser) session.getAttribute("userDetails");
        User userDto = userServiceImpl.findOneById(userDetails.getId());
        model.addAttribute("userDto", userDto);
        List<HospitalDto> lstHospital = hospitalServiceImpl.findAll();
        List<SpecializedDto> lstSpecialized = specializedServiceImpl.findAll();
        List<WorkTimeDto> lstWorkTime = workTimeServiceImpl.findAll();
        model.addAttribute("lstWorkTime", lstWorkTime);
        model.addAttribute("lstHospital", lstHospital);
        model.addAttribute("lstSpecialized", lstSpecialized);
        return "admin/views/editProfile";
    }
}
