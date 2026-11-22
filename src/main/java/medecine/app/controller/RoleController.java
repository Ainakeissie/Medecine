package medecine.app.controller;

import aina.annotation.Json;
import aina.annotation.MonController;
import aina.annotation.UrlMapping;
import java.util.List;
import medecine.app.model.Role;
import medecine.app.repository.RoleRepository;
import org.springframework.context.ApplicationContext;

@MonController
public class RoleController {

    @UrlMapping(value = "/api/roles", method = "GET")
    @Json
    public List<Role> list(ApplicationContext context) {
        return context.getBean(RoleRepository.class).findAll();
    }
}
