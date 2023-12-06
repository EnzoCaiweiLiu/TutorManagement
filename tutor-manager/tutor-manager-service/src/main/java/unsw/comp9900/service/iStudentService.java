package unsw.comp9900.service;

import unsw.comp9900.pojo.Student;

import java.util.List;

/**
 *
 */
public interface iStudentService {
    /**
     * 返回所有学生信息
     * @param
     * @return
     */
    List<Student> queryAllStudent() throws Exception;


    /**
     * 添加用户信息
     * @param student
     * @return
     */
    Integer addStudent(Student student) throws Exception;

    /**
     * 更新用户信息
     *
     * @param student
     */
    void updateStudent(Student student) throws Exception;


    /**
     * 根据编号删除用户信息
     * @param studentId
     * @return
     */
    Integer deleteStudent(Integer studentId) throws Exception;

    /**
     *
     * @param student
     * @return
     * @throws Exception
     */
    List<Student> findStudentByEmail(Student student)throws Exception;

    /**
     *
     * @param studentId
     * @return
     * @throws Exception
     */
    Student queryStudentById(Integer studentId) throws  Exception;
}
