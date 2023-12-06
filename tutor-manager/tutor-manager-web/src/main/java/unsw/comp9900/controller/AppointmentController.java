package unsw.comp9900.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import unsw.comp9900.Result;
import unsw.comp9900.pojo.Appointment;
import unsw.comp9900.pojo.AppointmentDTO;
import unsw.comp9900.pojo.Course;
import unsw.comp9900.pojo.CourseDTO;
import unsw.comp9900.service.iAppointmentService;
import unsw.comp9900.service.iCourseService;

import java.util.List;

@Controller
@RequestMapping("/appointment")
public class AppointmentController {

    @Autowired
    private iAppointmentService service;

    @Autowired
    private iCourseService courseService;

    @GetMapping("/queryAllAppointment")
    @ResponseBody
    public Result<List<AppointmentDTO>> queryAll() throws Exception{
        List<AppointmentDTO> list = service.queryAllAppointment();
        return Result.success(list);
    }

    @PostMapping("/findAppointmentBySelective")
    @ResponseBody
    public Result<List<AppointmentDTO>> findAppointment(@RequestBody AppointmentDTO appointmentDTO) throws Exception{
        List<AppointmentDTO> list = service.selectAppointmentBySelective(appointmentDTO);
        if (list.get(0) == null || list.isEmpty() || list.size() == 0) return Result.error("Appointment not found");
        return Result.success(list);
    }

    @PostMapping("/approved")
    @ResponseBody
    public Result<AppointmentDTO> approved(@RequestBody Appointment appointment) throws Exception{
        List<Appointment> list = service.findAppointmentByPrimaryKey(appointment.getAid());
        if (list.get(0) == null || list.isEmpty() || list.size() == 0) return Result.error("Appointment not found");
        Course course = courseService.findCourseByPrimaryKey(list.get(0).getCsid());
        if (course == null) return Result.error("Course not found");
        course.setIsSelect(true);
        service.updateAppointment(appointment);
        courseService.updateCourse(course);
        List<Appointment> list1 = service.findAppointmentByPrimaryKey(appointment.getAid());
        AppointmentDTO appointmentDTO = service.appointmentToAppointmentDTO(list1.get(0));
        return Result.success(appointmentDTO);
    }

    @PostMapping("/unapproved")
    @ResponseBody
    public Result<AppointmentDTO> unapproved(@RequestBody Appointment appointment) throws Exception{
        List<Appointment> list = service.findAppointmentByPrimaryKey(appointment.getAid());
        if (list.get(0) == null || list.isEmpty() || list.size() == 0) return Result.error("Appointment not found");
        Course course = courseService.findCourseByPrimaryKey(list.get(0).getCsid());
        if (course == null) return Result.error("Course not found");
        course.setIsSelect(false);
        service.updateAppointment(appointment);
        courseService.updateCourse(course);
        List<Appointment> list1 = service.findAppointmentByPrimaryKey(appointment.getAid());
        AppointmentDTO appointmentDTO = service.appointmentToAppointmentDTO(list1.get(0));
        return Result.success(appointmentDTO);
    }

    @PostMapping("/addAppointment")
    @ResponseBody
    public Result<AppointmentDTO> addAppointment(@RequestBody Appointment appointment) throws Exception{
        service.addAppointment(appointment);
        return Result.success("Add appointment success");
    }

    @PostMapping("/updateAppointment")
    @ResponseBody
    public Result<AppointmentDTO> updateCourse(@RequestBody Appointment appointment) throws Exception{
        List<Appointment> list = service.findAppointmentByPrimaryKey(appointment.getAid());
        if (list.get(0) == null || list.isEmpty() || list.size() == 0) return Result.error("Appointment not found");
        service.updateAppointment(appointment);
        List<Appointment> list1 = service.findAppointmentByPrimaryKey(appointment.getAid());
        AppointmentDTO appointmentDTO = service.appointmentToAppointmentDTO(list1.get(0));
        return Result.success(appointmentDTO);
    }

    @PostMapping("/deleteAppointment")
    @ResponseBody
    public Result<AppointmentDTO> deleteCourse(@RequestBody Appointment appointment) throws Exception{
        List<Appointment> list = service.findAppointmentByPrimaryKey(appointment.getAid());
        System.out.println("---------->" + list.size());
        if (list.get(0) == null || list.isEmpty() || list.size() == 0) return Result.error("Course not found");
        service.deleteAppointment(appointment.getAid());
        return Result.success();
    }
}
