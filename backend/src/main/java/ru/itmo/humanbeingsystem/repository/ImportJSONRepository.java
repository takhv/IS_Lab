package ru.itmo.humanbeingsystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.itmo.humanbeingsystem.model.ImportJSON;

@Repository
public interface ImportJSONRepository extends JpaRepository<ImportJSON, Integer> {}
