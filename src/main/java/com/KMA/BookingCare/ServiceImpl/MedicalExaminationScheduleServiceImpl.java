package com.KMA.BookingCare.ServiceImpl;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.KMA.BookingCare.Api.form.ChangeTimeCloseForm;
import com.cloudinary.api.exceptions.BadRequest;
import org.apache.logging.log4j.util.Strings;
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

	@Override
	public boolean changTimeClose(ChangeTimeCloseForm changeTimeCloseForm) {
		Long idWk = changeTimeCloseForm.getIdWk();
		MedicalExaminationScheduleEntity itemUpdate = medicalRepository.findById(changeTimeCloseForm.getIdMedical()).get();
		if(!validateChangTimeClose(changeTimeCloseForm, itemUpdate)){
			return false;
		};
		String date = "";
		Long count = medicalRepository.countMedicalWhenChangeWk(
				itemUpdate.getDate(),itemUpdate.getDoctor().getId(), itemUpdate.getWorkTimeID(), idWk
		);
		List<MedicalExaminationScheduleEntity> lstUpdate = new ArrayList<>();
		if(count == 0) {
			//update
			itemUpdate.setWorkTimeID(idWk);
			medicalRepository.save(itemUpdate);
		} else {
			// lst medical, id > changTimeClose.idMedical
			do {
				List<MedicalExaminationScheduleEntity> lst = new ArrayList<>();
				if (Strings.isEmpty(date)) {
					// lst get theo id
					lst = medicalRepository.findAllByDateAnđoctorId(itemUpdate.getDate(), itemUpdate.getDoctor().getId());
				}else {
					// lst get theo id va date
					lst = medicalRepository.findAllByDateAnđoctorId(date, itemUpdate.getDoctor().getId());
				}

				for (MedicalExaminationScheduleEntity item : lst) {
					if (item.getWorkTimeID() < idWk ) {
						if (idWk <= 16 ) {
							// truong hop tang nhưng chua vuot qua ca cuoi ngay
							item.setWorkTimeID(idWk);
							idWk = idWk +1;

						} else {
							// truong hop vuot qua ca cuoi ngay
							//cong ngay
							if(Strings.isEmpty(date) || Strings.isBlank(date)) {
								date = plusDate(itemUpdate.getDate());
							} else {
								date = plusDate(date);
							}
							idWk = 1L;
							item.setWorkTimeID(idWk);
							item.setDate(date);
							idWk ++;
						}
						lstUpdate.add(item);
					} else {
						if(!date.equals(itemUpdate.getDate()) && !Strings.isEmpty(date)) {
							item.setWorkTimeID(idWk);
							idWk = idWk +1;
						} else {
							date = "";
							break;
						}

					}
				}
			} while (Strings.isEmpty(date) || Strings.isBlank(date));
			medicalRepository.saveAll(lstUpdate);
		}
		return  true;
	}

	public String plusDate(String date) {
		String[] arr = date.split("-");
		LocalDate l = LocalDate.of(Integer.parseInt(arr[0]), Integer.parseInt(arr[1]), Integer.parseInt(arr[2]));
		l = l.plusDays(1);
		date = l.toString();
		return date;
	}

	public boolean validateChangTimeClose(ChangeTimeCloseForm changeTimeCloseForm, MedicalExaminationScheduleEntity entity) {
		if(changeTimeCloseForm.getIdWk() == null || changeTimeCloseForm.getIdWk().equals("") ) {
			return false;
		}
		if (changeTimeCloseForm.getIdWk() <= entity.getWorkTimeID()) {
			return false;
		}
		return  true;
	}

}
