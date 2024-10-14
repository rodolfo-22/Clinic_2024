package org.clinica.parcial.repositories;

import org.clinica.parcial.domain.entities.History;
import org.clinica.parcial.domain.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.UUID;
@Repository
public interface HistoryRepository extends JpaRepository<History, UUID> {
    List<History> findByUserOrderByTimestampDesc(User user);
    List<History> findByUserAndTimestampAfterOrderByTimestampDesc(User user, Date startDate);
    List<History> findByUserAndTimestampBeforeOrderByTimestampDesc(User user, Date endDate);
    List<History> findByUserAndTimestampBetweenOrderByTimestampDesc(User user, Date startDate, Date endDate);

    List<History> findByUser(User user);
}
