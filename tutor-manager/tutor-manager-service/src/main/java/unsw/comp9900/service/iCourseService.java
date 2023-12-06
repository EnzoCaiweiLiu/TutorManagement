package unsw.comp9900.service;

import unsw.comp9900.pojo.Course;
import unsw.comp9900.pojo.CourseDTO;
import unsw.comp9900.pojo.CourseType;

import java.util.List;

public interface iCourseService {


    Course courseDTOToCourse(CourseDTO courseDTO) throws Exception;
    CourseDTO courseToCourseDTO(Course course) throws Exception;

//    List<Course> queryAllCourse() throws Exception;

    List<CourseDTO> queryAllCourse() throws Exception;

    List<CourseDTO> selectCourseBySelective(CourseDTO courseDTO) throws Exception;


    /**
     * 添加课程信息
     * @param course
     * @return
     */
    Integer addCourse(Course course) throws Exception;

    /**
     * 更新课程信息
     *
     * @param course
     * @return
     */
    int updateCourse(Course course) throws Exception;


    /**
     * 根据编号删除课程信息
     * @param id
     * @return
     */
    Integer deleteCourse(Integer id) throws Exception;


    Course findCourseByPrimaryKey(int csid);

    List<Course> listCourse(Course course);

    List<CourseDTO> findCourseByName(String name);



    List<CourseType> queryAllCourseType() throws Exception;

    List<CourseType> findCourseTypeByName(CourseType courseType) throws Exception;

    CourseType findCourseTypeByPrimaryKey(int id);

    Integer addCourseType(CourseType courseType) throws Exception;

    Integer updateCourseType(CourseType courseType) throws Exception;

    Integer deleteCourseType(Integer id) throws Exception;



}
