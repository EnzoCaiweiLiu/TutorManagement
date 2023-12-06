package unsw.comp9900.service.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import unsw.comp9900.mapper.StudentMapper;
import unsw.comp9900.pojo.Student;
import unsw.comp9900.pojo.StudentExample;
import unsw.comp9900.service.iStudentService;

import java.util.List;

@Service
public class StudentServiceImpl implements iStudentService {

    @Autowired
    private StudentMapper mapper;

    @Override
    public List<Student> queryAllStudent() throws Exception {
        StudentExample example = new StudentExample();
        return mapper.selectByExample(example);
    }

    @Override
    public Integer addStudent(Student student) throws Exception {
        return mapper.insertSelective(student);
    }

    @Override
    public void updateStudent(Student student) throws Exception {
        mapper.updateByPrimaryKeySelective(student);
    }

    @Override
    public Integer deleteStudent(Integer studentId) throws Exception {
        return mapper.deleteByPrimaryKey(studentId);
    }

    @Override
    public List<Student> findStudentByEmail(Student student) throws Exception {
        StudentExample example = new StudentExample();

        StudentExample.Criteria criteria = example.createCriteria();

        criteria.andEmailEqualTo(student.getEmail());

        return mapper.selectByExample(example);
    }

    @Override
    public Student queryStudentById(Integer studentId) throws Exception {
        return mapper.selectByPrimaryKey(studentId);
    }


}
