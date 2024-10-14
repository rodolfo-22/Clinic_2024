package org.clinica.parcial.services.impl;

import org.clinica.parcial.domain.entities.History;
import org.clinica.parcial.domain.entities.User;
import org.clinica.parcial.repositories.HistoryRepository;
import org.clinica.parcial.services.HistoryService;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class HistoryServiceImpl implements HistoryService {
        final HistoryRepository historyRepository;

    public HistoryServiceImpl(HistoryRepository historyRepository) {

        this.historyRepository = historyRepository;
    }

    @Override
    public List<History> findHistoryByUserAndDateRange(User user, Date startDate, Date endDate) {
        if (startDate == null && endDate == null) {
            return historyRepository.findByUserOrderByTimestampDesc(user);
        } else if (startDate != null && endDate == null) {
            return historyRepository.findByUserAndTimestampAfterOrderByTimestampDesc(user, startDate);
        } else if (startDate == null && endDate != null) {
            return historyRepository.findByUserAndTimestampBeforeOrderByTimestampDesc(user, endDate);
        } else {
            return historyRepository.findByUserAndTimestampBetweenOrderByTimestampDesc(user, startDate, endDate);
        }
    }

    @Override
    public void createHistory(History history) {
        historyRepository.save(history);
    }

    @Override
    public List<History> findHistoryByUser(User user) {
        return historyRepository.findByUser(user);
    }
}
