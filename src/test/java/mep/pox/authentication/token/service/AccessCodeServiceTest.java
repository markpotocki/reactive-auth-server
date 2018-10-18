package mep.pox.authentication.token.service;


import com.fasterxml.jackson.databind.ObjectMapper;
import mep.pox.authentication.token.domain.AccessCodeToken;
import org.junit.Before;
import org.junit.Test;
import org.springframework.util.Base64Utils;

import javax.crypto.Cipher;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.time.Duration;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class AccessCodeServiceTest {

    private ObjectMapper mapper;

    @Before
    public void setup() {
        this.mapper = new ObjectMapper();
    }

    @Test
    public void encodingAccessCodeSuccessful() throws Exception {
        KeyPair keyPair = KeyPairGenerator.getInstance("RSA").generateKeyPair();
        AccessCodeService accessCodeService = new AccessCodeService(null, keyPair);
        accessCodeService.init();

        AccessCodeToken testCode = new AccessCodeToken("foo", "client", "CHALLENGECODE123", "SH256");
        String encodedCode = accessCodeService.encodeAccessCode(testCode).block(Duration.ofSeconds(30));
        assertNotNull(encodedCode);

        // decode the token and check value
        Cipher c = Cipher.getInstance("RSA");
        c.init(Cipher.DECRYPT_MODE, keyPair.getPrivate());
        AccessCodeToken actual = mapper.readValue(c.doFinal(Base64Utils.decodeFromString(encodedCode)), AccessCodeToken.class);

        assertEquals(testCode, actual);
    }

    @Test
    public void decodingAccessCodeSuccessful() throws Exception {
        KeyPair keyPair = KeyPairGenerator.getInstance("RSA").generateKeyPair();
        AccessCodeService accessCodeService = new AccessCodeService(null, keyPair);
        accessCodeService.init();

        // encode the token
        AccessCodeToken expected = new AccessCodeToken("foo", "client", "CHALLENGECODE123", "SH256");
        Cipher c = Cipher.getInstance("RSA");
        c.init(Cipher.ENCRYPT_MODE, keyPair.getPublic());
        String encoded = Base64Utils.encodeToString(c.doFinal(mapper.writeValueAsBytes(expected)));

        // the test
        AccessCodeToken actual = accessCodeService.decodeAccessCode(encoded).block(Duration.ofSeconds(30));

        assertEquals(expected, actual);
    }

}
