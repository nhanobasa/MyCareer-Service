package vn.nhantd.mycareer.model.job;

import org.apache.logging.log4j.core.config.plugins.validation.constraints.Required;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection = "Category")
public class CareerCategory {
    @Id
    @Field("_id")
    private String _id = null;
    private String _partition = "sync";

    @Indexed(unique = true)
    private String name = "";
    
    private Long total_user = 0L;
    private Long total_job = 0L;

    public CareerCategory() {
    }

    public CareerCategory(String _id, String _partition, String name, Long total_user, Long total_job) {
        this._id = _id;
        this._partition = _partition;
        this.name = name;
        this.total_user = total_user;
        this.total_job = total_job;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String get_partition() {
        return _partition;
    }

    public void set_partition(String _partition) {
        this._partition = _partition;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getTotal_user() {
        return total_user;
    }

    public void setTotal_user(Long total_user) {
        this.total_user = total_user;
    }

    public Long getTotal_job() {
        return total_job;
    }

    public void setTotal_job(Long total_job) {
        this.total_job = total_job;
    }
}
