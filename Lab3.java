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
    //private static ArrayList<Integer> counter = new ArrayList<Integer>();
    //private static ArrayList<Integer> sumResult = new ArrayList<Integer>();
    private static ArrayList<Integer> S = new ArrayList<>();
    private static ArrayList<Integer> M = new ArrayList<>();
    private static ArrayList<Integer> B = new ArrayList<>();
    //private static Set<String> res = new TreeSet<String>();
    private static int max = Integer.MIN_VALUE;
    private static int min = Integer.MAX_VALUE;
    //private static TreeSet<int[]> res = (TreeSet<int[]>)s.descendingSet();

    // TODO
    
    static void combine(int k){
        int n = 3;
        char[] set = {'S','M','B'};
        combineHelper(set, "", n, k);
    }   
 
    // The main recursive method
    // to print all possible
    // strings of length k
    static void combineHelper(char[] set,String prefix,int n, int k){
         
        // Base case: k is 0,
        // print prefix
        if (k == 0)
        {
            if(!prefix.contains("SS") && !prefix.contains("MM")){
                count(prefix);
            }
           return;
        }
     
        // One by one add all characters
        // from set and recursively
        // call for k equals to k-1
        for (int i = 0; i < n && !prefix.contains("SS") && !prefix.contains("MM"); ++i)
        {
     
            // Next character of input added
            String newPrefix = prefix + set[i];
            // k is decreased, because
            // we have added a new character
            combineHelper(set, newPrefix,
                                    n, k - 1);
        }
    }
    
    static private void count(String x){
        int sum=0;
        int counter=0;
        for(int i=0; i<S.size(); i++){
            switch(Character.toString(x.charAt(i))){
                case "S":
                    sum += S.get(i);
                    counter++;
                    break;
                case "M":
                    sum += M.get(i);
                    counter++;
                    break;
                case "B":
                    break;
            }
        }
        sum += B.get(counter);
        if(sum>max){
                max=sum;
                min=counter;}
        else if(sum>=max && counter<min){
                max=sum;
                min=counter;
            }
        counter=0;
        sum=0;
        //System.out.println(res);
    }
    static private int findMaxBerlian(ArrayList<Integer> S, ArrayList<Integer> M, ArrayList<Integer> B){
        combine(S.size());
        return max;

    }
    static private int findBanyakGalian(ArrayList<Integer> S, ArrayList<Integer> M, ArrayList<Integer> B) {
        return min;
    }

    public static void main(String args[]) throws IOException {

        InputStream inputStream = System.in;
        in = new InputReader(inputStream);
        OutputStream outputStream = System.out;
        out = new PrintWriter(outputStream);

        
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
        B.add(0,0);
        int jawabanBerlian = findMaxBerlian(S,M,B);
        int jawabanGalian = findBanyakGalian(S,M,B);
        //int[] tmp = maxJum(res);
        
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