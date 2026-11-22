package medecine.app.controller;

import java.util.List;

import aina.annotation.Json;
import aina.annotation.MonController;
import aina.annotation.UrlMapping;
import aina.util.ModAndView;
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

    @UrlMapping(value = "/patients", method = "GET")
    public ModAndView listePatients(ApplicationContext context) {
        ModAndView mav = new ModAndView();
        mav.setView("patient-liste");
        mav.addValue("liste_patient", context.getBean(PatientRepository.class).findAll());
        return mav;
    }

    @UrlMapping(value = "/api/patients", method = "POST")
    public ModAndView save(Patient patient, ApplicationContext context) {
        context.getBean(PatientRepository.class).save(patient);
        ModAndView mav = new ModAndView();
        mav.setView("patient-liste");
        mav.addValue("liste_patient", context.getBean(PatientRepository.class).findAll());
        return mav;
    }

    @UrlMapping(value = "/nouveauPatient", method = "GET")
    public ModAndView form_patient() {
        ModAndView mav = new ModAndView();
        mav.setView("patient-saisie");
        return mav;
    }

    @UrlMapping(value = "/modif/patients", method = "GET")
    public ModAndView modify(Long id, ApplicationContext context) {
        Patient patient = context.getBean(PatientRepository.class).findById(id).orElse(null);
        ModAndView mav = new ModAndView();
        mav.setView("patient-saisie");
        mav.addValue("patient", patient);
        return mav;
    }

    @UrlMapping(value = "/delete/patients", method = "GET")
    public ModAndView delete(Long id, ApplicationContext context) {
        context.getBean(PatientRepository.class).deleteById(id);
        ModAndView mav = new ModAndView();
        mav.setView("patient-liste");
        mav.addValue("liste_patient", context.getBean(PatientRepository.class).findAll());
        return mav;
    }
}
