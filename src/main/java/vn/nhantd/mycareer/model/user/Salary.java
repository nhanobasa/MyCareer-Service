package vn.nhantd.mycareer.model.user;

public class Salary {
    private String currency_unit;
    private Double from_salary;
    private Double to_salary;

    // Standard getters & setters
    public String getCurrency_unit() {
        return currency_unit;
    }

    public void setCurrency_unit(String currency_unit) {
        this.currency_unit = currency_unit;
    }

    public Double getFrom_salary() {
        return from_salary;
    }

    public void setFrom_salary(Double from_salary) {
        this.from_salary = from_salary;
    }

    public Double getTo_salary() {
        return to_salary;
    }

    public void setTo_salary(Double to_salary) {
        this.to_salary = to_salary;
    }

    @Override
    public String toString() {
        if (currency_unit.equals("Thỏa thuận"))
            return currency_unit;
        return from_salary + " - " + to_salary + " " + currency_unit;
    }
}
