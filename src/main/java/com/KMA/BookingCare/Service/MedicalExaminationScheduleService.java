package com.KMA.BookingCare.Service;

import java.util.List;

import com.KMA.BookingCare.Api.form.BookingForm;
import com.KMA.BookingCare.Api.form.ChangeTimeCloseForm;
import com.KMA.BookingCare.Api.form.UploadMedicalRecordsForm;
import com.KMA.BookingCare.Dto.MedicalExaminationScheduleDto;
import com.KMA.BookingCare.Entity.MedicalExaminationScheduleEntity;
import com.fasterxml.jackson.core.JsonProcessingException;

public interface MedicalExaminationScheduleService {
	void save(BookingForm form) throws JsonProcessingException;
	List<MedicalExaminationScheduleDto> findAllByStatus(Integer status);
	List<MedicalExaminationScheduleDto> findAllByDoctorIdAndStatus(Long doctorID,Integer Status);
	void updateMedicalByStatus(Integer status,List<String> ids);
	List<MedicalExaminationScheduleDto> findAllByUserIdAndStatus(Long userId, Integer status);
	boolean changTimeClose(ChangeTimeCloseForm changeTimeCloseForm) throws JsonProcessingException;

	void handleSendMedicalRecords(UploadMedicalRecordsForm form);
}
