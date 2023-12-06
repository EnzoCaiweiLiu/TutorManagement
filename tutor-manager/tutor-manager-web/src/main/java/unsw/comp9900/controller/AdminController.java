package unsw.comp9900.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import unsw.comp9900.Result;
import unsw.comp9900.TokenUtils;
import unsw.comp9900.pojo.Admin;
import unsw.comp9900.pojo.Student;
import unsw.comp9900.service.iAdminService;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    private iAdminService service;

    @GetMapping("/queryAllAdmin")
    @ResponseBody
    public Result<List<Admin>> queryAll() throws Exception{
        List<Admin> list = service.queryAllAdmin();
        return Result.success(list);
    }

    @PostMapping("/adminLogin")
    @ResponseBody
    public Result<List<Admin>> login(@RequestBody Admin admin) throws Exception {
        List<Admin> list = service.findAdminByEmail(admin);
        if (list != null && !list.isEmpty()) {
            Admin admin1 = list.get(0);
            if(admin1.getPassword().equals(admin.getPassword())){
                return Result.success(list);
            } else {
                return Result.error("Password is wrong");
            }
        } else {
            return Result.error("Admin not found");
        }
    }

    @PostMapping("/adminRegister")
    @ResponseBody
    public Result<List<Admin>> register(@RequestBody Admin admin) throws Exception {
        List<Admin> list = service.findAdminByEmail(admin);
        if (!list.isEmpty()) {
            return Result.error("Email already register");
        } else {
            service.addAdmin(admin);
            return Result.success(list);
        }
    }

    @PostMapping("/queryAdminByEmail")
    @ResponseBody
    public Result<List<Admin>> queryByEmail(@RequestBody Admin admin) throws Exception {
        List<Admin> list = service.findAdminByEmail(admin);
        if (list != null && !list.isEmpty()) {
            return Result.success(list);
        } else {
            return Result.error("Admin not found");
        }
    }

    @PostMapping("/queryAdminById")
    @ResponseBody
    public Result<List<Admin>> queryById(@RequestBody Admin admin) throws Exception {
        List<Admin> list = service.queryAdminById(admin.getUid());
        if (list != null && !list.isEmpty()) {
            return Result.success(list);
        } else {
            return Result.error("Admin not found");
        }
    }

    @RequestMapping("/updateAdmin")
    @ResponseBody
    public Result<Admin> updateStudent(@RequestBody Admin admin) throws Exception{
        List<Admin> list = service.queryAdminById(admin.getUid());
        if (list != null && !list.isEmpty()) {
            service.updateAdmin(admin);
            List<Admin> list1 = service.queryAdminById(admin.getUid());
            return Result.success(list1.get(0));
        } else {
            return Result.error("Admin not found");
        }
    }

    @RequestMapping("/deleteAdmin")
    @ResponseBody
    public Result<String> deleteTutor(@RequestBody Admin admin) throws Exception{
        int i = service.deleteAdmin(admin.getUid());
        if (i == 0) return Result.error("Admin not found");
        else return Result.success("Delete Successful!");
    }
}
