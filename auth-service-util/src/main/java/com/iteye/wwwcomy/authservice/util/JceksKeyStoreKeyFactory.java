package com.iteye.wwwcomy.authservice.util;

import java.security.Key;
import java.security.KeyStore;

import javax.crypto.SecretKey;

import org.springframework.core.io.Resource;

/**
 * Factory for secret key from a JCEKS keystore file. User provides a
 * {@link Resource} location of a keystore file and the password to unlock it,
 * and the factory grabs the secret from the store by name (and optionally
 * password).
 * 
 */
public class JceksKeyStoreKeyFactory {

    private Resource resource;

    private char[] password;

    private volatile KeyStore store;

    private Object lock = new Object();

    public JceksKeyStoreKeyFactory(Resource resource, char[] password) {
        this.resource = resource;
        this.password = password;
    }

    public Key getSecretKey(String alias, char[] password) {
        try {
            synchronized (lock) {
                if (store == null) {
                    synchronized (lock) {
                        store = KeyStore.getInstance("jceks");
                        store.load(resource.getInputStream(), this.password);
                    }
                }
            }
            SecretKey key = (SecretKey) store.getKey(alias, "".toCharArray());
            return key;
        } catch (Exception e) {
            throw new IllegalStateException("Cannot load keys from store: " + resource, e);
        }
    }

}
