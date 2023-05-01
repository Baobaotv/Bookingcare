package com.KMA.BookingCare.Entity;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;



import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;



@Entity
@Table(name = "user")
@Data
public class UserEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "fullName")
	private String fullName;
	
	private String sex;
	
	@Column(name = "phoneNumber")
	private String phoneNumber;
	
	private String location;
	
	private String description;
	
	private String shortDescription;
	
	private String username;
	
	private String password;
	
	private String email;
	
	private String img;
	
	
	private String yearOfBirth;
	
	private Integer status;
	
	private String peerId;

	private Long examinationPrice;
	
	public String getShortDescription() {
		return shortDescription;
	}


	public void setShortDescription(String shortDescription) {
		this.shortDescription = shortDescription;
	}


	public String getPeerId() {
		return peerId;
	}


	public void setPeerId(String peerId) {
		this.peerId = peerId;
	}


	public List<HandbookEntity> getLstHandbook() {
		return lstHandbook;
	}


	public void setLstHandbook(List<HandbookEntity> lstHandbook) {
		this.lstHandbook = lstHandbook;
	}


	@ManyToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
	@JoinTable(name = "user_role",
	joinColumns = @JoinColumn(name="user_id"),
	inverseJoinColumns = @JoinColumn(name="role_id"))
	private Set<RoleEntity> roles;
	
	@ManyToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
	@JoinTable(name = "user_wordTime",
	joinColumns = @JoinColumn(name="user_id"),
	inverseJoinColumns = @JoinColumn(name="workTime_id"))
	private Set<WorkTimeEntity> workTimeEntity;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "specializedId")
	private SpecializedEntity specialized;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "hospitalId")
	private HospitalEntity hospital;
	
	
	@OneToMany(mappedBy = "user")
	private List<HandbookEntity> lstHandbook=new ArrayList<HandbookEntity>();
	

	
	@OneToMany(mappedBy = "doctor")
	private List<MedicalExaminationScheduleEntity> medical=new ArrayList<MedicalExaminationScheduleEntity>();
	
	@OneToMany(mappedBy = "user")
	private List<MedicalExaminationScheduleEntity> medicalUser=new ArrayList<MedicalExaminationScheduleEntity>();
	
	@OneToMany(mappedBy = "user")
	private List<CommentEntity> comment =new ArrayList<CommentEntity>();
	

	public List<MedicalExaminationScheduleEntity> getMedicalUser() {
		return medicalUser;
	}


	public void setMedicalUser(List<MedicalExaminationScheduleEntity> medicalUser) {
		this.medicalUser = medicalUser;
	}


	public List<MedicalExaminationScheduleEntity> getMedical() {
		return medical;
	}


	public void setMedical(List<MedicalExaminationScheduleEntity> medical) {
		this.medical = medical;
	}


	public Integer getStatus() {
		return status;
	}


	public void setStatus(Integer status) {
		this.status = status;
	}


	public Set<RoleEntity> getRoles() {
		return roles;
	}


	public void setRoles(Set<RoleEntity> roles) {
		this.roles = roles;
	}

	
	public String getYearOfBirth() {
		return yearOfBirth;
	}

	public void setYearOfBirth(String yearOfBirth) {
		this.yearOfBirth = yearOfBirth;
	}


	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getImg() {
		return img;
	}

	public void setImg(String img) {
		this.img = img;
	}

	public Set<WorkTimeEntity> getWorkTimeEntity() {
		return workTimeEntity;
	}

	public void setWorkTimeEntity(Set<WorkTimeEntity> workTimeEntity) {
		this.workTimeEntity = workTimeEntity;
	}


	public UserEntity() {
		super();
	}
	
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}


	public SpecializedEntity getSpecialized() {
		return specialized;
	}


	public void setSpecialized(SpecializedEntity specialized) {
		this.specialized = specialized;
	}


	public HospitalEntity getHospital() {
		return hospital;
	}


	public void setHospital(HospitalEntity hospital) {
		this.hospital = hospital;
	}
	
}
