package vn.nhantd.mycareer.model.firebase.mesaging;

import java.io.Serializable;

public class MyCareerNotification implements Serializable {

    String token;
    String topic;
    Note note;

    public MyCareerNotification(String token, String topic, Note note) {
        this.token = token;
        this.topic = topic;
        this.note = note;
    }

    public MyCareerNotification(String token, Note note) {
        this.token = token;
        this.note = note;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Note getNote() {
        return note;
    }

    public void setNote(Note note) {
        this.note = note;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }
}
