package oe.roma.photodoc.domain;

import java.util.Date;

public class File {
    private Integer id;
    private Integer counter_id;
    private Department department = new Department();
    private String href;
    private String name;
    private Date date;
    private TypeDocument typeDocument = new TypeDocument();
    private Integer value_type_id;
    private String user_name;


    @Override
    public String toString() {
        return "File{" +
                "id=" + id +
                ", counter_id=" + counter_id +
                ", department=" + department +
                ", href='" + href + '\'' +
                ", name='" + name + '\'' +
                ", date=" + date +
                ", typeDocument=" + typeDocument +
                ", value_type_id=" + value_type_id +
                ", user_name=" + user_name +
                '}';
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCounter_id() {
        return counter_id;
    }

    public void setCounter_id(Integer counter_id) {
        this.counter_id = counter_id;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public TypeDocument getTypeDocument() {
        return typeDocument;
    }

    public void setTypeDocument(TypeDocument typeDocument) {
        this.typeDocument = typeDocument;
    }

    public Integer getValue_type_id() {
        return value_type_id;
    }

    public void setValue_type_id(Integer value_type_id) {
        this.value_type_id = value_type_id;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String full_name) {
        this.user_name = full_name;
    }
}
