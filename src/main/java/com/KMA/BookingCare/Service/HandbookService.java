package com.KMA.BookingCare.Service;

import java.text.ParseException;
import java.util.List;

import com.KMA.BookingCare.Dto.SearchFullTextDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;

import com.KMA.BookingCare.Api.form.HandbookForm;
import com.KMA.BookingCare.Api.form.searchHandbookForm;
import com.KMA.BookingCare.Dto.HandbookDto;
import com.KMA.BookingCare.Entity.HandbookEntity;

public interface HandbookService {
	void saveHandbook(HandbookForm form) throws ParseException;

	Page<HandbookDto> findAllByStatusPageable(Integer status, Pageable pageable);

	List<HandbookDto> findAllByStatus(Integer status);

	List<HandbookDto> findAllByStatusAndUserId(Integer status, Long id,Pageable pageable);

	void updateHandbookByStatus(List<String> ids);

	void update(HandbookForm form);

	HandbookDto findOneById(Long id);

	HandbookDto findOneByIdApi(Long id);

	List<HandbookDto> searchHandbook(searchHandbookForm form);
	
	List<HandbookDto> findRandomHandbook();
	
	List<HandbookDto> searchHandbookAndPageable(searchHandbookForm form,Long userId, Pageable page);

	Page<HandbookDto> searchHandbookAndPageableapi(String title,Long specialzed,String userId, Pageable page);

	List<HandbookEntity> getAll();

	List<HandbookDto> getListOfRecentHandbook();

	List<HandbookDto> getFeaturedHandbook();

	List<SearchFullTextDto> searchAllByFullText(String query);
}
