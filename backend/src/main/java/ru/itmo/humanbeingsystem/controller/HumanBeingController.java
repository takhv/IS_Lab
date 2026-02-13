package ru.itmo.humanbeingsystem.controller;

import jakarta.validation.Valid;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.itmo.humanbeingsystem.dto.*;
import ru.itmo.humanbeingsystem.service.HumanBeingService;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/human-being")
public class HumanBeingController {
  @Autowired private HumanBeingService service;

  @PostMapping
  public ResponseEntity<HumanDTO> create(@Valid @RequestBody HumanCreateDTO dto) {
    HumanDTO result = service.createHuman(dto);
    return ResponseEntity.ok(result);
  }

  @GetMapping
  public ResponseEntity<Page<HumanDTO>> getAll(Pageable pageable) {
    Page<HumanDTO> result = service.findAll(pageable);
    return ResponseEntity.ok(result);
  }

  @GetMapping("/{id}")
  public ResponseEntity<HumanDTO> getById(@PathVariable Integer id) {
    HumanDTO result = service.findById(id);
    return ResponseEntity.ok(result);
  }

  @PutMapping
  public ResponseEntity<HumanDTO> update(@Valid @RequestBody HumanUpdateDTO dto) {
    HumanDTO result = service.updateHuman(dto);
    return ResponseEntity.ok(result);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteById(@PathVariable Integer id) {
    service.deleteById(id);
    return ResponseEntity.noContent().build();
  }

  @GetMapping("/weaponType")
  public ResponseEntity<Long> countWeaponTypeGreaterThan(@RequestParam String weaponType) {
    long result = service.countWeaponTypeGreaterThan(weaponType);
    return ResponseEntity.ok(result);
  }

  @GetMapping("/soundtrack")
  public ResponseEntity<List<HumanDTO>> soundtrackNameLessThan(
      @RequestParam String soundtrackName) {
    List<HumanDTO> result = service.soundtrackNameLessThan(soundtrackName);
    return ResponseEntity.ok(result);
  }

  @GetMapping("/uniqWeapons")
  public ResponseEntity<List<String>> uniqWeaponTypes() {
    List<String> result = service.uniqWeaponTypes();
    return ResponseEntity.ok(result);
  }

  @PutMapping("/sad")
  public ResponseEntity<Void> allHumanSad() {
    service.allHumanSad();
    return ResponseEntity.noContent().build();
  }

  @PutMapping("/lada")
  public ResponseEntity<Void> giveLadaKalina() {
    service.giveLadaKalina();
    return ResponseEntity.noContent().build();
  }

  @PutMapping("/kurt")
  public ResponseEntity<Void> kurtCobain() {
    service.kurtCobain();
    return ResponseEntity.noContent().build();
  }
}
