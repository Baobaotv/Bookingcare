package com.KMA.BookingCare.ServiceImpl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.KMA.BookingCare.Mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.KMA.BookingCare.Dto.InteractiveDto;
import com.KMA.BookingCare.Dto.MessageDto;
import com.KMA.BookingCare.Dto.MyUser;
import com.KMA.BookingCare.Entity.InteractiveEntity;
import com.KMA.BookingCare.Entity.RoleEntity;
import com.KMA.BookingCare.Entity.UserEntity;
import com.KMA.BookingCare.Mapper.InteractiveMapper;
import com.KMA.BookingCare.Repository.InteractiveRepository;
import com.KMA.BookingCare.Repository.UserRepository;
import com.KMA.BookingCare.Service.InteractiveService;

@Service
public class InteractiveServiceImpl implements InteractiveService{

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private InteractiveRepository interactiveRepository;
	

	@Override
	public void saveOrUpdate(MessageDto chatMessage) {
		// TODO Auto-generated method stub
//		UserEntity userEntity=userRepository.findOneById(chatMessage.getReceiverId());
//		UserEntity you=userRepository.findOneById(chatMessage.getSenderId());
		InteractiveEntity entity= new InteractiveEntity();
		InteractiveEntity entityOld;
		
//		UserEntity userEntity1=userRepository.findOneById(chatMessage.getSenderId());
//		UserEntity you1=userRepository.findOneById(chatMessage.getReceiverId());
	
		entity.setCreatedDate(new Date());
		entity.setUserId(chatMessage.getReceiverId());
		entity.setYouId(chatMessage.getSenderId());
		
		entity.setStatus(0);
		 entityOld = interactiveRepository.findOneByUserIdAndYouId(entity.getUserId(),entity.getYouId());
		 if(entityOld!=null) {
			 entity.setId(entityOld.getId());
		 }
		 entity.setLastMessage(chatMessage.getContent());
		interactiveRepository.save(entity);
		
		entity.setUserId(chatMessage.getSenderId());
		entity.setYouId(chatMessage.getReceiverId());
//		entity.setYou(you1);
//		entity.setUser(userEntity1);
		entityOld=interactiveRepository.findOneByUserIdAndYouId(entity.getUserId(),entity.getYouId());
		 if(entityOld!=null) {
			 entity.setId(entityOld.getId());
		 }
		 entity.setLastMessage(chatMessage.getContent());
		interactiveRepository.save(entity);
		
	}


	@Override
	public List<InteractiveDto> findAll(MyUser userDetails) {
		List<InteractiveEntity> lstEntity = new ArrayList<>();
		if(userDetails.getRoles().contains("ROLE_ADMIN")) {
			lstEntity= interactiveRepository.findAllByYouId((long) 0);
		}else {
			lstEntity= interactiveRepository.findAllByYouId(userDetails.getId());
		}
		List<InteractiveDto> lstDto = new ArrayList<>();
		for(InteractiveEntity entity: lstEntity) {
			UserEntity userEntity=userRepository.findOneById(entity.getUserId());
			InteractiveDto dto = InteractiveMapper.convertToDto(entity,userEntity);
			lstDto.add(dto);
		}
		return lstDto;
	}

}
