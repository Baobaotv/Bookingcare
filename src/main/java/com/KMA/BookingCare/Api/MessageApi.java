package com.KMA.BookingCare.Api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.KMA.BookingCare.Dto.MyUser;
import com.KMA.BookingCare.Service.UserService;

@Controller
public class MessageApi {
	
	@Autowired UserService userServiceImpl;
	
    @PostMapping("/savePeerId")
//  @SendTo("/topic/publicChatRoom")
  public ResponseEntity<?> addUser(@RequestBody String peerId) {
  	MyUser a = (MyUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
  	System.out.println("tét");
  	userServiceImpl.updatePeerId(peerId, a.getId());
  	return new ResponseEntity<>( HttpStatus.OK);
  }
}
