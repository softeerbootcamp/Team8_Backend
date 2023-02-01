package site.devroad.softeer.src.user.dto;

public class PostSignUpReq {
    private String email;
    private String name;
    private String phone;
    private String password;

    public PostSignUpReq(String email, String name, String phone, String password) {
        this.email = email;
        this.name = name;
        this.phone = phone;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean hasNull() {
        if (email == null || name == null || phone == null || password == null)
            return true;
        if (email.trim().equals("") || name.trim().equals("") || phone.trim().equals("") || password.trim().equals(""))
            return true;
        return false;
    }
}
