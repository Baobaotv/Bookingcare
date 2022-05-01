package com.KMA.BookingCare.ServiceImpl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.KMA.BookingCare.Api.form.BookingForm;
import com.KMA.BookingCare.Dto.MedicalExaminationScheduleDto;
import com.KMA.BookingCare.Entity.MedicalExaminationScheduleEntity;
import com.KMA.BookingCare.Entity.UserEntity;
import com.KMA.BookingCare.Mapper.MedicalMapper;
import com.KMA.BookingCare.Repository.MedicalExaminationScheduleRepository;
import com.KMA.BookingCare.Repository.UserRepository;
import com.KMA.BookingCare.Service.MedicalExaminationScheduleService;

@Service
public class MedicalExaminationScheduleServiceImpl implements MedicalExaminationScheduleService {

	@Autowired
	private MedicalExaminationScheduleRepository medicalRepository;
	@Autowired
	private UserRepository userRepository;
	
	@Override
	public void  save(BookingForm form) {
		// TODO Auto-generated method stub
		MedicalExaminationScheduleEntity entity= new MedicalExaminationScheduleEntity();
		entity.setDate(form.getDate());
		entity.setLocation(form.getLocation());
		entity.setNamePatient(form.getNamePatient());
		entity.setNameScheduler(form.getNameScheduler());
		entity.setPhonePatient(form.getPhonePatient());
		entity.setPhoneScheduer(form.getPhoneScheduer());
		entity.setReason(form.getReason());
		entity.setSex(form.getSex());
		entity.setWorkTimeID(form.getIdWorktime());
		UserEntity userEntity= userRepository.findOneById(form.getIdDoctor());
		entity.setHospitalName(userEntity.getHospital().getName());
		entity.setDoctor(userEntity);
		entity.setYearOfBirth(form.getYearOfBirth());
		entity.setStatus(1);
		entity.setType(form.getType());
		if(form.getUserId()!=null&&!form.getUserId().equals("")) {
			UserEntity userEntity2= userRepository.findOneById(form.getUserId());
			entity.setUser(userEntity2);
		}
		medicalRepository.save(entity);
	}

	@Override
	public List<MedicalExaminationScheduleDto> findAllByStatus(Integer status) {
		List<MedicalExaminationScheduleEntity> lstEntity = medicalRepository.findAllByStatus(status);
		List<MedicalExaminationScheduleDto> lstDto = new ArrayList<MedicalExaminationScheduleDto>();
		for(MedicalExaminationScheduleEntity entity : lstEntity) {
			MedicalExaminationScheduleDto dto = MedicalMapper.convertToDto(entity);
			lstDto.add(dto);
		}
		return lstDto;
	}

	@Override
	public List<MedicalExaminationScheduleDto> findAllByDoctorIdAndStatus(Long doctorID, Integer Status) {
		List<MedicalExaminationScheduleEntity> lstEntity = medicalRepository.findAllByDoctorIdAndStatus(doctorID, Status);
		List<MedicalExaminationScheduleDto> lstDto = new ArrayList<MedicalExaminationScheduleDto>();
		for(MedicalExaminationScheduleEntity entity : lstEntity) {
			MedicalExaminationScheduleDto dto = MedicalMapper.convertToDto(entity);
			lstDto.add(dto);
		}
		return lstDto;
	}

	@Override
	public void updateMedicalByStatus(Integer status,List<String> ids) {
		medicalRepository.updateByStatus(status,ids);
	}

	@Override
	public List<MedicalExaminationScheduleDto> findAllByUserIdAndStatus(Long userId, Integer status) {
		List<MedicalExaminationScheduleEntity> lstEntity = medicalRepository.findAllByUserIdAndStatus(userId, status);
		List<MedicalExaminationScheduleDto> lstDto = new ArrayList<MedicalExaminationScheduleDto>();
		for(MedicalExaminationScheduleEntity entity : lstEntity) {
			MedicalExaminationScheduleDto dto = MedicalMapper.convertToDto(entity);
			lstDto.add(dto);
		}
		return lstDto;
	}

}
