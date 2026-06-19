package br.com.seuespacounb.turing.service;

import br.com.seuespacounb.turing.dto.request.HorarioSalaRequestDTO;
import br.com.seuespacounb.turing.dto.response.HorarioSalaResponseDTO;
import br.com.seuespacounb.turing.entity.HorarioSala;
import br.com.seuespacounb.turing.entity.Sala;
import br.com.seuespacounb.turing.exception.ConflictException;
import br.com.seuespacounb.turing.exception.NotFoundException;
import br.com.seuespacounb.turing.mapstruct.HorarioSalaMapper;
import br.com.seuespacounb.turing.repository.HorarioSalaRepository;
import br.com.seuespacounb.turing.repository.SalaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
public class HorarioSalaService {

    private final HorarioSalaRepository horarioRepository;
    private final SalaRepository salaRepository;
    private final HorarioSalaMapper mapper;

    @Transactional
    public HorarioSalaResponseDTO salvarHorario(HorarioSalaRequestDTO dto)
            throws NotFoundException, ConflictException {

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

    @Transactional
    public void excluirHorario(Long id) throws NotFoundException {
        HorarioSala horario = horarioRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Horário não encontrado com id: " + id));

        horarioRepository.delete(horario);
    }

    @Transactional(readOnly = true)
    public HorarioSalaResponseDTO buscarPorId(Long id) throws NotFoundException {
        HorarioSala horario = horarioRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Horário não encontrado com id: " + id));

        return mapper.paraHorarioResponseDTO(horario);
    }
}