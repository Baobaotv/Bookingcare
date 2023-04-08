package com.KMA.BookingCare.Service;

import com.KMA.BookingCare.Api.form.PaymentSaveForm;
import com.KMA.BookingCare.Entity.PaymentEntity;

public interface PaymentService {
    PaymentEntity save(PaymentSaveForm form);
}
