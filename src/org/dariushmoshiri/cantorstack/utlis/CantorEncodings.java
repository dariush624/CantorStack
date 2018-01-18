package org.dariushmoshiri.cantorstack.utlis;

import java.math.BigInteger;
import java.nio.charset.Charset;
import java.util.function.Function;

public class CantorEncodings {

    public static Function<Integer, BigInteger> integer() {
        return BigInteger::valueOf;
    }

    public static Function<Long, BigInteger> longs() {
        return BigInteger::valueOf;
    }

    public static Function<String, BigInteger> string(Charset charset) {
        return (value) -> {
          byte[] bytes = value.getBytes(charset);
          return new BigInteger(bytes);
        };
    }

}
