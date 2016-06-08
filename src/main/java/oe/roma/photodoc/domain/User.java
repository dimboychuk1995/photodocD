package oe.roma.photodoc.domain;

/**
 * Created by us8610 on 31.07.14.
 */
public class User {
    private Integer id;
    private String login;
    private Rem rem = new Rem();
    private Integer permission;
    private String full_name;
    private String email;
    private Integer enabled;
    private Integer tab_number;
    private String viddil;
    private Integer role;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public Rem getRem() {
        return rem;
    }

    public void setRem(Rem rem) {
        this.rem = rem;
    }

    public Integer getPermission(){
        return permission;
    }

    public void setPermission(int permission){
        this.permission = permission;
    }

    public String getFull_name() {
        return full_name;
    }

    public void setFull_name(String full_name) {
        this.full_name = full_name;
    }

    public Integer getEnabled(){return enabled;}

    public void setEnabled(Integer enabled) {
        this.enabled = enabled;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email){this.email = email; }

    public Integer getTab_number(){ return tab_number; }

    public void setTab_number(Integer tab_number){this.tab_number = tab_number;}

    public String getViddil() {return viddil;}

    public void setViddil(String viddil) {this.viddil = viddil;}

    public Integer getRole() {return role;}

    public void setRole(Integer role) {this.role = role;}

    public User(String login ,String name, String email){
        this.login = login;
        this.full_name = name;
        this.email = email;
    }

    public User(){

    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", login='" + login + '\'' +
                ", rem=" + rem +
                ", permission=" + permission +
                ", full_name=" + full_name +
                ", email=" + email +
                ", viddil=" + viddil +
                ", role=" + role +
                ", tab_number" + tab_number +
                '}';
    }
}
