package com.KMA.BookingCare.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.KMA.BookingCare.Entity.HandbookEntity;
import com.KMA.BookingCare.Entity.UserEntity;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
	public UserEntity findByUsername(String username);
	
	public Optional<UserEntity> findById(Long id);
	
	List<UserEntity> findAllByStatus(Integer status,Pageable pageable);
	
	UserEntity findOneById(Long id);
	
	@Transactional
	@Modifying
	@Query(value = "UPDATE user  SET status = 0 WHERE id in :ids", nativeQuery = true)
	Integer updateStatus(@Param("ids") List<String> ids);
	
	List<UserEntity> findAllBySpecializedIdAndStatus(Long id, Integer status );
	
	//Lấy danh sách tất cả những bác sĩ không có lịch khám trong ngày theo chuyên khoa đã chọn
	@Query(value = "SELECT * FROM bookingcare.user u where u.id not in (SELECT  doctor_id FROM bookingcare.medical_examination_schedule where date= :date and status =:statusMedical) and specialized_id= :specialized and status =:status ;", nativeQuery = true)
	List<UserEntity> findAllBySpecialized(@Param("date") String date,@Param("specialized") Long specialized,@Param("status") Integer status,@Param("statusMedical") Integer statusMedical);
	
	//Lấy ra tất cả những bác sĩ có lịch khám trong ngày
	@Query(value = "SELECT * FROM bookingcare.user as u where u.id in(SELECT doctor_id FROM bookingcare.medical_examination_schedule where date =:date and status =:statusMedical) and specialized_id= :specialized and status =:status ;", nativeQuery = true)
	List<UserEntity> findAllByMedical(@Param("date") String date,@Param("specialized") Long specialized,@Param("status") Integer status, @Param("statusMedical") Integer statusMedical);
	
	@Query(value = "SELECT * FROM bookingcare.user u ,bookingcare.user_role r where u.id =r.user_id and u.status =1 and r.role_id=2 ;",nativeQuery = true)
	List<UserEntity> findAllDoctor();
	
	@Query(value = "SELECT * FROM bookingcare.user u ,bookingcare.user_role r where u.id =r.user_id and u.status =1 and r.role_id=2 AND 1=:sql",nativeQuery = true)
	List<UserEntity> searchAllDoctor(@Param("sql") String sql);
	
	@Query(value = "SELECT peer_id FROM bookingcare.user u WHERE u.id=:id", nativeQuery = true)
	String findPeerIdById(Long id);
	
	@Transactional
	@Modifying
	@Query(value = "UPDATE user  SET peer_id = :peerId WHERE id = :id", nativeQuery = true)
	Integer updatePeerId(@Param("peerId") String peerId, @Param("id") Long id);
	
	@Query(value = "SELECT * FROM bookingcare.user u,bookingcare.user_role r where u.id=r.user_id and r.role_id=2 ORDER BY RAND() LIMIT 7;",nativeQuery = true)
	List<UserEntity> findRandomDoctor();
	
	//Lấy danh sách tất cả những bác sĩ không có lịch khám trong ngày theo bệnh viện đã chọn
		@Query(value = "SELECT * FROM bookingcare.user u where u.hospital_id= :hospitalId AND u.id not in (SELECT  doctor_id FROM bookingcare.medical_examination_schedule where date= :date and status =:statusMedical)  and status =:status ;", nativeQuery = true)
		List<UserEntity> findAllByHospital(@Param("date") String date,@Param("hospitalId") Long hospitalId,@Param("status") Integer status,@Param("statusMedical") Integer statusMedical);
		
		//Lấy ra tất cả những bác sĩ có lịch khám trong ngày theo bệnh viện đã chọn
		@Query(value = "SELECT * FROM bookingcare.user as u where u.id in(SELECT doctor_id FROM bookingcare.medical_examination_schedule where date =:date and status =:statusMedical) AND u.hospital_id= :hospitalId and status =:status ;", nativeQuery = true)
		List<UserEntity> findAllByMedicalAndHospital(@Param("date") String date,@Param("hospitalId") Long hospitalId,@Param("status") Integer status, @Param("statusMedical") Integer statusMedical);

		@Query(value = "SELECT u.description,u.id,u.email,u.full_name,u.img,u.location,u.phone_number,u.sex,u.username,"
				+ "u.password,u.status,u.year_of_birth,u.hospital_id,u.specialized_id,u.short_description,u.peer_id" + 
				" FROM bookingcare.user u ,user_role ur WHERE u.id= ur.user_id AND ur.role_id =2 AND u.status =1 AND u.full_name   LIKE CONCAT('%',:fullName,'%')" + 
				" AND  ( (:specializedId IS NOT NULL AND specialized_id =:specializedId) || :specializedId IS NULL)" + 
				" AND  ( (:hospitalId IS NOT NULL AND hospital_id =:hospitalId) || :hospitalId IS NULL)",nativeQuery = true)
		List<UserEntity> searchHandbookAndPageable(@Param("fullName") String fullName,@Param("specializedId") Long specializedId,@Param("hospitalId") Long hospitalId,Pageable page);

}
