package com.tamanna.demo.model.repository;

import com.tamanna.demo.model.entity.InterviewerSlot;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InterviewerSlotRepository extends JpaRepository<InterviewerSlot, Integer> {

    List<InterviewerSlot> queryAvailableSlots(List<Integer> interviewerIds, Integer candidateId);
}