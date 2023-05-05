package com.KMA.BookingCare.Api;

import com.KMA.BookingCare.Api.form.ChangeTimeCloseForm;
import com.KMA.BookingCare.Api.form.UploadMedicalRecordsForm;
import com.KMA.BookingCare.Api.form.formDelete;
import com.KMA.BookingCare.Dto.MedicalExaminationScheduleDto;
import com.KMA.BookingCare.Dto.MyUser;
import com.KMA.BookingCare.Entity.MedicalExaminationScheduleEntity;
import com.KMA.BookingCare.Mapper.MedicalMapper;
import com.KMA.BookingCare.Repository.MedicalExaminationScheduleRepository;
import com.KMA.BookingCare.ServiceImpl.UserDetailsImpl;
import com.KMA.BookingCare.common.Constant;
import com.fasterxml.jackson.core.JsonProcessingException;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.apache.logging.log4j.util.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.KMA.BookingCare.Service.MedicalExaminationScheduleService;

import javax.servlet.http.HttpSession;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

@RestController
//@RequestMapping("/api")
public class MedicalApi {

	private final Logger log = LoggerFactory.getLogger(MedicalApi.class);

	@Autowired
	private MedicalExaminationScheduleService medicalServiceImpl;

	@Autowired
	private MedicalExaminationScheduleRepository medicalExaminationScheduleRepository;

	@Hidden
	@PutMapping(value = {"/api/medical/update-status"})
	public ResponseEntity<?> updateStatusMedical(@RequestBody List<String> ids) {
		log.info("Request to update status");
		try {
			medicalServiceImpl.updateMedicalByStatus(0, ids);
		} catch (Exception e ) {
			log.error(e.getMessage());
		}
		return ResponseEntity.ok(HttpStatus.OK);
	}

	@Hidden
	@PostMapping(value ="/api/medical/complete")
	public ResponseEntity<?> completeMedical(@RequestBody List<String> ids) {
		log.info("Request to update medical complete {}", ids);
		try {
			medicalServiceImpl.updateMedicalByStatus(2, ids);
		} catch (Exception e ) {
			log.error(e.getMessage());
		}
		return ResponseEntity.ok(HttpStatus.OK);
	}

	@Operation(description = "get lịch khám của user đang login")
	@GetMapping(value = "/api/media/get-by-current-login")
	public ResponseEntity<List<MedicalExaminationScheduleDto>> getAllByCurrentLogin(HttpSession session){
		log.info("Request to get all by current login {}");
		AtomicReference<String> checkUser= new AtomicReference<>("");
		// 1 là bác sĩ
		// 2 là admin
		// 3 là user
		Object result = SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();
		UserDetailsImpl user = (UserDetailsImpl) result;
		user.getAuthorities().forEach(p->{
			if(p.getAuthority().equals("ROLE_ADMIN")) {
				checkUser.set("2");
			} else if(p.getAuthority().equals("ROLE_DOCTOR")) {
				checkUser.set("1");
			}  else {
				checkUser.set("3");
			}
		});
		List<MedicalExaminationScheduleDto> lstDto;
		if(checkUser.toString().equals("1")) {
			// role_doctor
			lstDto= medicalServiceImpl.findAllByDoctorIdAndStatus(user.getId(), 1);
			return  ResponseEntity.ok(lstDto);
		}

		if (checkUser.toString().equals("2")) {
			//role _admin
			lstDto= medicalServiceImpl.findAllByStatus(1);
			return  ResponseEntity.ok(lstDto);
		}
			//role user
		lstDto = medicalServiceImpl.findAllByUserIdAndStatus(user.getId(), 1);
		return  ResponseEntity.ok(lstDto);
	}

	@GetMapping(value = "/api/media/get-all-complete")
	public List<MedicalExaminationScheduleDto> getAllComplete(HttpSession session){
		MyUser user = (MyUser) session.getAttribute("userDetails");

		List<MedicalExaminationScheduleDto> lstMedical;
		if(user.getRoles().contains("ROLE_DOCTOR")) {
			lstMedical= medicalServiceImpl.findAllByDoctorIdAndStatus(user.getId(), 2);
		}else {
			lstMedical= medicalServiceImpl.findAllByStatus(2);
		}
		return  lstMedical;
	}

	@DeleteMapping("/api/media/{id}")
	public ResponseEntity<?> delete(@PathVariable Long id){
		log.info("Request to delete {}", id);
		medicalExaminationScheduleRepository.deleteById(id);
		return ResponseEntity.noContent().build();
	}

	@DeleteMapping("/api/media/deletes")
	public ResponseEntity<?> deletes(@RequestBody List<Long> ids){
		log.info("Request to delete multi by ids {}", ids);
		medicalExaminationScheduleRepository.deleteAllById(ids);
		return ResponseEntity.noContent().build();
	}

	// check bac si co... lich kham vao ngay...
	@Operation(description = "Check xem ngày {date}, ca {idWorktine, bác siz {idDoctor} có lịch khám không")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "OK.true- đã có lịch khám chuyển sang ca khác,false- không có lịch khám được phép đặt"
			),
			@ApiResponse(responseCode = "401", description = "Unauthorized"),
	})
	@GetMapping(value="/api/media/check/{idDoctor}/{idWorktime}/{date}")
	public ResponseEntity<?>  book(Model model, @PathVariable("idDoctor") Long idDoctor, @PathVariable("idWorktime") Long idWorktime,
								   @PathVariable("date") String date){

		Boolean check = medicalExaminationScheduleRepository.existsByDateAndAndDoctorIdAndWorkTimeID(date, idDoctor,idWorktime);
		//true da co lich
		//false chua co lich
		return ResponseEntity.ok(check);
	}

	@PostMapping(value = "/api/media/change-time-close")
	public ResponseEntity<?> changTimeClose(@RequestBody ChangeTimeCloseForm changeTimeCloseForm) throws JsonProcessingException {
		log.info("Request to changTimeClose {}");
		System.out.println(changeTimeCloseForm.getIdWk()+"-"+ changeTimeCloseForm.getIdMedical());
		boolean result = medicalServiceImpl.changTimeClose(changeTimeCloseForm);
		if (result) {
			return  ResponseEntity.ok(HttpStatus.OK);
		} else {
			return  ResponseEntity.badRequest().build();
		}
	}

	@PostMapping(value = "/admin/managerMedicalComplete/uploadMedicalRecords")
	public ResponseEntity<?> uploadMedicalRecords(@ModelAttribute UploadMedicalRecordsForm form) {
		return ResponseEntity.ok(HttpStatus.OK);
	}

	@PostMapping(value = {"/admin/managerMedical/delete","/doctor/managerMedical/delete"})
	public ResponseEntity<?> deleteMedical(@RequestBody formDelete form) {
		try {
			medicalServiceImpl.updateMedicalByStatus(0, form.getIds());
		} catch (Exception e ) {
			e.printStackTrace();
		}
		return ResponseEntity.ok("true");
	}
	@PostMapping(value = {"/admin/managerMedical/complete","/doctor/managerMedical/complete"})
	public ResponseEntity<?> completeMedical(@RequestBody formDelete form) {
		try {
			medicalServiceImpl.updateMedicalByStatus(2, form.getIds());
		} catch (Exception e ) {
			e.printStackTrace();
		}
		return ResponseEntity.ok("true");
	}

	@GetMapping(value = "/api/media/get-all-schedule-is-waiting-current-login")
	public ResponseEntity<List<MedicalExaminationScheduleDto>> getAllSheduleIsWaitingByCurrentLogin(){
		log.info("Request to getAllSheduleIsWaitingByCurrentLogin {}");
		Object result = SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();
		UserDetailsImpl user = (UserDetailsImpl) result;
		List<MedicalExaminationScheduleDto> lstDto = medicalServiceImpl.findAllByUserIdAndStatus(user.getId(), Constant.MEDICAL_SCHEDULE_IS_WAITING);
		return  ResponseEntity.ok(lstDto);
	}

	@GetMapping(value = "/api/media/get-all-schedule-is-complete-current-login")
	public ResponseEntity<List<MedicalExaminationScheduleDto>> getAllSheduleIsCompleteByCurrentLogin(){
		log.info("Request to getAllSheduleIsWaitingByCurrentLogin {}");
		Object result = SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();
		UserDetailsImpl user = (UserDetailsImpl) result;
		List<MedicalExaminationScheduleDto> lstDto = medicalServiceImpl.findAllByUserIdAndStatus(user.getId(), Constant.MEDICAL_SCHEDULE_IS_COMPLETE);
		return  ResponseEntity.ok(lstDto);
	}

	@GetMapping ("/api/media/{id}")
	public ResponseEntity<?> getOneById(@PathVariable Long id){
		log.info("Request to getOneById {}", id);
		Optional<MedicalExaminationScheduleEntity> optional = medicalServiceImpl.findOneById(id);
		if (!optional.isPresent()) return ResponseEntity.noContent().build();
		MedicalExaminationScheduleDto dto = MedicalMapper.convertToDto(optional.get());
		return ResponseEntity.ok(dto);
	}

}
