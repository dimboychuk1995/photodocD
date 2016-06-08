package oe.roma.photodoc.domain;

import java.util.List;

public class CustomerObject {

    private String name;
    private String address;
    private String inspector;
    private String counter_number;
    private List<File> files;
    private Integer counter_id;

    public CustomerObject(String name, String address, String inspector,String counter_number,Integer counter_id) {
        this.name = name;
        this.address = address;
        this.inspector = inspector;
        this.counter_number = counter_number;
        this.counter_id = counter_id;
    }

    public CustomerObject() {
    }

    public List<File> getFiles() {
        return files;
    }

    public void setFiles(List<File> files) {
        this.files = files;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getCounter_id() {
        return counter_id;
    }

    public void setCounter_id(Integer counter_id) {
        this.counter_id = counter_id;
    }

    public String getCounter_number() {
        return counter_number;
    }

    public void setCounter_number(String counter_number) {
        this.counter_number = counter_number;
    }

    @Override
    public String toString() {
        return "CustomerObject{" +
                "name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", inspector='" + inspector + '\'' +
                ", files=" + files +
                ", counter_id=" + counter_id +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CustomerObject object = (CustomerObject) o;

        if (address != null ? !address.equals(object.address) : object.address != null) return false;
        if (counter_id != null ? !counter_id.equals(object.counter_id) : object.counter_id != null) return false;
        if (counter_number != null ? !counter_number.equals(object.counter_number) : object.counter_number != null)
            return false;
        if (files != null ? !files.equals(object.files) : object.files != null) return false;
        if (inspector != null ? !inspector.equals(object.inspector) : object.inspector != null) return false;
        if (name != null ? !name.equals(object.name) : object.name != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (address != null ? address.hashCode() : 0);
        result = 31 * result + (inspector != null ? inspector.hashCode() : 0);
        result = 31 * result + (counter_number != null ? counter_number.hashCode() : 0);
        result = 31 * result + (files != null ? files.hashCode() : 0);
        result = 31 * result + (counter_id != null ? counter_id.hashCode() : 0);
        return result;
    }
}
