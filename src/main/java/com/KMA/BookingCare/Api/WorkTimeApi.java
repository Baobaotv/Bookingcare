package com.KMA.BookingCare.Api;

import com.KMA.BookingCare.Dto.WorkTimeDto;
import com.KMA.BookingCare.Service.WorkTimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/workTime")
public class WorkTimeApi {

    @Autowired
    private WorkTimeService workTimeService;

    @GetMapping("/get-all-by-doctor-and-date")
    public ResponseEntity<?> getAllByDoctorAndDate(@RequestParam("doctor-id") Long doctorId, @RequestParam("date") String date) {
        List<WorkTimeDto> workTimeDtos = workTimeService.findByDateAndDoctorId(date, doctorId);
        return ResponseEntity.ok(workTimeDtos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getOneById(@PathVariable("id") Long id) {
        WorkTimeDto workTimeDto = workTimeService.findOneById(id);
        return ResponseEntity.ok(workTimeDto);
    }

}
