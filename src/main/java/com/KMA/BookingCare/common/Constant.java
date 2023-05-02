package com.KMA.BookingCare.common;

public class Constant {
    public static final String username ="nguyenbaowwe@gmail.com";

    public static final String password ="********";

    public static final Integer MEDICAL_SCHEDULE_IS_COMPLETE = 2;

    public static final Integer MEDICAL_SCHEDULE_IS_WAITING = 1;

    public static final Integer MEDICAL_SCHEDULE_IS_CANCEL = 0;

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

    public static final String vnp_ReturnUrl = "http://localhost:3000/thong-tin-thanh-toan";

    public static final String vnp_PayUrl = "https://sandbox.vnpayment.vn/paymentv2/vpcpay.html";

    public static final Integer payment_paid = 1;

    public static final Integer payment_unPaid = 0;

    public static final Integer payment_error = 2;

    public static final Integer total_revenue_type_report = 1;
    public static final Integer type_of_medical_examination_type_report = 2;
    public static final Integer revenue_by_hospital_type_report = 3;
    public static final Integer revenue_by_doctor_type_report = 4;
    public static final Integer today_time_report = 1;
    public static final Integer yesterday_time_report = 2;
    public static final Integer last_week_time_report = 3;
    public static final Integer this_month_time_report = 4;
    public static final Integer last_month_time_report = 5;
    public static final Integer time_option_time_report = 6;

    public static final String group_by_day = "DAY";
    public static final String group_by_month = "MONTH";
    public static final String group_by_year = "YEAR";

}
