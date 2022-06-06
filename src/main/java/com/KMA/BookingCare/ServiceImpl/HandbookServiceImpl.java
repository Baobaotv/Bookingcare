package com.KMA.BookingCare.ServiceImpl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.KMA.BookingCare.Api.form.HandbookForm;
import com.KMA.BookingCare.Api.form.searchHandbookForm;
import com.KMA.BookingCare.Dto.HandbookDto;
import com.KMA.BookingCare.Dto.MyUser;
import com.KMA.BookingCare.Dto.User;
import com.KMA.BookingCare.Entity.HandbookEntity;
import com.KMA.BookingCare.Entity.SpecializedEntity;
import com.KMA.BookingCare.Entity.UserEntity;
import com.KMA.BookingCare.Mapper.HandbookMapper;
import com.KMA.BookingCare.Mapper.UserMapper;
import com.KMA.BookingCare.Repository.HandbookRepository;
import com.KMA.BookingCare.Repository.SpecializedRepository;
import com.KMA.BookingCare.Repository.UserRepository;
import com.KMA.BookingCare.Service.HandbookService;
import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;

@Service
public class HandbookServiceImpl implements HandbookService{
	
	@Autowired
	private HandbookRepository handbookRepository;
	@Autowired
	private SpecializedRepository specializedRepository;
	
	@Autowired
	private UserRepository UserRepository;
	
	@Autowired
	private Cloudinary cloudinary;
	
	@Override
	public void saveHandbook(HandbookForm form) throws ParseException {
//		UserDetails userDetails= (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		MyUser userDetails = (MyUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		HandbookEntity entity= new HandbookEntity();
		if(form.getId()!=null) {
			SimpleDateFormat datFormate = new SimpleDateFormat("dd/MM/yyyy");
			entity.setId(form.getId());
			entity.setCreatedDate(datFormate.parse(form.getCreatedDate()));
			entity.setCreatedBy(form.getCreatedBy());
		}else {
			entity.setCreatedBy(userDetails.getUsername());
			entity.setCreatedDate(new Date());
		}
		try {
			Map result= cloudinary.uploader().upload(form.getImg().getBytes(),
					ObjectUtils.asMap("resource_type","auto"));
			String urlImg=(String) result.get("secure_url");
			entity.setImg(urlImg);
		} catch (Exception e) {
			System.out.println("upload img fail");
		}
		UserEntity userEntity = UserRepository.findOneById(userDetails.getId());
		entity.setUser(userEntity);
		entity.setContent(form.getContent());
		entity.setDescription(form.getDescription());
		SpecializedEntity specializedEntity= specializedRepository.findOneById(form.getSpecializedId());
		entity.setSpecialized(specializedEntity);
		entity.setStatus(1);
		entity.setTitle(form.getTitle());
		entity.setModifiedBy(userDetails.getUsername());
		entity.setModifiedDate(new Date());
		handbookRepository.save(entity);
		
	}

	@Override
	public List<HandbookDto> findAllByStatus(Integer status) {
		List<HandbookEntity> lstEntity= handbookRepository.findAllByStatus(status);
		List<HandbookDto> lstDto = new ArrayList<HandbookDto>();
		for(HandbookEntity entity:lstEntity) {
			HandbookDto dto =HandbookMapper.covertToDto(entity);
			lstDto.add(dto);
		}
		return lstDto;
	}
	
	@Override
	public Page<HandbookDto> findAllByStatusPageable(Integer status, Pageable pageable) {
		Page<HandbookDto> dtos= handbookRepository.findAllByStatus(status,pageable);
		return dtos;
	}

	@Override
	public void updateHandbookByStatus(List<String> ids) {
		handbookRepository.updateByStatus(ids);
		
	}

	@Override
	public void update(HandbookForm form) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public HandbookDto findOneById(Long id) {
		HandbookEntity entity= handbookRepository.findOneById(id);
		HandbookDto dto= HandbookMapper.covertToDto(entity);
		return dto;
	}

	@Override
	public HandbookDto findOneByIdApi(Long id) {
		HandbookDto dto= handbookRepository.findOneByIdApi(id);
		return dto;
	}

	@Override
	public List<HandbookDto> findAllByStatusAndUserId(Integer status, Long id,Pageable pageable) {
		List<HandbookEntity> lstEntity= handbookRepository.findAllByStatusAndUserId(status,id,pageable);
		List<HandbookDto> lstDto = new ArrayList<HandbookDto>();
		for(HandbookEntity entity:lstEntity) {
			HandbookDto dto =HandbookMapper.covertToDto(entity);
			lstDto.add(dto);
		}
		return lstDto;
	}

	@Override
	public List<HandbookDto> searchHandbook(searchHandbookForm form) {
		List<HandbookEntity> lstEntity = new ArrayList<HandbookEntity>();
		if(form.getTitle()!=null&&!form.getTitle().equals("")) {
			if(form.getSpecializedId()!=null&&!form.getSpecializedId().equals("")) {
			 lstEntity = handbookRepository.findAllByTitleAndSpecializedId(form.getTitle(), form.getSpecializedId());
			}else {
			lstEntity = handbookRepository.findAllByTitleAndStatus(form.getTitle());
			}
		}else {
			if(form.getSpecializedId()!=null&&!form.getSpecializedId().equals("")) {
			 lstEntity = handbookRepository.findAllBySpecializedIdAndStatus(form.getSpecializedId(), 1);
			}else {
			 lstEntity = handbookRepository.findAllByStatus(1);
			}
		}
		List<HandbookDto> lstDto = new ArrayList<HandbookDto>();
		for(HandbookEntity entity  : lstEntity) {
			HandbookDto dto = HandbookMapper.covertToDto(entity);
			lstDto.add(dto);
		}
		return lstDto;
	}

	@Override
	public List<HandbookDto> findRandomHandbook() {
		List<HandbookEntity> lstEntity= handbookRepository.findRandomHandbook();
		List<HandbookDto> lstDto = new ArrayList<HandbookDto>();
		for(HandbookEntity entity:lstEntity) {
			HandbookDto dto =HandbookMapper.covertToDto(entity);
			lstDto.add(dto);
		}
		return lstDto;
	}

	@Override
	public List<HandbookDto> searchHandbookAndPageable(searchHandbookForm form,Long userId, Pageable page) {

		if(form.getTitle()==null||form.getTitle().equals("")) {
			form.setTitle("");
		}
		List<HandbookEntity> lstEntities= handbookRepository.searchHandbookAndPageable(form.getTitle(),form.getSpecializedId(),userId,page);
		List<HandbookDto> lstDto = new ArrayList<HandbookDto>();
		for(HandbookEntity entity: lstEntities) {
			HandbookDto dto = HandbookMapper.covertToDto(entity);
			lstDto.add(dto);
		}
		return lstDto;
	}

	@Override
	public Page<HandbookDto> searchHandbookAndPageableapi(String title, Long specialzed,String userId, Pageable pageable) {
		Page<HandbookDto> page= handbookRepository.searchHandbookAndPageableApi(title,specialzed,userId,pageable);
		return page;
	}

	

}
