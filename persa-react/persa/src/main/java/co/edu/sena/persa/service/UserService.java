package co.edu.sena.persa.service;

import co.edu.sena.persa.models.Role;
import co.edu.sena.persa.models.User;
import co.edu.sena.persa.repository.RoleRepository;
import co.edu.sena.persa.repository.UsersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UsersRepository usersRepository;
    private final RoleRepository roleRepository;

    public List<User> showAll(){
        return usersRepository.findAll();
    }

    public User showById(Long id){
        return usersRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
    }

    public User save(User user, Long roleId){
        Role role = roleRepository.findById(roleId)
                .orElseThrow(() -> new RuntimeException("Rol no encontrado"));
        user.setRole(role);
        return usersRepository.save(user);
    }

    public void delete(Long id){
        usersRepository.deleteById(id);
    }
}