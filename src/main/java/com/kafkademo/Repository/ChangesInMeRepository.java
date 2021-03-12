package com.kafkademo.Repository;

import com.kafkademo.models.ChangesInMe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface ChangesInMeRepository extends JpaRepository<ChangesInMe, Long> {

    List<ChangesInMe> findByReadFalse();

    List<ChangesInMe> findByBloodSugarLevelGreaterThan(Integer bloodSugarLevel);

    @Transactional
    @Modifying
    @Query("update ChangesInMe cim set cim.read = true where cim.id = :id")
    void setChangeInMeRead(@Param("id") Integer id);

}
