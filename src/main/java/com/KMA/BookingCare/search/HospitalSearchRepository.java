package com.KMA.BookingCare.search;

import com.KMA.BookingCare.document.HospitalDocument;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface HospitalSearchRepository extends ElasticsearchRepository<HospitalDocument, Long> {
}
