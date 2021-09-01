package ba.sum.fpmoz.my2fullauth.model;

public class Users {
    public String uid;
    public String name;
    public String email;
    public String jmbg;
    public String phonenumber;


public  Users(  String phonenumber){
    this.phonenumber=phonenumber;
};
    public Users( String uid, String name, String email, String jmbg) {
        this.uid=uid;
        this.name = name;
        this.email = email;
        this.jmbg= jmbg;

    }

public Users(){}

    public String getJmbg() {
        return jmbg;
    }

    public void setJmbg(String password) {
        this.jmbg = password;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhonenumber() {
        return phonenumber;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }
}
