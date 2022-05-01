package com.KMA.BookingCare.Repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.KMA.BookingCare.Entity.HospitalEntity;
import com.KMA.BookingCare.Entity.SpecializedEntity;

public interface SpecializedRepository extends JpaRepository<SpecializedEntity, Long> {
	SpecializedEntity findOneById(Long Id);
	List<SpecializedEntity> findAllByStatus(Integer status,Pageable pageable);
}
