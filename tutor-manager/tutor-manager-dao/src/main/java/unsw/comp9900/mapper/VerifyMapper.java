package unsw.comp9900.mapper;

import org.apache.ibatis.annotations.Param;
import unsw.comp9900.pojo.Verify;
import unsw.comp9900.pojo.VerifyExample;

import java.util.List;

public interface VerifyMapper {
    long countByExample(VerifyExample example);

    int deleteByExample(VerifyExample example);

    int deleteByPrimaryKey(Integer vid);

    int insert(Verify row);

    int insertSelective(Verify row);

    List<Verify> selectByExample(VerifyExample example);

    Verify selectByPrimaryKey(Integer vid);

    int updateByExampleSelective(@Param("row") Verify row, @Param("example") VerifyExample example);

    int updateByExample(@Param("row") Verify row, @Param("example") VerifyExample example);

    int updateByPrimaryKeySelective(Verify row);

    int updateByPrimaryKey(Verify row);
}