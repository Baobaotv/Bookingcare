package com.KMA.BookingCare.common;

public class Constant {
    public static final String username ="nguyenbaowwe@gmail.com";

    public static final String password ="********";

    public static final Integer MEDICAL_SCHEDULE_IS_COMPLETE = 2;

    public static final Integer MEDICAL_SCHEDULE_IS_DELETE = 0;

    public static final Integer MEDICAL_SCHEDULE_IS_NOT_COMPLETE = 1;

    public static final String NOTIFICATION_TYPE_USER_BOOKING_SCHEDULE = "1";

    public static final String NOTIFICATION_TYPE_CHANGE_SCHEDULE = "2";

    public static final String NOTIFICATION_TYPE_SEND_MEDICAL_RECORDS = "3";

    public static final String NOTIFICATION_TOPIC = "notification";

    public static final Integer LIMIT_DEFAULT = 6;

    public static final Integer OFFSET_DEFAULT = 0;

    public static final String vnp_TmnCode = "ZF7UBU1W";

    public static final String vnp_HashSecret = "UJNHGYEOIOIJYVMVFNHFHAAZZDBNBDIR";

    public static final String vnp_Version = "2.1.0";

    public static final String vnp_Command = "pay";

    public static final String vnp_Locale = "vn";

    public static final String vnp_ReturnUrl = "http://localhost:8080/api/payment/returnUrl";

    public static final String vnp_PayUrl = "https://sandbox.vnpayment.vn/paymentv2/vpcpay.html";

}
