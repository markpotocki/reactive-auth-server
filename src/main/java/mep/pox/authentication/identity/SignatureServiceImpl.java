package mep.pox.authentication.identity;

import lombok.extern.slf4j.Slf4j;
import org.springframework.util.Base64Utils;
import reactor.core.publisher.Mono;

import javax.annotation.PostConstruct;
import java.security.*;

@Slf4j
public class SignatureServiceImpl implements SignatureService {

    private String privateKey;
    private String publicKey;
    private Signature signer;
    private Signature verifier;

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

            // create signer object
            Signature s = Signature.getInstance("SHA256withRSA");
            s.initSign(keyPair.getPrivate());
            this.signer = s;

            // create verifier object
            Signature q = Signature.getInstance("SHA256withRSA");
            q.initVerify(keyPair.getPublic());
            this.verifier = q;

            return keyPair;

        } catch(NoSuchAlgorithmException nsae) {
            // TODO: log this error
            System.exit(600);
        } catch(InvalidKeyException ike) {
            // TODO: log this error
            System.exit(601);
        }
        return null;
    }

    private String formatKey(Key key) {
        return Base64Utils.encodeToString(key.getEncoded());
    }

    @Override
    public Mono<String> signData(Mono<String> data) {
        return data
                .map( stringData -> updateSignature(stringData.getBytes()))
                .map( stringData -> sign());
    }

    private String updateSignature(byte[] byteData) {
        try {
            this.signer.update(byteData);
        } catch (SignatureException se) {
            // TODO: log error of failing to update
        }
        return byteData.toString();
    }

    private String sign() {
        try {
            return this.signer.sign().toString();
        } catch (SignatureException se) {
            // TODO: log error of failing to sign
        }
        return null;
    }

    @Override
    public Mono<Boolean> verifySignature(Mono<String> data) {
        return null;
    }
}
