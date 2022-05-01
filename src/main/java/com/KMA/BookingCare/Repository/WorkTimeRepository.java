package com.KMA.BookingCare.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.KMA.BookingCare.Entity.HospitalEntity;
import com.KMA.BookingCare.Entity.SpecializedEntity;
import com.KMA.BookingCare.Entity.WorkTimeEntity;

public interface WorkTimeRepository extends JpaRepository<WorkTimeEntity, Long> {
	
	//tìm danh sách wk mà chưa có lịch khám  của doctor
	//khi mệnh đè not exists đúng, có nghĩa là không tồn tại table nào ghi lại lịch khám của bác sĩ theo ngày tìm kiếm nên sẽ lấy tất cả wk của bác sĩ
	//ngược lại exists sai sẽ lấy lại id những wk k có trong table lịch khám-> lấy được lịch còn lại của bác sĩ
	@Query(value = "select * from work_time where id in(select tb1.work_time_id From user_word_time tb1 where (not exists (SELECT work_time_id as wki FROM medical_examination_schedule as m where date =:date  and m.doctor_id= :idDoctor ) "
			+ "or(tb1.work_time_id not in (SELECT work_time_id as wki FROM medical_examination_schedule as m where date =:date and m.doctor_id= :idDoctor  )and tb1.user_id= :idDoctor )) )", nativeQuery = true)
	List<WorkTimeEntity> findByDateAndDoctorId(@Param("date") String date,@Param("idDoctor") Long idDoctor);
	
}
