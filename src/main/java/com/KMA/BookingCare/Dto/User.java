package com.KMA.BookingCare.Dto;

import com.KMA.BookingCare.Entity.UserEntity;
import com.KMA.BookingCare.Entity.WorkTimeEntity;
import com.KMA.BookingCare.Mapper.WorkTimeMapper;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

public class User {
	
	private Long id;
	private String name;
	private String sex;
	@NotEmpty(message = "Thiếu số điện thoại")
	@Size(max = 12)
	private String phone;
	private String img;
	private String description;
	private Long hospitalId;
	private String location;
	private String yearOfBirth;
	private Long specializedId;
	private String reason;
	@Email(message = "Email không hợp lệ")
	private String email;
	@NotEmpty(message = "Thiếu username")
	private String username;
	@NotEmpty(message = "Thiếu password")
    @Min(value = 6, message = "Password phải từ 6 kí tự trở lên")
	private String password;
	private Set<Role> roles;
	private String role;
	private List<WorkTimeDto> lstWorkTime;
	private String specializedName;
	private String shortDescription;
	private String hospitalName;
	private String hospitalLocation;
	public String getHospitalName() {
		return hospitalName;
	}

	public void setHospitalName(String hospitalName) {
		this.hospitalName = hospitalName;
	}

	public String getHospitalLocation() {
		return hospitalLocation;
	}

	public void setHospitalLocation(String hospitalLocation) {
		this.hospitalLocation = hospitalLocation;
	}

	public String getShortDescription() {
		return shortDescription;
	}

	public void setShortDescription(String shortDescription) {
		this.shortDescription = shortDescription;
	}

	public String getSpecializedName() {
		return specializedName;
	}

	public void setSpecializedName(String specializedName) {
		this.specializedName = specializedName;
	}

	public List<WorkTimeDto> getLstWorkTime() {
		return lstWorkTime;
	}

	public void setLstWorkTime(List<WorkTimeDto> lstWorkTime) {
		this.lstWorkTime = lstWorkTime;
	}

	public User() {
		super();
	}
	
	public String getImg() {
		return img;
	}
	public void setImg(String img) {
		this.img = img;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}

	public Long getHospitalId() {
		return hospitalId;
	}

	public void setHospitalId(Long hospitalId) {
		this.hospitalId = hospitalId;
	}

	public String getYearOfBirth() {
		return yearOfBirth;
	}
	public void setYearOfBirth(String yearOfBirth) {
		this.yearOfBirth = yearOfBirth;
	}

	public Long getSpecializedId() {
		return specializedId;
	}

	public void setSpecializedId(Long specializedId) {
		this.specializedId = specializedId;
	}

	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	public Set<Role> getRoles() {
		return roles;
	}
	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}

	public User(UserEntity entity) {
		this.setId(entity.getId());
		this.setImg(entity.getImg());
		this.setName(entity.getFullName());
		this.setDescription(entity.getDescription());
		this.setShortDescription(entity.getShortDescription());
		this.setSex(entity.getSex());
		this.setPhone(entity.getPhoneNumber());
		this.setLocation(entity.getLocation());
		if(entity.getHospital()!=null) {
			this.setHospitalId(entity.getHospital().getId());
		}
		this.setEmail(entity.getEmail());
		this.setUsername(entity.getUsername());
		this.setYearOfBirth(entity.getYearOfBirth());
		if(entity.getSpecialized()!=null) {
			this.setSpecializedId(entity.getSpecialized().getId());
			this.setSpecializedName(entity.getSpecialized().getName());
		}
		if(entity.getHospital()!=null) {
			this.setHospitalName(entity.getHospital().getName());
			this.setHospitalLocation(entity.getHospital().getLocation());
		}
		if(entity.getWorkTimeEntity()!=null) {
			Set<WorkTimeEntity> wkEntityLst= entity.getWorkTimeEntity();
			Set<WorkTimeDto> wkDtoLst= new HashSet<WorkTimeDto>();
			for(WorkTimeEntity wkEntity: wkEntityLst) {
				WorkTimeDto wkDto = WorkTimeMapper.convertToDto(wkEntity);
				wkDtoLst.add(wkDto);
			}
			List<WorkTimeDto> targetList = new ArrayList<>(wkDtoLst);
			this.setLstWorkTime( targetList);
		}
	}
}
