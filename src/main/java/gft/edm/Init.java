package gft.edm;

import gft.edm.model.dto.DispositionDto;
import gft.edm.service.DispositionService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.function.Function;
import java.util.function.UnaryOperator;

@Component
@RequiredArgsConstructor
public class Init implements InitializingBean {

    private final DispositionService dispositionService;

    @Override
    public void afterPropertiesSet() throws Exception {
        dispositionService.createDisposition(new DispositionDto(LocalDate.now(), "10:00", "16:00", "NF-001"));
        dispositionService.createDisposition(new DispositionDto(LocalDate.now().plusDays(1), "10:00", "16:00", "NF-002"));
        dispositionService.createDisposition(new DispositionDto(LocalDate.now().plusDays(2), "10:00", "16:00", "NF-003"));
        dispositionService.createDisposition(new DispositionDto(LocalDate.now().plusDays(3), "10:00", "16:00", "NF-004"));
        dispositionService.createDisposition(new DispositionDto(LocalDate.now().plusDays(4), "10:00", "16:00", "NF-001"));

    }
}
