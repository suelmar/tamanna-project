package com.tamanna.demo.model.dto;

import java.time.LocalDateTime;

public class CandidateSlotDTO {

    private Integer id;

    private LocalDateTime slotDate;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public LocalDateTime getSlotDate() {
        return slotDate;
    }

    public void setSlotDate(LocalDateTime slotDate) {
        this.slotDate = slotDate;
    }
}