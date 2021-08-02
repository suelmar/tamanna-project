package com.tamanna.demo.services;

import com.tamanna.demo.exception.ValidationError;
import com.tamanna.demo.model.SlotUtil;
import com.tamanna.demo.model.dto.CandidateSlotDTO;
import com.tamanna.demo.model.dto.InterviewerSlotDTO;
import com.tamanna.demo.model.entity.Candidate;
import com.tamanna.demo.model.entity.CandidateSlot;
import com.tamanna.demo.model.entity.Interviewer;
import com.tamanna.demo.model.entity.InterviewerSlot;
import com.tamanna.demo.model.repository.CandidateSlotRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CandidateSlotService {

    @Autowired
    private CandidateSlotRepository repository;

    private ModelMapper mapper = new ModelMapper();

    public List<CandidateSlotDTO> findAll(final Candidate candidadte) {
        List<CandidateSlot> slots = findAllByCandidate(candidadte);

        return slots.stream()
                .map(slot -> mapper.map(slot, CandidateSlotDTO.class))
                .collect(Collectors.toList());
    }

    protected List<CandidateSlot> findAllByCandidate(final Candidate interviewer) {
        final CandidateSlot filter = new CandidateSlot();
        filter.setCandidate(interviewer);

        return repository.findAll(Example.of(filter), Sort.by(
                new Sort.Order(Sort.Direction.ASC, "date")));
    }

    public CandidateSlotDTO create(final Candidate candidate, final CandidateSlotDTO candidateSlotDTO)
            throws ValidationError {

        final CandidateSlot slot = mapper.map(candidateSlotDTO, CandidateSlot.class);
        slot.setCandidate(candidate);

        SlotUtil.validateSlot(slot);

        return mapper.map(repository.save(slot), CandidateSlotDTO.class);
    }
}