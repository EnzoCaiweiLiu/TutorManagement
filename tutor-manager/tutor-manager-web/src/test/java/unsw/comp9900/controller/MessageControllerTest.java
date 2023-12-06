package unsw.comp9900.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import unsw.comp9900.Result;
import unsw.comp9900.pojo.Message;
import unsw.comp9900.pojo.MessageDTO;
import unsw.comp9900.service.iMessageService;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class MessageControllerTest {

    @Mock
    private iMessageService messageService;

    @InjectMocks
    private MessageController controller;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void queryAll() throws Exception {
        List<MessageDTO> expectedList = new ArrayList<>();
        when(messageService.queryAllMessage()).thenReturn(expectedList);

        Result<List<MessageDTO>> result = controller.queryAll();

        assertEquals(1, result.getCode());
        assertEquals("success", result.getMsg());
        assertEquals(expectedList, result.getData());
        verify(messageService).queryAllMessage();
    }

    @Test
    void findMessageBySelective() throws Exception {
        MessageDTO messageDTO = new MessageDTO();
        List<MessageDTO> list = new ArrayList<>();
        list.add(messageDTO);
        when(messageService.selectMessageBySelective(any(MessageDTO.class))).thenReturn(list);

        Result<List<MessageDTO>> result = controller.findMessage(messageDTO);

        assertEquals(1, result.getCode());
        assertEquals("success", result.getMsg());
        verify(messageService).selectMessageBySelective(any(MessageDTO.class));
    }

    @Test
    void addMessage() throws Exception {
        Message message = new Message();

        Result<MessageDTO> result = controller.addAppointment(message);

        assertEquals(1, result.getCode());
        assertEquals("Add Message success", result.getMsg());
        verify(messageService).addMessage(any(Message.class));
    }

    @Test
    void updateMessage() throws Exception {
        Message message = new Message();
        message.setMid(1);
        List<MessageDTO> list = new ArrayList<>();
        list.add(new MessageDTO()); // 确保列表非空
        when(messageService.findMessageByPrimaryKey(message.getMid())).thenReturn(list);

        Result<MessageDTO> result = controller.updateCourse(message);

        assertEquals(1, result.getCode());
        assertNotEquals("Message not found", result.getMsg());
        verify(messageService).findMessageByPrimaryKey(message.getMid());
        verify(messageService).updateMessage(any(Message.class));
    }

    @Test
    void deleteMessage() throws Exception {
        Message message = new Message();
        message.setMid(1);
        List<MessageDTO> list = new ArrayList<>();
        list.add(new MessageDTO()); // 确保列表非空
        when(messageService.findMessageByPrimaryKey(message.getMid())).thenReturn(list);

        Result<MessageDTO> result = controller.deleteMessage(message);

        assertEquals(1, result.getCode());
        assertNotEquals("Message not found", result.getMsg());
        verify(messageService).findMessageByPrimaryKey(message.getMid());
        verify(messageService).deleteMessage(message.getMid());
    }
}
