package unsw.comp9900.service;

import unsw.comp9900.pojo.Appointment;
import unsw.comp9900.pojo.AppointmentDTO;

import java.util.List;

public interface iAppointmentService {

    Appointment appointmentDTOToAppointment(AppointmentDTO appointmentDTO) throws Exception;
    AppointmentDTO appointmentToAppointmentDTO(Appointment appointment) throws Exception;

    List<AppointmentDTO> queryAllAppointment() throws Exception;

    List<AppointmentDTO> selectAppointmentBySelective(AppointmentDTO appointmentDTO) throws Exception;


    /**
     * 添加课程信息
     * @param appointment
     * @return
     */
    Integer addAppointment(Appointment appointment) throws Exception;

    /**
     * 更新课程信息
     *
     * @param appointment
     * @return
     */
    int updateAppointment(Appointment appointment) throws Exception;


    /**
     * 根据编号删除课程信息
     * @param id
     * @return
     */
    Integer deleteAppointment(Integer id) throws Exception;

    List<Appointment> findAppointmentByPrimaryKey(Integer aid);
}
