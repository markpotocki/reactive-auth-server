package mep.pox.authentication.identity;

import reactor.core.publisher.Mono;

import java.security.KeyPair;

public interface SignatureService {

    /**
     * Needs to be started to initialize the keys to be used for creating the signature. It is blocking but should be
     * given a timeout. If this Service fails to start the application should exit because it will not function without
     * a working KeyPair (there are no default fallbacks)
     * @return returns the KeyPair to be used
     */
    KeyPair initKeyPair();

    /**
     * Uses the Signature from the initialized KeyPair to sign a given String.
     * @param data base64 encoding of the data to be signed
     * @return the String representation of the Signature in Base64 encoding
     */
    Mono<String> signData(Mono<String> data);

    /**
     * Checks whether the given Signature is valid and returns a boolean of true if it is a valid signature and false
     * if it is not. Valid would be a signature generated from this service's KeyPair.
     * @param data base64 encoding of the data to verify
     * @return True if the Signature was signed by this service and false if not
     */
    Mono<Boolean> verifySignature(Mono<String> data);


}
