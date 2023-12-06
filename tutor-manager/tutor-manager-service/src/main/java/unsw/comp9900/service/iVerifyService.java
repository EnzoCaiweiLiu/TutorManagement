package unsw.comp9900.service;

import unsw.comp9900.pojo.Verify;

import java.util.List;

public interface iVerifyService {
    List<Verify> queryAllVerify() throws Exception;

    Integer addVerify(Verify verify) throws Exception;

    List<Verify> findByEmail(String email) throws Exception;
}
