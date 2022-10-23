package com.KMA.BookingCare.document;

import lombok.Data;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

@Data
@Document(indexName = "specialized")
public class SpecializedDocument {

	@Field(type = FieldType.Keyword)
	private Long id;
	
	private String name;
	
	private String code;
	
	private String description;
	
	private String img;
	
	private Integer status;
	
}
