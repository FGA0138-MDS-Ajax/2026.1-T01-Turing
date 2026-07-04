package br.com.seuespacounb.turing.validation;

import br.com.seuespacounb.turing.dto.request.HorarioSalaRequestDTO;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class HorarioValidoValidator implements ConstraintValidator<HorarioValido, HorarioSalaRequestDTO> {

    @Override
    public boolean isValid(HorarioSalaRequestDTO dto, ConstraintValidatorContext context) {
        if (dto.inicioHora() == null || dto.fimHora() == null) {
            return true;
        }
        return dto.inicioHora().isBefore(dto.fimHora());
    }
}