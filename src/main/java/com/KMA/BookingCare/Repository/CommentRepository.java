package com.KMA.BookingCare.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.KMA.BookingCare.Entity.CommentEntity;

public interface CommentRepository extends JpaRepository<CommentEntity, Long> {
	List<CommentEntity> findAllByHandbookId(Long id);

}
