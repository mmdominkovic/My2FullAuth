package ba.sum.fpmoz.my2fullauth.model;

public class Users {
    public String uid;
    public String name;
    public String email;
    public String password;
    public String phonenumber;

public  Users(  String phonenumber){
    this.phonenumber=phonenumber;
};
    public Users( String name, String email, String password) {

        this.name = name;
        this.email = email;
        this.password= password;

    }


    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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
