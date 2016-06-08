package oe.roma.photodoc.domain;

/**
 * Created by us8610 on 26.06.14.
 */
public class Procedure {

    private String counter_number;
    private String contract_number;
    private String customer_name;
    private String object_name;
    private String address;
    private String inspector;
    private Integer counter_id;

    public String getCounter_number() {
        return counter_number;
    }

    public void setCounter_number(String counter_number) {
        this.counter_number = counter_number;
    }

    public String getContract_number() {
        return contract_number;
    }

    public void setContract_number(String contract_number) {
        this.contract_number = contract_number;
    }

    public String getCustomer_name() {
        return customer_name;
    }

    public void setCustomer_name(String customer_name) {
        this.customer_name = customer_name;
    }

    public String getObject_name() {
        return object_name;
    }

    public void setObject_name(String object_name) {
        this.object_name = object_name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getInspector() {
        return inspector;
    }

    public void setInspector(String inspector) {
        this.inspector = inspector;
    }

    public Integer getCounter_id() {
        return counter_id;
    }

    public void setCounter_id(Integer counter_id) {
        this.counter_id = counter_id;
    }
}
