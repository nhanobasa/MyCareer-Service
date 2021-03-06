package vn.nhantd.mycareer.model;

import java.util.ArrayList;
import java.util.List;

public class Transaction {
    private Long dt;
    private String transaction_code; // view - like- unlike
    private String user_id;
    private List<String> cv_path = new ArrayList<>();
    private Boolean viewed = false;

    public Transaction(Long dt, String transaction_code, String user_id, List<String> cv_path) {
        this.dt = dt;
        this.transaction_code = transaction_code;
        this.user_id = user_id;
        this.cv_path = cv_path;
    }

    public Transaction() {
        this.dt = System.currentTimeMillis();
    }

    public Long getDt() {
        return dt;
    }

    public void setDt(Long dt) {
        this.dt = dt;
    }

    public String getTransaction_code() {
        return transaction_code;
    }

    public void setTransaction_code(String transaction_code) {
        this.transaction_code = transaction_code;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public List<String> getCv_path() {
        return cv_path;
    }

    public void setCv_path(List<String> cv_path) {
        this.cv_path = cv_path;
    }

    public Boolean getViewed() {
        return viewed;
    }

    public void setViewed(Boolean viewed) {
        this.viewed = viewed;
    }
}
