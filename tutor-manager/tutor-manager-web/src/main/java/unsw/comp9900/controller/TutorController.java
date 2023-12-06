package unsw.comp9900.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Controller;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import unsw.comp9900.SendMail;
import unsw.comp9900.pojo.Student;
import unsw.comp9900.pojo.Tutor;
import unsw.comp9900.pojo.TutorWithBLOBs;
import unsw.comp9900.Result;
import unsw.comp9900.pojo.Verify;
import unsw.comp9900.service.iTutorService;
import unsw.comp9900.service.iVerifyService;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Paths;
import java.util.Date;
import java.util.List;
import java.util.Random;

@Controller
@RequestMapping("/tutor")
public class TutorController {

    @Autowired
    private iTutorService service;

    @Autowired
    private iVerifyService verifyService;

    @GetMapping("/queryAllTutor")
    @ResponseBody
    public Result<List<TutorWithBLOBs>> queryAll() throws Exception{
        List<TutorWithBLOBs> list = service.queryAllTutor();
        return Result.success(list);
    }

    @PostMapping("/tutorLogin")
    @ResponseBody
    public Result<List<TutorWithBLOBs>> login(@RequestBody Tutor tutor) throws Exception {
        List<TutorWithBLOBs> list = service.findTutorByEmail(tutor);
        if (list != null && !list.isEmpty()) {
            Tutor tutor1 = list.get(0);
            if(tutor1.getPassword().equals(tutor.getPassword())){
                 return Result.success(list);
            } else {
                return Result.error("Password is wrong");
            }
        } else {
            return Result.error("Tutor not found");
        }
    }

    @PostMapping("/tutorRegister")
    @ResponseBody
    public Result<List<TutorWithBLOBs>> register(@RequestBody TutorWithBLOBs tutor) throws Exception {
        List<TutorWithBLOBs> list = service.findTutorByEmail(tutor);
        if (!list.isEmpty()) {
            return Result.error("Email already register");
        } else {
            service.addTutor(tutor);
            return Result.success(list);
        }
    }

    @PostMapping("/queryTutorByEmail")
    @ResponseBody
    public Result<List<TutorWithBLOBs>> queryByEmail(@RequestBody Tutor tutor) throws Exception {
        List<TutorWithBLOBs> list = service.findTutorByEmail(tutor);
        if (list != null && !list.isEmpty()) {
            return Result.success(list);
        } else {
            return Result.error("Tutor not found");
        }
    }

    @PostMapping("/queryTutorById")
    @ResponseBody
    public Result<List<TutorWithBLOBs>> queryById(@RequestBody Tutor tutor) throws Exception {
        List<TutorWithBLOBs> list = service.queryTutorById(tutor.getUid());
        if (list != null && !list.isEmpty()) {
            return Result.success(list);
        } else {
            return Result.error("Tutor not found");
        }
    }

    //发送验证码
    @RequestMapping("/sendEmail")
    @ResponseBody
    public Result<String> sendEmail(@RequestBody Tutor tutor) throws Exception {

        // Instantiate an object for sending emails
        SendMail mySendMail = new SendMail();
        //Generate a six-digit verification code
        String Captcha = String.valueOf(new Random().nextInt(899999) + 100000);

        Date time = new Date();

        SimpleMailMessage message = new SimpleMailMessage();
        message.setSubject("Verification code");
        message.setText("Verification code:" + Captcha);
        mySendMail.sendMail(tutor.getEmail(), "Your verification code is: " + Captcha);
        Verify verify = new Verify();
        verify.setEmail(tutor.getEmail());
        verify.setTime(time);
        verify.setCode(Integer.parseInt(Captcha));
        verifyService.addVerify(verify);

        return Result.success(Captcha);
    }

    @RequestMapping("/updateTutorPsw")
    @ResponseBody
    public Result<String> updatePassword(@RequestBody Tutor tutor) throws Exception{
        List<TutorWithBLOBs> list = service.findTutorByEmail(tutor);
        if (list != null && !list.isEmpty()) {
            if(list.get(0).getPassword().equals(tutor.getPassword())){
                return Result.error("Password cannot be same");
            } else {
                list.get(0).setPassword(tutor.getPassword());
                service.updateTutor(list.get(0));
                return Result.success("Success!");
            }
        } else {
            return Result.error("Tutor not found");
        }
    }

    @RequestMapping("/updateTutor")
    @ResponseBody
    public Result<TutorWithBLOBs> updateStudent(@RequestBody TutorWithBLOBs tutor) throws Exception{
        List<TutorWithBLOBs> list = service.queryTutorById(tutor.getUid());
        if (list != null && !list.isEmpty()) {
            service.updateTutor(tutor);
            List<TutorWithBLOBs> list1 = service.queryTutorById(tutor.getUid());
            return Result.success(list1.get(0));
        } else {
            return Result.error("Tutor not found");
        }
    }

    @RequestMapping("/deleteTutor")
    @ResponseBody
    public Result<String> deleteTutor(@RequestBody Tutor tutor) throws Exception{
        int i = service.deleteTutor(tutor.getUid());
        if (i == 0) return Result.error("Tutor not found");
        else return Result.success("Delete Successful!");
    }

    @PostMapping("/upPhoto/{tid}")
    @ResponseBody
    public Result<String> uploadPhoto(@PathVariable int tid, MultipartFile multipartFile, HttpSession session) throws Exception {
        List<TutorWithBLOBs> list = service.queryTutorById(tid);
        if (list == null || list.isEmpty()) return Result.error("User not found");
        if (multipartFile.isEmpty()) return Result.error("File is empty");
        TutorWithBLOBs tutor = list.get(0);
        String fileName = multipartFile.getOriginalFilename();
        String hzName = fileName.substring(fileName.lastIndexOf("."));
        fileName = String.valueOf(tid) + hzName;
        ServletContext servletContext = session.getServletContext();
        //real path of webapp
        String filePath = servletContext.getRealPath("tutor_photo");
        File file = new File(filePath);
        //judge file is exist or not
        if (!file.exists()) {
            file.mkdir();
        }
        String finalPath = filePath + File.separator + fileName;
        //file transfer to finalPath
        multipartFile.transferTo(new File(finalPath));
        tutor.setPhotoUrl(finalPath);
        service.updateTutor(tutor);
        return Result.success(finalPath);
    }

    @PostMapping("/upBio/{tid}")
    @ResponseBody
    public Result<String[]> upload(@PathVariable int tid, MultipartFile multipartFile, HttpSession session) throws Exception {
        List<TutorWithBLOBs> list = service.queryTutorById(tid);
        if (list == null || list.isEmpty() || list.get(0)==null) return Result.error("User not found");
        if (multipartFile.isEmpty()) return Result.error("File is empty");
        String fileName = multipartFile.getOriginalFilename();
        ServletContext servletContext = session.getServletContext();
        //real path of webapp
        String filePath = servletContext.getRealPath("upload_file_" + String.valueOf(tid));
        File file = new File(filePath);
        //judge file is exist or not
        if (!file.exists()) {
            file.mkdir();
        }
        String finalPath = filePath + File.separator + fileName;
        TutorWithBLOBs tutor = list.get(0);
        //file transfer to finalPath
        multipartFile.transferTo(new File(finalPath));
        String[] list1 = file.list();
        String result = String.join(", ", list1);
        tutor.setDocument(result);
        service.updateTutor(tutor);
        return Result.success(list1);
    }

    @PostMapping("/downBio/{tid}")
    @ResponseBody
    public ResponseEntity<byte[]> download(@PathVariable int tid, String fileName, HttpSession session) {
        ServletContext servletContext = session.getServletContext();
        String directoryPath = servletContext.getRealPath("upload_file_" + tid);
        String realPath = Paths.get(directoryPath, fileName).toString();

        byte[] bytes;
        MultiValueMap<String, String> headers = new HttpHeaders();
        try {
            bytes = Files.readAllBytes(Paths.get(realPath));
            String encodedFileName = URLEncoder.encode(fileName, "UTF-8").replaceAll("\\+", "%20");
            headers.add("Content-Disposition", "attachment;filename*=UTF-8''" + encodedFileName);
            headers.add("Content-Type", servletContext.getMimeType(realPath));
        } catch (NoSuchFileException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }

        return new ResponseEntity<>(bytes, headers, HttpStatus.OK);
    }
}
