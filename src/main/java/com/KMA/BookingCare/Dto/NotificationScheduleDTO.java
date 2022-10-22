package com.KMA.BookingCare.Dto;

import java.util.List;

public class NotificationScheduleDTO {

    private List<Long> ids;

    private String typeNotification;

    public List<Long> getIds() {
        return ids;
    }

    public void setIds(List<Long> ids) {
        this.ids = ids;
    }

    public String getTypeNotification() {
        return typeNotification;
    }

    public void setTypeNotification(String typeNotification) {
        this.typeNotification = typeNotification;
    }

    public NotificationScheduleDTO() {
    }

    public NotificationScheduleDTO(List<Long> ids, String typeNotification) {
        this.ids = ids;
        this.typeNotification = typeNotification;
    }
}
