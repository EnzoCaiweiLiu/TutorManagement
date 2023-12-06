package unsw.comp9900.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import unsw.comp9900.Result;
import unsw.comp9900.pojo.Tutor;
import unsw.comp9900.pojo.TutorWithBLOBs;
import unsw.comp9900.service.iTutorService;
import unsw.comp9900.service.iVerifyService;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TutorControllerTest {

    @Mock
    private iTutorService tutorService;

    @InjectMocks
    private TutorController controller;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void queryAll() throws Exception {
        List<TutorWithBLOBs> expectedList = new ArrayList<>();
        when(tutorService.queryAllTutor()).thenReturn(expectedList);

        Result<List<TutorWithBLOBs>> result = controller.queryAll();

        assertEquals(1, result.getCode());
        assertEquals("success", result.getMsg());
        assertEquals(expectedList, result.getData());
        verify(tutorService).queryAllTutor();
    }

    @Test
    void login() throws Exception {
        Tutor tutor = new Tutor();
        tutor.setEmail("example@example.com");
        tutor.setPassword("password");
        List<TutorWithBLOBs> list = new ArrayList<>();
        TutorWithBLOBs tutorWithBLOBs = new TutorWithBLOBs();
        tutorWithBLOBs.setEmail(tutor.getEmail());
        tutorWithBLOBs.setPassword(tutor.getPassword());
        list.add(tutorWithBLOBs);
        when(tutorService.findTutorByEmail(any(Tutor.class))).thenReturn(list);

        Result<List<TutorWithBLOBs>> result = controller.login(tutor);

        assertEquals(1, result.getCode());
        assertEquals("success", result.getMsg());
        verify(tutorService).findTutorByEmail(any(Tutor.class));
    }

    @Test
    void tutorRegister() throws Exception {
        TutorWithBLOBs tutor = new TutorWithBLOBs();
        List<TutorWithBLOBs> list = new ArrayList<>();
        when(tutorService.findTutorByEmail(any(TutorWithBLOBs.class))).thenReturn(list);

        Result<List<TutorWithBLOBs>> result = controller.register(tutor);

        assertEquals(1, result.getCode());
        assertEquals("success", result.getMsg());
        verify(tutorService).findTutorByEmail(any(TutorWithBLOBs.class));
        verify(tutorService).addTutor(any(TutorWithBLOBs.class));
    }
    @Test
    void queryTutorByEmail() throws Exception {
        Tutor tutor = new Tutor();
        tutor.setEmail("example@example.com");
        List<TutorWithBLOBs> list = new ArrayList<>();
        list.add(new TutorWithBLOBs());
        when(tutorService.findTutorByEmail(any(Tutor.class))).thenReturn(list);

        Result<List<TutorWithBLOBs>> result = controller.queryByEmail(tutor);

        assertEquals(1, result.getCode());
        assertEquals("success", result.getMsg());
        assertEquals(list, result.getData());
        verify(tutorService).findTutorByEmail(any(Tutor.class));
    }

    @Test
    void queryTutorById() throws Exception {
        Tutor tutor = new Tutor();
        tutor.setUid(1);
        List<TutorWithBLOBs> list = new ArrayList<>();
        list.add(new TutorWithBLOBs());
        when(tutorService.queryTutorById(tutor.getUid())).thenReturn(list);

        Result<List<TutorWithBLOBs>> result = controller.queryById(tutor);

        assertEquals(1, result.getCode());
        assertEquals("success", result.getMsg());
        assertEquals(list, result.getData());
        verify(tutorService).queryTutorById(tutor.getUid());
    }

    @Test
    void updateTutorPassword() throws Exception {
        Tutor tutor = new Tutor();
        tutor.setUid(1);
        tutor.setPassword("newPassword");
        TutorWithBLOBs existingTutor = new TutorWithBLOBs();
        existingTutor.setPassword("oldPassword");
        List<TutorWithBLOBs> list = new ArrayList<>();
        list.add(existingTutor);
        when(tutorService.findTutorByEmail(any(Tutor.class))).thenReturn(list);

        Result<String> result = controller.updatePassword(tutor);

        assertNotNull(result);
        verify(tutorService).findTutorByEmail(any(Tutor.class));
        verify(tutorService).updateTutor(any(TutorWithBLOBs.class));
    }

    @Test
    void updateTutor() throws Exception {
        TutorWithBLOBs tutor = new TutorWithBLOBs();
        tutor.setUid(1);
        List<TutorWithBLOBs> list = new ArrayList<>();
        list.add(tutor);
        when(tutorService.queryTutorById(tutor.getUid())).thenReturn(list);

        Result<TutorWithBLOBs> result = controller.updateStudent(tutor);

        assertEquals(1, result.getCode());
        assertEquals("success", result.getMsg());
        verify(tutorService, times(2)).queryTutorById(tutor.getUid());
        verify(tutorService).updateTutor(tutor);
    }

    @Test
    void deleteTutor() throws Exception {
        Tutor tutor = new Tutor();
        tutor.setUid(1);
        when(tutorService.deleteTutor(tutor.getUid())).thenReturn(1);

        Result<String> result = controller.deleteTutor(tutor);

        assertEquals(1, result.getCode());
        assertEquals("Delete Successful!", result.getMsg());
        verify(tutorService).deleteTutor(tutor.getUid());
    }
}
