package com.KMA.BookingCare.ServiceImpl;

import com.KMA.BookingCare.Api.form.PaymentSaveForm;
import com.KMA.BookingCare.Entity.MedicalExaminationScheduleEntity;
import com.KMA.BookingCare.Entity.PaymentEntity;
import com.KMA.BookingCare.Mapper.PaymentMapper;
import com.KMA.BookingCare.Repository.PaymentRepository;
import com.KMA.BookingCare.Service.MedicalExaminationScheduleService;
import com.KMA.BookingCare.Service.PaymentService;
import com.KMA.BookingCare.common.Constant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
public class PaymentServiceImpl implements PaymentService {

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Autowired
    private MedicalExaminationScheduleService medicalExaminationScheduleService;

    @Override
    public PaymentEntity save(PaymentSaveForm form) {
        Optional<MedicalExaminationScheduleEntity> optional = medicalExaminationScheduleService.findOneById(form.getMedicalId());
        MedicalExaminationScheduleEntity medicalExaminationSchedule = optional.get();
        medicalExaminationSchedule.setStatusPayment(Constant.payment_paid);
        medicalExaminationScheduleService.update(medicalExaminationSchedule);

        PaymentEntity entity = PaymentMapper.convertToEntity(form);
        entity.setCreatedDate(new Date());
        entity.setCreatedBy(userDetailsService.getUserDetailsImplFromContext().getId());
        entity.setMedicalExaminationSchedule(medicalExaminationSchedule);
        entity = paymentRepository.save(entity);
        entity.getMedicalExaminationSchedule().setUser(null);
        entity.getMedicalExaminationSchedule().setDoctor(null);
        return entity;
    }

}
