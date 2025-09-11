package co.edu.sena.persa.controllers;

import co.edu.sena.persa.models.User;
import co.edu.sena.persa.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping
    public List<User> findAll() {
        return userService.showAll();
    }

    @GetMapping("/{id}")
    public User findById(@PathVariable Long id) {
        return userService.showById(id);
    }

    @PostMapping
    public User save(@RequestBody User user, @RequestParam Long role_id) {
        return userService.save(user, role_id);
    }

    @PutMapping("/{id}")
    public User update(@PathVariable Long id, @RequestBody User user, @RequestParam Long role_id) {
        user.setId(id);
        return userService.save(user, role_id);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        userService.delete(id);
    }
}