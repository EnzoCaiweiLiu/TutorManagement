package unsw.comp9900.mapper;

import org.apache.ibatis.annotations.Param;
import unsw.comp9900.pojo.Message;
import unsw.comp9900.pojo.MessageDTO;
import unsw.comp9900.pojo.MessageExample;

import java.util.List;

public interface MessageMapper {
    long countByExample(MessageExample example);

    int deleteByExample(MessageExample example);

    int deleteByPrimaryKey(Integer mid);

    int insert(Message row);

    int insertSelective(Message row);

    List<Message> selectByExampleWithBLOBs(MessageExample example);

    List<Message> selectByExample(MessageExample example);

    Message selectByPrimaryKey(Integer mid);

    int updateByExampleSelective(@Param("row") Message row, @Param("example") MessageExample example);

    int updateByExampleWithBLOBs(@Param("row") Message row, @Param("example") MessageExample example);

    int updateByExample(@Param("row") Message row, @Param("example") MessageExample example);

    int updateByPrimaryKeySelective(Message row);

    int updateByPrimaryKeyWithBLOBs(Message row);

    int updateByPrimaryKey(Message row);

    List<MessageDTO> getMessageDTO(MessageDTO messageDTO);

    List<MessageDTO> getMessageDTOBySelective(MessageDTO messageDTO);

}