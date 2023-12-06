package unsw.comp9900.service.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import unsw.comp9900.mapper.TutorMapper;
import unsw.comp9900.pojo.*;
import unsw.comp9900.service.iTutorService;

import java.util.List;

@Service
public class TutorServiceImpl implements iTutorService {

    @Autowired
    private TutorMapper mapper;



    @Override
    public List<TutorWithBLOBs> queryAllTutor() throws Exception {
        TutorExample example = new TutorExample();
        return mapper.selectByExampleWithBLOBs(example);
    }

    @Override
    public Integer addTutor(TutorWithBLOBs tutor) throws Exception {
        return mapper.insertSelective(tutor);
    }

    @Override
    public void updateTutor(TutorWithBLOBs tutor) throws Exception {
        mapper.updateByPrimaryKeySelective(tutor);
    }

    @Override
    public Integer deleteTutor(Integer tutorId) throws Exception {
        return mapper.deleteByPrimaryKey(tutorId);
    }

    @Override
    public List<TutorWithBLOBs> findTutorByEmail(Tutor tutor) throws Exception {
        TutorExample example = new TutorExample();

        TutorExample.Criteria criteria = example.createCriteria();

        criteria.andEmailEqualTo(tutor.getEmail());

        return mapper.selectByExampleWithBLOBs(example);
    }

    @Override
    public List<TutorWithBLOBs> queryTutorById(Integer tutorId) throws Exception {
        TutorExample example = new TutorExample();

        TutorExample.Criteria criteria = example.createCriteria();

        criteria.andUidEqualTo(tutorId);

        return mapper.selectByExampleWithBLOBs(example);
    }

}

