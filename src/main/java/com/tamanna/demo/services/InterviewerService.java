package com.tamanna.demo.services;

import com.tamanna.demo.exception.EntityNotFoundError;
import com.tamanna.demo.exception.EntityAlreadyExistsError;
import com.tamanna.demo.exception.ValidationError;
import com.tamanna.demo.model.dto.InterviewerDTO;
import com.tamanna.demo.model.dto.InterviewerSlotDTO;
import com.tamanna.demo.model.entity.Interviewer;
import com.tamanna.demo.model.repository.InterviewerRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class InterviewerService {

    private ModelMapper mapper = new ModelMapper();

    @Autowired
    private InterviewerRepository repository;

    @Autowired
    private InterviewerSlotService slotService;

    protected Interviewer getById(final Integer interviewerId) throws EntityNotFoundError {
        final Optional<Interviewer> optional = repository.findById(interviewerId);

        if (!optional.isPresent()) {
            throw new EntityNotFoundError(Interviewer.class, interviewerId);
        }

        return optional.get();
    }

    public InterviewerDTO find(final Integer interviewerId) throws EntityNotFoundError {
        return mapper.map(getById(interviewerId), InterviewerDTO.class);
    }

    public List<InterviewerDTO> findAll() {
        List<Interviewer> interviewers = repository.findAll();

        return interviewers.stream()
                .map(interviewer -> mapper.map(interviewer, InterviewerDTO.class))
                .collect(Collectors.toList());
    }

    protected boolean isInterviewerExists(final Interviewer interviewer) {
        return repository.exists(Example.of(new Interviewer().setName(interviewer.getName())));
    }

    public InterviewerDTO create(final InterviewerDTO interviewerDTO) throws EntityAlreadyExistsError {
        //validate

        Interviewer interviewer = mapper.map(interviewerDTO, Interviewer.class);

        if (isInterviewerExists(interviewer)) {
            throw new EntityAlreadyExistsError();
        }

        return mapper.map(repository.save(interviewer), InterviewerDTO.class);
    }

    public InterviewerDTO update(final Integer interviewerId, final InterviewerDTO interviewerDTO)
            throws EntityNotFoundError, EntityAlreadyExistsError {
        //validate

        final Interviewer modifiedInterviewer = getById(interviewerId);
        modifiedInterviewer.setName(interviewerDTO.getName());

        if (isInterviewerExists(modifiedInterviewer)) {
            throw new EntityAlreadyExistsError();
        }

        return mapper.map(repository.save(modifiedInterviewer), InterviewerDTO.class);
    }

    public void delete(final Integer interviewerId) throws EntityNotFoundError {
        //check if interviwer has slots!

        final Interviewer interviewer = getById(interviewerId);
        repository.delete(interviewer);
    }

    public List<InterviewerSlotDTO> findSlotsByInterviewer(final Integer interviewerId) throws EntityNotFoundError {
        final Interviewer interviewer = getById(interviewerId);



        return slotService.findAll(interviewer);
    }

    public InterviewerSlotDTO createSlot(Integer interviewerId, InterviewerSlotDTO interviewerSlotDTO)
            throws EntityNotFoundError, ValidationError {

        final Interviewer interviewer = getById(interviewerId);
        return slotService.create(interviewer, interviewerSlotDTO);
    }
}