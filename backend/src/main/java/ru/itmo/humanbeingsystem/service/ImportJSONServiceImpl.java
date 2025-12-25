package ru.itmo.humanbeingsystem.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;
import ru.itmo.humanbeingsystem.dto.*;
import ru.itmo.humanbeingsystem.model.*;
import ru.itmo.humanbeingsystem.repository.HumanBeingRepository;
import ru.itmo.humanbeingsystem.repository.ImportJSONRepository;

@Service
@Transactional
public class ImportJSONServiceImpl implements ImportJSONService {
  @Autowired private HumanBeingRepository humanBeingRepository;

  @Autowired private ImportJSONRepository importJSONRepository;

  @Autowired private ObjectMapper objectMapper;

  @Autowired private MinioService minioService;

  @Override
  @Transactional
  public ImportJSON importFromJSON(MultipartFile file) {
    ImportJSON importLog = new ImportJSON();
    importLog.setImportTime(LocalDateTime.now());

    String minioFileName = minioService.uploadFile(file);
    importLog.setFileName(minioFileName);

    try {
      byte[] fileBytes = file.getBytes();
      String jsonString = new String(fileBytes);
      List<HumanCreateDTO> dtos =
          objectMapper.readValue(
              jsonString,
              objectMapper
                  .getTypeFactory()
                  .constructCollectionType(List.class, HumanCreateDTO.class));

      int successCount = 0;
      for (HumanCreateDTO dto : dtos) {
        HumanBeing entity = convertToEntity(dto);
        humanBeingRepository.save(entity);
        successCount++;
      }

      importLog.setObjectsCount(successCount);
      importLog.setStatus(ImportStatus.SUCCESS);

      return importJSONRepository.save(importLog);
    } catch (Exception e) {
      if (minioFileName != null) {
        minioService.deleteFile(minioFileName);
      }
      throw new RuntimeException("Import failed", e);
    }
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

  @Override
  public List<ImportJSON> findAll() {
    List<ImportJSON> result = importJSONRepository.findAll();
    return result;
  }

  @Override
  public Optional<ImportJSON> findById(Integer id) {
    return importJSONRepository.findById(id);
  }

  @Override
  public void deleteById(Integer id) {
    if (!importJSONRepository.existsById(id)) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND);
    }
    importJSONRepository.deleteById(id);
  }
}
