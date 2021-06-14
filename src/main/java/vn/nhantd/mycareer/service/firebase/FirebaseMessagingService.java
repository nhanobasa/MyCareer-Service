package vn.nhantd.mycareer.service.firebase;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import org.springframework.stereotype.Service;
import vn.nhantd.mycareer.model.firebase.mesaging.MyCareerNotification;
import vn.nhantd.mycareer.model.firebase.mesaging.Note;

import java.util.ArrayList;
import java.util.List;

@Service
public class FirebaseMessagingService {

    private final FirebaseMessaging firebaseMessaging;

    public FirebaseMessagingService(FirebaseMessaging firebaseMessaging) {
        this.firebaseMessaging = firebaseMessaging;
    }


    public String sendNotificationToTopic(MyCareerNotification myCareerNotification) throws FirebaseMessagingException {

        Message message = getMessageTopic(myCareerNotification);

        return firebaseMessaging.send(message);
    }

    public String sendNotificationToToken(MyCareerNotification myCareerNotification) throws FirebaseMessagingException {
        Message message = getMessageToken(myCareerNotification);

        return firebaseMessaging.send(message);
    }

    public Boolean sendNotificationToList(List<MyCareerNotification> myCareerNotifications) {

        List<Message> messageList = new ArrayList<>();

        for (MyCareerNotification myCareerNotification : myCareerNotifications) {

            Message message = getMessageToken(myCareerNotification);

            messageList.add(message);
        }

        try {
            firebaseMessaging.sendAll(messageList);
            return true;
        } catch (FirebaseMessagingException e) {
            e.printStackTrace();
        }
        return false;
    }

    private Notification getNotification(Note note) {
        Notification notification = Notification
                .builder()
                .setTitle(note.getSubject())
                .setBody(note.getContent())
                .setImage(note.getImage())
                .build();

        return notification;
    }

    /**
     * Chú ý chỉ được dùng token hoặc topic, hoặc điều kiện nào khác một cách duy nhất, không được dùng chung.
     *
     * @param myCareerNotification
     * @return
     */
    private Message getMessageToken(MyCareerNotification myCareerNotification) {

        String token = myCareerNotification.getToken();
        Note note = myCareerNotification.getNote();
        Message message;
        if (note.getData() != null) {
            message = Message
                    .builder()
                    .setToken(token)
                    .setNotification(getNotification(note))
                    .putAllData(note.getData())
                    .build();
        } else {
            message = Message
                    .builder()
                    .setToken(token)
                    .setNotification(getNotification(note))
                    .build();
        }


        return message;
    }

    private Message getMessageTopic(MyCareerNotification myCareerNotification) {

        String topic = myCareerNotification.getTopic();
        Note note = myCareerNotification.getNote();

        Message message;

        if (note.getData() != null) {
            message = Message
                    .builder()
                    .setTopic(topic)
                    .setNotification(getNotification(note))
                    .putAllData(note.getData())
                    .build();
        } else {
            message = Message
                    .builder()
                    .setTopic(topic)
                    .setNotification(getNotification(note))
                    .build();
        }

        return message;
    }

}