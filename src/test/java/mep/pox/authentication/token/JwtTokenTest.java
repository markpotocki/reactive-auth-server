package mep.pox.authentication.token;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertNotNull;

public class JwtTokenTest {

    @Test
    public void testEncodingWorksCorrectly() {
        ObjectMapper mapper = new ObjectMapper();
        JwtToken token = new JwtToken("foo", List.of("http://foo.com"));
        String encoded = token.getEncodedToken();
        assertNotNull(encoded);

        String[] splitEncoded = encoded.split("\\.");

    }
}
