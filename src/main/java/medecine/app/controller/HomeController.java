package medecine.app.controller;

import aina.annotation.MonController;
import aina.annotation.UrlMapping;
import aina.util.AppSession;
import aina.util.ModAndView;
import java.util.List;
import medecine.app.model.Role;
import medecine.app.model.Utilisateur;
import medecine.app.repository.PatientRepository;
import medecine.app.repository.RoleRepository;
import medecine.app.repository.UtilisateurRepository;
import org.springframework.context.ApplicationContext;

@MonController
public class HomeController {

    @UrlMapping(value = "/", method = "GET")
    public ModAndView home(ApplicationContext context) {
        ModAndView mav = new ModAndView();
        mav.setView("home");
        mav.addValue("roles", context.getBean(RoleRepository.class).findAll());
        return mav;
    }

    @UrlMapping(value = "/connexion", method = "GET")
    public ModAndView connexion(ApplicationContext context) {
        ModAndView mav = new ModAndView();
        mav.setView("role-saisie");
        mav.addValue("roles", context.getBean(RoleRepository.class).findAll());
        return mav;
    }

    @UrlMapping(value = "/login", method = "POST")
    public ModAndView login(String username, String password, ApplicationContext context, AppSession session) {
        List<Utilisateur> utilisateurs = context.getBean(UtilisateurRepository.class).findAll();
        for (Utilisateur utilisateur : utilisateurs) {
            if (utilisateur.getLogin().equals(username) && utilisateur.getPassword().equals(password)) {
                session.addSessionData("user", utilisateur);
                ModAndView mav = new ModAndView();
                mav.setView("patient-liste");
                mav.addValue("liste_patient", context.getBean(PatientRepository.class).findAll());
                return mav;
            }
        }
        ModAndView mav = new ModAndView();
        mav.setView("home");
        mav.addValue("roles", context.getBean(RoleRepository.class).findAll());
        return mav;
    }

    @UrlMapping(value = "/connexion", method = "POST")
    public ModAndView inscription(String username, String password, String roleId, ApplicationContext context) {
        UtilisateurRepository repo = context.getBean(UtilisateurRepository.class);
        List<Utilisateur> utilisateurs = repo.findAll();
        for (Utilisateur utilisateur : utilisateurs) {
            if (utilisateur.getLogin().equals(username)) {
                ModAndView mav = new ModAndView();
                mav.setView("role-saisie");
                mav.addValue("roles", context.getBean(RoleRepository.class).findAll());
                return mav;
            }
        }
        Utilisateur utilisateur = new Utilisateur();
        utilisateur.setLogin(username);
        utilisateur.setPassword(password);
        if (roleId != null && !roleId.isBlank()) {
            Role role = context.getBean(RoleRepository.class).findById(Long.valueOf(roleId)).orElse(null);
            utilisateur.setRole(role);
        }
        repo.save(utilisateur);
        ModAndView mav = new ModAndView();
        mav.setView("home");
        mav.addValue("roles", context.getBean(RoleRepository.class).findAll());
        return mav;
    }
}
