package com.KMA.BookingCare.ServiceImpl;

import com.KMA.BookingCare.Dto.HitsArrayDto;
import com.KMA.BookingCare.Dto.SearchAllDto;
import com.KMA.BookingCare.Dto.SearchHitDto;
import com.KMA.BookingCare.Entity.HandbookEntity;
import com.KMA.BookingCare.Entity.HospitalEntity;
import com.KMA.BookingCare.Entity.SpecializedEntity;
import com.KMA.BookingCare.Entity.UserEntity;
import com.KMA.BookingCare.Repository.HandbookRepository;
import com.KMA.BookingCare.Repository.HospitalRepository;
import com.KMA.BookingCare.Repository.SpecializedRepository;
import com.KMA.BookingCare.Repository.UserRepository;
import com.KMA.BookingCare.Service.ElasticSearchService;
import com.KMA.BookingCare.Service.SpecializedService;
import com.KMA.BookingCare.document.HandbookDocument;
import com.KMA.BookingCare.document.HospitalDocument;
import com.KMA.BookingCare.document.SpecializedDocument;
import com.KMA.BookingCare.document.UserDocument;
import com.KMA.BookingCare.search.HandbookingSearchRepository;
import com.KMA.BookingCare.search.HospitalSearchRepository;
import com.KMA.BookingCare.search.SpecializedSearchRepository;
import com.KMA.BookingCare.search.UserSearchRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.index.query.Operator;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.json.GsonJsonParser;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ElasticSearchServiceImpl implements ElasticSearchService {

//    @Autowired
//    private HandbookRepository handbookRepository;
//
//    @Autowired
//    private SpecializedRepository specializedRepository;
//
//    @Autowired
//    private UserRepository userRepository;
//
//    @Autowired
//    private HospitalRepository hospitalRepository;
//
//    @Autowired
//    private HandbookingSearchRepository handbookingSearchRepository;
//
//    @Autowired
//    private SpecializedSearchRepository specializedSearchRepository;
//
//    @Autowired
//    private UserSearchRepository userSearchRepository;
//
//    @Autowired
//    private HospitalSearchRepository hospitalSearchRepository;
//
//    @Value("${spring.elasticsearch.uris}")
//    private String elasticsearchUrl;
//
//
//    @Override
//    public void syncData() {
//        deleteDataSearch();
//        syncDataHandbook();
//        syncDataUser();
//        syncDataSpecialized();
//        syncDataHospital();
//    }
//
//    @Override
//    public List<SearchAllDto> searchAll(String param) throws IOException {
//        String url= "http://" + elasticsearchUrl + "/_search";
//
//        File fileSearch = ResourceUtils.getFile("classpath:static/querySearchAll.json");
//        String query = new String(Files.readAllBytes(fileSearch.toPath()));
//        String body = query.replace("__PARAM__", param);
//
//        HttpHeaders httpHeaders = new HttpHeaders();
//        //yeu cau tra ve dinh dang json
//        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
//
//        // HttpEntity<String>: To get result as String.
//        HttpEntity<String> entity = new HttpEntity<String>(body,httpHeaders);
//
//        // RestTemplate
//        RestTemplate restTemplate = new RestTemplate();
//
//        // Gửi yêu cầu với phương thức GET, và các thông tin Headers.
//        ResponseEntity<String> response = restTemplate.exchange(url, //
//                HttpMethod.POST, entity, String.class);
//        String result = response.getBody();
//        Gson gson = new Gson();
//        SearchHitDto searchHit = gson.fromJson(result, SearchHitDto.class);
//        return searchHit.getHits().getHits().stream().map(item -> item.get_source()).collect(Collectors.toList());
//    }
//
//    private void deleteDataSearch() {
//        handbookingSearchRepository.deleteAll();
//        userSearchRepository.deleteAll();
//        specializedSearchRepository.deleteAll();
//        hospitalSearchRepository.deleteAll();
//    }
//
//    private void syncDataUser() {
//        List<UserEntity> userEntities = userRepository.findAll();
//        List<UserDocument> documentList = new ArrayList<>();
//        for(UserEntity entity : userEntities) {
//            UserDocument document = this.convertToDocument(entity);
//            documentList.add(document);
//        }
//        userSearchRepository.saveAll(documentList);
//    }
//
//    private UserDocument convertToDocument(UserEntity entity) {
//        UserDocument document = new UserDocument();
//        document.setId(entity.getId());
//        document.setEmail(entity.getEmail());
//        document.setImg(entity.getImg());
//        document.setDescription(entity.getDescription());
//        document.setFullName(entity.getFullName());
//        document.setStatus(entity.getStatus());
//        document.setLocation(entity.getLocation());
//        document.setSex(entity.getSex());
//        document.setShortDescription(entity.getShortDescription());
//        document.setPhoneNumber(entity.getPhoneNumber());
//        document.setYearOfBirth(entity.getYearOfBirth());
//        document.setUsername(entity.getUsername());
//        return document;
//    }
//
//    private void syncDataHospital() {
//        List<HospitalEntity> hospitalEntities = hospitalRepository.findAll();
//        List<HospitalDocument> documentList = new ArrayList<>();
//        for(HospitalEntity entity : hospitalEntities) {
//            HospitalDocument document = this.convertToDocument(entity);
//            documentList.add(document);
//        }
//        hospitalSearchRepository.saveAll(documentList);
//    }
//
//    private HospitalDocument convertToDocument(HospitalEntity entity) {
//        HospitalDocument document = new HospitalDocument();
//        document.setId(entity.getId());
//        document.setImg(entity.getImg());
//        document.setDescription(entity.getDescription());
//        document.setStatus(entity.getStatus());
//        document.setLocation(entity.getLocation());
//        document.setName(entity.getName());
//        return document;
//    }
//
//    private void syncDataSpecialized() {
//        List<SpecializedEntity> specializedEntities = specializedRepository.findAll();
//        List<SpecializedDocument> documentList = new ArrayList<>();
//        for(SpecializedEntity entity : specializedEntities) {
//            SpecializedDocument document = this.convertToDocument(entity);
//            documentList.add(document);
//        }
//        specializedSearchRepository.saveAll(documentList);
//    }
//
//    private SpecializedDocument convertToDocument(SpecializedEntity entity) {
//        SpecializedDocument document = new SpecializedDocument();
//        document.setId(entity.getId());
//        document.setCode(entity.getCode());
//        document.setImg(entity.getImg());
//        document.setDescription(entity.getDescription());
//        document.setStatus(entity.getStatus());
//        document.setName(entity.getName());
//        return document;
//    }
//
//    private void syncDataHandbook() {
//        List<HandbookEntity> handbookEntities = handbookRepository.findAll();
//        List<HandbookDocument> documentList = new ArrayList<>();
//        for(HandbookEntity entity : handbookEntities) {
//            HandbookDocument document = this.convertToDocument(entity);
//            documentList.add(document);
//        }
//        handbookingSearchRepository.saveAll(documentList);
//    }
//
//    private HandbookDocument convertToDocument(HandbookEntity entity) {
//        HandbookDocument document = new HandbookDocument();
//        document.setId(entity.getId());
//        document.setContent(entity.getContent());
//        document.setImg(entity.getImg());
//        document.setDescription(entity.getDescription());
//        document.setCreatedBy(entity.getCreatedBy());
//        document.setStatus(entity.getStatus());
//        document.setTitle(entity.getTitle());
//        document.setModifiedBy(entity.getModifiedBy());
//        return document;
//    }
}
