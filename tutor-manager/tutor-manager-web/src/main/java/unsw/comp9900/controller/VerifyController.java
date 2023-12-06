package unsw.comp9900.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import unsw.comp9900.Result;
import unsw.comp9900.SendMail;
import unsw.comp9900.pojo.Student;
import unsw.comp9900.pojo.Verify;
import unsw.comp9900.service.iVerifyService;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Random;

@Controller
@RequestMapping("/verify")
public class VerifyController {
    @Autowired
    private iVerifyService service;

    @GetMapping("/queryAllVerify")
    @ResponseBody
    public Result<List<Verify>> queryAll() throws Exception{
        List<Verify> list = service.queryAllVerify();
        return Result.success(list);
    }

    @RequestMapping("/sendEmail")
    @ResponseBody
    public Result<String> sendEmail(@RequestBody Verify verify) throws Exception {

        // Instantiate an object for sending emails
        SendMail mySendMail = new SendMail();
        //Generate a six-digit verification code
        String Captcha = String.valueOf(new Random().nextInt(899999) + 100000);

        Date time = new Date();

        SimpleMailMessage message = new SimpleMailMessage();
        message.setSubject("Verification code");
        message.setText("Verification code:" + Captcha);
        mySendMail.sendMail(verify.getEmail(), "Your verification code is: " + Captcha);
        Verify verify1 = new Verify();
        verify1.setEmail(verify.getEmail());
        verify1.setTime(time);
        verify1.setCode(Integer.parseInt(Captcha));
        service.addVerify(verify1);

        return Result.success("Success");
    }

    @PostMapping("/verifyCode")
    @ResponseBody
    public Result<String> queryAll(@RequestBody Verify verify) throws Exception {
        List<Verify> list = service.findByEmail(verify.getEmail());

        if (list == null ||list.isEmpty() ) {
            return Result.error("No verification code found for the provided email!");
        }

        Date curTime = new Date();
        long differenceInMillis = Math.abs(curTime.getTime() - list.get(0).getTime().getTime());

        if (differenceInMillis <= 15 * 60 * 1000) {

        } else {
            System.out.println("Verification code has expired");
        }
        if (Objects.equals(verify.getCode(), list.get(0).getCode())) {
            return Result.success("Successful");
        } else {
            return Result.error("Code is not same!");
        }
    }
}
