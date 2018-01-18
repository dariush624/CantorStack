package org.dariushmoshiri.cantorstack.utlis;

import java.math.BigInteger;
import java.nio.charset.Charset;
import java.util.function.Function;

public class CantorDecodings {

    public static Function<BigInteger, Integer> integer() {
        return BigInteger::intValue;
    }


    public static Function<BigInteger, Long> longs() {
        return BigInteger::longValue;
    }

    public static Function<BigInteger, String> string(Charset charset) {
        return (value) -> new String(value.toByteArray(), charset);
    }

}
