package com.KMA.BookingCare.Api;

import com.KMA.BookingCare.Dto.InteractiveDto;
import com.KMA.BookingCare.Dto.MyUser;
import com.KMA.BookingCare.Service.InteractiveService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.List;

@RestController()
@RequestMapping(value = "/api")
public class InteractiveApi {

    private final Logger logger = LoggerFactory.getLogger(InteractiveApi.class);

    @Autowired
    private InteractiveService interactiveServiceImpl;

    @GetMapping(value = "/interactive/get-by-current-login")
    public List<InteractiveDto> getByCurrentLogin(HttpSession httpSession){
        logger.info("Request to get all by current login {}");
        MyUser userDetails= (MyUser) httpSession.getAttribute("userDetails");
        List<InteractiveDto> lstInteractive= interactiveServiceImpl.findAll();
        return lstInteractive;
    }

    @GetMapping(value = "/interactive")
    public List<InteractiveDto> getAll(){
        logger.info("Request to get All");
        List<InteractiveDto> interactiveDtoList = interactiveServiceImpl.findAll();
        return interactiveDtoList;
    }

}
