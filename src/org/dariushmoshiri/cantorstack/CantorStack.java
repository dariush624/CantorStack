package org.dariushmoshiri.cantorstack;

import javafx.util.Pair;

import java.math.BigInteger;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.function.Function;

/**
 * This kind of stack stores all the information inside a number by using the Cantor Pairing Function.
 * It can be used only if the data can be represented as a number.
 *
 * BigInteger is used to store information in order to avoid overflow's errors.
 *
 * @param <T>
 * @author Dariush Moshiri
 */

public class CantorStack<T> implements Iterable<T> {

    private BigInteger base = BigInteger.ZERO;
    private int size = 0;
    private Function<BigInteger, T> decoding;
    private Function<T, BigInteger> encoding;

    /**
     * CantorStack's constructor
     * @param decoding Function used to decode the BigInteger number to user Type
     * @param encoding Function used to encode the user Type to BigInteger number
     */
    public CantorStack(Function<BigInteger, T> decoding, Function<T, BigInteger> encoding) {
        this.decoding = decoding;
        this.encoding = encoding;
    }

    private BigInteger pair(BigInteger first, BigInteger second) {
        return (first.add(second)).multiply(first.add(second).add(BigInteger.ONE)).divide(BigInteger.valueOf(2)).add(second);
    }

    private Pair<BigInteger, BigInteger> decode(BigInteger encoded) {
        BigInteger w = (sqrt(BigInteger.valueOf(8).multiply(encoded).add(BigInteger.ONE)).subtract(BigInteger.ONE)).divide(BigInteger.valueOf(2));
        BigInteger t = (w.pow(2).add(w)).divide(BigInteger.valueOf(2));
        BigInteger y = encoded.subtract(t);
        BigInteger x = w.subtract(y);
        return new Pair<>(x, y);
    }

    //Thanks to Maximilian Köstler, Stack Overflow question: https://stackoverflow.com/questions/42204941/square-root-and-operators-for-biginteger-and-bigdecimal
    private BigInteger sqrt(BigInteger n) {
        BigInteger a = BigInteger.ONE;
        BigInteger b = n.shiftRight(5).add(BigInteger.valueOf(8));
        while (b.compareTo(a) >= 0) {
            BigInteger mid = a.add(b).shiftRight(1);
            if (mid.multiply(mid).compareTo(n) > 0) {
                b = mid.subtract(BigInteger.ONE);
            } else {
                a = mid.add(BigInteger.ONE);
            }
        }
        return a.subtract(BigInteger.ONE);
    }


    /**
     * Insert an element inside the stack
     * @param elem element to be inserted
     */
    public void push(T elem) {
        base = pair(base, encoding.apply(elem));
        size++;
    }

    /**
     * Remove the first elements from the stack and returns it
     * @return the first element of the stack
     */
    public T pop() {

        if(size == 0)
            throw new NoSuchElementException("Stack is empty");

        Pair<BigInteger, BigInteger> result = decode(base);
        base = result.getKey();
        size--;
        return decoding.apply(result.getValue());
    }

    /**
     *
     * @return stack's size
     */
    public int size() {
        return size;
    }

    /**
     *
     * @return true if the stack is empty, false otherwise
     */
    public boolean isEmpty() {
        return size==0;
    }

    @Override
    public Iterator<T> iterator() {
        return new CantorIterator();
    }

    private class CantorIterator implements Iterator<T> {

        @Override
        public boolean hasNext() {
            return size != 0;
        }

        @Override
        public T next() {
            return pop();
        }
    }

}
