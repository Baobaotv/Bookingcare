package com.KMA.BookingCare.common;

import com.KMA.BookingCare.Dto.Role;
import org.springframework.stereotype.Component;

import java.util.Set;

public class GetUtils {

    public static String getRole(Set<Role> roles) {
        if(roles.stream().anyMatch(item -> item.getName().equals("ROLE_ADMIN"))) return "ROLE_ADMIN";
        if(roles.stream().anyMatch(item -> item.getName().equals("ROLE_DOCTOR"))) return "ROLE_DOCTOR";
        return "ROLE_USER";
    }
}
