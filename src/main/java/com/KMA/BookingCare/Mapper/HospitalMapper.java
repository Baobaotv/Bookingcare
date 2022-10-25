package com.KMA.BookingCare.Mapper;

import com.KMA.BookingCare.Dto.HospitalDto;
import com.KMA.BookingCare.Entity.HospitalEntity;
import com.KMA.BookingCare.document.HospitalDocument;

public class HospitalMapper {
	
	public static HospitalDto convertToDto(HospitalEntity entity) {
		HospitalDto dto = new HospitalDto();
		dto.setId(entity.getId());
		dto.setLocation(entity.getLocation());
		dto.setName(entity.getName());
		dto.setId(entity.getId());
		dto.setImg(entity.getImg());
		dto.setDescription(entity.getDescription());
		dto.setLatitude(entity.getLatitude());
		dto.setLongitude(entity.getLongitude());
		return dto;
	}

	public static HospitalDocument convertToDocument(HospitalEntity entity) {
		HospitalDocument document = new HospitalDocument();
		document.setId(entity.getId());
		document.setImg(entity.getImg());
		document.setDescription(entity.getDescription());
		document.setStatus(entity.getStatus());
		document.setLocation(entity.getLocation());
		document.setName(entity.getName());
		return document;
	}

}
