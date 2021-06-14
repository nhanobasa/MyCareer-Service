package vn.nhantd.mycareer.model.user;

import com.google.gson.annotations.SerializedName;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection = "User")
public class User {

    @Id
    @Field("_id")
    private String _id = null;
    private String _partition = null;
    private String address = null;
    private User_career_goals career_goals = null;
    private String dob = null;
    private User_education education = null;
    private String email = null;
    private User_experience experience = null;
    private String marital_status = null;
    private String name = null;
    private String phone = null;
    private String photoUrl = null;
    private User_references references = null;
    private String sex = null;

    private String uid = null;

    private String username = null;

    private String fgm_token = "";

    public String getFgm_token() {
        return fgm_token;
    }

    public void setFgm_token(String fgm_token) {
        this.fgm_token = fgm_token;
    }


    // Standard getters & setters
    public String getId() {
        return _id;
    }

    public void setId(String id) {
        this._id = id;
    }

    public String get_partition() {
        return _partition;
    }

    public void set_partition(String _partition) {
        this._partition = _partition;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public User_career_goals getCareer_goals() {
        return career_goals;
    }

    public void setCareer_goals(User_career_goals career_goals) {
        this.career_goals = career_goals;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public User_education getEducation() {
        return education;
    }

    public void setEducation(User_education education) {
        this.education = education;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public User_experience getExperience() {
        return experience;
    }

    public void setExperience(User_experience experience) {
        this.experience = experience;
    }

    public String getMarital_status() {
        return marital_status;
    }

    public void setMarital_status(String marital_status) {
        this.marital_status = marital_status;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public User_references getReferences() {
        return references;
    }

    public void setReferences(User_references references) {
        this.references = references;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }


}
