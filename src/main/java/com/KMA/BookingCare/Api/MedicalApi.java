package com.KMA.BookingCare.Api;

import com.KMA.BookingCare.Dto.MedicalExaminationScheduleDto;
import com.KMA.BookingCare.Dto.MyUser;
import com.KMA.BookingCare.Repository.MedicalExaminationScheduleRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.KMA.BookingCare.Api.form.formDelete;
import com.KMA.BookingCare.Service.MedicalExaminationScheduleService;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api")
public class MedicalApi {

	private final Logger log = LoggerFactory.getLogger(MedicalApi.class);

	@Autowired
	private MedicalExaminationScheduleService medicalServiceImpl;

	@Autowired
	private MedicalExaminationScheduleRepository medicalExaminationScheduleRepository;
	
	@PutMapping(value = {"/medical/update-status"})
	public ResponseEntity<?> updateStatusMedical(@RequestBody List<String> ids) {
		log.info("Request to update status");
		try {
			medicalServiceImpl.updateMedicalByStatus(0, ids);
		} catch (Exception e ) {
			log.error(e.getMessage());
		}
		return ResponseEntity.ok(HttpStatus.OK);
	}
	@PostMapping(value ="/medical/complete")
	public ResponseEntity<?> completeMedical(@RequestBody List<String> ids) {
		log.info("Request to update medical complete {}", ids);
		try {
			medicalServiceImpl.updateMedicalByStatus(2, ids);
		} catch (Exception e ) {
			log.error(e.getMessage());
		}
		return ResponseEntity.ok(HttpStatus.OK);
	}

	@GetMapping(value = "/media/get-by-current-login")
	public List<MedicalExaminationScheduleDto> getAllByCurrentLogin(HttpSession session){
		log.info("Request to get all by current login {}");
		MyUser user = (MyUser) session.getAttribute("userDetails");
		List<MedicalExaminationScheduleDto> lstDto = new ArrayList<>();
		if(user.getRoles().contains("ROLE_DOCTOR")) {
			lstDto= medicalServiceImpl.findAllByDoctorIdAndStatus(user.getId(), 1);
		}else {
			lstDto= medicalServiceImpl.findAllByStatus(1);
		}
		return  lstDto;
	}

	@GetMapping(value = "/media/get-all-complete")
	public List<MedicalExaminationScheduleDto> getAllComplete(HttpSession session){
		MyUser user = (MyUser) session.getAttribute("userDetails");

		List<MedicalExaminationScheduleDto> lstMedical= new ArrayList<MedicalExaminationScheduleDto>();
		if(user.getRoles().contains("ROLE_DOCTOR")) {
//			 lstHandbook= handbookSeviceImpl.findAllByStatusAndUserId(1, user.getId());
			lstMedical= medicalServiceImpl.findAllByDoctorIdAndStatus(user.getId(), 2);
		}else {
			lstMedical= medicalServiceImpl.findAllByStatus(2);
		}
		return  lstMedical;
	}

	@DeleteMapping("/media/{id}")
	public ResponseEntity<?> delete(@PathVariable Long id){
		log.info("Request to delete {}", id);
		medicalExaminationScheduleRepository.deleteById(id);
		return ResponseEntity.noContent().build();
	}

	@DeleteMapping("/media/deletes")
	public ResponseEntity<?> deletes(@RequestBody List<Long> ids){
		log.info("Request to delete multi by ids {}", ids);
		medicalExaminationScheduleRepository.deleteAllById(ids);
		return ResponseEntity.noContent().build();
	}

	// check bac si co... lich kham vao ngay...
	@GetMapping(value="/media/check/{idDoctor}/{idWorktime}/{date}")
	public ResponseEntity<?>  book(Model model, @PathVariable("idDoctor") Long idDoctor, @PathVariable("idWorktime") Long idWorktime,
								   @PathVariable("date") String date){

		Boolean check = medicalExaminationScheduleRepository.existsByDateAndAndDoctorIdAndWorkTimeID(date, idDoctor,idWorktime);
		//true da co lich
		//false chua co lich
		return ResponseEntity.ok(check);
	}


}
