package unsw.comp9900.mapper;

import org.apache.ibatis.annotations.Param;
import unsw.comp9900.pojo.Course;
import unsw.comp9900.pojo.CourseDTO;
import unsw.comp9900.pojo.CourseExample;

import java.util.List;

public interface CourseMapper {
    long countByExample(CourseExample example);

    int deleteByExample(CourseExample example);

    int deleteByPrimaryKey(Integer csid);

    int insert(Course row);

    int insertSelective(Course row);

    List<Course> selectByExample(CourseExample example);

    Course selectByPrimaryKey(Integer csid);

    int updateByExampleSelective(@Param("row") Course row, @Param("example") CourseExample example);

    int updateByExample(@Param("row") Course row, @Param("example") CourseExample example);

    int updateByPrimaryKeySelective(Course row);

    int updateByPrimaryKey(Course row);

    List<CourseDTO> selectByCourseDTO();

    List<CourseDTO> getCourseDTO(CourseDTO courseDTO);
}