package unsw.comp9900.service.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import unsw.comp9900.mapper.*;
import unsw.comp9900.pojo.*;
import unsw.comp9900.service.iRateService;

import java.util.List;

@Service
public class RateServiceImpl implements iRateService {
    @Autowired
    private RateMapper rateMapper;

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
    public RateDTO rateToRateDTO(Rate rate) throws Exception {
        Appointment appointment = appointmentMapper.selectByPrimaryKey(rate.getAid());
        Course course = courseMapper.selectByPrimaryKey(appointment.getCsid());
        CourseType courseType = courseTypeMapper.selectByPrimaryKey(course.getCid());

        Tutor tutor = tutorMapper.selectByPrimaryKey(appointment.getTid());
        Student student = studentMapper.selectByPrimaryKey(appointment.getSid());
        RateDTO rateDTO = new RateDTO();

        rateDTO.setTid(appointment.getTid());
        rateDTO.setTFName(tutor.getFirstName());
        rateDTO.setTMName(tutor.getMiddleName());
        rateDTO.setTLName(tutor.getLastName());
        rateDTO.setCsid(course.getCsid());
        rateDTO.setCname(courseType.getName());
        rateDTO.setSid(appointment.getSid());
        rateDTO.setSFName(student.getFirstName());
        rateDTO.setSMName(student.getMiddleName());
        rateDTO.setSLName(student.getLastName());
        rateDTO.setAid(rate.getAid());
        rateDTO.setRate(rate.getRate());
        rateDTO.setFeedback(rate.getFeedback());
        rateDTO.setIsDelete(rate.getIsDelete());
        return rateDTO;
    }

    @Override
    public Rate rateDTOToRate(RateDTO rateDTO) throws Exception {
        return null;
    }

    @Override
    public List<RateDTO> selectRateBySelective(RateDTO rateDTO) throws Exception {
        return rateMapper.getRateDTO(rateDTO);
    }

    @Override
    public Integer addRate(Rate rate) throws Exception {
        return rateMapper.insertSelective(rate);
    }

    @Override
    public Integer deleteRate(Integer aid) throws Exception {
        return rateMapper.deleteByPrimaryKey(aid);
    }
}
