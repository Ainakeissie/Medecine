package medecine.app.controller;

import java.util.List;

import aina.annotation.Json;
import aina.annotation.MonController;
import aina.annotation.UrlMapping;
import medecine.app.model.Patient;
import medecine.app.repository.PatientRepository;
import org.springframework.context.ApplicationContext;

@MonController
public class PatientController {

    @UrlMapping(value = "/api/patients", method = "GET")
    @Json
    public List<Patient> list(ApplicationContext context) {
        return context.getBean(PatientRepository.class).findAll();
    }

    @UrlMapping(value = "/api/patients", method = "POST")
    @Json
    public Patient save(Patient patient, ApplicationContext context) {
        return context.getBean(PatientRepository.class).save(patient);
    }
}
