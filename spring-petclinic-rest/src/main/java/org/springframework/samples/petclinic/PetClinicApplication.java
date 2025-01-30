package org.springframework.samples.petclinic;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

import co.elastic.apm.attach.ElasticApmAttacher;

@SpringBootApplication
public class PetClinicApplication extends SpringBootServletInitializer {

	public static void main(String[] args) {
		ElasticApmAttacher.attach();
		SpringApplication.run(PetClinicApplication.class, args);
	}
}
