package ru.itmo.humanbeingsystem.service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import ru.itmo.humanbeingsystem.dto.*;
import ru.itmo.humanbeingsystem.model.*;
import ru.itmo.humanbeingsystem.repository.HumanBeingRepository;

@Service
@Transactional
public class HumanBeingServiceImpl implements HumanBeingService {
  @Autowired private HumanBeingRepository repository;

  public HumanBeingServiceImpl(HumanBeingRepository repository) {
    this.repository = repository;
  }

  private HumanBeing convertToEntity(HumanCreateDTO dto) {
    HumanBeing entity = new HumanBeing();
    entity.setName(dto.getName());
    entity.setCoordinates(new Coordinates(dto.getX(), dto.getY()));
    entity.setRealHero(dto.isRealHero());
    entity.setHasToothpick(dto.isHasToothpick());
    entity.setCar(new Car(dto.getCarCool(), dto.getCarType()));
    entity.setMood(dto.getMood() != null ? Mood.valueOf(dto.getMood()) : null);
    entity.setImpactSpeed(dto.getImpactSpeed());
    entity.setSoundtrackName(dto.getSoundtrackName());
    entity.setWeaponType(WeaponType.valueOf(dto.getWeaponType()));
    return entity;
  }

  private HumanDTO convertToDTO(HumanBeing entity) {
    HumanDTO dto = new HumanDTO();
    dto.setId(entity.getId());
    dto.setName(entity.getName());
    dto.setX(entity.getCoordinates().getX());
    dto.setY(entity.getCoordinates().getY());
    dto.setCreationDate(entity.getCreationDate());
    dto.setRealHero(entity.isRealHero());
    dto.setHasToothpick(entity.getHasToothpick());
    dto.setCarCool(entity.getCar() != null ? entity.getCar().getCool() : null);
    dto.setCarType(entity.getCar() != null ? entity.getCar().getCarType() : null);
    dto.setMood(entity.getMood() != null ? entity.getMood().toString() : null);
    dto.setImpactSpeed(entity.getImpactSpeed());
    dto.setSoundtrackName(entity.getSoundtrackName());
    dto.setWeaponType(entity.getWeaponType().toString());
    return dto;
  }

  @Override
  public HumanDTO createHuman(HumanCreateDTO dto) {
    HumanBeing entity = convertToEntity(dto);
    entity.setCreationDate(LocalDate.now());
    HumanBeing savedEntity = repository.save(entity);
    return convertToDTO(savedEntity);
  }

  @Override
  public HumanDTO findById(Integer id) {
    HumanBeing entity =
        repository
            .findById(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    return convertToDTO(entity);
  }

  @Override
  public Page<HumanDTO> findAll(Pageable pageable) {
    Page<HumanBeing> entities = repository.findAll(pageable);
    return entities.map(this::convertToDTO);
  }

  @Override
  public HumanDTO updateHuman(HumanUpdateDTO dto) {
    HumanBeing entity =
        repository
            .findById(dto.getId())
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

    entity.setName(dto.getName());
    entity.setCoordinates(new Coordinates(dto.getX(), dto.getY()));
    entity.setRealHero(dto.isRealHero());
    entity.setHasToothpick(dto.getHasToothpick());
    entity.setCar(new Car(dto.getCarCool(), dto.getCarType()));
    entity.setMood(Mood.valueOf(dto.getMood()));
    entity.setImpactSpeed(dto.getImpactSpeed());
    entity.setSoundtrackName(dto.getSoundtrackName());
    entity.setWeaponType(WeaponType.valueOf(dto.getWeaponType()));

    HumanBeing newEntity = repository.save(entity);
    return convertToDTO(newEntity);
  }

  @Override
  public void deleteById(Integer id) {
    if (!repository.existsById(id)) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND);
    }
    repository.deleteById(id);
  }

  @Override
  public long countWeaponTypeGreaterThan(String weaponType) {
    WeaponType weaponTypeEnum = WeaponType.valueOf(weaponType);
    return repository.countWeaponTypeGreaterThan(weaponTypeEnum);
  }

  @Override
  public List<HumanDTO> soundtrackNameLessThan(String soundtrackName) {
    List<HumanBeing> entities = repository.soundtrackNameLessThan(soundtrackName);
    return entities.stream().map(this::convertToDTO).collect(Collectors.toList());
  }

  @Override
  public List<String> uniqWeaponTypes() {
    List<WeaponType> weaponTypes = repository.uniqWeaponTypes();
    return weaponTypes.stream().map(Enum::toString).collect(Collectors.toList());
  }

  @Override
  public void allHumanSad() {
    List<HumanBeing> allEntities = repository.findAll();
    for (HumanBeing human : allEntities) {
      human.setMood(Mood.LONGING);
    }

    repository.saveAll(allEntities);
  }

  @Override
  public void giveLadaKalina() {
    List<HumanBeing> allEntities = repository.findAll();
    for (HumanBeing human : allEntities) {
      if (human.getCar().getCarType() == null) {
        human.setCar(new Car(true, "red lada kalina"));
      }
    }

    repository.saveAll(allEntities);
  }
}
