package oe.roma.photodoc.domain;
public class TypeDocument {

    private Integer id;
    private String name;
    private Integer document_type;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getDocument_type(){
        return document_type;
    }

    public void setDocument_type(Integer document_type){
        this.document_type = document_type;
    }

    @Override
    public String toString() {
        return "TypeDocument{" +
                "id=" + id +
                ", name='" + name +
                ", document_type='" + document_type +
                '\'' +
                '}';
    }
}
