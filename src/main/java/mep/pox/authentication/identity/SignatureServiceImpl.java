package mep.pox.authentication.identity;

import lombok.extern.slf4j.Slf4j;
import org.springframework.util.Base64Utils;
import reactor.core.publisher.Mono;

import javax.annotation.PostConstruct;
import java.security.Key;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;

@Slf4j
public class SignatureServiceImpl implements SignatureService {

    private String privateKey;
    private String publicKey;

    @Override
    @PostConstruct
    public KeyPair initKeyPair() {
        if(privateKey != null) {
            // TODO: add error log and disallow running of the method
            return null;
        }
        try {
            KeyPairGenerator generator = KeyPairGenerator.getInstance("RSA");

            generator.initialize(4096);
            KeyPair keyPair = generator.generateKeyPair();

            // save the keypairs
            this.privateKey = formatKey(keyPair.getPrivate());
            this.publicKey = formatKey(keyPair.getPublic());

            return keyPair;

        } catch(NoSuchAlgorithmException nsae) {
            // TODO: log this error
            System.exit(600);
        }
        return null;
    }

    private String formatKey(Key key) {
        return Base64Utils.encodeToString(key.getEncoded());
    }

    @Override
    public Mono<String> signData(String data) {
        return null;
    }

    @Override
    public Mono<Boolean> verifySignature(String data, String signature) {
        return null;
    }
}
