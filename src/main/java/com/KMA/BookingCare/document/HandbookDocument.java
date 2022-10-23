package com.KMA.BookingCare.document;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.util.Date;

@Document(indexName = "handbook")
@Data
public class HandbookDocument {

    @Field(type = FieldType.Keyword)
    private Long id;

    private String title;

    private String img;

    private String description;

    private String content;

    private Integer status;

    private String createdBy;

    private String modifiedBy;

}
