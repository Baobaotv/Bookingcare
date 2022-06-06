package com.KMA.BookingCare.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.KMA.BookingCare.Entity.MedicalExaminationScheduleEntity;

public interface MedicalExaminationScheduleRepository extends JpaRepository<MedicalExaminationScheduleEntity, Long> {
	@Query(value = "SELECT work_time_id FROM bookingcare.medical_examination_schedule where doctor_id=:id and date=:date and status =1",nativeQuery = true)
	List<Long> findAllWorkTimeIdByDateAndDoctorIdAndStatus(@Param("id") Long id,@Param("date") String date);
	List<MedicalExaminationScheduleEntity> findAllByStatus(Integer status);
	List<MedicalExaminationScheduleEntity> findAllByDoctorIdAndStatus(Long doctorID,Integer Status);
	List<MedicalExaminationScheduleEntity> findAllByUserIdAndStatus(Long userId, Integer status);
	
	@Transactional
	@Modifying
	@Query(value = "UPDATE medical_examination_schedule  SET status = :status WHERE id in :ids", nativeQuery = true)
	Integer updateByStatus(@Param("status") Integer status,@Param("ids") List<String> ids);

	Boolean existsByDateAndAndDoctorIdAndWorkTimeID(String date, Long docterId, Long workTimeId);
}
