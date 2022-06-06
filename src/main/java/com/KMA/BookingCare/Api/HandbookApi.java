package com.KMA.BookingCare.Api;

import java.util.List;

import com.KMA.BookingCare.Repository.HandbookRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import com.KMA.BookingCare.Api.form.HandbookForm;
import com.KMA.BookingCare.Dto.HandbookDto;
import com.KMA.BookingCare.Service.HandbookService;
import com.KMA.BookingCare.Service.SpecializedService;

import javax.servlet.http.HttpSession;

@RestController()
@RequestMapping("/api")
public class HandbookApi {

	private final Logger log = LoggerFactory.getLogger(HandbookApi.class);
	
	@Autowired
	private HandbookService handbookServiceImpl;

	@Autowired
	private HandbookRepository handbookRepository;
	
	@Autowired
	private SpecializedService specializeServiceImpl;

	@PostMapping(value = "/handbook")
	public ResponseEntity<?> addHandbook(@RequestBody HandbookForm form) {
		log.info("Request to add handbook {}");
		try {
			handbookServiceImpl.saveHandbook(form);
			System.out.println("oke");
			
		} catch (Exception e ) {
			// TODO Auto-generated catch block
			log.error(e.getMessage());
		}
		return ResponseEntity.ok(HttpStatus.OK);
	}

	
	@PutMapping(value = "/handbok")
	public ResponseEntity<?> editHandbook(@RequestBody HandbookForm form) {
		log.info("Request to edit handbook {}");
		try {
			handbookServiceImpl.saveHandbook(form);
		} catch (Exception e ) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			log.error(e.getMessage());
		}
		return ResponseEntity.ok(HttpStatus.OK);
	}

	@GetMapping(value = "/handbook")
	public ResponseEntity<Page<HandbookDto>> getAll(@PageableDefault(page = 0, size = 20) Pageable pageable){
		log.info("Request to getAll {}");
		Page<HandbookDto> page = handbookServiceImpl.findAllByStatusPageable(1, pageable);
		return ResponseEntity.ok(page);
	}

	/*
	* hien thi len trang chu
	*  */
	@GetMapping(value = "/handbook/get-random")
	public ResponseEntity<List<HandbookDto>> getRandom(){
		log.info("Request to get Random");
		List<HandbookDto> lstCamNangRandom = handbookServiceImpl.findRandomHandbook();
		return ResponseEntity.ok(lstCamNangRandom);
	}

	@GetMapping(value = "/handbook/search")
	public ResponseEntity<Page<HandbookDto>> search(@PageableDefault(page = 0, size = 10) Pageable pageable
											, @RequestParam(required = false) String title,
													@RequestParam(required = false) Long specialzedId,
													HttpSession httpSession){
		log.info("Request to search Handbook {}");
		UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();
		boolean isDoctor = false;
		for (GrantedAuthority authority : userDetails.getAuthorities()) {
			if(authority.getAuthority().equals("ROLE_DOCTOR")){
				isDoctor = true;
			}
		}
		if(isDoctor) {
			Page<HandbookDto> page= handbookServiceImpl.searchHandbookAndPageableapi(title,specialzedId, userDetails.getUsername(),pageable);
			return  ResponseEntity.ok(page);
		}else{
			Page<HandbookDto> page= handbookServiceImpl.searchHandbookAndPageableapi(title,specialzedId,null,pageable);
			return  ResponseEntity.ok(page);
		}

	}

	@GetMapping(value = "/handbook/{id}")
	public ResponseEntity<HandbookDto> getOne(@PathVariable long id){
		log.info("Request to getOneById {}", id);
		HandbookDto dto = handbookServiceImpl.findOneByIdApi(id);
		return ResponseEntity.ok(dto);
	}

	@DeleteMapping("/handbook/{id}")
	public ResponseEntity<?> delete(@PathVariable long id){
		log.info("Request to delete {}", id);
		handbookRepository.deleteById(id);
		return ResponseEntity
				.noContent()
				.build();
	}

	@DeleteMapping("/handbook/deletes")
	public ResponseEntity<?> deleteALl(@RequestBody List<Long> ids){
		log.info("Request to delete multi {}", ids);
		handbookRepository.deleteAllById(ids);
		return ResponseEntity
				.noContent()
				.build();
	}
}
