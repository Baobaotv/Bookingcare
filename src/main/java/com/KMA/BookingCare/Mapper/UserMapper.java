package com.KMA.BookingCare.Mapper;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.KMA.BookingCare.Dto.User;
import com.KMA.BookingCare.Dto.WorkTimeDto;
import com.KMA.BookingCare.Entity.UserEntity;
import com.KMA.BookingCare.Entity.WorkTimeEntity;

public class UserMapper {
	public static User convertToDto(UserEntity entity) {
		User dto = new User();
		dto.setId(entity.getId());
		dto.setImg(entity.getImg());
		dto.setName(entity.getFullName());
		dto.setDescription(entity.getDescription());
		dto.setShortDescription(entity.getShortDescription());
		dto.setSex(entity.getSex());
		dto.setPhone(entity.getPhoneNumber());
		dto.setLocation(entity.getLocation());
		if(entity.getHospital()!=null) {
			dto.setHospitalId(entity.getHospital().getId());
		}
		dto.setEmail(entity.getEmail());
		dto.setUsername(entity.getUsername());
		dto.setYearOfBirth(entity.getYearOfBirth());
		if(entity.getSpecialized()!=null) {
			dto.setSpecializedId(entity.getSpecialized().getId());
			dto.setSpecializedName(entity.getSpecialized().getName());
		}
		if(entity.getHospital()!=null) {
			dto.setHospitalName(entity.getHospital().getName());
			dto.setHospitalLocation(entity.getHospital().getLocation());
		}
		if(entity.getWorkTimeEntity()!=null) {
			Set<WorkTimeEntity> wkEntityLst= entity.getWorkTimeEntity();
			Set<WorkTimeDto> wkDtoLst= new HashSet<WorkTimeDto>();
			for(WorkTimeEntity wkEntity: wkEntityLst) {
				WorkTimeDto wkDto = WorkTimeMapper.convertToDto(wkEntity);
				wkDtoLst.add(wkDto);
			}
			List<WorkTimeDto> targetList = new ArrayList<>(wkDtoLst);
			dto.setLstWorkTime( targetList);
		}
		return dto;
	}

}
