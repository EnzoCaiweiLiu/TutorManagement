package unsw.comp9900.service.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import unsw.comp9900.mapper.SupportMapper;
import unsw.comp9900.pojo.SupportExample;
import unsw.comp9900.pojo.SupportWithBLOBs;
import unsw.comp9900.service.iSupportService;

import java.util.List;

@Service
public class SupportServiceImpl implements iSupportService {

    @Autowired
    private SupportMapper mapper;

    @Override
    public List<SupportWithBLOBs> queryAllSupport() throws Exception {
        SupportExample example = new SupportExample();
        return mapper.selectByExampleWithBLOBs(example);
    }

    @Override
    public Integer addSupport(SupportWithBLOBs support) throws Exception {
        return mapper.insertSelective(support);
    }

    @Override
    public Integer deleteSupport(Integer sid) throws Exception {
        return mapper.deleteByPrimaryKey(sid);
    }

    @Override
    public void updateSupport(SupportWithBLOBs support) throws Exception {
        mapper.updateByPrimaryKeySelective(support);
    }

    @Override
    public List<SupportWithBLOBs> findSupportByEmail(String email) throws Exception {
        SupportExample example = new SupportExample();

        SupportExample.Criteria criteria = example.createCriteria();

        criteria.andEmailEqualTo(email);
        return mapper.selectByExampleWithBLOBs(example);
    }

    @Override
    public SupportWithBLOBs querySupportById(Integer sid) throws Exception {
        return mapper.selectByPrimaryKey(sid);
    }
}
