package com.yql.biz.support.validation;

import com.yql.biz.exception.JatSignInvalidException;
import com.yql.biz.model.Jat;
import org.springframework.stereotype.Component;
import org.springframework.util.Base64Utils;
import org.springframework.util.StringUtils;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

/**
 * @author wangxiaohong
 */
@Component
public class JatSignValidator implements Validator<Jat> {
    @Override
    public boolean isValid(Jat jat) {
        String sign = jat.getSign();
        String alg = jat.getAlg();
        if (!StringUtils.hasText(alg)) {
            alg = Algorithm.HS256.name();
        }
        try {
            byte[] hmac = signHmac(Algorithm.valueOf(alg), jat.toString(), jat.getAppKey().getBytes());
            String encodeToString = Base64Utils.encodeToString(hmac);
            if (!equals(sign, encodeToString)) {
                throw new JatSignInvalidException();
            }
        } catch (NoSuchAlgorithmException | InvalidKeyException e) {
            throw new JatSignInvalidException();
        }
        return true;
    }

    private static boolean equals(String str1, String str2) {
        return str1 == null ? str2 == null : str1.equals(str2);
    }

    private static byte[] signHmac(final Algorithm algorithm, final String msg, final byte[] secret) throws NoSuchAlgorithmException, InvalidKeyException {
        final Mac mac = Mac.getInstance(algorithm.getValue());
        mac.init(new SecretKeySpec(secret, algorithm.getValue()));
        return mac.doFinal(msg.getBytes());
    }

    /**
     * Supported Library Algorithms
     * <p>
     * https://docs.oracle.com/javase/7/docs/technotes/guides/security/StandardNames.html#KeyPairGenerator
     */
    private enum Algorithm {

        HS256("HmacSHA256"), HS384("HmacSHA384"), HS512("HmacSHA512");

        Algorithm(final String value) {
            this.value = value;
        }

        private String value;

        public String getValue() {
            return value;
        }
    }
}
