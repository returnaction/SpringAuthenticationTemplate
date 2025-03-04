package springsecurity1.demo.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import springsecurity1.demo.models.MyUser;
import springsecurity1.demo.models.Role;
import springsecurity1.demo.repositories.RoleRepository;
import springsecurity1.demo.repositories.UserRepository;

import java.util.Collections;

@Service
public class UserService  implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    @Lazy
    private PasswordEncoder passwordEncoder;

    // Возвращаем объект User, который является экземпляром UserDetails
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Извлекаем пользователя по имени и возвращаем его, так как User уже реализует UserDetails
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Пользователь " + username + " не найден"));
    }

    // Метод для регистрации нового пользователя
    public MyUser registerNewUser(MyUser myUser) {
        // Проверим, существует ли уже пользователь с таким именем
        if (userRepository.findByUsername(myUser.getUsername()).isPresent()) {
            throw new IllegalArgumentException("Пользователь с таким именем уже существует");
        }

        // Шифруем пароль
        myUser.setPassword(passwordEncoder.encode(myUser.getPassword()));

        // Получаем роль USER из базы данных
        Role role = roleRepository.findByName("ROLE_USER")
                .orElseThrow(() -> new IllegalArgumentException("Роль USER не найдена"));

        // Назначаем роль
        myUser.setRoles(Collections.singleton(role));

        // Сохраняем нового пользователя
        return userRepository.save(myUser);
    }
}
