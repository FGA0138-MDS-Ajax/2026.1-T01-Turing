package br.com.seuespacounb.turing.dto.response;

import br.com.seuespacounb.turing.entity.StatusSolicitacao;

import java.time.LocalDateTime;

public record SolicitacaoResponseDTO(
        Long id,
<<<<<<< Updated upstream
        String motivoSolicitacao,
=======
        String motivo,
        Integer quantidadeParticipantes,
>>>>>>> Stashed changes
        LocalDateTime dataSolicitacao,
        StatusSolicitacao status,
        String justificativa,
        Long usuarioId,
        Long horarioId
) {}