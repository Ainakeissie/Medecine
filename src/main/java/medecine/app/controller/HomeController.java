package medecine.app.controller;

import aina.annotation.Json;
import aina.annotation.MonController;
import aina.annotation.UrlMapping;
import aina.util.AppSession;
import aina.util.ModAndView;
import java.util.List;
import medecine.app.model.Role;
import org.springframework.context.ApplicationContext;
import medecine.app.repository.RoleRepository;

@MonController
public class HomeController {

    @UrlMapping(value = "/", method = "GET")
    public ModAndView home() {
        ModAndView mav = new ModAndView();
        mav.setView("home");
        return mav;
    }

    @UrlMapping(value = "/connexion", method = "GET")
    public ModAndView connexion() {
        ModAndView mav = new ModAndView();
        mav.setView("connexion");
        return mav;
    }

    @UrlMapping(value = "/connexion", method = "POST")
    @Json
    public Role login(String username, String password, ApplicationContext context, AppSession session) {
        List<Role> roles = context.getBean(RoleRepository.class).findAll();
        for (Role role : roles) {
            if (role.getLogin().equals(username) && role.getPassword().equals(password)) {
                session.addSessionData("user", role);
                return role;
            }
        }
        return null;
    }
}
