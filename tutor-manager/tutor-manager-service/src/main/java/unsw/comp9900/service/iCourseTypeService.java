package unsw.comp9900.service;

import unsw.comp9900.pojo.Course;
import unsw.comp9900.pojo.CourseType;

import java.util.List;

public interface iCourseTypeService {
    List<CourseType> queryAllCourseType() throws Exception;
    Integer addCourseType(CourseType courseType) throws Exception;
    Integer deleteCourseType(Integer id) throws Exception;
}
