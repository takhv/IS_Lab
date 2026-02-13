package ru.itmo.humanbeingsystem.repository;

import jakarta.persistence.QueryHint;
import java.util.List;
import org.hibernate.jpa.HibernateHints;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.itmo.humanbeingsystem.model.HumanBeing;
import ru.itmo.humanbeingsystem.model.WeaponType;

@Repository
public interface HumanBeingRepository extends JpaRepository<HumanBeing, Integer> {
  @Query("SELECT COUNT(h) FROM HumanBeing h WHERE h.weaponType > :weaponType")
  long countWeaponTypeGreaterThan(@Param("weaponType") WeaponType weaponType);

  @Query("SELECT h FROM HumanBeing h WHERE h.soundtrackName < :soundtrackName")
  List<HumanBeing> soundtrackNameLessThan(@Param("soundtrackName") String soundtrackName);

  @Query("SELECT DISTINCT h.weaponType FROM HumanBeing h")
  List<WeaponType> uniqWeaponTypes();

  List<HumanBeing> findByName(String name);

  List<HumanBeing> findByMood(String mood);

  List<HumanBeing> findByWeaponType(WeaponType weaponType);

  @Override
  @QueryHints(@QueryHint(name = HibernateHints.HINT_CACHEABLE, value = "true"))
  List<HumanBeing> findAll();
}
