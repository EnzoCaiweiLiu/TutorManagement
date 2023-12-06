package unsw.comp9900.mapper;

import org.apache.ibatis.annotations.Param;
import unsw.comp9900.pojo.Support;
import unsw.comp9900.pojo.SupportExample;
import unsw.comp9900.pojo.SupportWithBLOBs;

import java.util.List;

public interface SupportMapper {
    long countByExample(SupportExample example);

    int deleteByExample(SupportExample example);

    int deleteByPrimaryKey(Integer sid);

    int insert(SupportWithBLOBs row);

    int insertSelective(SupportWithBLOBs row);

    List<SupportWithBLOBs> selectByExampleWithBLOBs(SupportExample example);

    List<Support> selectByExample(SupportExample example);

    SupportWithBLOBs selectByPrimaryKey(Integer sid);

    int updateByExampleSelective(@Param("row") SupportWithBLOBs row, @Param("example") SupportExample example);

    int updateByExampleWithBLOBs(@Param("row") SupportWithBLOBs row, @Param("example") SupportExample example);

    int updateByExample(@Param("row") Support row, @Param("example") SupportExample example);

    int updateByPrimaryKeySelective(SupportWithBLOBs row);

    int updateByPrimaryKeyWithBLOBs(SupportWithBLOBs row);

    int updateByPrimaryKey(Support row);
}