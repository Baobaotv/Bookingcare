package com.KMA.BookingCare.Mapper;

import com.KMA.BookingCare.Api.form.PaymentSaveForm;
import com.KMA.BookingCare.Entity.PaymentEntity;

public class PaymentMapper {

    public static PaymentEntity convertToEntity(PaymentSaveForm form) {
        PaymentEntity entity = new PaymentEntity();
        entity.setVnpAmount(form.getVnpAmount());
        entity.setVnpBankCode(form.getVnpBankCode());
        entity.setVnpBankTranNo(form.getVnpBankTranNo());
        entity.setVnpCardType(form.getVnpCardType());
        entity.setVnpPayDate(form.getVnpPayDate());
        entity.setVnpOrderInfo(form.getVnpOrderInfo());
        entity.setVnpResponseCode(form.getVnpResponseCode());
        entity.setVnpSecureHash(form.getVnpSecureHash());
        entity.setVnpSecureHashType(form.getVnpSecureHashType());
        entity.setVnpTmnCode(form.getVnpTmnCode());
        entity.setVnpTxnRef(form.getVnpTxnRef());
        return entity;
    }
}
