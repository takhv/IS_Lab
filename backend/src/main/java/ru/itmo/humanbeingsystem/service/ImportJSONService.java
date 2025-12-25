package ru.itmo.humanbeingsystem.service;

import java.util.List;
import java.util.Optional;
import org.springframework.web.multipart.MultipartFile;
import ru.itmo.humanbeingsystem.model.ImportJSON;

public interface ImportJSONService {
  ImportJSON importFromJSON(MultipartFile file);

  List<ImportJSON> findAll();

  Optional<ImportJSON> findById(Integer id);

  void deleteById(Integer id);
}
