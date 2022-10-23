package com.KMA.BookingCare.Repository;

import java.util.List;

import com.KMA.BookingCare.Dto.HandbookDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.KMA.BookingCare.Entity.HandbookEntity;
import com.KMA.BookingCare.Entity.UserEntity;

public interface HandbookRepository extends JpaRepository<HandbookEntity, Long> {
	List<HandbookEntity> findAllByStatus(Integer status);
	@Query(value = "SELECT new com.KMA.BookingCare.Dto.HandbookDto(h.id, h.title, h.img) FROM HandbookEntity h where  h.status =:status")
	Page<HandbookDto> findAllByStatus(Integer status, Pageable pageable);
	List<HandbookEntity> findAllByStatusAndUserId(Integer status, Long id,Pageable pageable);
	@Transactional
	@Modifying
	@Query(value = "UPDATE handbook  SET status = 0 WHERE id in :ids", nativeQuery = true)
	Integer updateByStatus(@Param("ids") List<String> ids);
	
	HandbookEntity findOneById(Long id);

	@Query(value = "SELECT new com.KMA.BookingCare.Dto.HandbookDto(h.id, h.title, h.description, h.content, h.createdBy, h.createdDate, h.modifiedDate, h.modifiedBy, h.specialized.name,h.specialized.id,h.img,h.createdBy,h.user.id) FROM HandbookEntity h WHERE h.id =:id")
	HandbookDto findOneByIdApi(@Param("id") Long id);

	@Query(value = "SELECT * FROM handbook as u where title like CONCAT('%',:title,'%')  and status =1 ;", nativeQuery = true)
	List<HandbookEntity> findAllByTitleAndStatus(@Param("title") String title);
	
	@Query(value = "SELECT * FROM handbook as u where title like CONCAT('%',:title,'%') and specialized_id =:specializedId and status =1 ;", nativeQuery = true)
	List<HandbookEntity> findAllByTitleAndSpecializedId(@Param("title") String title,@Param("specializedId") Long specializedId);
	
	List<HandbookEntity> findAllBySpecializedIdAndStatus(Long SpecializedId, Integer status);
	
	@Query(value = "SELECT * FROM handbook h where h.status =1 ORDER BY RAND() LIMIT 4;",nativeQuery = true)
	List<HandbookEntity> findRandomHandbook();
	
	@Query(value = "SELECT * FROM handbook h  where h.status =1 AND title   like CONCAT('%',:title,'%') "
			+ "AND  ( (:specializedId IS NOT NULL AND specialized_id =:specializedId) || :specializedId IS NULL)"
			+ "AND ( (:userId IS NOT NULL AND user_id =:userId) || :userId IS NULL)",nativeQuery = true)
	List<HandbookEntity> searchHandbookAndPageable(@Param("title") String title,@Param("specializedId") Long specializedId,@Param("userId") Long userId,Pageable page);

	@Query(value = "SELECT new com.KMA.BookingCare.Dto.HandbookDto(h.id, h.title, h.img) FROM HandbookEntity h  where h.status =1 AND h.title like CONCAT('%',:title,'%') "
			+ "AND ((:specializedId is not null and h.specialized.id =:specializedId) or :specializedId is null) "
			+ "AND ((:userId IS NOT NULL AND h.user.username =:userId) or :userId is null)")
	Page<HandbookDto> searchHandbookAndPageableApi(@Param("title") String title,@Param("specializedId") Long specializedId,@Param("userId") String userId,Pageable page);

//	List<HandbookEntity> finAll();
}
