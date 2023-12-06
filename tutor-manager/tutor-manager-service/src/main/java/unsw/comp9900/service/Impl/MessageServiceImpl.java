package unsw.comp9900.service.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import unsw.comp9900.mapper.AppointmentMapper;
import unsw.comp9900.mapper.MessageMapper;
import unsw.comp9900.pojo.*;
import unsw.comp9900.service.iMessageService;

import java.util.List;

@Service
public class MessageServiceImpl implements iMessageService {

    @Autowired
    private MessageMapper messageMapper;

    @Override
    public List<MessageDTO> queryAllMessage() throws Exception {
        MessageDTO messageDTO = new MessageDTO();
        return messageMapper.getMessageDTO(messageDTO);
    }

    @Override
    public List<MessageDTO> selectMessageBySelective(MessageDTO messageDTO) throws Exception {
        return messageMapper.getMessageDTOBySelective(messageDTO);
    }

    @Override
    public List<MessageDTO> findMessageByPrimaryKey(int mid) {
        MessageDTO messageDTO = new MessageDTO();
        messageDTO.setMid(mid);
        return messageMapper.getMessageDTOBySelective(messageDTO);
    }

    @Override
    public Integer addMessage(Message message) throws Exception {
        System.out.println(message);
        return messageMapper.insertSelective(message);
    }

    @Override
    public int updateMessage(Message message) throws Exception {
        return messageMapper.updateByPrimaryKeySelective(message);
    }

    @Override
    public Integer deleteMessage(Integer mid) throws Exception {
        return messageMapper.deleteByPrimaryKey(mid);
    }

//    @Override
//    public List<Message> findMessageByPrimaryKey(Integer mid) {
//        return null;
//    }
}
