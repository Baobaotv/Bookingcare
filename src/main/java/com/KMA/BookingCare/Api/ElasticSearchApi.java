package com.KMA.BookingCare.Api;

import com.KMA.BookingCare.Service.ElasticSearchService;
import com.KMA.BookingCare.Service.HandbookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.text.ParseException;

@RestController
@RequestMapping("/api/elasticsearch")
public class ElasticSearchApi {

    @Autowired
    private HandbookService handbookService;

    @Autowired
    private ElasticSearchService elasticSearchService;

    @GetMapping("/get-all-handbook")
    public ResponseEntity<?> getAll() {
        handbookService.getAll();
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @GetMapping("/sync-data")
    public ResponseEntity<?> syncFormDbToElasticSearch() throws ParseException {
        elasticSearchService.syncData();
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @GetMapping("/search-all")
    public ResponseEntity<?> searchAll(@Param("query") String query) throws IOException {
        return ResponseEntity.ok(elasticSearchService.searchAll(query));
    }
}
