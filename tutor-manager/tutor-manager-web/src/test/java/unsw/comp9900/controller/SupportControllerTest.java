package unsw.comp9900.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import unsw.comp9900.Result;
import unsw.comp9900.pojo.SupportWithBLOBs;
import unsw.comp9900.service.iSupportService;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class SupportControllerTest {

    @Mock
    private iSupportService supportService;

    @InjectMocks
    private SupportController controller;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void queryAll() throws Exception {
        List<SupportWithBLOBs> expectedList = new ArrayList<>();
        when(supportService.queryAllSupport()).thenReturn(expectedList);

        Result<List<SupportWithBLOBs>> result = controller.queryAll();

        assertEquals(1, result.getCode());
        assertEquals("success", result.getMsg());
        assertEquals(expectedList, result.getData());
        verify(supportService).queryAllSupport();
    }

    @Test
    void queryByEmail() throws Exception {
        SupportWithBLOBs support = new SupportWithBLOBs();
        support.setEmail("example@example.com");
        List<SupportWithBLOBs> list = new ArrayList<>();
        list.add(support);
        when(supportService.findSupportByEmail(anyString())).thenReturn(list);

        Result<List<SupportWithBLOBs>> result = controller.queryByEmail(support);

        assertEquals(1, result.getCode());
        assertEquals("success", result.getMsg());
        assertEquals(list, result.getData());
        verify(supportService).findSupportByEmail(anyString());
    }

    @Test
    void queryById() throws Exception {
        SupportWithBLOBs support = new SupportWithBLOBs();
        support.setSid(1);
        when(supportService.querySupportById(support.getSid())).thenReturn(support);

        Result<SupportWithBLOBs> result = controller.queryById(support);

        assertEquals(1, result.getCode());
        assertEquals("success", result.getMsg());
        assertEquals(support, result.getData());
        verify(supportService).querySupportById(support.getSid());
    }

    @Test
    void addSupport() throws Exception {
        SupportWithBLOBs support = new SupportWithBLOBs();

        Result<String> result = controller.addRate(support);

        assertEquals(1, result.getCode());
        assertEquals("Add support successful!", result.getMsg());
        verify(supportService).addSupport(any(SupportWithBLOBs.class));
    }

    @Test
    void updateSupport() throws Exception {
        SupportWithBLOBs support = new SupportWithBLOBs();
        support.setSid(1);
        when(supportService.querySupportById(support.getSid())).thenReturn(support);

        Result<SupportWithBLOBs> result = controller.updateCourse(support);

        assertEquals(1, result.getCode());
        assertEquals("success", result.getMsg());
        verify(supportService, times(2)).querySupportById(support.getSid());
        verify(supportService).updateSupport(support);
    }

    @Test
    void deleteSupport() throws Exception {
        SupportWithBLOBs support = new SupportWithBLOBs();
        support.setSid(1);
        when(supportService.deleteSupport(support.getSid())).thenReturn(1);

        Result<String> result = controller.deleteRate(support);

        assertEquals(1, result.getCode());
        assertEquals("Delete successful!", result.getMsg());
        verify(supportService).deleteSupport(support.getSid());
    }
}
