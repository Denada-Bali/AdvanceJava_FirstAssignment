package model;

import java.util.Iterator;

/**
 * @author Denalda | Danja
 * Created on January 02, 2023, at 02:32 AM
 */

public class NGram implements Iterator<String> {

    private static final int DEFAULT_N = 2;
    private String str;
    private final int n;

    int position = 0;


    /**
     * Default value for n-gram length (2).
     */

    public NGram(String str) {
        this.n = DEFAULT_N;
        this.str = str;
    }

    /**
     * Given value for n-gram length.
     */
    public NGram(final int n) {
        this.n = n;
    }

    /**
     * Given value for n-gram length and String (in our case is text)
     */
    public NGram(int n, String str) {
        this.n = n;
        this.str = str;
    }

    @Override
    public boolean hasNext() {
        return position < str.length() - n + 1;
    }

    @Override
    public String next() {
        return str.substring(position, position++ + n);
    }

}