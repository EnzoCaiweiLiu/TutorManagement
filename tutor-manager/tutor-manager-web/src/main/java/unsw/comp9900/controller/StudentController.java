package unsw.comp9900.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import unsw.comp9900.SendMail;
import unsw.comp9900.TokenUtils;
import unsw.comp9900.pojo.Student;
import unsw.comp9900.Result;
import unsw.comp9900.pojo.Verify;
import unsw.comp9900.service.iStudentService;
import unsw.comp9900.service.iVerifyService;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.util.Date;
import java.util.List;
import java.util.Random;

@Controller
@RequestMapping("/student")
public class StudentController {

    @Autowired
    private iStudentService service;

    @Autowired
    private iVerifyService verifyService;

    @GetMapping("/queryAllStudent")
    @ResponseBody
    public Result<List<Student>> queryAll() throws Exception{
        List<Student> list = service.queryAllStudent();
        return Result.success(list);
    }

    @PostMapping("/studentLogin")
    @ResponseBody
    public Result<List<Student>> login(@RequestBody Student student) throws Exception {
        String token="";
        List<Student> list = service.findStudentByEmail(student);
        if (list != null && !list.isEmpty()) {
            Student student1 = list.get(0);
            if(student1.getPassword().equals(student.getPassword())){
                //生产签名，发送给前端
                token= TokenUtils.sign(student.getEmail(),student.getPassword());
                return Result.success(token,list);
            } else {
                return Result.error("Password is wrong");
            }
        } else {
            return Result.error("Student not found");
        }
    }

    @PostMapping("/studentRegister")
    @ResponseBody
    public Result<List<Student>> register(@RequestBody Student student) throws Exception {
        List<Student> list = service.findStudentByEmail(student);
        if (!list.isEmpty()) {
            return Result.error("Email already register");
        } else {
            service.addStudent(student);
            return Result.success(list);
        }
    }

    @PostMapping("/queryStudentByEmail")
    @ResponseBody
    public Result<List<Student>> queryByEmail(@RequestBody Student student) throws Exception {
        List<Student> list = service.findStudentByEmail(student);
        if (list != null && !list.isEmpty()) {
            return Result.success(list);
        } else {
            return Result.error("Student not found");
        }
    }

    @PostMapping("/queryStudentById")
    @ResponseBody
    public Result<Student> queryById(@RequestBody Student student) throws Exception {
        Student student1 = service.queryStudentById(student.getUid());
        if (student1 != null) {
            return Result.success(student1);
        } else {
            return Result.error("Student not found");
        }
    }

    //发送验证码
    @RequestMapping("/sendEmail")
    @ResponseBody
    public Result<String> sendEmail(@RequestBody Student student) throws Exception {

        // Instantiate an object for sending emails
        SendMail mySendMail = new SendMail();
        //Generate a six-digit verification code
        String Captcha = String.valueOf(new Random().nextInt(899999) + 100000);

        Date time = new Date();

        SimpleMailMessage message = new SimpleMailMessage();
        message.setSubject("Verification code");
        message.setText("Verification code:" + Captcha);
        mySendMail.sendMail(student.getEmail(), "Your verification code is: " + Captcha);
        Verify verify = new Verify();
        verify.setEmail(student.getEmail());
        verify.setTime(time);
        verify.setCode(Integer.parseInt(Captcha));
        verifyService.addVerify(verify);

        return Result.success("Success");
    }



    @RequestMapping("/updateStudentPsw")
    @ResponseBody
    public Result<String> updatePassword(@RequestBody Student student) throws Exception{
        List<Student> list = service.findStudentByEmail(student);
        if (list != null && !list.isEmpty()) {
            if(list.get(0).getPassword().equals(student.getPassword())){
                return Result.error("Password cannot be same");
            } else {
                list.get(0).setPassword(student.getPassword());
                service.updateStudent(list.get(0));
                return Result.success("Success!");
            }
        } else {
            return Result.error("Student not found");
        }
    }

    @RequestMapping("/updateStudent")
    @ResponseBody
    public Result<Student> updateStudent(@RequestBody Student student) throws Exception{
        Student student1 = service.queryStudentById(student.getUid());
        if (student1 != null) {
            service.updateStudent(student);
            Student list1 = service.queryStudentById(student.getUid());
            return Result.success(list1);
        } else {
            return Result.error("Student not found");
        }
    }

    @RequestMapping("/deleteStudent")
    @ResponseBody
    public Result<String> deleteTutor(@RequestBody Student student) throws Exception{
        int i = service.deleteStudent(student.getUid());
        if (i == 0) return Result.error("Student not found");
        else return Result.success("Delete Successful!");
    }

    @PostMapping("/upPhoto/{sid}")
    @ResponseBody
    public Result<String> uploadPhoto(@PathVariable int sid, MultipartFile multipartFile, HttpSession session) throws Exception {
        Student student = service.queryStudentById(sid);
        if (student == null) return Result.error("User not found");
        if (multipartFile.isEmpty()) return Result.error("File is empty");
        String fileName = multipartFile.getOriginalFilename();
        String hzName = fileName.substring(fileName.lastIndexOf("."));
        fileName = String.valueOf(sid) + hzName;
        ServletContext servletContext = session.getServletContext();
        //real path of webapp
        String filePath = servletContext.getRealPath("student_photo");
        File file = new File(filePath);
        //judge file is exist or not
        if (!file.exists()) {
            file.mkdir();
        }
        String finalPath = filePath + File.separator + fileName;
        //file transfer to finalPath
        multipartFile.transferTo(new File(finalPath));
        student.setPhotoUrl(finalPath);
        service.updateStudent(student);
        return Result.success(finalPath);
    }
}
