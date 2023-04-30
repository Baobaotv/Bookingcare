package com.KMA.BookingCare.Repository;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.KMA.BookingCare.Entity.MedicalExaminationScheduleEntity;

public interface MedicalExaminationScheduleRepository extends JpaRepository<MedicalExaminationScheduleEntity, Long> {
	@Query(value = "SELECT work_time_id FROM medical_examination_schedule where doctor_id=:id and date=:date and status =1",nativeQuery = true)
	List<Long> findAllWorkTimeIdByDateAndDoctorIdAndStatus(@Param("id") Long id,@Param("date") String date);
	List<MedicalExaminationScheduleEntity> findAllByStatus(Integer status);
	List<MedicalExaminationScheduleEntity> findAllByDoctorIdAndStatus(Long doctorID,Integer Status);
	List<MedicalExaminationScheduleEntity> findAllByUserIdAndStatus(Long userId, Integer status);
	
	@Transactional
	@Modifying
	@Query(value = "UPDATE medical_examination_schedule  SET status = :status WHERE id in :ids", nativeQuery = true)
	Integer updateByStatus(@Param("status") Integer status,@Param("ids") List<String> ids);

	Boolean existsByDateAndAndDoctorIdAndWorkTimeID(String date, Long docterId, Long workTimeId);

	@Query(value = "SELECT COUNT(*) FROM medical_examination_schedule m " +
			"WHERE m.date =:date and m.doctor_id = :idDoctor and m.work_time_id >:startId and m.work_time_id<=:endId ", nativeQuery = true)
	Long countMedicalWhenChangeWk(@Param("date") String date, @Param("idDoctor") Long idDoctor,
								  @Param("startId") Long startId, @Param("endId") Long endId);

	@Query(value = "select * from medical_examination_schedule m \n" +
			"where m.date = :date and m.doctor_id =:doctorId \n" +
			"order by work_time_id asc", nativeQuery = true)
	List<MedicalExaminationScheduleEntity> findAllByDateAnđoctorId(@Param("date") String date,@Param("doctorId")Long doctorId);

	@Query(value = "SELECT DATE_FORMAT(m.createdDate, :formatTime) as time, sum(m.examinationPrice) as price " +
			"FROM MedicalExaminationScheduleEntity m " +
			"WHERE DATE_FORMAT(m.createdDate,'%Y-%m-%d') between DATE_FORMAT(:startDate,'%Y-%m-%d') AND DATE_FORMAT(:endDate, '%Y-%m-%d') AND m.statusPayment = 1 " +
			"GROUP BY time")
	List<Map<String, Object>> getSaleReportByDateAndGroupByDate(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate, @Param("formatTime") String formatTime);

	@Query(value = "SELECT DATE_FORMAT(m.createdDate, :formatTime) as time, sum(m.examinationPrice) as price " +
			"FROM MedicalExaminationScheduleEntity m " +
			"WHERE DATE_FORMAT(m.createdDate,'%Y-%m-%d') between DATE_FORMAT(:startDate,'%Y-%m-%d') AND DATE_FORMAT(:endDate, '%Y-%m-%d') AND m.statusPayment = 1 " +
			"AND m.type =:type " +
			"GROUP BY time")
	List<Map<String, Object>> getSaleReportByDateAndTypeMedicalGroupByDate(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate,
																		   @Param("type") String type, @Param("formatTime") String formatTime);
	@Query(value = "SELECT sum(m.examinationPrice) as price, w.time as time, w.id as id " +
			"FROM MedicalExaminationScheduleEntity m " +
			"INNER JOIN WorkTimeEntity as w " +
			"ON w.id = m.workTimeID " +
			"WHERE DATE_FORMAT(m.createdDate,'%Y-%m-%d') = DATE_FORMAT(:date,'%Y-%m-%d') AND m.statusPayment = 1 " +
			"AND m.type =:type " +
			"GROUP BY id " +
			"ORDER BY id")
	List<Map<String, Object>> getSaleReportByOneDateAndTypeMedicalGroupByWorkTimeId(@Param("date") LocalDate date, @Param("type") String type);

	@Query(value = "SELECT coalesce(sum(m.examinationPrice), 0) as price " +
			"FROM MedicalExaminationScheduleEntity m " +
			"WHERE DATE_FORMAT(m.createdDate,'%Y-%m-%d') between DATE_FORMAT(:startDate,'%Y-%m-%d') AND DATE_FORMAT(:endDate, '%Y-%m-%d') " +
			"AND m.type =:type ")
	Long getTotalPriceByDateAndTypeMedicalGroupByDate(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate,
																		   @Param("type") String type);
	@Query(value = "SELECT coalesce(sum(m.examinationPrice), 0) as price " +
			"FROM MedicalExaminationScheduleEntity m " +
			"WHERE DATE_FORMAT(m.createdDate,'%Y-%m-%d') = DATE_FORMAT(:date,'%Y-%m-%d')  " +
			"AND m.type =:type ")
	Long getTotalPriceByOneDateAndTypeMedical(@Param("date") LocalDate date, @Param("type") String type);

	@Query(value = "SELECT count(*) FROM MedicalExaminationScheduleEntity m " +
			"WHERE m.status IN (:status) " +
			"AND DATE_FORMAT(m.createdDate,'%Y-%m-%d') between DATE_FORMAT(:startDate,'%Y-%m-%d') AND DATE_FORMAT(:endDate, '%Y-%m-%d') ")
	Long getTotalScheduleBetweenTwoDateAndStatus(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate, @Param("status") List<Integer> status);

	@Query(value = "SELECT count(*) FROM MedicalExaminationScheduleEntity m " +
			"WHERE m.status IN (:status) " +
			"AND DATE_FORMAT(m.createdDate,'%Y-%m-%d') = DATE_FORMAT(:date,'%Y-%m-%d') ")
	Long getTotalScheduleByOneDateAndStatus(@Param("date") LocalDate date, @Param("status") List<Integer> status);


	@Query(value = "SELECT sum(m.examinationPrice) as price, w.time as time, w.id as id " +
			"FROM MedicalExaminationScheduleEntity m " +
			"INNER JOIN WorkTimeEntity as w " +
			"ON w.id = m.workTimeID " +
			"WHERE DATE_FORMAT(m.createdDate,'%Y-%m-%d') = DATE_FORMAT(:date,'%Y-%m-%d') AND m.statusPayment = 1 " +
			"GROUP BY id " +
			"ORDER BY id")
	List<Map<String, Object>> getSaleReportByOneDateAndGroupByWorkTimeId(@Param("date") LocalDate startDate);

	@Query(value = "SELECT m.doctor.id as doctorId, m.doctor.fullName as fullName, sum(m.examinationPrice) as price,  COUNT(m.doctor.id) as totalSchedule " +
			"FROM MedicalExaminationScheduleEntity m " +
			"WHERE DATE_FORMAT(m.createdDate,'%Y-%m-%d') between DATE_FORMAT(:startDate,'%Y-%m-%d') AND DATE_FORMAT(:endDate,'%Y-%m-%d') AND m.statusPayment = 1 " +
			"GROUP BY doctorId " +
			"ORDER BY doctorId")
	List<Map<String, Object>> getSaleReportGroupByDoctorId(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);

	@Query(value = "SELECT m.hospitalName as hospitalName, sum(m.examinationPrice) as price,  COUNT(m.hospitalName) as totalSchedule " +
			"FROM MedicalExaminationScheduleEntity m " +
			"WHERE DATE_FORMAT(m.createdDate,'%Y-%m-%d') between DATE_FORMAT(:startDate,'%Y-%m-%d') AND DATE_FORMAT(:endDate,'%Y-%m-%d') AND m.statusPayment = 1 " +
			"GROUP BY hospitalName " +
			"ORDER BY hospitalName")
	List<Map<String, Object>> getSaleReportGroupByHospital(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);

	@Query(value = "SELECT m.type as type, sum(m.examinationPrice) as price,  COUNT(m.type) as totalSchedule " +
			"FROM MedicalExaminationScheduleEntity m " +
			"WHERE DATE_FORMAT(m.createdDate,'%Y-%m-%d') between DATE_FORMAT(:startDate,'%Y-%m-%d') AND DATE_FORMAT(:endDate,'%Y-%m-%d') AND m.statusPayment = 1 " +
			"GROUP BY type " +
			"ORDER BY type")
	List<Map<String, Object>> getSaleReportGroupByTypeMedical(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);

}
