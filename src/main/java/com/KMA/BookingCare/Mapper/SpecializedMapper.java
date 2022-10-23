package com.KMA.BookingCare.Mapper;

import com.KMA.BookingCare.Dto.SpecializedDto;
import com.KMA.BookingCare.Entity.SpecializedEntity;
import com.KMA.BookingCare.document.SpecializedDocument;

public class SpecializedMapper {
	public static SpecializedDto convertToDto(SpecializedEntity entity) {
		SpecializedDto dto = new SpecializedDto();
		dto.setId(entity.getId());
		dto.setCode(entity.getCode());
		dto.setDescription(entity.getDescription());
		dto.setName(entity.getName());
		dto.setImg(entity.getImg());
		return dto;
	}

	public static SpecializedDocument convertToDocument(SpecializedEntity entity) {
		SpecializedDocument document = new SpecializedDocument();
		document.setId(entity.getId());
		document.setCode(entity.getCode());
		document.setImg(entity.getImg());
		document.setDescription(entity.getDescription());
		document.setStatus(entity.getStatus());
		document.setName(entity.getName());
		return document;
	}

}
