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

public class Lab0 {
    private static InputReader in;
    private static PrintWriter out;


    static int multiplyMod(int N, int P, int[] a) {
        BigInteger total =new BigInteger("1");
        for(int x : a){
            total = total.multiply(new BigInteger(Integer.toString(x)));
        }
        return total.mod(new BigInteger(Integer.toString(P))).intValue();
    }
    
    public static void main(String[] args) throws IOException {
        InputStream inputStream = System.in;
        in = new InputReader(inputStream);
        OutputStream outputStream = System.out;
        out = new PrintWriter(outputStream);

        // Read value of N and P
        int N = in.nextInt();

        int P = in.nextInt();

        // Read value of a
        int[] a = new int[N];
        for (int i = 0; i < N; ++i) {
            a[i] = in.nextInt();
        }
        
        // TODO: implement method multiplyMod(int, int, int[]) to get the answer
        int ans = multiplyMod(N, P, a);
        out.println(ans);
        // don't forget to close/flush the output
        out.close();
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