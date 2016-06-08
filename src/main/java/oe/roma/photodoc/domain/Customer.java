package oe.roma.photodoc.domain;

import java.util.List;

/**
 * Created by us8610 on 19.06.14.
 */
public class Customer {

    private String contract_number;
    private String name;
    private List<CustomerObject> objects;

    public Customer(String contract_number, String name) {
        this.contract_number = contract_number;
        this.name = name;
    }

    public Customer() {
    }

    public String getContract_number() {
        return contract_number;
    }

    public void setContract_number(String contract_number) {
        this.contract_number = contract_number;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<CustomerObject> getObjects() {
        return objects;
    }

    public void setObjects(List<CustomerObject> objects) {
        this.objects = objects;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Customer customer = (Customer)o;

        if (name != null ? !name.equals(customer.name) : customer.name != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return name != null ? name.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "Customer{" +
                ", contract_number='" + contract_number + '\'' +
                ", name='" + name + '\'' +
                ", objects=" + objects +
                '}';
    }
}
