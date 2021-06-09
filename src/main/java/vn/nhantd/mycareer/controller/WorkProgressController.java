package vn.nhantd.mycareer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import vn.nhantd.mycareer.model.user.WorkProgress;
import vn.nhantd.mycareer.repository.WPRepository;

import java.util.List;

@RestController
@RequestMapping("/mycareer/api/v1/user/work_progress")
public class WorkProgressController {
    @Autowired
    WPRepository wpRepository;

    @Autowired
    MongoTemplate mongoTemplate;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public List<WorkProgress> getWorkProgressOfUser(
            @RequestParam(required = false, name = "user_id") String user_id) {
        Query query = new Query(Criteria.where("user_id").is(user_id));

        List<WorkProgress> list = mongoTemplate.find(query, WorkProgress.class);

        return list;
    }
}
