package unsw.comp9900.service.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import unsw.comp9900.mapper.*;
import unsw.comp9900.pojo.*;
import unsw.comp9900.service.iAppointmentService;

import java.util.ArrayList;
import java.util.List;

@Service
public class AppointmentServiceImpl implements iAppointmentService {

    @Autowired
    private AppointmentMapper appointmentMapper;

    @Autowired
    private CourseMapper courseMapper;

    @Autowired
    private CourseTypeMapper courseTypeMapper;

    @Autowired
    private TutorMapper tutorMapper;

    @Autowired
    private StudentMapper studentMapper;




    @Override
    public Appointment appointmentDTOToAppointment(AppointmentDTO appointmentDTO) throws Exception {
        return null;
    }

    @Override
    public AppointmentDTO appointmentToAppointmentDTO(Appointment appointment) throws Exception {
        Course course = courseMapper.selectByPrimaryKey(appointment.getCsid());
        CourseType courseType = courseTypeMapper.selectByPrimaryKey(course.getCid());
        Tutor tutor = tutorMapper.selectByPrimaryKey(appointment.getTid());
        Student student = studentMapper.selectByPrimaryKey(appointment.getTid());
        AppointmentDTO appointmentDTO = new AppointmentDTO();
        appointmentDTO.setAid(appointment.getAid());
        appointmentDTO.setCsid(appointment.getCsid());
        appointmentDTO.setCname(courseType.getName());
        appointmentDTO.setTid(appointment.getTid());
        appointmentDTO.setTFName(tutor.getFirstName());
        appointmentDTO.setTMName(tutor.getMiddleName());
        appointmentDTO.setTLName(tutor.getLastName());
        appointmentDTO.setSid(appointment.getSid());
        appointmentDTO.setSFName(student.getFirstName());
        appointmentDTO.setSMName(student.getMiddleName());
        appointmentDTO.setSLName(student.getLastName());
        appointmentDTO.setStartTime(appointment.getStartTime());
        appointmentDTO.setFinishTime(appointment.getFinishTime());
        appointmentDTO.setLocation(course.getLocation());
        appointmentDTO.setApprove(appointment.getApprove());
        appointmentDTO.setIsDelete(appointment.getIsDelete());
        return appointmentDTO;
    }

    @Override
    public List<AppointmentDTO> queryAllAppointment() throws Exception {
        return appointmentMapper.selectByAppointmentDTO();
    }

    @Override
    public List<AppointmentDTO> selectAppointmentBySelective(AppointmentDTO appointmentDTO) throws Exception {
        return appointmentMapper.getAppointmentDTO(appointmentDTO);
    }

    @Override
    public Integer addAppointment(Appointment appointment) throws Exception {
        return appointmentMapper.insertSelective(appointment);
    }

    @Override
    public int updateAppointment(Appointment appointment) throws Exception {
        return appointmentMapper.updateByPrimaryKeySelective(appointment);
    }

    @Override
    public Integer deleteAppointment(Integer id) throws Exception {
        return appointmentMapper.deleteByPrimaryKey(id);
    }

    @Override
    public List<Appointment> findAppointmentByPrimaryKey(Integer aid) {
        List<Appointment> list = new ArrayList<Appointment>();
        list.add(appointmentMapper.selectByPrimaryKey(aid));
        return list;
    }
}
