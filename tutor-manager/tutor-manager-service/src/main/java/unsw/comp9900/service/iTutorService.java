package unsw.comp9900.service;

import unsw.comp9900.pojo.Student;
import unsw.comp9900.pojo.Tutor;
import unsw.comp9900.pojo.TutorWithBLOBs;

import java.util.List;

/**
 *
 */
public interface iTutorService {
    /**
     * 返回所有信息
     * @param
     * @return
     */
    List<TutorWithBLOBs> queryAllTutor() throws Exception;


    /**
     * 添加用户信息
     * @param tutor
     * @return
     */
    Integer addTutor(TutorWithBLOBs tutor) throws Exception;

    /**
     * 更新用户信息
     *
     * @param tutor
     */
    void updateTutor(TutorWithBLOBs tutor) throws Exception;


    /**
     * 根据编号删除用户信息
     * @param tutorId
     * @return
     */
    Integer deleteTutor(Integer tutorId) throws Exception;

    /**
     *
     * @param tutor
     * @return
     * @throws Exception
     */
    List<TutorWithBLOBs> findTutorByEmail(Tutor tutor)throws Exception;

    /**
     *
     * @param tutorId
     * @return
     * @throws Exception
     */
    List<TutorWithBLOBs> queryTutorById(Integer tutorId) throws  Exception;

}
