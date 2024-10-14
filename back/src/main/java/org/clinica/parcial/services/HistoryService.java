package org.clinica.parcial.services;

import org.clinica.parcial.domain.entities.History;
import org.clinica.parcial.domain.entities.User;

import java.util.Date;
import java.util.List;

public interface HistoryService {
    List<History> findHistoryByUserAndDateRange(User user, Date startDate, Date endDate);

    void createHistory(History req);
    public List<History> findHistoryByUser(User user);
}
