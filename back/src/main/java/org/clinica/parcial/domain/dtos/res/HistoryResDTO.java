package org.clinica.parcial.domain.dtos.res;

import java.util.Date;
import java.util.UUID;

public class HistoryResDTO {
    private UUID id;
    private Date timestamp;
    private String reason;

    public HistoryResDTO(UUID id, Date timestamp, String reason) {
        this.id = id;
        this.timestamp = timestamp;
        this.reason = reason;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
}
