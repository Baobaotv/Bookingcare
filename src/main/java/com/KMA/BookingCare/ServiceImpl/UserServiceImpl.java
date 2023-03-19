package com.KMA.BookingCare.ServiceImpl;

import java.io.File;
import java.io.IOException;
import java.nio.file.attribute.UserPrincipal;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

import com.KMA.BookingCare.Dto.*;
import com.KMA.BookingCare.document.UserDocument;
import com.KMA.BookingCare.search.UserSearchRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.KMA.BookingCare.Api.form.UpdateCientForm;
import com.KMA.BookingCare.Api.form.UserForm;
import com.KMA.BookingCare.Api.form.searchDoctorForm;
import com.KMA.BookingCare.Entity.HandbookEntity;
import com.KMA.BookingCare.Entity.HospitalEntity;
import com.KMA.BookingCare.Entity.RoleEntity;
import com.KMA.BookingCare.Entity.SpecializedEntity;
import com.KMA.BookingCare.Entity.UserEntity;
import com.KMA.BookingCare.Entity.WorkTimeEntity;
import com.KMA.BookingCare.Mapper.HandbookMapper;
import com.KMA.BookingCare.Mapper.UserMapper;
import com.KMA.BookingCare.Repository.HospitalRepository;
import com.KMA.BookingCare.Repository.MedicalExaminationScheduleRepository;
import com.KMA.BookingCare.Repository.RoleRepository;
import com.KMA.BookingCare.Repository.SpecializedRepository;
import com.KMA.BookingCare.Repository.UserRepository;
import com.KMA.BookingCare.Repository.WorkTimeRepository;
import com.KMA.BookingCare.Service.UserService;
import com.KMA.BookingCare.Service.WorkTimeService;
import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;


@Service
@Transactional
public class UserServiceImpl implements UserService{
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	PasswordEncoder passwordEncoder;
	
	@Autowired
	private WorkTimeService workTimeServiceImpl;
	
	@Autowired
	private HospitalRepository hospitalRepository;
	
	@Autowired
	private SpecializedRepository specializedRepository;
	
	@Autowired
	private Cloudinary cloudinary;
	
	@Autowired
	private MedicalExaminationScheduleRepository medicalRepository;
	
	@Autowired
	private WorkTimeRepository wkRepository;

	@Autowired
	private UserSearchRepository userSearchRepository;
	
	@Override
	public void add(User user,String nameRole) {
		UserEntity userEntity= new UserEntity();
		if(user.getId() != null && !user.getId().equals("") && user.getId() != 0) {
			userEntity.setId(user.getId());
		}
	    userEntity.setEmail(user.getEmail());
	    userEntity.setUsername(user.getUsername());
	    userEntity.setLocation(user.getLocation());
	    userEntity.setSex(user.getSex());
		userEntity.setPhoneNumber(user.getPhone());
	    userEntity.setPassword(passwordEncoder.encode(user.getPassword()));
	
	    if(nameRole.equals("admin")) {
	    	Set<RoleEntity> role= new HashSet<RoleEntity>(roleRepository.findAll());
	    	userEntity.setRoles(role);
	    }
	    else if (nameRole.equals("user")) {
	    	Set<RoleEntity> role= new HashSet<RoleEntity>(roleRepository.findByName("ROLE_USER"));
	    	userEntity.setRoles(role);
		}
	    else if (nameRole.equals("doctor")) {
	    	Set<RoleEntity> role= new HashSet<RoleEntity>(roleRepository.findByName("ROLE_DOCTOR"));
	    	userEntity.setRoles(role);
		}
		userEntity = userRepository.save(userEntity);
		UserDocument document = UserMapper.convertToDocument(userEntity);
		userSearchRepository.save(document);
	}

	@Override
	public void update(User user) {
		UserEntity userEntity = new UserEntity();
		UserDetails userDetails= (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String password= userDetails.getPassword();
		userEntity.setPassword(passwordEncoder.encode(user.getPassword()));
		userRepository.save(userEntity);
	}

	@Override
	public void delete(Long id) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public List<User> getAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public User findByUsername(String username) {
		User user = new User();
		UserEntity userEntity = userRepository.findByUsername(username);
		user.setId(userEntity.getId());
		user.setName(userEntity.getFullName());
		user.setUsername(userEntity.getUsername());
		user.setPassword((userEntity.getPassword()));
		user.setEmail(userEntity.getEmail());
		user.setImg(userEntity.getImg());
		Set<RoleEntity> set = userEntity.getRoles();
		Set<Role> roles = new HashSet<Role>();
		for (RoleEntity roleEn : userEntity.getRoles()) {
			Role role = new Role();
			role.setId(roleEn.getId());
			role.setName(roleEn.getName());
			roles.add(role);
		}
		user.setRoles(roles);
		return user;
		
	}

	@Override
	public void saveDoctor(UserForm form) throws IllegalStateException, IOException {
		UserEntity entity = new UserEntity();
		if(form.getId()!=null && !form.getId().equals("")) {
			entity= userRepository.findOneById(form.getId());
			if(form.getImg().getOriginalFilename()!=null&&!form.getImg().getOriginalFilename().equals("")) {
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
			entity.setPassword(passwordEncoder.encode(form.getPassword()));
			entity.setUsername(form.getUsername());
			entity.setStatus(1);
			Set<RoleEntity> role= new HashSet<RoleEntity>(roleRepository.findByName(form.getRoleName()));
			entity.setRoles(role);
			try {
				Map result= cloudinary.uploader().upload(form.getImg().getBytes(),
						ObjectUtils.asMap("resource_type","auto"));
				String urlImg=(String) result.get("secure_url");
				entity.setImg(urlImg);
			} catch (Exception e) {
				System.out.println("upload img fail");
			}
		}
		entity.setShortDescription(form.getShortDescription());
		entity.setDescription(form.getDescription());
		entity.setEmail(form.getEmail());
		entity.setFullName(form.getFullname());
		entity.setLocation(form.getLocation());
		entity.setPhoneNumber(form.getPhoneNumber());
		entity.setYearOfBirth(form.getYearOfBirth());
		entity.setSex(form.getSex());
		HospitalEntity hospital =hospitalRepository.findOneById(form.getHospitalId());
		entity.setHospital(hospital);
		SpecializedEntity specialized= specializedRepository.findOneById(form.getSpecializedId());
		entity.setSpecialized(specialized);
		if(form.getWorkTimeId()!=null) {
			Set<WorkTimeEntity> lstWorkTimeEntities=new HashSet<WorkTimeEntity>(workTimeServiceImpl.findByListId(form.getWorkTimeId())) ;
			if(lstWorkTimeEntities!=null) {
				entity.setWorkTimeEntity(lstWorkTimeEntities);
			}
		}
		entity = userRepository.save(entity);
		UserDocument document = UserMapper.convertToDocument(entity);
		userSearchRepository.save(document);
	}

	@Override
	public List<User> findAll() {
		List<UserEntity> lstEntity= userRepository.findAll();
		List<User> lstDto = new ArrayList<User>();
		for(UserEntity entity : lstEntity) {
			User dto = new User();
			dto.setId(entity.getId());
			dto.setDescription(entity.getDescription());
			dto.setImg(entity.getImg());
			dto.setName(entity.getFullName());
			dto.setLocation(entity.getLocation());
			dto.setPhone(entity.getPhoneNumber());
			dto.setSex(entity.getSex());
			if(entity.getSpecialized()!=null) {
				dto.setSpecializedId(entity.getSpecialized().getId());
			}
			else {
//				dto.setSpecializedId("null");
			}
			Set<RoleEntity> lstRole= entity.getRoles();
			StringBuilder roleName= new StringBuilder();
			for(RoleEntity roleEntity: lstRole) {
				roleName.append(roleEntity.getName());
				roleName.append(", ");
			}
			String strRoleName= roleName.toString();
			strRoleName= (String) strRoleName.subSequence(0,(strRoleName.length()-2));
			dto.setRole(strRoleName);
			dto.setUsername(entity.getUsername());
			lstDto.add(dto);
		}
		return lstDto;
	}

	@Override
	public List<User> findAllByStatus(Integer status,Pageable pageable) {
		List<UserEntity> lstEntity= userRepository.findAllByStatus(status,pageable);
		List<User> lstDto = new ArrayList<User>();
		for(UserEntity entity : lstEntity) {
			User dto = new User();
			dto.setId(entity.getId());
			dto.setDescription(entity.getDescription());
			dto.setImg(entity.getImg());
			dto.setName(entity.getFullName());
			dto.setLocation(entity.getLocation());
			dto.setPhone(entity.getPhoneNumber());
			dto.setSex(entity.getSex());
			if(entity.getSpecialized()!=null) {
				dto.setSpecializedId(entity.getSpecialized().getId());
			}else {
				
			}
			Set<RoleEntity> lstRole= entity.getRoles();
			StringBuilder roleName= new StringBuilder();
			for(RoleEntity roleEntity: lstRole) {
				roleName.append(roleEntity.getName());
				roleName.append(", ");
			}
			String strRoleName= roleName.toString();
			strRoleName= (String) strRoleName.subSequence(0,(strRoleName.length()-2));
			dto.setRole(strRoleName);
			dto.setUsername(entity.getUsername());
			lstDto.add(dto);
		}
		return lstDto;
	}

	@Override
	public void updateUserByStatus(List<String> ids) {
		userRepository.updateStatus(ids);
		
	}

	@Override
	public User findOneById(Long id) {
		UserEntity entity= userRepository.findOneById(id);
		User dto= UserMapper.convertToDto(entity);
		return dto;
	}

	@Override
	public List<User> findAllBySpecializedIdAndStatus(Long id, Integer status) {
		List<UserEntity> lstEntity= userRepository.findAllBySpecializedIdAndStatus(id, status);
		List<User> lstDto= new ArrayList<User>();
		for(UserEntity entity: lstEntity) {
			User dto = UserMapper.convertToDto(entity);
			lstDto.add(dto);
		}
		return lstDto;
	}



	@Override
	public List<User> findAllByMedical(String date, Long specialized, Integer status) {
		List<UserEntity> lstEntity= userRepository.findAllByMedical(date,specialized, status,1); //lấy tất cả bác sĩ có lịch khám theo date, chuyên khoa specialized
		for(UserEntity entity: lstEntity) {
			List<Long> lstWktId= medicalRepository.findAllWorkTimeIdByDateAndDoctorIdAndStatus(entity.getId(), date); //lấy danh sách lịch khám từng bác sĩ
			Set<WorkTimeEntity> lstWk = new HashSet<WorkTimeEntity>(entity.getWorkTimeEntity());
			for(WorkTimeEntity wk: entity.getWorkTimeEntity()) {
				if(lstWktId.contains(wk.getId())) {
					lstWk.remove(wk);
				}
			}
			entity.setWorkTimeEntity(lstWk);
		}
		List<User> lstDto= new ArrayList<User>();
		for(UserEntity entity: lstEntity) {
			User dto = UserMapper.convertToDto(entity);
			lstDto.add(dto);
		}
		return lstDto;
	}

	@Override
	public List<User>  findAllBySpecialized(String date, Long specialized, Integer status) {
		List<UserEntity> lstEntity= userRepository.findAllBySpecialized(date, specialized, status,1);
		List<User> lstDto= new ArrayList<User>();
		for(UserEntity entity: lstEntity) {
			User dto = UserMapper.convertToDto(entity);
			lstDto.add(dto);
		}
		return lstDto;
	}

	@Override
	public List<User> findAllDoctorOfSpecialized( Long specialized, Integer status) {
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
		String date = formatter.format(new Date());
		List<User> lstDto1=  findAllBySpecialized(date,  specialized,  status) ; //lấy danh sách những bác sĩ không có lịch khám trong ngày
		List<User> lstDto2=  findAllByMedical(date,specialized, status);
		lstDto1.addAll(lstDto2);
		System.out.println("test");
		return lstDto1;
	}

	@Override
	public Page<User> findAllDoctorOfSpecializedApi(Long specialized, Integer status, Pageable pageable) {
		return  userRepository.findAllBySpecializedApi(specialized, status, pageable);
	}

	@Override
	public List<User> findAllDoctorOfHospital( Long hospitalId, Integer status) {
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
		String date = formatter.format(new Date());
		List<UserEntity> lstEntity1=  userRepository.findAllByHospital(date,  hospitalId,  status,1) ; //lấy danh sách những bác sĩ không có lịch khám trong ngày
		List<UserEntity> lstEntity2=  userRepository.findAllByMedicalAndHospital(date,hospitalId, status,1);
		for(UserEntity entity: lstEntity2) {
			List<Long> lstWktId= medicalRepository.findAllWorkTimeIdByDateAndDoctorIdAndStatus(entity.getId(), date); //lấy danh sách lịch khám từng bác sĩ
			Set<WorkTimeEntity> lstWk = new HashSet<WorkTimeEntity>(entity.getWorkTimeEntity());
			for(WorkTimeEntity wk: entity.getWorkTimeEntity()) {
				if(lstWktId.contains(wk.getId())) {
					lstWk.remove(wk);
				}
			}
			entity.setWorkTimeEntity(lstWk);
		}
		lstEntity1.addAll(lstEntity2);
		List<User> lstDto= new ArrayList<User>();
		for(UserEntity entity: lstEntity1) {
			User dto = UserMapper.convertToDto(entity);
			lstDto.add(dto);
		}
		return lstDto;
	}

	@Override
	public Page<User> findAllDoctorOfHospitalApi(Long hospitalId, Integer status, Pageable pageable) {
		return userRepository.findAllDoctorByHospitalAndStatus(hospitalId, status, pageable);
	}

	@Override
	public void updateClient(UpdateCientForm form) {
		UserDetailsImpl user = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();
		MyUser userDetails = UserMapper.convertToMyUser(user);
		UserEntity entity = userRepository.findOneById(userDetails.getId());
		entity.setFullName(form.getFullName());
		entity.setEmail(form.getEmail());
		entity.setPhoneNumber(form.getPhone());
		if(form.getPasswod() != null && !form.getPasswod().equals("")) {
			entity.setPassword(passwordEncoder.encode(form.getPasswod()));
		}
		userRepository.save(entity);
		
	}

	@Override
	public List<User> findAllDoctor() {
		List<UserEntity> lstEntity = userRepository.findAllDoctor();
		List<User> lstDto = new ArrayList<User>();
		for(UserEntity entity : lstEntity) {
			User dto = UserMapper.convertToDto(entity);
			lstDto.add(dto);
		}
		return lstDto;
	}

	@Override
	public List<User> searchDoctor(searchDoctorForm form) {
		String name= form.getName();
		Long specializedId= form.getSpecializedId();
		Long hospitalId= form.getHospitalId();
		StringBuilder sql = new StringBuilder("1");
		if(name!=null||hospitalId!=null||specializedId!=null) {
//			sql.append(" WHERE 1");
			if(name!=null) {
				sql.append(" AND ");
				sql.append(" full_name like CONCAT('%',");
				sql.append(name);
				sql.append(",'%')");
			}if(specializedId!=null) {
				sql.append(" AND ");
				sql.append("specialized_id=");
				sql.append(specializedId);
			}if(hospitalId!=null) {
				sql.append(" AND ");
				sql.append(" hospital_id =");
				sql.append(hospitalId);
			}
		}else {
			sql.append(" 1");
		}
		String sqlString = sql.toString();
		List<UserEntity> lstEntities= userRepository.searchAllDoctor(sqlString);
		List<User> lstDto = new ArrayList<User>();
		for(UserEntity entity: lstEntities) {
			User dto = UserMapper.convertToDto(entity);
			lstDto.add(dto);
		}
		return lstDto;
	}

	@Override
	public User findOneDoctorAndWorktime(Long id) {
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
		String date = formatter.format(new Date());
		UserEntity entity = userRepository.findOneById(id);
		List<WorkTimeEntity> lstWkEntity= wkRepository.findByDateAndDoctorId(date, id);
		Set<WorkTimeEntity> setWkEntity= new HashSet<WorkTimeEntity>(lstWkEntity);
		entity.setWorkTimeEntity(setWkEntity);
		User dto = UserMapper.convertToDto(entity);
		return dto;
	}

	@Override
	public String findPeerIdById(Long id) {
		// TODO Auto-generated method stub
		return userRepository.findPeerIdById(id);
	}

	@Override
	public Integer updatePeerId(String peerId, Long id) {
		// TODO Auto-generated method stub
		return userRepository.updatePeerId(peerId, id);
	}

	@Override
	public List<User> findRandomDoctor() {
		List<UserEntity> lstEntity = userRepository.findRandomDoctor();
		List<User> lstDto = new ArrayList<User>();
		for(UserEntity entity :lstEntity) {
			User dto =  UserMapper.convertToDto(entity);
			lstDto.add(dto);
		}
		return lstDto;
	}

	@Override
	public List<User> getDoctorOnline() {
		Pageable pageable = PageRequest.of(0, 4);
		List<User> userPage = userRepository.getDoctorOnline(pageable);
		return userPage;
	}

	@Override
	public List<User> getFeaturedDoctor() {
		List<UserFeaturedDto> userFeaturedDtoList = userRepository.getFeaturedUser();
		List<Long> ids = userFeaturedDtoList.stream().map(UserFeaturedDto::getId).collect(Collectors.toList());
		return userRepository.findAllDoctorByIds(ids);
	}

	@Override
	public List<User> searchDoctorAndPageable(searchDoctorForm form, String roleUser, Pageable page) {

		if(form.getName()==null||form.getName().equals("")) {
			form.setName("");
		}

		List<Integer> roleIds;
		if ("ADMIN".equals(roleUser)) roleIds = List.of(1, 2);
		else roleIds = List.of(2);
		List<UserEntity> lstEntities= userRepository.searchHandbookAndPageable(form.getName(),form.getSpecializedId(),form.getHospitalId(),page, roleIds);
		List<User> lstDto = new ArrayList<User>();
		for(UserEntity entity: lstEntities) {
			User dto = UserMapper.convertToDto(entity);
			lstDto.add(dto);
		}
		return lstDto;
	}

	@Override
	public Page<User> findAllDoctor(Pageable pageable) {
		return userRepository.findAllDoctor(pageable);
	}

	@Transactional
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		UserEntity user = userRepository.findByUsername(username);
		return UserDetailsImpl.build(user);
	}

	


}
