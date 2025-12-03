package ru.itmo.humanbeingsystem.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.itmo.humanbeingsystem.model.ImportJSON;
import ru.itmo.humanbeingsystem.service.ImportJSONService;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/import")
public class ImportController {

  @Autowired private ImportJSONService importService;

  @PostMapping(consumes = "multipart/form-data")
  public ResponseEntity<ImportJSON> importHumanBeings(@RequestParam("file") MultipartFile file) {
    ImportJSON result = importService.importFromJSON(file);
    return ResponseEntity.ok(result);
  }

  @GetMapping("/history")
  public ResponseEntity<List<ImportJSON>> getImportHistory() {
    List<ImportJSON> result = importService.findAll();
    return ResponseEntity.ok(result);
  }
}
