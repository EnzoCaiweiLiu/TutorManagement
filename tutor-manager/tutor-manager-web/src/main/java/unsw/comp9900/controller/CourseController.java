package unsw.comp9900.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import unsw.comp9900.pojo.Course;
import unsw.comp9900.pojo.CourseDTO;
import unsw.comp9900.Result;
import unsw.comp9900.pojo.CourseType;
import unsw.comp9900.pojo.Student;
import unsw.comp9900.service.iCourseService;

import java.util.List;

@Controller
@RequestMapping("/course")
public class CourseController {

    @Autowired
    private iCourseService service;

    @GetMapping("/queryAllCourse")
    @ResponseBody
    public Result<List<CourseDTO>> queryAll() throws Exception{
        List<CourseDTO> list = service.queryAllCourse();
        return Result.success(list);
    }

    @PostMapping("/findCourseBySelective")
    @ResponseBody
    public Result<List<CourseDTO>> findCourses(@RequestBody CourseDTO courseDTO) throws Exception{
        List<CourseDTO> list = service.selectCourseBySelective(courseDTO);
        if (list == null || list.isEmpty()) return Result.error("Course not found");
        return Result.success(list);
    }

    @PostMapping("/addCourse")
    @ResponseBody
    public Result<CourseDTO> addCourse(@RequestBody Course course) throws Exception{
        service.addCourse(course);
        return Result.success("add Course success");
    }

    @PostMapping("/updateCourse")
    @ResponseBody
    public Result<CourseDTO> updateCourse(@RequestBody Course course) throws Exception{
        Course course1 = service.findCourseByPrimaryKey(course.getCsid());
        if (course1 == null ) return Result.error("Course not found");
        service.updateCourse(course);
        Course course2 = service.findCourseByPrimaryKey(course.getCsid());
        CourseDTO courseDTO = service.courseToCourseDTO(course2);
        return Result.success(courseDTO);
    }

    @PostMapping("/deleteCourse")
    @ResponseBody
    public Result<Course> deleteCourse(@RequestBody Course course) throws Exception{
        Course course1 = service.findCourseByPrimaryKey(course.getCsid());
        if (course1 == null ) return Result.error("Course not found");
        service.deleteCourse(course.getCsid());
        return Result.success();
    }

    @GetMapping("/queryAllCourseType")
    @ResponseBody
    public Result<List<CourseType>> queryAllCourseType() throws Exception{
        List<CourseType> list = service.queryAllCourseType();
        return Result.success(list);
    }

    @PostMapping("/findCourseTypeByName")
    @ResponseBody
    public Result<List<CourseType>> findCourseTypeByName(@RequestBody CourseType courseType) throws Exception{
        List<CourseType> list = service.findCourseTypeByName(courseType);
        if (list == null || list.isEmpty()) return Result.error("Course not found");
        return Result.success(list);
    }


    @PostMapping("/addCourseType")
    @ResponseBody
    public Result<CourseType> addCourseType(@RequestBody CourseType courseType) throws Exception{
        service.addCourseType(courseType);
        return Result.success("add Course success");
    }

    @PostMapping("/updateCourseType")
    @ResponseBody
    public Result<CourseType> updateCourseType(@RequestBody CourseType courseType) throws Exception{
        CourseType courseType1 = service.findCourseTypeByPrimaryKey(courseType.getCid());
        if (courseType1 == null ) return Result.error("Course not found");
        service.updateCourseType(courseType);
        CourseType courseType2 = service.findCourseTypeByPrimaryKey(courseType.getCid());
        return Result.success(courseType2);
    }

    @PostMapping("/deleteCourseType")
    @ResponseBody
    public Result<CourseType> deleteCourseType(@RequestBody CourseType courseType) throws Exception{
        CourseType courseType1 = service.findCourseTypeByPrimaryKey(courseType.getCid());
        if (courseType1 == null ) return Result.error("Course not found");
        service.deleteCourseType(courseType.getCid());
        return Result.success();
    }


}
