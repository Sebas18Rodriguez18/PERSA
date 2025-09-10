package co.edu.sena.persa.service;

import java.util.List;

import org.springframework.stereotype.Service;

import co.edu.sena.persa.models.Role;
import co.edu.sena.persa.repository.RoleRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RoleService {

    private final RoleRepository roleRepository;

    public List<Role> showAll(){
        return roleRepository.findAll();
    }

    public Role showById(Long id) {
        return roleRepository.findById(id).orElseThrow(()->new RuntimeException("Rol no encontrado"));
    }

    public Role save (Role role){
        return roleRepository.save(role);
    }

    public void delete(Long id) {
        roleRepository.deleteById(id);
    }
}