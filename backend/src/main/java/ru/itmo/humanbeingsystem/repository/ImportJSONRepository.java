package ru.itmo.humanbeingsystem.repository;

import jakarta.persistence.QueryHint;
import java.util.List;
import org.hibernate.jpa.HibernateHints;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.stereotype.Repository;
import ru.itmo.humanbeingsystem.model.ImportJSON;

@Repository
public interface ImportJSONRepository extends JpaRepository<ImportJSON, Integer> {

  @Query("SELECT i FROM ImportJSON i")
  @QueryHints(@QueryHint(name = HibernateHints.HINT_CACHEABLE, value = "true"))
  @Override
  List<ImportJSON> findAll();
}
