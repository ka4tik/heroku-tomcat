package messenger.service;

import messenger.model.Message;

import java.util.List;

public interface MessageService {

     List<Message> getAllMessages();

     List<Message> getAllMessagesForYear(int year);

     List<Message> getAllMessagesPaginated(int start, int size);

     Message getMessage(long id);

     Message addMessage(Message message);

     Message updateMessage(Message message);

     Message removeMessage(long id);
}
