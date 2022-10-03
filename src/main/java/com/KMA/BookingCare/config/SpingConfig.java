package com.KMA.BookingCare.config;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpingConfig {
	@Bean
	public Cloudinary cloudinary() {
		 Cloudinary cloudinary = new Cloudinary(ObjectUtils.asMap(
	                "cloud_name", "dtvkhopoe", // insert here you cloud name
	                "api_key", "677117172728478", // insert here your api code
	                "api_secret", "tlt9oatNQYmCYbpS4G4pxbMLOU0", // insert here your api secret
		 			"secure", true
				 ));
		 return cloudinary;
	}
}
