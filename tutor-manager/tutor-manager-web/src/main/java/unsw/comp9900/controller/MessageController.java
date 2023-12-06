package unsw.comp9900.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import unsw.comp9900.Result;
import unsw.comp9900.pojo.Message;
import unsw.comp9900.pojo.MessageDTO;
import unsw.comp9900.service.iMessageService;
import java.util.List;

@Controller
@RequestMapping("/message")
public class MessageController {

    @Autowired
   private iMessageService messageService;

    @GetMapping("/queryAllMessage")
    @ResponseBody
    public Result<List<MessageDTO>> queryAll() throws Exception{
        List<MessageDTO> list = messageService.queryAllMessage();
        return Result.success(list);
    }

    @PostMapping("/findMessageBySelective")
    @ResponseBody
    public Result<List<MessageDTO>> findMessage(@RequestBody MessageDTO messageDTO) throws Exception{
        List<MessageDTO> list = messageService.selectMessageBySelective(messageDTO);
        if (list == null || list.isEmpty()) return Result.error("Appointment not found");
        return Result.success(list);
    }



    @PostMapping("/addMessage")
    @ResponseBody
    public Result<MessageDTO> addAppointment(@RequestBody Message message) throws Exception{
        messageService.addMessage(message);
        return Result.success("Add Message success");
    }

    @PostMapping("/updateMessage")
    @ResponseBody
    public Result<MessageDTO> updateCourse(@RequestBody Message message) throws Exception{
        List<MessageDTO> list = messageService.findMessageByPrimaryKey(message.getMid());
        if (list.isEmpty()) return Result.error("Message not found");
        messageService.updateMessage(message);
        return Result.success();
    }

    @PostMapping("/deleteMessage")
    @ResponseBody
    public Result<MessageDTO> deleteMessage(@RequestBody Message message) throws Exception{
        List<MessageDTO> list = messageService.findMessageByPrimaryKey(message.getMid());
        if (list.isEmpty()) return Result.error("Message not found");
        messageService.deleteMessage(message.getMid());
        return Result.success();
    }

}
