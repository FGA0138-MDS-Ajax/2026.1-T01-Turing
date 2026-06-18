package br.com.seuespacounb.turing.service;

import br.com.seuespacounb.turing.dto.HorarioSalaRequestDTO;
import br.com.seuespacounb.turing.dto.HorarioSalaResponseDTO;
import br.com.seuespacounb.turing.entity.HorarioSala;
import br.com.seuespacounb.turing.entity.Sala;
import br.com.seuespacounb.turing.entity.StatusHorario;
import br.com.seuespacounb.turing.exception.ConflictException;
import br.com.seuespacounb.turing.exception.NotFoundException;
import br.com.seuespacounb.turing.mapstruct.HorarioSalaMapper;
import br.com.seuespacounb.turing.repository.HorarioSalaRepository;
import br.com.seuespacounb.turing.repository.SalaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class

HorarioSalaService {
    private final HorarioSalaRepository horarioRepository;
    private final SalaRepository salaRepository;
    private final HorarioSalaMapper mapper;

    public HorarioSalaResponseDTO salvarHorario(HorarioSalaRequestDTO horarioRequestDTO)throws ConflictException, NotFoundException {
        Sala sala = salaRepository.findById(horarioRequestDTO.salaId())
                .orElseThrow(()-> new NotFoundException("A sala não foi encontrada"));
        HorarioSala novoHorario = mapper.paraHorarioSala(horarioRequestDTO);
        novoHorario.setSala(sala);
        boolean conflitoHorario = horarioRepository.conflito(novoHorario.getSala().getId(), novoHorario.getFimPeriodo(),
                novoHorario.getInicioPeriodo(), novoHorario.getDiaSemana(),
                novoHorario.getFimHora(), novoHorario.getInicioHora());
        if(conflitoHorario) {
            throw new ConflictException("Infelizmente não foi possivel salvar este horário, pois houve conflito de horário na sala escolhida.");
        }
        return mapper.paraHorarioResponseDTO(horarioRepository.saveAndFlush(novoHorario));
    }

    @Transactional(readOnly = true)
    public List<HorarioSalaResponseDTO> listarHorariosPorSala(Long salaId){
        return mapper.paraListaHorarioResponseDTO(horarioRepository.findBySalaId(salaId));
    }

    @Transactional
    public void excluirHorarioPorSala(Long salaId, LocalDate inicioPeriodo, DayOfWeek diaSemana, LocalTime inicioHora)throws NotFoundException{
        boolean existeHorario = horarioRepository.existsBySalaIdAndInicioPeriodoAndDiaSemanaAndInicioHora(
                        salaId, inicioPeriodo, diaSemana, inicioHora);
        if(!existeHorario){
            throw new NotFoundException("Não foi possível excluir, pois o horário informado não foi encontrado.");
        }
        horarioRepository.deleteBySalaIdAndInicioPeriodoAndDiaSemanaAndInicioHora(salaId, inicioPeriodo, diaSemana, inicioHora);
    }

    @Transactional
    public HorarioSalaResponseDTO atualizarStatusHorario(Long id, StatusHorario novoStatus) throws NotFoundException {
        HorarioSala horario = horarioRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Horário não encontrado"));
        horario.setStatus(novoStatus);
        return mapper.paraHorarioResponseDTO(horarioRepository.save(horario));
    }
}