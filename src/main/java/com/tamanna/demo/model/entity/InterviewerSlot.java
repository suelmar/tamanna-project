package com.tamanna.demo.model.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import java.time.LocalDateTime;

@Entity
@Table(name  = "INTERVIEWER_SLOT",
        uniqueConstraints = {
            @UniqueConstraint(name = "uniqueDateInterviewer", columnNames = { "SLOT_DATE", "INTERVIEWER_ID" })
        })
@NamedQuery(name = "InterviewerSlot.queryAvailableSlots",
        query = "from InterviewerSlot iSlot where iSlot.interviewer.id in ?1 and exists " +
            "(from CandidateSlot cSlot where cSlot.candidate.id = ?2 and iSlot.date = cSlot.date) " +
            "order by iSlot.date asc")
public class InterviewerSlot implements Slot {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "SLOT_DATE", nullable = false)
    private LocalDateTime date;

    @ManyToOne(fetch = FetchType.LAZY)
    private Interviewer interviewer;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public Interviewer getInterviewer() {
        return interviewer;
    }

    public void setInterviewer(Interviewer interviewer) {
        this.interviewer = interviewer;
    }
}