package messenger.service;

import messenger.db.MockDatabaseClass;
import messenger.model.Message;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;


public class MessageMockService implements MessageService {

    private Map<Long, Message> messages = MockDatabaseClass.getMessages();


    public MessageMockService() {
        DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
        try {
            messages.put(1L, new Message(1, "Hello world!", "kartik"));
            messages.put(2L, new Message(2, "Hello jersy!", "josh"));
            messages.put(3L, new Message(3, "Hello 2014!", dateFormat.parse("20140216"), "kartik"));
            messages.put(4L, new Message(4, "Hello 2013!", dateFormat.parse("20130301"), "john"));
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

    }


    public List<Message> getAllMessages() {
        return new ArrayList<Message>(messages.values());
    }

    public List<Message> getAllMessagesForYear(int year) {
        ArrayList<Message> messagesForYear = new ArrayList<>();
        Calendar calendar = Calendar.getInstance();
        for (Message message : messages.values()) {
            calendar.setTime(message.getCreated());
            if (calendar.get(Calendar.YEAR) == year)
                messagesForYear.add(message);
        }
        return messagesForYear;
    }

    public List<Message> getAllMessagesPaginated(int start, int size) {
        List<Message> list = new ArrayList<>(messages.values());
        if (start + size > list.size())
            return new ArrayList<>();
        else
            return list.subList(start, start + size);
    }


    public Message getMessage(long id) {
        return messages.get(id);
    }

    public Message addMessage(Message message) {
        message.setId(messages.size() + 1);
        message.setCreated(new Date());
        messages.put(message.getId(), message);
        return message;
    }

    public Message updateMessage(Message message) {
        if (message.getId() <= 0)
            return null;
        messages.put(message.getId(), message);
        return message;
    }

    public Message removeMessage(long id) {
        return messages.remove(id);
    }

}
