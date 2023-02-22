package com.KMA.BookingCare.Api;

import com.KMA.BookingCare.Dto.HandbookDto;
import com.KMA.BookingCare.Dto.SpecializedDto;
import com.KMA.BookingCare.Repository.SpecializedRepository;
import com.KMA.BookingCare.search.SpecializedSearchRepository;
import io.swagger.v3.oas.annotations.Hidden;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import com.KMA.BookingCare.Api.form.HospitalForm;
import com.KMA.BookingCare.Api.form.SpecializedForm;
import com.KMA.BookingCare.Api.form.formDelete;
import com.KMA.BookingCare.Service.HospitalService;
import com.KMA.BookingCare.Service.SpecializedService;

import java.util.List;

@RestController
@RequestMapping("/api")
public class SpecialzedApi {

	private final Logger log = LoggerFactory.getLogger(SpecialzedApi.class);
	
	@Autowired
	private SpecializedService specializedServiceImpl;

	@Autowired
	private SpecializedRepository specializedRepository;

//	@Autowired
//	private SpecializedSearchRepository specializedSearchRepository;

	@Hidden
	@PostMapping(value = "/api/specialized")
	public ResponseEntity<?> addHospital(@ModelAttribute SpecializedForm form) {
		log.info("Request to add specialed {}");
		try {
			specializedServiceImpl.saveOrUpdateSpecialized(form);

		} catch (Exception e ) {
			log.info(e.getMessage());
		}
		return ResponseEntity.ok(HttpStatus.OK);
	}

	@Hidden
	@PutMapping(value = "/api/specialized")
	public ResponseEntity<?> editHospital(@ModelAttribute SpecializedForm form) {
		log.info("Request to update specialed");
		try {
			specializedServiceImpl.saveOrUpdateSpecialized(form);
		} catch (Exception e ) {
			log.error(e.getMessage());
		}
		return ResponseEntity.ok(HttpStatus.OK);
	}

	@GetMapping(value = "/specicalized")
	public ResponseEntity<Page<SpecializedDto>> getAll(@PageableDefault(page = 0, size = 20) Pageable pageable){
		log.info("Request to getAll {}");
		Page<SpecializedDto> lstChuyenKhoa = specializedServiceImpl.findAllByStatusApi(1, pageable);
		return  ResponseEntity.ok(lstChuyenKhoa);
	}

	/*
	 * hien thi len trang chu
	 *  */
	@GetMapping(value = "/specicalized/get-random")
	public ResponseEntity<List<SpecializedDto>> getRandom(){
		log.info("Request to get Random specicalized");
		List<SpecializedDto> lstChuyenKhoa = specializedServiceImpl.findRandomSpecicalized();
		return ResponseEntity.ok(lstChuyenKhoa);
	}

	@Hidden
	@DeleteMapping(value = "/specicalized/{id}")
	public ResponseEntity<?> delete(@PathVariable Long id){
		log.info("Request to delete {}", id);
		specializedRepository.deleteById(id);
//		specializedSearchRepository.deleteById(String.valueOf(id));
		return  ResponseEntity.noContent().build();
	}

	@Hidden
	@DeleteMapping(value = "/specicalized/delete")
	public ResponseEntity<?> deletes(@RequestBody List<Long> ids){
		log.info("Request to deletes by ids {}", ids);
		specializedRepository.deleteAllById(ids);
		specializedRepository.deleteAllById(ids);
		return ResponseEntity.noContent().build();
	}
}
