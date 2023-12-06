package unsw.comp9900.service;

import unsw.comp9900.pojo.Rate;
import unsw.comp9900.pojo.RateDTO;

import java.util.List;

public interface iRateService {

    RateDTO rateToRateDTO(Rate rate) throws Exception;
    Rate rateDTOToRate(RateDTO rateDTO) throws Exception;
    List<RateDTO> selectRateBySelective(RateDTO rateDTO) throws Exception;
    Integer addRate(Rate rate) throws Exception;
    Integer deleteRate(Integer aid) throws Exception;
}
