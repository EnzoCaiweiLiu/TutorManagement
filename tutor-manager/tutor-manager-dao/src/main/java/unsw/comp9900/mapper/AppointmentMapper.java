package unsw.comp9900.mapper;

import org.apache.ibatis.annotations.Param;
import unsw.comp9900.pojo.Appointment;
import unsw.comp9900.pojo.AppointmentDTO;
import unsw.comp9900.pojo.AppointmentExample;

import java.util.List;

public interface AppointmentMapper {
    long countByExample(AppointmentExample example);

    int deleteByExample(AppointmentExample example);

    int deleteByPrimaryKey(Integer aid);

    int insert(Appointment row);

    int insertSelective(Appointment row);

    List<Appointment> selectByExample(AppointmentExample example);

    List<AppointmentDTO> selectByAppointmentDTO();

    List<AppointmentDTO> getAppointmentDTO(AppointmentDTO appointmentDTO);

    Appointment selectByPrimaryKey(Integer aid);

    int updateByExampleSelective(@Param("row") Appointment row, @Param("example") AppointmentExample example);

    int updateByExample(@Param("row") Appointment row, @Param("example") AppointmentExample example);

    int updateByPrimaryKeySelective(Appointment row);

    int updateByPrimaryKey(Appointment row);
}