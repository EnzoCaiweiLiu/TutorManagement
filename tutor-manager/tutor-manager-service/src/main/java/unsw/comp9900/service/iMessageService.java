package unsw.comp9900.service;

import unsw.comp9900.pojo.*;

import java.util.List;

public interface iMessageService {



    List<MessageDTO> queryAllMessage() throws Exception;

    List<MessageDTO> selectMessageBySelective(MessageDTO messageDTO) throws Exception;

    List<MessageDTO> findMessageByPrimaryKey(int mid);

    Integer addMessage(Message message) throws Exception;

    int updateMessage(Message message) throws Exception;

    Integer deleteMessage(Integer mid) throws Exception;


}
