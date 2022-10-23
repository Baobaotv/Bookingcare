package com.KMA.BookingCare.document;

import lombok.Data;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

@Data
@Document(indexName = "user")
public class UserDocument {

	@Field(type = FieldType.Keyword)
	private Long id;

	private String fullName;

	private String sex;

	private String phoneNumber;

	private String location;

	private String description;

	private String shortDescription;

	private String username;

	private String email;

	private String img;

	private String yearOfBirth;

	private Integer status;

//	private Set<RoleEntity> roles;

}
