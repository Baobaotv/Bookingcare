package com.KMA.BookingCare.Repository;

import java.util.List;

import com.KMA.BookingCare.Dto.HospitalDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.KMA.BookingCare.Entity.HospitalEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface HospitalRepository extends JpaRepository<HospitalEntity, Long> {
	HospitalEntity findOneById(Long id);
	@Query(value = "SELECT new com.KMA.BookingCare.Dto.HospitalDto(h.id, h.name, h.location, h.description, h.img) FROM HospitalEntity h WHERE h.status=:status")
	Page<HospitalDto> findAllByStatusApi(Integer status, Pageable pageable);
	List<HospitalEntity> findAllByStatus(Integer status);
	List<HospitalEntity> findAllByStatus(Integer status,Pageable pageable);

	@Query(value = "select * from bookingcare.hospital where status =1 limit 5", nativeQuery = true)
	List<HospitalEntity> findRandomSpecicalized(@Param("status") Integer status);

}
