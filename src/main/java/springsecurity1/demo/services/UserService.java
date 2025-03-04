package springsecurity1.demo.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import springsecurity1.demo.models.User;
import springsecurity1.demo.models.Role;
import springsecurity1.demo.repositories.RoleRepository;
import springsecurity1.demo.repositories.UserRepository;

import java.util.Collections;

@Service
@Transactional
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // Возвращаем объект User, который является экземпляром UserDetails
    @Override
    public User loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User " + username + " not found"));
        return user;
    }

    // Метод для регистрации нового пользователя
    public void registerNewUser(User user) {
        // Проверим, существует ли уже пользователь с таким именем
        if (userRepository.findByUsername(user.getUsername()).isPresent()) {
            throw new IllegalArgumentException("Пользователь с таким именем уже существует");
        }
        System.out.println("User is saving passwird: " + user.getPassword());
        // Шифруем пароль
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        // Получаем роль USER из базы данных
        Role role = roleRepository.findByName("ROLE_USER")
                .orElseThrow(() -> new IllegalArgumentException("Роль USER не найдена"));

        // Назначаем роль
        user.setRoles(Collections.singleton(role));

        // Сохраняем нового пользователя
        userRepository.save(user);
    }
}
