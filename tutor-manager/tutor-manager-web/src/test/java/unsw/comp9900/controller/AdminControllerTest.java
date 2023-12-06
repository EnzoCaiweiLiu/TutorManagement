package unsw.comp9900.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import unsw.comp9900.Result;
import unsw.comp9900.pojo.Admin;
import unsw.comp9900.service.iAdminService;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AdminControllerTest {

    @Mock
    private iAdminService service;

    @InjectMocks
    private AdminController controller;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void queryAll() throws Exception {
        List<Admin> expectedList = new ArrayList<>();
        when(service.queryAllAdmin()).thenReturn(expectedList);

        Result<List<Admin>> result = controller.queryAll();

        assertEquals(1, result.getCode());
        assertEquals("success", result.getMsg());
        assertEquals(expectedList, result.getData());
        verify(service).queryAllAdmin();
    }

    @Test
    void login() throws Exception {
        Admin admin = new Admin();
        admin.setEmail("test@example.com");
        admin.setPassword("password");
        List<Admin> list = new ArrayList<>();
        list.add(admin);

        when(service.findAdminByEmail(any(Admin.class))).thenReturn(list);

        Result<List<Admin>> result = controller.login(admin);

        assertEquals(1, result.getCode());
        assertEquals("success", result.getMsg());
        verify(service).findAdminByEmail(any(Admin.class));
    }

    @Test
    void register() throws Exception {
        Admin admin = new Admin();
        admin.setEmail("new@example.com");
        List<Admin> list = new ArrayList<>();

        when(service.findAdminByEmail(any(Admin.class))).thenReturn(list);

        Result<List<Admin>> result = controller.register(admin);

        assertEquals(1, result.getCode());
        assertEquals("success", result.getMsg());
        verify(service).findAdminByEmail(any(Admin.class));
        verify(service).addAdmin(any(Admin.class));
    }

    @Test
    void queryByEmail() throws Exception {
        Admin admin = new Admin();
        admin.setEmail("existing@example.com");
        List<Admin> list = new ArrayList<>();
        list.add(admin);

        when(service.findAdminByEmail(any(Admin.class))).thenReturn(list);

        Result<List<Admin>> result = controller.queryByEmail(admin);

        assertEquals(1, result.getCode());
        assertEquals("success", result.getMsg());
        verify(service).findAdminByEmail(any(Admin.class));
    }

    @Test
    void queryById() throws Exception {
        Admin admin = new Admin();
        admin.setUid(1);
        List<Admin> list = new ArrayList<>();
        list.add(admin);

        when(service.queryAdminById(admin.getUid())).thenReturn(list);

        Result<List<Admin>> result = controller.queryById(admin);

        assertEquals(1, result.getCode());
        assertEquals("success", result.getMsg());
        verify(service).queryAdminById(admin.getUid());
    }

    @Test
    void updateStudent() throws Exception {
        Admin admin = new Admin();
        admin.setUid(1);
        List<Admin> list = new ArrayList<>();
        list.add(admin);

        when(service.queryAdminById(admin.getUid())).thenReturn(list);

        Result<Admin> result = controller.updateStudent(admin);

        assertEquals(1, result.getCode());
        assertEquals("success", result.getMsg());
        verify(service, times(2)).queryAdminById(admin.getUid()); // Expecting 2 calls
        verify(service).updateAdmin(any(Admin.class));
    }
}
