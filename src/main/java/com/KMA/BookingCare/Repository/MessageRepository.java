package com.KMA.BookingCare.Repository;

import java.util.List;

import org.hibernate.annotations.Parameter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.KMA.BookingCare.Entity.MessageEntity;

public interface MessageRepository extends JpaRepository<MessageEntity, Long> {

	List<MessageEntity> findBySenderIdOrReceiverIdOrderByCreatedDateAsc(Long sender, Long receiver);
	
	@Query(value = "SELECT * FROM bookingcare.message WHERE (receiver_id =:idUser and sender_id= :idServer) "
			+ "OR(receiver_id =:idServer and sender_id=:idUser) ORDER BY created_date ASC", nativeQuery = true)
	List<MessageEntity> findAllMessageBySelectUser(@Param( "idServer")Long sender,@Param("idUser") Long receiver);
}
