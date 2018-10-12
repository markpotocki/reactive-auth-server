package mep.pox.authentication.identity;

import org.junit.Test;
import org.springframework.util.Base64Utils;
import reactor.core.publisher.Mono;

import java.security.KeyPair;
import java.security.Signature;
import java.time.Duration;

import static org.junit.Assert.*;

public class SignatureServiceImplTest {

    @Test
    public void testInitializeWorks() {
        SignatureServiceImpl sigSerivce = new SignatureServiceImpl();
        KeyPair keys = sigSerivce.initKeyPair();

        // assert keys exist
        assertNotNull(keys.getPrivate());
        assertNotNull(keys.getPublic());
    }

    @Test
    public void testInitializeTwiceFails() {
        SignatureServiceImpl sigService = new SignatureServiceImpl();
        sigService.initKeyPair();
        KeyPair keys = sigService.initKeyPair();

        // should be null
        assertNull(keys);
    }

    @Test
    public void testSigning() throws Exception {
        SignatureServiceImpl sigService = new SignatureServiceImpl();
        KeyPair keyPairs = sigService.initKeyPair();

        Mono<String> testData = Mono.just("HelloWorld");
        String signature = sigService.signData(testData).block(Duration.ofSeconds(30));

        Signature s = Signature.getInstance("SHA256withRSA");

        s.initVerify(keyPairs.getPublic());
        s.update(testData.block(Duration.ofSeconds(30)).getBytes());
        boolean verifySuccess = s.verify(Base64Utils.decodeFromString(signature));

        assertTrue(verifySuccess);
    }

}
