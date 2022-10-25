package com.KMA.BookingCare.ServiceImpl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.KMA.BookingCare.Dto.SpecializedDto;
import com.KMA.BookingCare.Mapper.SpecializedMapper;
import com.KMA.BookingCare.Mapper.UserMapper;
import com.KMA.BookingCare.document.HospitalDocument;
import com.KMA.BookingCare.search.HospitalSearchRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.KMA.BookingCare.Api.form.HandbookForm;
import com.KMA.BookingCare.Api.form.HospitalForm;
import com.KMA.BookingCare.Dto.HospitalDto;
import com.KMA.BookingCare.Dto.MyUser;
import com.KMA.BookingCare.Entity.HandbookEntity;
import com.KMA.BookingCare.Entity.HospitalEntity;
import com.KMA.BookingCare.Entity.SpecializedEntity;
import com.KMA.BookingCare.Mapper.HospitalMapper;
import com.KMA.BookingCare.Repository.HospitalRepository;
import com.KMA.BookingCare.Service.HospitalService;
import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
@Service
public class HospitalServiceImpl implements HospitalService{
	
	@Autowired
	private HospitalRepository hospitalRepository;

	@Autowired
	private Cloudinary cloudinary;

	@Autowired
	private HospitalSearchRepository hospitalSearchRepository;

	@Override
	public List<HospitalDto> findAll() {
		List<HospitalEntity> lstEntity= hospitalRepository.findAll();
		List<HospitalDto> lstDto = new ArrayList<HospitalDto>();
		for(HospitalEntity entity: lstEntity) {
			HospitalDto dto = new HospitalDto();
			dto.setDescription(entity.getDescription());
			dto.setId(entity.getId());
			dto.setLocation(entity.getLocation());
			dto.setName(entity.getName());
			lstDto.add(dto);
		}
		return lstDto;
	}

	@Override
	public void saveHospital(HospitalForm form)  throws ParseException {
		UserDetailsImpl user = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();
		MyUser userDetails = UserMapper.convertToMyUser(user);
		HospitalEntity entity= new HospitalEntity();
		if(form.getId()!=null) {
			entity.setId(form.getId());
			if(form.getImg().getOriginalFilename()==null||form.getImg().getOriginalFilename().equals("")) {
				entity.setImg(form.getImgOld());
			}else {
				try {
					Map result= cloudinary.uploader().upload(form.getImg().getBytes(),
							ObjectUtils.asMap("resource_type","auto"));
					String urlImg=(String) result.get("secure_url");
					entity.setImg(urlImg);
				} catch (Exception e) {
					System.out.println("upload img fail");
				}
			}
		}else {
			try {
				Map result= cloudinary.uploader().upload(form.getImg().getBytes(),
						ObjectUtils.asMap("resource_type","auto"));
				String urlImg=(String) result.get("secure_url");
				entity.setImg(urlImg);
			} catch (Exception e) {
				System.out.println("upload img fail");
			}
		}
		
		entity.setName(form.getName());
		entity.setLocation(form.getLocation());
		entity.setDescription(form.getDescription());
		entity.setLatitude(form.getLatitude());
		entity.setLongitude(form.getLongitude());
		entity.setStatus(1);
		entity = hospitalRepository.save(entity);
		HospitalDocument document = HospitalMapper.convertToDocument(entity);
		hospitalSearchRepository.save(document);
		
	}

	@Override
	public List<HospitalDto> findAllByStatus(Integer status) {
		List<HospitalEntity> lstEntity = hospitalRepository.findAllByStatus(1);
		List<HospitalDto> lstDto = new ArrayList<HospitalDto>();
		for(HospitalEntity entity: lstEntity) {
			HospitalDto dto = HospitalMapper.convertToDto(entity);
			lstDto.add(dto);
		}
		return lstDto;
	}

	@Override
	public List<HospitalDto> findRandomSpecicalized() {
		List<HospitalEntity> lstEntity = hospitalRepository.findRandomSpecicalized(1);
		List<HospitalDto> lstDto = new ArrayList<HospitalDto>();
		for(HospitalEntity entity: lstEntity) {
			HospitalDto dto = HospitalMapper.convertToDto(entity);
			lstDto.add(dto);
		}
		return lstDto;
	}

	@Override
	public List<HospitalDto> findAllByStatus(Integer status, Pageable pageable) {
		List<HospitalEntity> lstEntity = hospitalRepository.findAllByStatus(1,pageable);
		List<HospitalDto> lstDto = new ArrayList<HospitalDto>();
		for(HospitalEntity entity: lstEntity) {
			HospitalDto dto = HospitalMapper.convertToDto(entity);
			lstDto.add(dto);
		}
		return lstDto;
	}

	@Override
	public Page<HospitalDto> findAllByStatusApi(Integer status, Pageable pageable) {
		Page<HospitalDto> page = hospitalRepository.findAllByStatusApi(1,pageable);
		return page;
	}

}
