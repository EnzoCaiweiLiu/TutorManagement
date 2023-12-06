package unsw.comp9900.mapper;

import org.apache.ibatis.annotations.Param;
import unsw.comp9900.pojo.CourseType;
import unsw.comp9900.pojo.CourseTypeExample;

import java.util.List;

public interface CourseTypeMapper {
    long countByExample(CourseTypeExample example);

    int deleteByExample(CourseTypeExample example);

    int deleteByPrimaryKey(Integer cid);

    int insert(CourseType row);

    int insertSelective(CourseType row);

    List<CourseType> selectByExample(CourseTypeExample example);

    CourseType selectByPrimaryKey(Integer cid);

    int updateByExampleSelective(@Param("row") CourseType row, @Param("example") CourseTypeExample example);

    int updateByExample(@Param("row") CourseType row, @Param("example") CourseTypeExample example);

    int updateByPrimaryKeySelective(CourseType row);

    int updateByPrimaryKey(CourseType row);

    List<CourseType> findCourseTypeByName(CourseType row);
}