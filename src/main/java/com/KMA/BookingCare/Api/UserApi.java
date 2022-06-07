package com.KMA.BookingCare.Api;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.KMA.BookingCare.Dto.*;
import com.KMA.BookingCare.Entity.UserEntity;
import io.swagger.v3.oas.annotations.Hidden;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.KMA.BookingCare.Api.form.UpdateCientForm;
import com.KMA.BookingCare.Api.form.UserForm;
import com.KMA.BookingCare.Api.form.formDelete;
import com.KMA.BookingCare.Api.form.searchDoctorForm;
import com.KMA.BookingCare.Service.HospitalService;
import com.KMA.BookingCare.Service.SpecializedService;
import com.KMA.BookingCare.Service.UserService;

import javax.servlet.http.HttpSession;

@RestController
public class UserApi {

	private final Logger log = LoggerFactory.getLogger(UserApi.class);
	
	@Autowired
	private UserService userServiceImpl;

	@Autowired
	private SpecializedService specializedServiceImpl;

	@Autowired
	private HospitalService hospitalServiceImpl;
	
	
	@PostMapping("/user")
	public ResponseEntity<User> getByUsername(@RequestBody UserInput userInput){
		User user=userServiceImpl.findByUsername(userInput.getUsername());
		return new ResponseEntity<User>(user, HttpStatus.OK);
}
	@Hidden
	@PostMapping(value = "/admin/api/managerUser/add")
	public ResponseEntity<?> addOrUser(@ModelAttribute UserForm form) {
		try {
			userServiceImpl.saveDoctor(form);
			System.out.println("oke");
		} catch (IllegalStateException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("looxi");
		}
			return ResponseEntity.ok("true");
		
	}
	
	@Hidden
	@PostMapping(value = "/admin/api/managerUser/delete")
	public ResponseEntity<?> deleteUserOke(@RequestBody formDelete userDelete) {
		try {
//			userServiceImpl.saveDoctor(form);
			userServiceImpl.updateUserByStatus(userDelete.getIds());
		} catch (Exception e ) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("looxi");
		}
			return ResponseEntity.ok("true");
		
	}
	 @PostMapping(value = "/updateClient")
	   public ResponseEntity<?> addUser(@ModelAttribute UpdateCientForm form) {
		   System.out.println("hohi");
		   userServiceImpl.updateClient(form);
				return ResponseEntity.ok("true");
			
	 }

	 /*
	 * hien thi len trang chu
	 * */
	 @GetMapping(value = "/api/user/get-random-docter")
	public List<User> getRandomDocter(){
		log.info("Request to get random docter {}");
		 List<User> lstBacsi = userServiceImpl.findRandomDoctor();
		 return lstBacsi;
	 }

	 @GetMapping(value = "/api/user/specialzed/{id}")
	public ResponseEntity<Page<User>> getALlBySpecialzedId(@PathVariable long id,
																 @PageableDefault(page = 0, size = 10) Pageable pageable){
		log.info("Request to getAllBySpecialzedId {}");
		 Page<User> page = userServiceImpl.findAllDoctorOfSpecializedApi(id, 1,pageable );
		 return  ResponseEntity.ok(page);
	 }

	 @GetMapping(value = "/api/user/hospital/{id}")
	public ResponseEntity<Page<User>> getAllByHospitalId(@PathVariable long id, @PageableDefault(page = 0, size = 10) Pageable pageable){
		log.info("Request to getAllByHospitalId {} ");
		 Page<User> page = userServiceImpl.findAllDoctorOfHospitalApi(id,1, pageable);
		 return  ResponseEntity.ok(page);
	 }

	 //get thong tin bac si
	@GetMapping(value="/api/user/docter/{id}")
	public ResponseEntity<User>  infoDoctor(Model model,@PathVariable("id") Long id){
		log.info("Request to infoDocter");
		User user = userServiceImpl.findOneDoctorAndWorktime(id);
		return ResponseEntity.ok(user);
	}

	@GetMapping(value = "api/user/search-docter")
	public List<User> searchDocter (@RequestBody searchDoctorForm form, @RequestParam Pageable pageable){
		log.info("Request to search docter {}", form);
		List<User> lstUser = userServiceImpl.searchDoctorAndPageable(form, pageable);
		return  lstUser;
	}

	@GetMapping(value = "/api/current-login")
	public User getCurrentLogin(HttpSession session){
		log.info("Request to get current login {}");
		MyUser userDetails = (MyUser) session.getAttribute("userDetails");
		User userDto = userServiceImpl.findOneById(userDetails.getId());
		return  userDto;
	}

	@PutMapping(value = "/api/user/{id}")
	public ResponseEntity<?> updateUser(@RequestBody UserForm form) throws IOException {
		log.info("Request to update user");
		userServiceImpl.saveDoctor(form);
		return  ResponseEntity.ok(HttpStatus.OK);
	}

	@GetMapping(value = "/api/user/docter")
	public ResponseEntity<Page<User>> findAllDoctor(@PageableDefault(page = 0, size = 10) Pageable pageable) {
		 log.debug("Request to get all doctor");
		 Page<User> page = userServiceImpl.findAllDoctor(pageable);
		return ResponseEntity.ok(page);
	}


	   
}
