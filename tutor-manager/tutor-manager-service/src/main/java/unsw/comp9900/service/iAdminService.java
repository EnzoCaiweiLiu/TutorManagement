package unsw.comp9900.service;

import unsw.comp9900.pojo.Admin;
import unsw.comp9900.pojo.Student;

import java.util.List;

public interface iAdminService {

    /**
     * 返回所有Admin信息
     * @param
     * @return
     */
    List<Admin> queryAllAdmin() throws Exception;

    /**
     * 添加用户信息
     * @param admin
     * @return
     */
    Integer addAdmin(Admin admin) throws Exception;


    /**
     *
     * @param admin
     * @throws Exception
     */
    void updateAdmin(Admin admin) throws Exception;


    /**
     *
     * @param admin
     * @return
     * @throws Exception
     */
    List<Admin> findAdminByEmail(Admin admin)throws Exception;


    /**
     *
     * @param aid
     * @return
     * @throws Exception
     */
    List<Admin> queryAdminById(Integer aid) throws  Exception;

    /**
     *
     * @param aId
     * @return
     */
    Integer deleteAdmin(Integer aId) throws Exception;
}
