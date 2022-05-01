package com.KMA.BookingCare.Service;

import java.util.List;

import com.KMA.BookingCare.Dto.InteractiveDto;
import com.KMA.BookingCare.Dto.MessageDto;

public interface InteractiveService {
	void saveOrUpdate(MessageDto chatMessage);
//	List<E>
	List<InteractiveDto> findAll();

}
