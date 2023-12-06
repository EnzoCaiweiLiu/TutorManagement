package unsw.comp9900.mapper;

import org.apache.ibatis.annotations.Param;
import unsw.comp9900.pojo.Student;
import unsw.comp9900.pojo.StudentExample;

import java.util.List;

public interface StudentMapper {
    long countByExample(StudentExample example);

    int deleteByExample(StudentExample example);

    int deleteByPrimaryKey(Integer uid);

    int insert(Student row);

    int insertSelective(Student row);

    List<Student> selectByExampleWithBLOBs(StudentExample example);

    List<Student> selectByExample(StudentExample example);

    Student selectByPrimaryKey(Integer uid);

    int updateByExampleSelective(@Param("row") Student row, @Param("example") StudentExample example);

    int updateByExampleWithBLOBs(@Param("row") Student row, @Param("example") StudentExample example);

    int updateByExample(@Param("row") Student row, @Param("example") StudentExample example);

    int updateByPrimaryKeySelective(Student row);

    int updateByPrimaryKeyWithBLOBs(Student row);

    int updateByPrimaryKey(Student row);
}