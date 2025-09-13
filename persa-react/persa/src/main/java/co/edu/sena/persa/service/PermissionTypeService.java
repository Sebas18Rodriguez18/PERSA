package co.edu.sena.persa.service;

import co.edu.sena.persa.models.PermissionType;
import co.edu.sena.persa.repository.PermissionTypeRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class PermissionTypeService {

    private final PermissionTypeRepository typeRepository;

    public List<PermissionType> showAll(){
        return typeRepository.findAll();
    }

    public PermissionType showById(Long id){
        return typeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Tipo de permiso no encontrado"));
    }

    public PermissionType save(PermissionType type){
        return typeRepository.save(type);
    }

    public void delete(Long id){
        typeRepository.deleteById(id);
    }
}