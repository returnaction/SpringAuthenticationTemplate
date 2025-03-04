package springsecurity1.demo.services;

import jakarta.annotation.PostConstruct;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import springsecurity1.demo.models.Role;
import springsecurity1.demo.repositories.RoleRepository;

@Service
public class RoleInitializationService {

    @Autowired
    public RoleRepository roleRepository;

    @PostConstruct
    @Transactional
    public void initRoles(){
        if(!roleRepository.findByName("ROLE_USER").isPresent()){
            Role role = new Role();
            role.setName("ROLE_USER");
            roleRepository.save(role);
        }

        if(!roleRepository.findByName("ROLE_ADMIN").isPresent()){
            Role role = new Role();
            role.setName("ROLE_ADMIN");
            roleRepository.save(role);
        }
    }
}
