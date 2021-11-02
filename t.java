import java.io.IOException;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.*;

import static java.lang.Math.min;
import static java.lang.Math.max;
import java.math.BigInteger;

public class t {
    private static InputReader in;
    private static PrintWriter out;

    
    public static void main(String[] args) throws IOException {
        InputStream inputStream = System.in;
        in = new InputReader(inputStream);
        OutputStream outputStream = System.out;
        out = new PrintWriter(outputStream);
        processThis("a + b * c + d * e * f + g");

        
    }
    static void processThis(String strinput) {
    StringTokenizer tok = new StringTokenizer(strinput," ");
    System.out.println(tok.toString());
    Stack<String> s = new Stack<>();
    s.push("");
    while (tok.hasMoreTokens()) {
       String t = tok.nextToken();
       if (isOperator(t)) {
          while (!isHigherOrder(t, s.peek())) 
             System.out.print(s.pop());
             s.push(t);
       } else System.out.print(t);
    }
    while (!s.isEmpty())
        System.out.print(s.pop());
    System.out.println();
 }

static boolean isOperator(String t) {
    return t.equals("+") || t.equals("*");
}

static boolean isHigherOrder(String x, String y) {
    if (y.equals("")) return true;
    if (orderOf(x) > orderOf( y )) return true;
    return false;
}

static int orderOf(String op) {
    if (op.equals("+")) return 1;
    if (op.equals("*")) return 2;
    return -99;
}

    // taken from https://codeforces.com/submissions/Petr
    // together with PrintWriter, these input-output (IO) is much faster than the usual Scanner(System.in) and System.out
    // please use these classes to avoid your fast algorithm gets Time Limit Exceeded caused by slow input-output (IO)
    static class InputReader {
        public BufferedReader reader;
        public StringTokenizer tokenizer;
 
        public InputReader(InputStream stream) {
            reader = new BufferedReader(new InputStreamReader(stream), 32768);
            tokenizer = null;
        }
 
        public String next() {
            while (tokenizer == null || !tokenizer.hasMoreTokens()) {
                try {
                    tokenizer = new StringTokenizer(reader.readLine());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            return tokenizer.nextToken();
        }
 
        public int nextInt() {
            return Integer.parseInt(next());
        }
 
    }
}