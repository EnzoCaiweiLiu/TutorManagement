package unsw.comp9900.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import unsw.comp9900.Result;
import unsw.comp9900.pojo.Verify;
import unsw.comp9900.service.iVerifyService;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class VerifyControllerTest {

    @Mock
    private iVerifyService verifyService;

    @InjectMocks
    private VerifyController controller;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void queryAllVerify() throws Exception {
        List<Verify> expectedList = new ArrayList<>();
        when(verifyService.queryAllVerify()).thenReturn(expectedList);

        Result<List<Verify>> result = controller.queryAll();

        assertEquals(1, result.getCode());
        assertEquals("success", result.getMsg());
        assertEquals(expectedList, result.getData());
        verify(verifyService).queryAllVerify();
    }

    @Test
    void sendEmail() throws Exception {
        Verify verify = new Verify();
        verify.setEmail("example@example.com");

        Result<String> result = controller.sendEmail(verify);

        assertEquals(1, result.getCode());
        assertEquals("Success", result.getMsg());
        verify(verifyService).addVerify(any(Verify.class));
    }

    @Test
    void verifyCode() throws Exception {
        Verify verify = new Verify();
        verify.setEmail("example@example.com");
        verify.setCode(123456);

        List<Verify> list = new ArrayList<>();
        Verify existingVerify = new Verify();
        existingVerify.setEmail("example@example.com");
        existingVerify.setCode(123456);
        existingVerify.setTime(new Date());
        list.add(existingVerify);

        when(verifyService.findByEmail(anyString())).thenReturn(list);

        Result<String> result = controller.queryAll(verify);

        assertNotNull(result);
        verify(verifyService).findByEmail(anyString());
    }
}
