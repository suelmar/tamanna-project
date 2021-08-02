package com.tamanna.demo.model.repository;

import com.tamanna.demo.model.entity.CandidateSlot;
import com.tamanna.demo.model.entity.InterviewerSlot;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CandidateSlotRepository extends JpaRepository<CandidateSlot, Integer> {

}