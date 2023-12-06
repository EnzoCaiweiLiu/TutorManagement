package unsw.comp9900.service.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import unsw.comp9900.mapper.VerifyMapper;
import unsw.comp9900.pojo.Verify;
import unsw.comp9900.pojo.VerifyExample;
import unsw.comp9900.service.iVerifyService;

import java.util.List;

@Service
public class VerifyServiceImpl implements iVerifyService {
    @Autowired
    private VerifyMapper mapper;

    @Override
    public List<Verify> queryAllVerify() {
        VerifyExample example = new VerifyExample();
        return mapper.selectByExample(example);
    }

    @Override
    public Integer addVerify(Verify verify) throws Exception {
        return mapper.insertSelective(verify);
    }

    @Override
    public List<Verify> findByEmail(String email) {
        VerifyExample example = new VerifyExample();
        VerifyExample.Criteria criteria = example.createCriteria();
        criteria.andEmailEqualTo(email);
        example.setOrderByClause("time DESC");
        List<Verify> results = mapper.selectByExample(example);
        return results.isEmpty() ? null : results;
    }
}
