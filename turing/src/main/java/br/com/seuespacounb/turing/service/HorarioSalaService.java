package br.com.seuespacounb.turing.service;

import br.com.seuespacounb.turing.dto.request.HorarioSalaRequestDTO;
import br.com.seuespacounb.turing.dto.response.HorarioSalaResponseDTO;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.seuespacounb.turing.entity.HorarioSala;
import br.com.seuespacounb.turing.entity.Sala;
import br.com.seuespacounb.turing.exception.ConflictException;
import br.com.seuespacounb.turing.exception.HttpMessageNotReadableException;
import br.com.seuespacounb.turing.exception.NotFoundException;
import br.com.seuespacounb.turing.mapstruct.HorarioSalaMapper;
import br.com.seuespacounb.turing.repository.HorarioSalaRepository;
import br.com.seuespacounb.turing.repository.SalaRepository;
import br.com.seuespacounb.turing.repository.SolicitacaoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
public class HorarioSalaService {

    private final SolicitacaoRepository solicitacaoRepository;
    private final HorarioSalaRepository horarioRepository;
    private final SalaRepository salaRepository;
    private final HorarioSalaMapper mapper;

    @Transactional
    public HorarioSalaResponseDTO salvarHorario(HorarioSalaRequestDTO dto)
            throws NotFoundException, ConflictException, HttpMessageNotReadableException {

        Sala sala = salaRepository.findById(dto.salaId())
                .orElseThrow(() -> new NotFoundException("Sala não encontrada com id: " + dto.salaId()));

        boolean temConflito = horarioRepository.existeConflito(
                dto.salaId(),
                dto.diaSemana(),
                dto.inicioHora(),
                dto.fimHora(),
                -1L);

        if (temConflito) {
            throw new ConflictException("Já existe um horário cadastrado neste dia e horário para esta sala.");
        }

        HorarioSala horario = mapper.paraHorarioSala(dto);
        horario.setSala(sala);

        return mapper.paraHorarioResponseDTO(horarioRepository.saveAndFlush(horario));
    }

    @Transactional(readOnly = true)
    public List<HorarioSalaResponseDTO> listarHorariosPorSala(Long salaId) throws NotFoundException {
        salaRepository.findById(salaId)
                .orElseThrow(() -> new NotFoundException("Sala não encontrada com id: " + salaId));

        return mapper.paraListaHorarioResponseDTO(horarioRepository.findBySalaId(salaId));
    }

    @Transactional(readOnly = true)
    public List<HorarioSalaResponseDTO> listarTodosHorarios() {
        return mapper.paraListaHorarioResponseDTO(horarioRepository.findAll());
    }

    @Transactional
    public void excluirHorario(Long id) throws NotFoundException, ConflictException {
        HorarioSala horario = horarioRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Horário não encontrado com id: " + id));

        if (solicitacaoRepository.existsByHorarioSalaId(id)) {
            throw new ConflictException(
                    "Não é possível excluir este horário pois existem solicitações vinculadas a ele."
            );
        }

        horarioRepository.delete(horario);
    }

    @Transactional(readOnly = true)
    public HorarioSalaResponseDTO buscarPorId(Long id) throws NotFoundException {
        HorarioSala horario = horarioRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Horário não encontrado com id: " + id));

        return mapper.paraHorarioResponseDTO(horario);
    }
}