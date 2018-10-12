package mep.pox.authentication.token;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.util.Base64Utils;
import org.springframework.util.StringUtils;

import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

public class JwtToken {

    private final String ISSUER_STRING = "https://local.mep.dev";
    private final Long TOKEN_DURATION = Duration.ofMinutes(15).toMillis();
    private ObjectMapper mapper = new ObjectMapper();

    private JwtTokenHeader header;
    private JwtTokenPayload payload;

    public JwtToken(String subject, List<String> audience) {
        this.header = new JwtTokenHeader();
        this.payload = new JwtTokenPayload(subject, StringUtils.collectionToCommaDelimitedString(audience));
    }

    public String getEncodedToken() {
        return getEncoded(header) + "." + getEncoded(payload);
    }

    private String getEncoded(Object o) {
        try {
            String jsonified = mapper.writeValueAsString(o);
            return Base64Utils.encodeToString(jsonified.getBytes());
        } catch(JsonProcessingException jpe) {
            // TODO: log this error
        }
        return null;
    }


    class JwtTokenHeader {
        private String alg;
        private String typ;

        JwtTokenHeader() {
            this.alg = "RS4096";
            this.typ = "JWT";
        }
    }

    class JwtTokenPayload {
        private String iss;
        private String sub;
        private String aud;
        private Long exp;
        private Long nbf;
        private Long iat;
        private String jti;

        private List<GrantedAuthority> grantedAuthorities;

        JwtTokenPayload(String subject, String audience) {
            this.iss = ISSUER_STRING;
            this.exp = Instant.now().toEpochMilli() + TOKEN_DURATION;
            this.nbf = Instant.now().toEpochMilli();
            this.iat = Instant.now().toEpochMilli();
            this.jti = UUID.randomUUID().toString();

            this.sub = subject;
            this.aud = audience;
        }

    }
}
