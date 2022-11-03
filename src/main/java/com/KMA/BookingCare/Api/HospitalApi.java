package com.KMA.BookingCare.Api;

import com.KMA.BookingCare.Dto.HospitalDto;
import com.KMA.BookingCare.Dto.SpecializedDto;
import com.KMA.BookingCare.Entity.HospitalEntity;
import com.KMA.BookingCare.Repository.HospitalRepository;
import com.KMA.BookingCare.ServiceImpl.HospitalServiceImpl;
import com.KMA.BookingCare.search.HospitalSearchRepository;
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
import com.KMA.BookingCare.Api.form.formDelete;
import com.KMA.BookingCare.Service.HospitalService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
//@RequestMapping("")
public class HospitalApi {

	private final Logger log = LoggerFactory.getLogger(HospitalApi.class);
	
	@Autowired
	private HospitalService hospitalServiceImpl;

	@Autowired
	private HospitalRepository hospitalRepository;

	@Autowired
	private HospitalSearchRepository hospitalSearchRepository;


	@Hidden
	@PostMapping(value = "/api/hospital")
	public ResponseEntity<?> addHospital(@RequestBody HospitalForm form) {
		log.info("Request to save hospital {}");
		try {
			hospitalServiceImpl.saveHospital(form);
		} catch (Exception e ) {
			log.error(e.getMessage());
		}
		return ResponseEntity.ok(HttpStatus.OK);
	}

	@Hidden
	@PutMapping(value = "/api/hospital/{id}")
	public ResponseEntity<?> editHospital(@RequestBody HospitalForm form, @PathVariable long id) {
		log.info("Request to update hospital {}", id);
		try {
			hospitalServiceImpl.saveHospital(form);
		} catch (Exception e ) {
			// TODO Auto-generated catch block
			log.error(e.getMessage());
		}
		return ResponseEntity.ok("true");
	}

	@GetMapping(value = "/api/hospital/{id}")
	public ResponseEntity<HospitalEntity> findOne(@PathVariable Long id){
		log.info("Request to get one by Id {}", id);
		HospitalEntity hospitalEntity = hospitalRepository.findOneById(id);
		return  ResponseEntity.ok(hospitalEntity);
	}

	@GetMapping(value = "/api/hospital")
	public ResponseEntity<Page<HospitalDto>> findAll(@PageableDefault(page = 0, size = 10) Pageable pageable){
		log.info("Request to find all {}");
		Page<HospitalDto> page = hospitalServiceImpl.findAllByStatusApi(1,pageable);
		return  ResponseEntity.ok(page);

	}

	
	@GetMapping(value = "/api/hospital/get-all-by-status")
	public ResponseEntity<Page<HospitalDto>> getAllByStatus(@PageableDefault(page = 0, size = 10) Pageable pageable){
		log.info("Request to get All By Status {}");
		Page<HospitalDto> page = hospitalServiceImpl.findAllByStatusApi(1,pageable);
		return ResponseEntity.ok(page);
	}

	@Hidden
	@DeleteMapping("/api/hospital/deletes")
	public ResponseEntity<?> deleteAll(@RequestBody List<Long> ids){
		log.info("Request to delete all by ids {}", ids);
		hospitalRepository.deleteAllById(ids);
		hospitalSearchRepository.deleteAllById(ids);
		return ResponseEntity.noContent().build();
	}

	@Hidden
	@DeleteMapping("/api/hospital/{id}")
	public ResponseEntity<?> delete(@PathVariable Long id){
		log.info("Request to delete {}", id);
		hospitalRepository.deleteById(id);
		hospitalSearchRepository.deleteById(id);
		return ResponseEntity.noContent().build();
	}

	/*
	 * hien thi len trang chu
	 *  */
	@GetMapping(value = "/api/hospital/get-random")
	public ResponseEntity<List<HospitalDto>> getRandom(){
		log.info("Request to get Random specicalized");
		List<HospitalDto> lstBenhVien = hospitalServiceImpl.findRandomSpecicalized();
		return ResponseEntity.ok(lstBenhVien);
	}

	//old

	@PostMapping(value = "/admin/api/managerHospital/add")
	public ResponseEntity<?> addHospitalOld(@ModelAttribute HospitalForm form) {
		try {
			hospitalServiceImpl.saveHospital(form);
		} catch (Exception e ) {
			e.printStackTrace();
			System.out.println("looxi");
		}
		return ResponseEntity.ok("true");
	}

	@PostMapping(value = "/admin/api/managerHospital/delete")
	public ResponseEntity<?> deleteHospital(@RequestBody formDelete form) {
		try {
			hospitalRepository.deleteAllById(form.getIds().stream().map(p -> Long.parseLong(p)).collect(Collectors.toList()));
			hospitalSearchRepository.deleteAllById(form.getIds().stream().map(p -> Long.parseLong(p)).collect(Collectors.toList()));
		} catch (Exception e ) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ResponseEntity.ok("true");
	}

	@PostMapping(value = "/admin/api/managerHospital/edit")
	public ResponseEntity<?> editHospital(@ModelAttribute HospitalForm form) {
		try {
			hospitalServiceImpl.saveHospital(form);
		} catch (Exception e ) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ResponseEntity.ok("true");
	}
}
