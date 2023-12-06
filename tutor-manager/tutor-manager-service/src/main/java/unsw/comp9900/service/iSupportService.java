package unsw.comp9900.service;

import unsw.comp9900.pojo.SupportWithBLOBs;

import java.util.List;

public interface iSupportService {
    List<SupportWithBLOBs> queryAllSupport() throws Exception;

    Integer addSupport(SupportWithBLOBs support) throws Exception;

    Integer deleteSupport(Integer sid) throws Exception;

    void updateSupport(SupportWithBLOBs support) throws Exception;

    List<SupportWithBLOBs> findSupportByEmail(String email)throws Exception;

    SupportWithBLOBs querySupportById(Integer sid) throws  Exception;
}
