package com.tamanna.demo.model.repository;

import com.tamanna.demo.model.entity.Candidate;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CandidateRepository extends JpaRepository<Candidate, Integer> {

}