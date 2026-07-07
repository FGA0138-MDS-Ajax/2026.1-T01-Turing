package br.com.seuespacounb.turing.service;

import br.com.seuespacounb.turing.dto.response.GoogleCalendarResponse;
import br.com.seuespacounb.turing.entity.Solicitacao;
import br.com.seuespacounb.turing.entity.StatusSolicitacao;
import br.com.seuespacounb.turing.exception.BadRequestException;
import br.com.seuespacounb.turing.exception.NotFoundException;
import br.com.seuespacounb.turing.repository.SolicitacaoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

@Service
@RequiredArgsConstructor
public class GoogleCalendarService {
    private final SolicitacaoRepository solicitacaoRepository;

    private Solicitacao pegarSolicitacao(Long solicitacaoId) throws NotFoundException, BadRequestException{
        Solicitacao solicitacao = solicitacaoRepository
                .findByIdAndStatus(solicitacaoId, StatusSolicitacao.APROVADA)
                .orElseThrow(()-> new NotFoundException("A solicitação não foi encontrada ou não foi aprovada."));
        if(solicitacao.getDataUso().isBefore(LocalDate.now()))
            throw new BadRequestException("Não é possível exportar reservas com data anterior ao dia atual.");
        return solicitacao;
    }

    private String formatarDataHorario(LocalDate dataSolicitacao, LocalTime horarioSolicitacao){
        LocalDateTime dateTime = LocalDateTime.of(dataSolicitacao, horarioSolicitacao);
        return dateTime.format(DateTimeFormatter.ofPattern("yyyyMMdd'T'HHmmss"));
    }

    private String montarUrlGoogleCalendar(Solicitacao solicitacao){
        String titulo = String.format("Sala reservada (%s)", solicitacao.getHorarioSala().getSala().getNome());
        String inicioHora = formatarDataHorario(solicitacao.getDataUso(), solicitacao.getHorarioSala().getInicioHora());
        String fimHora = formatarDataHorario(solicitacao.getDataUso(), solicitacao.getHorarioSala().getFimHora());
        String localizacao = solicitacao.getHorarioSala().getSala().getLocalizacao();
        return UriComponentsBuilder
                .fromUriString("https://calendar.google.com/calendar/render")
                .queryParam("action", "TEMPLATE")
                .queryParam("text", titulo)
                .queryParam("dates", inicioHora + "/" + fimHora)
                .queryParam("ctz", "America/Sao_Paulo")
                .queryParam("details", solicitacao.getMotivo())
                .queryParam("location", localizacao)
                .toUriString();
    }

    public GoogleCalendarResponse retornarUrlGoogleCalendar(Long solicitacaoId) throws NotFoundException, BadRequestException{
        Solicitacao solicitacao = pegarSolicitacao(solicitacaoId);
        String url = montarUrlGoogleCalendar(solicitacao);
        return new GoogleCalendarResponse(url);
    }
}