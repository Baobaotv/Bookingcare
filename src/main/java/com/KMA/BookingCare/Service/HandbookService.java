package com.KMA.BookingCare.Service;

import java.text.ParseException;
import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;

import com.KMA.BookingCare.Api.form.HandbookForm;
import com.KMA.BookingCare.Api.form.searchHandbookForm;
import com.KMA.BookingCare.Dto.HandbookDto;
import com.KMA.BookingCare.Entity.HandbookEntity;

public interface HandbookService {
	void saveHandbook(HandbookForm form) throws ParseException;
	List<HandbookDto> findAllByStatusPageable(Integer status,Pageable pageable);
	List<HandbookDto> findAllByStatus(Integer status);
	List<HandbookDto> findAllByStatusAndUserId(Integer status, Long id,Pageable pageable);
	public void updateHandbookByStatus(List<String> ids);
	public void update(HandbookForm form);
	HandbookDto findOneById(Long id);
	List<HandbookDto> searchHandbook(searchHandbookForm form);
	
	List<HandbookDto> findRandomHandbook();
	/*
	 * List<HandbookDto> findAllByTitleAndStatus( String title); List<HandbookDto>
	 * findAllByTitleAndSpecializedId( String title,Long specializedId);
	 * List<HandbookDto> findAllBySpecializedIdAndStatus(Long SpecializedId, Integer
	 * status);
	 */
	
	List<HandbookDto> searchHandbookAndPageable(searchHandbookForm form,Long userId, Pageable page);
}
