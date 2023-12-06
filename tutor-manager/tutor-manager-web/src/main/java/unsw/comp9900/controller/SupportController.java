package unsw.comp9900.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import unsw.comp9900.Result;
import unsw.comp9900.SendMail;
import unsw.comp9900.pojo.*;
import unsw.comp9900.service.iSupportService;

import java.util.Date;
import java.util.List;
import java.util.Random;

@Controller
@RequestMapping("/support")
public class SupportController {

    @Autowired
    private iSupportService service;


    @GetMapping("/queryAllSupport")
    @ResponseBody
    public Result<List<SupportWithBLOBs>> queryAll() throws Exception{
        List<SupportWithBLOBs> list = service.queryAllSupport();
        return Result.success(list);
    }

    @PostMapping("/querySupportByEmail")
    @ResponseBody
    public Result<List<SupportWithBLOBs>> queryByEmail(@RequestBody SupportWithBLOBs support) throws Exception {
        List<SupportWithBLOBs> list = service.findSupportByEmail(support.getEmail());
        if (list != null && !list.isEmpty()) {
            return Result.success(list);
        } else {
            return Result.error("Support not found");
        }
    }

    @PostMapping("/querySupportById")
    @ResponseBody
    public Result<SupportWithBLOBs> queryById(@RequestBody SupportWithBLOBs support) throws Exception {
        SupportWithBLOBs supportWithBLOBs = service.querySupportById(support.getSid());
        if (supportWithBLOBs != null) {
            return Result.success(supportWithBLOBs);
        } else {
            return Result.error("Support not found");
        }
    }

    @PostMapping("/addSupport")
    @ResponseBody
    public Result<String> addRate(@RequestBody SupportWithBLOBs support) throws Exception{
        service.addSupport(support);
        return Result.success("Add support successful!");
    }

    @PostMapping("/updateSupport")
    @ResponseBody
    public Result<SupportWithBLOBs> updateCourse(@RequestBody SupportWithBLOBs support) throws Exception{
        SupportWithBLOBs list = service.querySupportById(support.getSid());
        if (list == null) return Result.error("Support not found");
        service.updateSupport(support);
        SupportWithBLOBs list1 = service.querySupportById(support.getSid());
        return Result.success(list1);
    }

    @PostMapping("/deleteSupport")
    @ResponseBody
    public Result<String> deleteRate(@RequestBody SupportWithBLOBs support) throws Exception{
        int i = service.deleteSupport(support.getSid());
        if (i == 0) return Result.error("Not find support!");
        else return Result.success("Delete successful!");
    }

    @PostMapping("/sendSupport")
    @ResponseBody
    public Result<String> sendEmail(@RequestBody SupportWithBLOBs support) throws Exception {
        SendMail mySendMail = new SendMail();

        SimpleMailMessage message = new SimpleMailMessage();
        message.setSubject("Support Feedback");
        message.setText("Feedback for your support: " + support.getInfo());
        mySendMail.sendMail(support.getEmail(), "Your support message : " + support.getInfo() + "<br> Feedback for your support: " + support.getAnswer());
        return Result.success("Success!");
    }

}
