package com.tamanna.demo.services;

import com.tamanna.demo.exception.ValidationError;
import com.tamanna.demo.model.SlotUtil;
import com.tamanna.demo.model.dto.InterviewerSlotDTO;
import com.tamanna.demo.model.entity.Interviewer;
import com.tamanna.demo.model.entity.InterviewerSlot;
import com.tamanna.demo.model.repository.InterviewerSlotRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class InterviewerSlotService {

    @Autowired
    private InterviewerSlotRepository repository;

    private ModelMapper mapper = new ModelMapper();

    public List<InterviewerSlotDTO> findAll(final Interviewer interviewer) {
        List<InterviewerSlot> slots = findAllByInterviewer(interviewer);

        return slots.stream()
                .map(slot -> mapper.map(slot, InterviewerSlotDTO.class))
                .collect(Collectors.toList());
    }

    protected List<InterviewerSlot> findAllByInterviewer(final Interviewer interviewer) {
        final InterviewerSlot filter = new InterviewerSlot();
        filter.setInterviewer(interviewer);

        return repository.findAll(Example.of(filter), Sort.by(
                new Sort.Order(Sort.Direction.ASC, "date")));
    }

    public List<InterviewerSlotDTO> findAvailableSlots(List<Integer> interviewerIds, Integer candidateId) {
        final List<InterviewerSlot> slots = repository.queryAvailableSlots(interviewerIds, candidateId);

        return slots.stream()
                .map(slot -> mapper.map(slot, InterviewerSlotDTO.class))
                .collect(Collectors.toList());
    }

    public InterviewerSlotDTO create(final Interviewer interviewer, final InterviewerSlotDTO interviewerSlotDTO)
                throws ValidationError {

        final InterviewerSlot slot = mapper.map(interviewerSlotDTO, InterviewerSlot.class);
        slot.setInterviewer(interviewer);

        SlotUtil.validateSlot(slot);

        return mapper.map(repository.save(slot), InterviewerSlotDTO.class);
    }
}