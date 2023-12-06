package unsw.comp9900.service.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import unsw.comp9900.mapper.CourseMapper;
import unsw.comp9900.mapper.CourseTypeMapper;
import unsw.comp9900.mapper.TutorMapper;
import unsw.comp9900.pojo.*;
import unsw.comp9900.service.iCourseService;

import java.util.ArrayList;
import java.util.List;

@Service
public class CourseServiceImpl implements iCourseService {

    @Autowired
    private CourseTypeMapper typeMapper;

    @Autowired
    private CourseMapper courseMapper;

    @Autowired
    private TutorMapper tutorMapper;

    @Autowired
    private CourseTypeMapper courseTypeMapper;

    @Override
    public Course courseDTOToCourse(CourseDTO courseDTO) throws Exception {
        Course course = new Course();
        course.setCsid(courseDTO.getCsid());
        course.setCid(courseDTO.getCid());
        course.setTid(courseDTO.getTid());
        course.setStartTime(courseDTO.getStartTime());
        course.setFinishTime(courseDTO.getFinishTime());
        course.setLocation(courseDTO.getLocation());
        return course;
    }
//
    @Override
    public CourseDTO courseToCourseDTO(Course course) throws Exception {
        Tutor tutor = tutorMapper.selectByPrimaryKey(course.getTid());
        CourseType courseType = typeMapper.selectByPrimaryKey(course.getCid());
        CourseDTO courseDTO = new CourseDTO();
        courseDTO.setCsid(course.getCsid());
        courseDTO.setTid(course.getTid());
        courseDTO.setTFName(tutor.getFirstName());
        courseDTO.setTMName(tutor.getMiddleName());
        courseDTO.setTLName(tutor.getLastName());
        courseDTO.setCid(course.getCid());
        courseDTO.setCname(courseType.getName());
        courseDTO.setLocation(course.getLocation());
        courseDTO.setStartTime(course.getStartTime());
        courseDTO.setFinishTime(course.getFinishTime());
        courseDTO.setIsSelect(course.getIsSelect());
        courseDTO.setIsDelete(course.getIsDelete());
        return courseDTO;
    }

    @Override
    public List<CourseDTO> queryAllCourse() throws Exception {
        return courseMapper.selectByCourseDTO();
    }

    @Override
    public List<CourseDTO> selectCourseBySelective(CourseDTO courseDTO) throws Exception {
        return courseMapper.getCourseDTO(courseDTO);
    }

//    @Override
//    public List<CourseDTO> queryAllCourse() throws Exception {
//        CourseExample example = new CourseExample();
//        List listCourse = courseMapper.selectByExample(example);
//        List listCourseDTO = new ArrayList<CourseDTO>();
//        for (Object c:listCourse){
//            listCourseDTO.add(courseToCourseDTO((Course) c));
//        }
//        return listCourseDTO;
//
//    }


    @Override
    public Integer addCourse(Course course) throws Exception {
        return courseMapper.insertSelective(course);
    }

    @Override
    public int updateCourse(Course course) throws Exception {
        return courseMapper.updateByPrimaryKeySelective(course);
    }

    @Override
    public Integer deleteCourse(Integer id) throws Exception {
        return courseMapper.deleteByPrimaryKey(id);
    }

    @Override
    public List<Course> listCourse(Course course) {
        return null;
    }

    @Override
    public List<CourseDTO> findCourseByName(String name) {
        return null;
    }

    @Override
    public List<CourseType> queryAllCourseType() throws Exception {
        CourseTypeExample example = new CourseTypeExample();
        return courseTypeMapper.selectByExample(example);
    }

    @Override
    public List<CourseType> findCourseTypeByName(CourseType courseType) throws Exception {
        return courseTypeMapper.findCourseTypeByName(courseType);
    }

    @Override
    public CourseType findCourseTypeByPrimaryKey(int id) {
        return courseTypeMapper.selectByPrimaryKey(id);
    }

    @Override
    public Integer addCourseType(CourseType courseType) throws Exception {
        return courseTypeMapper.insertSelective(courseType);
    }

    @Override
    public Integer updateCourseType(CourseType courseType) throws Exception {
        return courseTypeMapper.updateByPrimaryKeySelective(courseType);
    }

    @Override
    public Integer deleteCourseType(Integer id) throws Exception {
        return courseTypeMapper.deleteByPrimaryKey(id);
    }

    @Override
    public Course findCourseByPrimaryKey(int csid) {
        return courseMapper.selectByPrimaryKey(csid);
    }


}
