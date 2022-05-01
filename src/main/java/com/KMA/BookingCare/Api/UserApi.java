package com.KMA.BookingCare.Api;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.KMA.BookingCare.Api.form.UpdateCientForm;
import com.KMA.BookingCare.Api.form.UserForm;
import com.KMA.BookingCare.Api.form.formDelete;
import com.KMA.BookingCare.Api.form.searchDoctorForm;
import com.KMA.BookingCare.Dto.HospitalDto;
import com.KMA.BookingCare.Dto.SpecializedDto;
import com.KMA.BookingCare.Dto.User;
import com.KMA.BookingCare.Dto.UserInput;
import com.KMA.BookingCare.Service.HospitalService;
import com.KMA.BookingCare.Service.SpecializedService;
import com.KMA.BookingCare.Service.UserService;

@RestController
public class UserApi {
	
	
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
	   
	  

}
