package mep.pox.authentication.identity;

import org.junit.Test;

import java.security.KeyPair;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

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
}
