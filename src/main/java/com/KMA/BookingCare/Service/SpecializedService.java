package com.KMA.BookingCare.Service;

import java.text.ParseException;
import java.util.List;

import org.springframework.data.domain.Pageable;

import com.KMA.BookingCare.Api.form.SpecializedForm;
import com.KMA.BookingCare.Dto.SpecializedDto;

public interface SpecializedService {
	List<SpecializedDto> findAll();
	List<SpecializedDto> findAllByStatus(Integer status,Pageable pageable);
	void saveOrUpdateSpecialized(SpecializedForm form) throws ParseException;
}
