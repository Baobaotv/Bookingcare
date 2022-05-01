package com.KMA.BookingCare.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.KMA.BookingCare.Entity.InteractiveEntity;

public interface InteractiveRepository extends JpaRepository<InteractiveEntity, Long> {
	
	InteractiveEntity findOneByUserIdAndYouId(Long userId,Long youId);
	List<InteractiveEntity> findAllByYouId(Long youId);

}
