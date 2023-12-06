package unsw.comp9900.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import unsw.comp9900.Result;
import unsw.comp9900.pojo.Rate;
import unsw.comp9900.pojo.RateDTO;
import unsw.comp9900.service.Impl.RateServiceImpl;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class RateControllerTest {

    @Mock
    private RateServiceImpl rateService;

    @InjectMocks
    private RateController controller;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void findRateBySelective() throws Exception {
        RateDTO rateDTO = new RateDTO();
        List<RateDTO> list = new ArrayList<>();
        list.add(rateDTO);
        when(rateService.selectRateBySelective(any(RateDTO.class))).thenReturn(list);

        Result<List<RateDTO>> result = controller.findRate(rateDTO);

        assertEquals(1, result.getCode());
        assertEquals("success", result.getMsg());
        verify(rateService).selectRateBySelective(any(RateDTO.class));
    }

    @Test
    void addRate() throws Exception {
        Rate rate = new Rate();

        Result<String> result = controller.addRate(rate);

        assertEquals(1, result.getCode());
        assertEquals("Add rate successful!", result.getMsg());
        verify(rateService).addRate(any(Rate.class));
    }

    @Test
    void deleteRate() throws Exception {
        Rate rate = new Rate();
        rate.setAid(1);
        when(rateService.deleteRate(rate.getAid())).thenReturn(1); // Assuming deletion is successful

        Result<String> result = controller.deleteRate(rate);

        assertEquals(1, result.getCode());
        assertEquals("Delete successful!", result.getMsg());
        verify(rateService).deleteRate(rate.getAid());
    }
}
