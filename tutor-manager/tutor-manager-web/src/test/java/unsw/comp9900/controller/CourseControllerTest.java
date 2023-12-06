package unsw.comp9900.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import unsw.comp9900.Result;
import unsw.comp9900.pojo.Course;
import unsw.comp9900.pojo.CourseDTO;
import unsw.comp9900.pojo.CourseType;
import unsw.comp9900.service.iCourseService;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CourseControllerTest {

    @Mock
    private iCourseService courseService;

    @InjectMocks
    private CourseController controller;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void queryAll() throws Exception {
        List<CourseDTO> expectedList = new ArrayList<>();
        when(courseService.queryAllCourse()).thenReturn(expectedList);

        Result<List<CourseDTO>> result = controller.queryAll();

        assertEquals(1, result.getCode());
        assertEquals("success", result.getMsg());
        assertEquals(expectedList, result.getData());
        verify(courseService).queryAllCourse();
    }

    @Test
    void findCoursesBySelective() throws Exception {
        CourseDTO courseDTO = new CourseDTO();
        List<CourseDTO> list = new ArrayList<>();
        list.add(courseDTO);
        when(courseService.selectCourseBySelective(any(CourseDTO.class))).thenReturn(list);

        Result<List<CourseDTO>> result = controller.findCourses(courseDTO);

        assertEquals(1, result.getCode());
        assertEquals("success", result.getMsg());
        verify(courseService).selectCourseBySelective(any(CourseDTO.class));
    }

    @Test
    void addCourse() throws Exception {
        Course course = new Course();

        Result<CourseDTO> result = controller.addCourse(course);

        assertEquals(1, result.getCode());
        assertEquals("add Course success", result.getMsg());
        verify(courseService).addCourse(any(Course.class));
    }

    @Test
    void updateCourse() throws Exception {
        Course course = new Course();
        course.setCsid(1);
        Course existingCourse = new Course();
        when(courseService.findCourseByPrimaryKey(course.getCsid())).thenReturn(existingCourse);

        Result<CourseDTO> result = controller.updateCourse(course);

        assertEquals(1, result.getCode());
        assertEquals("success", result.getMsg());
        verify(courseService, times(2)).findCourseByPrimaryKey(course.getCsid());
        verify(courseService).updateCourse(course);
    }

    @Test
    void deleteCourse() throws Exception {
        Course course = new Course();
        course.setCsid(1);
        Course existingCourse = new Course();
        when(courseService.findCourseByPrimaryKey(course.getCsid())).thenReturn(existingCourse);

        Result<Course> result = controller.deleteCourse(course);

        assertEquals(1, result.getCode());
        verify(courseService).findCourseByPrimaryKey(course.getCsid());
        verify(courseService).deleteCourse(course.getCsid());
    }

    @Test
    void queryAllCourseType() throws Exception {
        List<CourseType> expectedList = new ArrayList<>();
        when(courseService.queryAllCourseType()).thenReturn(expectedList);

        Result<List<CourseType>> result = controller.queryAllCourseType();

        assertEquals(1, result.getCode());
        assertEquals("success", result.getMsg());
        assertEquals(expectedList, result.getData());
        verify(courseService).queryAllCourseType();
    }

    @Test
    void findCourseTypeByName() throws Exception {
        CourseType courseType = new CourseType();
        List<CourseType> list = new ArrayList<>();
        list.add(courseType);
        when(courseService.findCourseTypeByName(any(CourseType.class))).thenReturn(list);

        Result<List<CourseType>> result = controller.findCourseTypeByName(courseType);

        assertEquals(1, result.getCode());
        assertEquals("success", result.getMsg());
        verify(courseService).findCourseTypeByName(any(CourseType.class));
    }

    @Test
    void addCourseType() throws Exception {
        CourseType courseType = new CourseType();

        Result<CourseType> result = controller.addCourseType(courseType);

        assertEquals(1, result.getCode());
        assertEquals("add Course success", result.getMsg());
        verify(courseService).addCourseType(any(CourseType.class));
    }

    @Test
    void updateCourseType() throws Exception {
        CourseType courseType = new CourseType();
        courseType.setCid(1);
        CourseType existingCourseType = new CourseType();
        when(courseService.findCourseTypeByPrimaryKey(courseType.getCid())).thenReturn(existingCourseType);

        Result<CourseType> result = controller.updateCourseType(courseType);

        assertEquals(1, result.getCode());
        assertEquals("success", result.getMsg());
        verify(courseService, times(2)).findCourseTypeByPrimaryKey(courseType.getCid());
        verify(courseService).updateCourseType(courseType);
    }

    @Test
    void deleteCourseType() throws Exception {
        CourseType courseType = new CourseType();
        courseType.setCid(1);
        CourseType existingCourseType = new CourseType();
        when(courseService.findCourseTypeByPrimaryKey(courseType.getCid())).thenReturn(existingCourseType);

        Result<CourseType> result = controller.deleteCourseType(courseType);

        assertEquals(1, result.getCode());
        verify(courseService).findCourseTypeByPrimaryKey(courseType.getCid());
        verify(courseService).deleteCourseType(courseType.getCid());
    }
}
