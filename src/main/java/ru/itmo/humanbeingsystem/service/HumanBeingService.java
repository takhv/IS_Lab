package ru.itmo.humanbeingsystem.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import ru.itmo.humanbeingsystem.dto.*;

public interface HumanBeingService {
    HumanDTO createHuman(HumanCreateDTO dto);
    HumanDTO findById(Integer id);
    Page<HumanDTO> findAll(Pageable pageable);
    HumanDTO updateHuman(HumanUpdateDTO dto);
    void deleteById(Integer id);

    long countWeaponTypeGreaterThan(String weaponType);
    List<HumanDTO> soundtrackNameLessThan(String soundtrackName);
    List<String> uniqWeaponTypes();
    void allHumanSad();
    void giveLadaKalina();
}
