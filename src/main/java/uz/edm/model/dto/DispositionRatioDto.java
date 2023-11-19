package uz.edm.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DispositionRatioDto {

    private String employeeCode;
    private BigDecimal workingDispositionRatio;

}
