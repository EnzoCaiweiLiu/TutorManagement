package unsw.comp9900.mapper;

import org.apache.ibatis.annotations.Param;
import unsw.comp9900.pojo.Rate;
import unsw.comp9900.pojo.RateDTO;
import unsw.comp9900.pojo.RateExample;

import java.util.List;

public interface RateMapper {
    long countByExample(RateExample example);

    int deleteByExample(RateExample example);

    int deleteByPrimaryKey(Integer aid);

    int insert(Rate row);

    int insertSelective(Rate row);

    List<Rate> selectByExample(RateExample example);

    Rate selectByPrimaryKey(Integer aid);

    int updateByExampleSelective(@Param("row") Rate row, @Param("example") RateExample example);

    int updateByExample(@Param("row") Rate row, @Param("example") RateExample example);

    int updateByPrimaryKeySelective(Rate row);

    int updateByPrimaryKey(Rate row);

    List<RateDTO> getRateDTO(RateDTO rateDTO);
}