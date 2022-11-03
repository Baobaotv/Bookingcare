package com.KMA.BookingCare.ServiceImpl;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import com.KMA.BookingCare.Api.form.ChangeTimeCloseForm;
import com.KMA.BookingCare.Api.form.UploadMedicalRecordsForm;
import com.KMA.BookingCare.Dto.NotificationScheduleDTO;
import com.KMA.BookingCare.common.Constant;
import com.cloudinary.Cloudinary;
import com.cloudinary.api.exceptions.BadRequest;
import com.cloudinary.utils.ObjectUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
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

	@Autowired
	private JavaMailSender emailSender;

	@Autowired
	private KafkaTemplate<String, String> kafkaTemplate;

	@Autowired
	private Cloudinary cloudinary;
	
	@Override
	public void  save(BookingForm form) throws JsonProcessingException {
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
		entity = medicalRepository.save(entity);
		sendKafkaNotification(entity, Constant.NOTIFICATION_TYPE_USER_BOOKING_SCHEDULE);
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
	public boolean changTimeClose(ChangeTimeCloseForm changeTimeCloseForm) throws JsonProcessingException {
		Long idWk = changeTimeCloseForm.getIdWk();
		MedicalExaminationScheduleEntity itemUpdate = medicalRepository.findById(changeTimeCloseForm.getIdMedical()).get();
		if(!validateChangTimeClose(changeTimeCloseForm, itemUpdate)) return false;
		String date = "";
		Long count = medicalRepository.countMedicalWhenChangeWk(
				itemUpdate.getDate(),itemUpdate.getDoctor().getId(), itemUpdate.getWorkTimeID(), idWk
		);
		List<MedicalExaminationScheduleEntity> lstUpdate = new ArrayList<>();

		if(count == 0) {
			//update
			itemUpdate.setWorkTimeID(idWk);
			medicalRepository.save(itemUpdate);
			sendKafkaNotification(itemUpdate, Constant.NOTIFICATION_TYPE_CHANGE_SCHEDULE);
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
			sendKafkaNotification(lstUpdate, Constant.NOTIFICATION_TYPE_CHANGE_SCHEDULE);
//			sendMail(lstUpdate);
		}
		return  true;
	}

	@Override
	public void handleSendMedicalRecords(UploadMedicalRecordsForm form) {
		try {
			Map result= cloudinary.uploader().upload(form.getMedicalRecords().getBytes(),
					ObjectUtils.asMap("resource_type","auto"));
			String url = (String) result.get("secure_url");
			NotificationScheduleDTO notificationSchedule = new NotificationScheduleDTO();
			notificationSchedule.setIds(List.of(form.getMedicalId()));
			notificationSchedule.setTypeNotification(Constant.NOTIFICATION_TYPE_SEND_MEDICAL_RECORDS);
			notificationSchedule.setFile(url);
			sendKafkaNotification(notificationSchedule);
		} catch (Exception e) {
			System.out.println("upload img fail");
		}
	}

	public String plusDate(String date) {
		String[] arr = date.split("-");
		LocalDate l = LocalDate.of(Integer.parseInt(arr[0]), Integer.parseInt(arr[1]), Integer.parseInt(arr[2]));
		l = l.plusDays(1);
		date = l.toString();
		return date;
	}

	public boolean validateChangTimeClose(ChangeTimeCloseForm changeTimeCloseForm, MedicalExaminationScheduleEntity entity) {
		if(changeTimeCloseForm.getIdWk() == null || changeTimeCloseForm.getIdWk().equals("") ) return false;
		if (changeTimeCloseForm.getIdWk() <= entity.getWorkTimeID()) return false;
		return  true;
	}

	private void sendMail(List<MedicalExaminationScheduleEntity> lstUpdate) {
		Long[] ids = new Long[lstUpdate.size()];
		for(int i = 0; i<lstUpdate.size();i++) {
			ids[i] = lstUpdate.get(i).getId();
		}
		List<String> lstEmail = userRepository.getEmailByIds(ids);
		StringBuilder content = new StringBuilder("Bookingcare xin chào bạn. Lời đầu tiên BookingCare cảm ơn bạn đã tin tưởng và đồng ");
		content.append("cùng chúng tôi. ");
		content.append("Do hiện tại số lượng bệnh nhân cũng như khách hàng quá đông, hệ thông đang có tình trạng tắc nghẽn ");
		content.append("cho nên ca khám của bạn đã được đẩy lùi muộn hơn so với dự kiến. ");
		content.append("Mời bạn truy cập vào hệ thống để cập nhập lại tình hình ca khám của mình. ");
		content.append("Bookingcare rất xin lỗi vì sự cố trên. ");
		content.append("Chúng tôi xin chân thành cảm ơn!");
		if(lstEmail != null && lstEmail.size() != 0) {
			lstEmail.forEach(p->{
				SimpleMailMessage message = new SimpleMailMessage();
				message.setTo(p);
				message.setSubject("Hệ thống đăng kí lịch khám BookingCare ");
				message.setText(content.toString());
				emailSender.send(message);
			});
		}
	}

	private void sendKafkaNotification(MedicalExaminationScheduleEntity entity, String typeNotification) throws JsonProcessingException {
		NotificationScheduleDTO notificationSchedule = new NotificationScheduleDTO();
		notificationSchedule.setIds(List.of(entity.getId()));
		notificationSchedule.setTypeNotification(typeNotification);
		ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
		String json = ow.writeValueAsString(notificationSchedule);
		kafkaTemplate.send(Constant.NOTIFICATION_TOPIC, json);
	}

	private void sendKafkaNotification(List<MedicalExaminationScheduleEntity> entities, String typeNotification) throws JsonProcessingException {
		NotificationScheduleDTO notificationSchedule = new NotificationScheduleDTO();
		List<Long> ids = entities.stream().map(item ->item.getId()).collect(Collectors.toList());
		notificationSchedule.setIds(ids);
		notificationSchedule.setTypeNotification(typeNotification);
		ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
		String json = ow.writeValueAsString(notificationSchedule);
		kafkaTemplate.send(Constant.NOTIFICATION_TOPIC, json);
	}

	private void sendKafkaNotification(NotificationScheduleDTO dto) throws JsonProcessingException {
		ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
		String json = ow.writeValueAsString(dto);
		kafkaTemplate.send(Constant.NOTIFICATION_TOPIC, json);
	}

}
