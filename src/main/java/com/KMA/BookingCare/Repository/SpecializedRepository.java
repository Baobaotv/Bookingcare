package com.KMA.BookingCare.Repository;

import java.util.List;

import com.KMA.BookingCare.Dto.SpecializedDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.KMA.BookingCare.Entity.HospitalEntity;
import com.KMA.BookingCare.Entity.SpecializedEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface SpecializedRepository extends JpaRepository<SpecializedEntity, Long> {
	SpecializedEntity findOneById(Long Id);
	List<SpecializedEntity> findAllByStatus(Integer status,Pageable pageable);

	@Query(value = "select * from bookingcare.specialized where status =:status limit 5 ", nativeQuery = true)
	List<SpecializedEntity> findRandomSpecicalized(@Param("status") Integer status);

	@Query(value = "SELECT new com.KMA.BookingCare.Dto.SpecializedDto(s.id, s.name,s.code, s.description, s.img) FROM SpecializedEntity s WHERE s.status = :status ")
	Page<SpecializedDto> findAllByStatusApi(Integer status, Pageable pageable);
}
