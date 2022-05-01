package com.KMA.BookingCare.Entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "medicalExaminationSchedule")
public class MedicalExaminationScheduleEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "date")
	private String date;

	@Column(name = "workTimeId")
	private Long workTimeID;

	@ManyToOne
	@JoinColumn(name = "doctorId")
	private UserEntity doctor;

	private String nameScheduler;
	private String phoneScheduer;
	private String namePatient;
	private String sex;
	private String phonePatient;
	private String location;
	private String reason;
	private String yearOfBirth;
	private Integer status;// 0 đã xoá, 1 chưa xử lý(chưa khám), 2 đã khám
	private String type; //on khám online, off là khám offline
	private String hospitalName;
	
	
	public String getHospitalName() {
		return hospitalName;
	}

	public void setHospitalName(String hospitalName) {
		this.hospitalName = hospitalName;
	}

	@ManyToOne
	@JoinColumn(name = "userId")
	private UserEntity user;
	
	
	

	public UserEntity getUser() {
		return user;
	}

	public void setUser(UserEntity user) {
		this.user = user;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getYearOfBirth() {
		return yearOfBirth;
	}

	public void setYearOfBirth(String yearOfBirth) {
		this.yearOfBirth = yearOfBirth;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public Long getWorkTimeID() {
		return workTimeID;
	}

	public void setWorkTimeID(Long workTimeID) {
		this.workTimeID = workTimeID;
	}

	public UserEntity getDoctor() {
		return doctor;
	}

	public void setDoctor(UserEntity doctor) {
		this.doctor = doctor;
	}

	public String getNameScheduler() {
		return nameScheduler;
	}

	public void setNameScheduler(String nameScheduler) {
		this.nameScheduler = nameScheduler;
	}

	public String getPhoneScheduer() {
		return phoneScheduer;
	}

	public void setPhoneScheduer(String phoneScheduer) {
		this.phoneScheduer = phoneScheduer;
	}

	public String getNamePatient() {
		return namePatient;
	}

	public void setNamePatient(String namePatient) {
		this.namePatient = namePatient;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getPhonePatient() {
		return phonePatient;
	}

	public void setPhonePatient(String phonePatient) {
		this.phonePatient = phonePatient;
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

}
