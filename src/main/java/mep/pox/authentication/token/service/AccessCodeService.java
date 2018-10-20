package mep.pox.authentication.token.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mep.pox.authentication.client.ClientService;
import mep.pox.authentication.token.domain.AccessCodeToken;
import org.springframework.util.Base64Utils;
import reactor.core.publisher.Mono;

import javax.annotation.PostConstruct;
import javax.crypto.Cipher;
import java.security.KeyPair;
import java.security.KeyPairGenerator;

@RequiredArgsConstructor
@Slf4j
public class AccessCodeService {

    private final ClientService clientService;
    private KeyPair keyPair;
    private Cipher encryptor;
    private Cipher decryptor;
    private ObjectMapper objectMapper = new ObjectMapper();

    public AccessCodeService(ClientService cs, KeyPair p) {
        this.clientService = cs;
        this.keyPair = p;
    }

    @PostConstruct
    public void init() {
        try {
            if(this.keyPair == null) {
                log.warn("No key pair given. Generating one for AccessCodeService.");
                this.keyPair = KeyPairGenerator.getInstance("RSA").generateKeyPair();
            }
            this.encryptor = Cipher.getInstance("RSA");
            this.decryptor =
                    Cipher.getInstance("RSA");
            this.encryptor.init(Cipher.ENCRYPT_MODE, this.keyPair.getPublic());
            this.decryptor.init(Cipher.DECRYPT_MODE, this.keyPair.getPrivate());
        } catch (Exception e) {
            log.error("Initialization error in AccessCodeService: Failed to initialize keyPairs");
        }
    }


    public Mono<String> encodeAccessCode(AccessCodeToken code) {

        return Mono.fromSupplier( () -> {
            try {
                byte[] stringifiedCode = objectMapper.writeValueAsBytes(code);
                return Base64Utils.encodeToUrlSafeString(this.encryptor.doFinal(stringifiedCode));
            } catch (JsonProcessingException jpe) {
                log.error("Error mapping access code: Failed to map object to string");
            } catch (Exception e) {
                log.error("Error with encrypting AccessCode");
            }
            return null; // TODO remove null return
        });
    }

    public Mono<AccessCodeToken> decodeAccessCode(String base64EncodedEncryptedToken) {
        return Mono.fromSupplier( () -> {
            try {
                byte[] decryptedString = decryptor.doFinal(Base64Utils.decodeFromUrlSafeString(base64EncodedEncryptedToken));
                return objectMapper.readValue(decryptedString, AccessCodeToken.class);
            } catch (Exception e) {
                log.error("Exception in decrpytor", e);
                return null;
            }
            });
    }

}
