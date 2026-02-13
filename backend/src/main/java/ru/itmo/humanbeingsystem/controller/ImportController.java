package ru.itmo.humanbeingsystem.controller;

import java.io.InputStream;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.itmo.humanbeingsystem.aspect.CacheStatsTracked;
import ru.itmo.humanbeingsystem.model.ImportJSON;
import ru.itmo.humanbeingsystem.service.ImportJSONService;
import ru.itmo.humanbeingsystem.service.MinioService;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/import")
public class ImportController {

  @Autowired private ImportJSONService importService;

  @Autowired private MinioService minioService;

  @PostMapping(consumes = "multipart/form-data")
  public ResponseEntity<ImportJSON> importHumanBeings(@RequestParam("file") MultipartFile file) {
    ImportJSON result = importService.importFromJSON(file);
    return ResponseEntity.ok(result);
  }

  @CacheStatsTracked
  @GetMapping("/history")
  public ResponseEntity<List<ImportJSON>> getImportHistory() {
    List<ImportJSON> result = importService.findAll();
    return ResponseEntity.ok(result);
  }

  @GetMapping("/history/{id}/download")
  public ResponseEntity<InputStreamResource> downloadImportFile(@PathVariable Integer id) {
    ImportJSON operation =
        importService
            .findById(id)
            .orElseThrow(() -> new RuntimeException("Import operation not found"));

    String fileName = operation.getFileName();
    if (fileName == null) {
      return ResponseEntity.notFound().build();
    }

    try {
      InputStream inputStream = minioService.downloadFile(fileName);
      InputStreamResource resource = new InputStreamResource(inputStream);

      return ResponseEntity.ok()
          .contentType(MediaType.APPLICATION_OCTET_STREAM)
          .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileName + "\"")
          .body(resource);
    } catch (Exception e) {
      System.err.println("error download file: " + e.getMessage());
      return ResponseEntity.status(500).build();
    }
  }

  @DeleteMapping("/history/{id}")
  public ResponseEntity<Void> deleteImportFile(@PathVariable Integer id) {
    ImportJSON operation =
        importService
            .findById(id)
            .orElseThrow(() -> new RuntimeException("Import operation not found"));

    String fileName = operation.getFileName();
    if (fileName == null) {
      return ResponseEntity.status(500).build();
    }

    try {
      minioService.deleteFile(fileName);
      importService.deleteById(id);
    } catch (Exception e) {
      e.printStackTrace();
      return ResponseEntity.status(504).build();
    }

    return ResponseEntity.noContent().build();
  }
}
