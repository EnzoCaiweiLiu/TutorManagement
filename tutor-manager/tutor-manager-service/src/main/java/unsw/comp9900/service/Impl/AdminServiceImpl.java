package unsw.comp9900.service.Impl;

import org.aspectj.lang.annotation.AdviceName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import unsw.comp9900.mapper.AdminMapper;
import unsw.comp9900.pojo.Admin;
import unsw.comp9900.pojo.AdminExample;
import unsw.comp9900.pojo.Student;
import unsw.comp9900.service.iAdminService;

import java.util.List;

@Service
public class AdminServiceImpl implements iAdminService {
    @Autowired
    private AdminMapper mapper;

    @Override
    public List<Admin> queryAllAdmin() throws Exception {
        AdminExample example = new AdminExample();
        return mapper.selectByExample(example);
    }

    @Override
    public Integer addAdmin(Admin admin) throws Exception {
        return mapper.insertSelective(admin);
    }

    @Override
    public void updateAdmin(Admin admin) throws Exception {
        mapper.updateByPrimaryKeySelective(admin);
    }

    @Override
    public List<Admin> findAdminByEmail(Admin admin) throws Exception {
        AdminExample example = new AdminExample();

        AdminExample.Criteria criteria = example.createCriteria();

        criteria.andEmailEqualTo(admin.getEmail());

        return mapper.selectByExample(example);
    }

    @Override
    public List<Admin> queryAdminById(Integer aid) throws Exception {
        AdminExample example = new AdminExample();

        AdminExample.Criteria criteria = example.createCriteria();

        criteria.andUidEqualTo(aid);

        return mapper.selectByExample(example);
    }

    @Override
    public Integer deleteAdmin(Integer aId) throws Exception {
        return mapper.deleteByPrimaryKey(aId);
    }
}
