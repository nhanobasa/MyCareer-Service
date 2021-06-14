package vn.nhantd.mycareer.model;

import vn.nhantd.mycareer.model.user.User;

import java.io.Serializable;
import java.util.List;

public class ViewCV implements Serializable {
    User user;
    List<String> cv_path;

    public ViewCV() {
    }

    public ViewCV(User user, List<String> cv_path) {
        this.user = user;
        this.cv_path = cv_path;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<String> getCv_path() {
        return cv_path;
    }

    public void setCv_path(List<String> cv_path) {
        this.cv_path = cv_path;
    }
}
