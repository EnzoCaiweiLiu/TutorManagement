package unsw.comp9900.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import unsw.comp9900.Result;
import unsw.comp9900.pojo.Student;
import unsw.comp9900.service.iStudentService;
import unsw.comp9900.service.iVerifyService;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class StudentControllerTest {

    @Mock
    private iStudentService studentService;

    @Mock
    private iVerifyService verifyService;

    @InjectMocks
    private StudentController controller;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void queryAll() throws Exception {
        List<Student> expectedList = new ArrayList<>();
        when(studentService.queryAllStudent()).thenReturn(expectedList);

        Result<List<Student>> result = controller.queryAll();

        assertEquals(1, result.getCode());
        assertEquals("success", result.getMsg());
        assertEquals(expectedList, result.getData());
        verify(studentService).queryAllStudent();
    }

    @Test
    void login() throws Exception {
        Student student = new Student();
        student.setEmail("test@example.com");
        student.setPassword("testPassword");
        List<Student> list = new ArrayList<>();
        list.add(student);
        when(studentService.findStudentByEmail(any(Student.class))).thenReturn(list);

        Result<List<Student>> result = controller.login(student);

        assertNotNull(result);
        verify(studentService).findStudentByEmail(any(Student.class));
    }

    @Test
    void register() throws Exception {
        Student student = new Student();
        List<Student> list = new ArrayList<>();
        when(studentService.findStudentByEmail(any(Student.class))).thenReturn(list);

        Result<List<Student>> result = controller.register(student);

        assertEquals(1, result.getCode());
        verify(studentService).findStudentByEmail(any(Student.class));
        verify(studentService).addStudent(any(Student.class));
    }

    @Test
    void updatePassword() throws Exception {
        Student student = new Student();
        student.setEmail("example@example.com");
        student.setPassword("newPassword");
        List<Student> list = new ArrayList<>();
        Student existingStudent = new Student();
        existingStudent.setPassword("oldPassword");
        list.add(existingStudent);
        when(studentService.findStudentByEmail(any(Student.class))).thenReturn(list);

        Result<String> result = controller.updatePassword(student);

        assertNotNull(result);
        verify(studentService).findStudentByEmail(any(Student.class));
        verify(studentService).updateStudent(any(Student.class));
    }

    @Test
    void queryByEmail() throws Exception {
        Student student = new Student();
        student.setEmail("example@example.com");
        List<Student> list = new ArrayList<>();
        list.add(student);
        when(studentService.findStudentByEmail(any(Student.class))).thenReturn(list);

        Result<List<Student>> result = controller.queryByEmail(student);

        assertEquals(1, result.getCode());
        assertEquals("success", result.getMsg());
        assertEquals(list, result.getData());
        verify(studentService).findStudentByEmail(any(Student.class));
    }

    @Test
    void queryById() throws Exception {
        Student student = new Student();
        student.setUid(1);
        when(studentService.queryStudentById(student.getUid())).thenReturn(student);

        Result<Student> result = controller.queryById(student);

        assertEquals(1, result.getCode());
        assertEquals("success", result.getMsg());
        assertEquals(student, result.getData());
        verify(studentService).queryStudentById(student.getUid());
    }

    @Test
    void sendEmail() throws Exception {
        Student student = new Student();
        student.setEmail("alice.johnson@email.com");

        Result<String> result = controller.sendEmail(student);

        assertEquals(1, result.getCode());
        assertEquals("Success", result.getMsg());
    }

    @Test
    void updateStudent() throws Exception {
        Student student = new Student();
        student.setUid(1);
        when(studentService.queryStudentById(student.getUid())).thenReturn(student);

        Result<Student> result = controller.updateStudent(student);

        assertEquals(1, result.getCode());
        assertEquals("success", result.getMsg());
        verify(studentService, times(2)).queryStudentById(student.getUid());
        verify(studentService).updateStudent(student);
    }

    @Test
    void deleteTutor() throws Exception {
        Student student = new Student();
        student.setUid(1);
        when(studentService.deleteStudent(student.getUid())).thenReturn(1);

        Result<String> result = controller.deleteTutor(student);

        assertEquals(1, result.getCode());
        assertEquals("Delete Successful!", result.getMsg());
        verify(studentService).deleteStudent(student.getUid());
    }

}
