package com.KMA.BookingCare.ServiceImpl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

import com.KMA.BookingCare.Dto.*;
import com.KMA.BookingCare.Entity.UserEntity;
import com.KMA.BookingCare.Mapper.SpecializedMapper;
import com.KMA.BookingCare.Mapper.UserMapper;
import com.KMA.BookingCare.Repository.CustomRepository.CustomHospitalRepository;
import com.KMA.BookingCare.Repository.UserRepository;
import com.KMA.BookingCare.Service.UserService;
import com.KMA.BookingCare.document.HospitalDocument;
import com.KMA.BookingCare.search.HospitalSearchRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.KMA.BookingCare.Api.form.HandbookForm;
import com.KMA.BookingCare.Api.form.HospitalForm;
import com.KMA.BookingCare.Entity.HandbookEntity;
import com.KMA.BookingCare.Entity.HospitalEntity;
import com.KMA.BookingCare.Entity.SpecializedEntity;
import com.KMA.BookingCare.Mapper.HospitalMapper;
import com.KMA.BookingCare.Repository.HospitalRepository;
import com.KMA.BookingCare.Service.HospitalService;
import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;

import javax.persistence.EntityManager;
import javax.persistence.Query;

@Service
public class HospitalServiceImpl implements HospitalService{
	
	@Autowired
	private HospitalRepository hospitalRepository;

	@Autowired
	private Cloudinary cloudinary;

	@Autowired
	private HospitalSearchRepository hospitalSearchRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private UserService userService;

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
		entity.setLatitude(form.getLongitude());
		entity.setLongitude(form.getLatitude());
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
	public HospitalEntity findOneById(Long id) {
		HospitalEntity hospitalEntity = hospitalRepository.findOneById(id);
		hospitalEntity.setLstuser(Collections.emptyList());
		return hospitalEntity;
	}

	@Override
	public List<HospitalDto> getFeaturedHospital() {
		List<HospitalFeaturedDto> hospitalFeaturedDtos = hospitalRepository.getFeaturedHospital();
		List<Long> hospitalIds = hospitalFeaturedDtos.stream().map(HospitalFeaturedDto::getId).collect(Collectors.toList());
		List<HospitalDto> results = hospitalRepository.findAllByIds(hospitalIds);
		return results;
	}

	@Override
	public List<SearchFullTextDto> searchAllByFullText(String query) {
		List<HospitalEntity> hospitalEntities = hospitalRepository.searchAllByFullText(query);
		return hospitalEntities.stream()
				.map(e -> SearchFullTextDto.builder()
						.id(e.getId())
						.title(e.getName())
						.description(e.getDescription())
						.img(e.getImg())
						.tableName("HOSPITAL")
						.build())
				.collect(Collectors.toList());
	}

	@Override
	public Page<HospitalDto> searchByNameAndStatus(String query, Pageable pageable) {
		Page<HospitalDto> page = hospitalRepository.findAllByNameIsLikeIgnoreCaseAndStatus(query,1, pageable);
		return page;
	}

	@Override
	public boolean isExistItemRelationWithSpecialIsUsing(List<String> ids) {
		Long totalUser = userRepository.existsByHospital(ids.stream().map(Long::parseLong).collect(Collectors.toList()));
		return !Objects.equals(0L, totalUser);
	}

	@Override
	public void updateByStatusAndIds(List<String> ids, Integer status) {
		hospitalRepository.updateStatusByIds(ids.stream()
						.map(Long::parseLong)
						.collect(Collectors.toList()),
				status);
	}

	@Override
	public void deleteHospitals(List<String> ids) {
		List<Long> hospitalIds = ids.stream().map(Long::parseLong).collect(Collectors.toList());
		List<UserEntity> userEntities = userRepository.findAllByHospitalIds(hospitalIds);
		List<String> userIds = userEntities.stream().map(e -> String.valueOf(e.getId())).distinct().collect(Collectors.toList());
		userService.deleteUser(userIds);
		hospitalRepository.deleteAllById(hospitalIds);
	}

	@Override
	public List<HospitalDto> findAllByStatus(Integer status, Pageable pageable) {
		List<HospitalEntity> lstEntity = hospitalRepository.findAllByStatus(status,pageable);
		List<HospitalDto> lstDto = new ArrayList<>();
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
