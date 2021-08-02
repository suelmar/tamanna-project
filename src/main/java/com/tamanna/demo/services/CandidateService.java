package com.tamanna.demo.services;

import com.tamanna.demo.exception.EntityNotFoundError;
import com.tamanna.demo.exception.EntityAlreadyExistsError;
import com.tamanna.demo.exception.ValidationError;
import com.tamanna.demo.model.dto.CandidateDTO;
import com.tamanna.demo.model.dto.CandidateSlotDTO;
import com.tamanna.demo.model.entity.Candidate;
import com.tamanna.demo.model.repository.CandidateRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CandidateService {

    private ModelMapper mapper = new ModelMapper();

    @Autowired
    private CandidateRepository repository;

    @Autowired
    private CandidateSlotService slotService;

    protected Candidate getById(final Integer candidateId) throws EntityNotFoundError {
        final Optional<Candidate> optional = repository.findById(candidateId);

        if (!optional.isPresent()) {
            throw new EntityNotFoundError(Candidate.class, candidateId);
        }

        return optional.get();
    }

    public CandidateDTO find(final Integer candidateId) throws EntityNotFoundError {
        return mapper.map(getById(candidateId), CandidateDTO.class);
    }

    protected boolean isCandidateExists(final Candidate newCandidate) {
        final Optional<Candidate> optionalCandidate = repository.findOne(Example.of(new Candidate().setName(newCandidate.getName())));
        return optionalCandidate.isPresent() && optionalCandidate.get().getId() != newCandidate.getId();
    }

    public CandidateDTO create(final CandidateDTO candidateDTO) throws EntityAlreadyExistsError {
        Candidate candidate = mapper.map(candidateDTO, Candidate.class);

        if (isCandidateExists(candidate)) {
            throw new EntityAlreadyExistsError();
        }

        return mapper.map(repository.save(candidate), CandidateDTO.class);
    }

    public CandidateDTO update(final Integer candidateId, final CandidateDTO candidateDTO)
            throws EntityNotFoundError, EntityAlreadyExistsError {
        //validate

        final Candidate modifiedCandidate = getById(candidateId);
        modifiedCandidate.setName(candidateDTO.getName());

        if (isCandidateExists(modifiedCandidate)) {
            throw new EntityAlreadyExistsError();
        }

        return mapper.map(repository.save(modifiedCandidate), CandidateDTO.class);
    }

    public void delete(final Integer candidateId) throws EntityNotFoundError {
        //check if interviwer has slots!

        final Candidate candidate = getById(candidateId);
        repository.delete(candidate);
    }

    public List<CandidateSlotDTO> findSlotsByCandidate(final Integer candidateId) throws EntityNotFoundError {
        final Candidate candidate = getById(candidateId);

        return slotService.findAll(candidate);
    }

    public CandidateSlotDTO createSlot(Integer candidateId, CandidateSlotDTO candidateSlotDTO)
            throws EntityNotFoundError, ValidationError {

        final Candidate candidate = getById(candidateId);
        return slotService.create(candidate, candidateSlotDTO);
    }
}