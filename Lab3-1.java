import java.io.IOException;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.*;
import static java.lang.Math.min;
import static java.lang.Math.max;


class Lab3 {

    private static InputReader in;
    private static PrintWriter out;
    private static ArrayList<Integer> tmp7 = new ArrayList<Integer>();
    private static ArrayList<Integer> tmp = new ArrayList<Integer>();
    // TODO
    static private int findMaxBerlian(ArrayList<Integer> S, ArrayList<Integer> M, ArrayList<Integer> B) {
        int sum1=0;
        int sum2=0;
        ArrayList<Integer> tmp2 = new ArrayList<Integer>();
        ArrayList<Integer> tmp3 = new ArrayList<Integer>();
        for(int i=0; i<S.size(); i++){
            if(i%2==0){
                sum1 += S.get(i);
                sum2 += M.get(i);
                tmp2.add(S.get(i));
                tmp3.add(M.get(i));
                continue;
            }
            sum1 += M.get(i);
            sum2 += S.get(i);
            tmp2.add(M.get(i));
            tmp3.add(S.get(i));
            
        }
        ArrayList<Integer> tmp4 = new ArrayList<Integer>();
        ArrayList<Integer> tmp5 = new ArrayList<Integer>();
        ArrayList<Integer> tmp6 = new ArrayList<Integer>();
        tmp4=tmp3;
        tmp5=tmp3;
        tmp6=tmp2;
        if(sum1>sum2){
            tmp4=tmp2;
            tmp5=tmp2;
            tmp6=tmp3;
        }
        int sum=0;
        int counter=0;
        for(int i=S.size()-2, k=0; i>=0 && k<S.size()-2; i--,k++){
            for(int j=k; j<i; j++){
                sum += tmp4.get(j);
                counter++;
            }
            int max = Math.max(tmp4.get(i), tmp5.get(i+1));
            max = Math.max(max, tmp6.get(i+1));
            //System.out.println(max);
            sum += max;
            sum+= B.get(counter);
            tmp.add(sum);
            tmp7.add(counter+1);
            sum=0;
            counter=0;
            if(i==0){
                k++;
                i=S.size();
            }
        }
        
        return Collections.max(tmp);
    }

    // TODO
    static private int findBanyakGalian(ArrayList<Integer> S, ArrayList<Integer> M, ArrayList<Integer> B) {
        return tmp7.get(tmp.indexOf(Collections.max(tmp)));//
    }

    public static void main(String args[]) throws IOException {

        InputStream inputStream = System.in;
        in = new InputReader(inputStream);
        OutputStream outputStream = System.out;
        out = new PrintWriter(outputStream);

        ArrayList<Integer> S = new ArrayList<>();
        ArrayList<Integer> M = new ArrayList<>();
        ArrayList<Integer> B = new ArrayList<>();
        
        int N = in.nextInt();
        
        for(int i=0;i<N;i++) {
            int tmp = in.nextInt();
            S.add(tmp);
        }

        for(int i=0;i<N;i++) {
            int tmp = in.nextInt();
            M.add(tmp);
        }

        for(int i=0;i<N;i++) {
            int tmp = in.nextInt();
            B.add(tmp);
        }

        int jawabanBerlian = findMaxBerlian(S,M,B);
        int jawabanGalian = findBanyakGalian(S,M,B);

        out.print(jawabanBerlian + " " + jawabanGalian);

        out.flush();
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