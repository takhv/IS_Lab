package ru.itmo.humanbeingsystem.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "imports")
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class ImportJSON {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @Column(name = "status", nullable = false)
  @Enumerated(EnumType.STRING)
  private ImportStatus status;

  @Column(name = "import_time", nullable = false)
  private LocalDateTime importTime;

  @Column(name = "objects_count")
  private Integer objectsCount;

  @Column(name = "file_name")
  private String fileName;
}
