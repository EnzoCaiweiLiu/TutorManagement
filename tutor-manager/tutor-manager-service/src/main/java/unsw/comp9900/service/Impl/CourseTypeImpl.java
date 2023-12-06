package unsw.comp9900.service.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import unsw.comp9900.mapper.CourseTypeMapper;
import unsw.comp9900.pojo.CourseType;
import unsw.comp9900.pojo.CourseTypeExample;
import unsw.comp9900.service.iCourseTypeService;

import java.util.List;

@Service
public class CourseTypeImpl implements iCourseTypeService {

    @Autowired
    private CourseTypeMapper mapper;
    @Override
    public List<CourseType> queryAllCourseType() throws Exception {
        CourseTypeExample example = new CourseTypeExample();
        return mapper.selectByExample(example);
    }

    @Override
    public Integer addCourseType(CourseType courseType) throws Exception {
        return mapper.insertSelective(courseType);
    }

    @Override
    public Integer deleteCourseType(Integer id) throws Exception {
        return mapper.deleteByPrimaryKey(id);
    }
}
