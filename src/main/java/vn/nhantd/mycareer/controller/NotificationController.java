package vn.nhantd.mycareer.controller;


import com.google.firebase.messaging.FirebaseMessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import vn.nhantd.mycareer.model.firebase.mesaging.MyCareerNotification;
import vn.nhantd.mycareer.model.firebase.mesaging.Note;
import vn.nhantd.mycareer.model.firebase.mesaging.Notification;
import vn.nhantd.mycareer.service.firebase.FirebaseMessagingService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/mycareer/api/v1/notification")
public class NotificationController {

    @Autowired
    FirebaseMessagingService firebaseMessagingService;

    @Autowired
    MongoTemplate mongoTemplate;

    @RequestMapping(value = "/send", method = RequestMethod.POST)
    @ResponseBody
    public String sendNotification(@RequestBody Note note,
                                   @RequestParam String token) throws FirebaseMessagingException {
        return firebaseMessagingService.sendNotificationToToken(new MyCareerNotification(token, note));
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    @ResponseBody
    public List<Notification> sendNotification(@RequestParam(value = "user_id") String user_id) {
        List<Notification> notificationList = mongoTemplate.find(new Query(Criteria.where("user_id").is(user_id)), Notification.class);
        return notificationList;
    }
}
