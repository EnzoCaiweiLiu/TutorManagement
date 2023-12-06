package unsw.comp9900.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import unsw.comp9900.Result;
import unsw.comp9900.pojo.Appointment;
import unsw.comp9900.pojo.AppointmentDTO;
import unsw.comp9900.pojo.Course;
import unsw.comp9900.service.iAppointmentService;
import unsw.comp9900.service.iCourseService;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AppointmentControllerTest {

    @Mock
    private iAppointmentService appointmentService;

    @Mock
    private iCourseService courseService;

    @InjectMocks
    private AppointmentController controller;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void queryAll() throws Exception {
        List<AppointmentDTO> expectedList = new ArrayList<>();
        when(appointmentService.queryAllAppointment()).thenReturn(expectedList);

        Result<List<AppointmentDTO>> result = controller.queryAll();

        assertEquals(1, result.getCode());
        assertEquals("success", result.getMsg());
        assertEquals(expectedList, result.getData());
        verify(appointmentService).queryAllAppointment();
    }

    @Test
    void findAppointmentBySelective() throws Exception {
        AppointmentDTO appointmentDTO = new AppointmentDTO();
        List<AppointmentDTO> list = new ArrayList<>();
        list.add(appointmentDTO);
        when(appointmentService.selectAppointmentBySelective(any(AppointmentDTO.class))).thenReturn(list);

        Result<List<AppointmentDTO>> result = controller.findAppointment(appointmentDTO);

        assertEquals(1, result.getCode());
        assertEquals("success", result.getMsg());
        verify(appointmentService).selectAppointmentBySelective(any(AppointmentDTO.class));
    }

    @Test
    void approved() throws Exception {
        Appointment appointment = new Appointment();
        appointment.setAid(1);
        appointment.setCsid(1);
        List<Appointment> list = new ArrayList<>();
        list.add(appointment);
        Course course = new Course();

        when(appointmentService.findAppointmentByPrimaryKey(appointment.getAid())).thenReturn(list);
        when(courseService.findCourseByPrimaryKey(appointment.getCsid())).thenReturn(course);

        Result<AppointmentDTO> result = controller.approved(appointment);

        assertEquals(1, result.getCode());
        assertEquals("success", result.getMsg());
        verify(appointmentService, times(2)).findAppointmentByPrimaryKey(appointment.getAid()); // Expecting 2 calls
        verify(courseService).findCourseByPrimaryKey(appointment.getCsid());
        verify(appointmentService).updateAppointment(any(Appointment.class));
        verify(courseService).updateCourse(any(Course.class));
    }

    @Test
    void unapproved() throws Exception {
        Appointment appointment = new Appointment();
        appointment.setAid(1);
        appointment.setCsid(1);
        List<Appointment> list = new ArrayList<>();
        list.add(appointment);
        Course course = new Course();

        when(appointmentService.findAppointmentByPrimaryKey(appointment.getAid())).thenReturn(list);
        when(courseService.findCourseByPrimaryKey(appointment.getCsid())).thenReturn(course);

        Result<AppointmentDTO> result = controller.unapproved(appointment);

        assertEquals(1, result.getCode());
        assertEquals("success", result.getMsg());
        verify(appointmentService, times(2)).findAppointmentByPrimaryKey(appointment.getAid());
        verify(courseService).findCourseByPrimaryKey(appointment.getCsid());
        verify(appointmentService).updateAppointment(any(Appointment.class));
        verify(courseService).updateCourse(any(Course.class));
    }

    @Test
    void updateAppointment() throws Exception {
        Appointment appointment = new Appointment();
        appointment.setAid(1);
        List<Appointment> list = new ArrayList<>();
        list.add(appointment);

        when(appointmentService.findAppointmentByPrimaryKey(appointment.getAid())).thenReturn(list);

        Result<AppointmentDTO> result = controller.updateCourse(appointment);

        assertEquals(1, result.getCode());
        assertEquals("success", result.getMsg());
        verify(appointmentService, times(2)).findAppointmentByPrimaryKey(appointment.getAid()); // Expecting 2 calls
        verify(appointmentService).updateAppointment(any(Appointment.class));
    }

    @Test
    void addAppointment() throws Exception {
        Appointment appointment = new Appointment();

        Result<AppointmentDTO> result = controller.addAppointment(appointment);

        assertEquals(1, result.getCode());
        assertEquals("Add appointment success", result.getMsg());
        verify(appointmentService).addAppointment(any(Appointment.class));
    }

    @Test
    void deleteAppointment() throws Exception {
        Appointment appointment = new Appointment();
        appointment.setAid(1);
        List<Appointment> list = new ArrayList<>();
        list.add(appointment);

        when(appointmentService.findAppointmentByPrimaryKey(appointment.getAid())).thenReturn(list);

        Result<AppointmentDTO> result = controller.deleteCourse(appointment);

        assertEquals(1, result.getCode());
        verify(appointmentService).findAppointmentByPrimaryKey(appointment.getAid());
        verify(appointmentService).deleteAppointment(appointment.getAid());
    }
}
