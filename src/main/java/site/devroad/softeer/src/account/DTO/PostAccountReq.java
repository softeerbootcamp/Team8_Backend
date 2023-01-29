package site.devroad.softeer.src.account.DTO;

import lombok.Data;

@Data
public class PostAccountReq {
    private String name;
    private String email;
    private String password;

    public boolean isValid(){
        return name != null && email != null && password != null;
    }
}
