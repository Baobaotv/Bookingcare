package com.KMA.BookingCare.Service;

import java.util.List;

import com.KMA.BookingCare.Dto.WorkTimeDto;
import com.KMA.BookingCare.Entity.WorkTimeEntity;

public interface WorkTimeService {
	List<WorkTimeDto> findAll();
	List<WorkTimeEntity> findByListId(List<Long> id);
	List<WorkTimeDto> findByDateAndDoctorId(String date, Long id);

	WorkTimeDto findOneById(Long id);
}
