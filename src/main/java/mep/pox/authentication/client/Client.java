package mep.pox.authentication.client;


import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.List;

@Document
@Data
public class Client {

    @Id
    private String id;
    private String clientId;
    private List<SimpleGrantedAuthority> grantedAuthorities;
    private List<String> scopes;
    private List<String> redirectUris;
    private Boolean hasClientSecret;
    private String clientSecret;

}
