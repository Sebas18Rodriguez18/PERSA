package co.edu.sena.persa.service;

import co.edu.sena.persa.models.User;
import co.edu.sena.persa.repository.UsersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UsersRepository userRepository;

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }

    public User save(User user) {
        return userRepository.save(user);
    }

    public User update(Long id, User user) {
        return userRepository.findById(id)
                .map(existing -> {
                    existing.setFullname(user.getFullname());
                    existing.setEmail(user.getEmail());
                    existing.setPassword(user.getPassword());
                    existing.setRole(user.getRole());
                    return userRepository.save(existing);
                })
                .orElseThrow(() -> new RuntimeException("User not found with id " + id));
    }

    public void delete(Long id) {
        userRepository.deleteById(id);
    }
}