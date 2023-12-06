package unsw.comp9900.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import unsw.comp9900.Result;
import unsw.comp9900.pojo.Rate;
import unsw.comp9900.pojo.RateDTO;
import unsw.comp9900.service.Impl.RateServiceImpl;

import java.util.List;

@Controller
@RequestMapping("/rate")
public class RateController {
    @Autowired
    private RateServiceImpl rateService;

    @PostMapping("/findRateBySelective")
    @ResponseBody
    public Result<List<RateDTO>> findRate(@RequestBody RateDTO rateDTO) throws Exception{
        List<RateDTO> list = rateService.selectRateBySelective(rateDTO);
        if (list == null || list.isEmpty()) return Result.error("Rate not found");
        return Result.success(list);
    }

    @PostMapping("/addRate")
    @ResponseBody
    public Result<String> addRate(@RequestBody Rate rate) throws Exception{
        rateService.addRate(rate);
        return Result.success("Add rate successful!");
    }

    @PostMapping("/deleteRate")
    @ResponseBody
    public Result<String> deleteRate(@RequestBody Rate rate) throws Exception{
        int i = rateService.deleteRate(rate.getAid());
        if (i == 0) return Result.error("Not find rate!");
        else return Result.success("Delete successful!");
    }
}
