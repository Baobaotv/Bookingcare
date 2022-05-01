package com.KMA.BookingCare.Repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.KMA.BookingCare.Entity.HospitalEntity;

public interface HospitalRepository extends JpaRepository<HospitalEntity, Long> {
	HospitalEntity findOneById(Long id);
	List<HospitalEntity> findAllByStatus(Integer status,Pageable pageable);
	List<HospitalEntity> findAllByStatus(Integer status);
}
