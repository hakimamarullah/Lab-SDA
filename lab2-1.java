import java.io.IOException;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.*;
import static java.lang.Math.min;
import static java.lang.Math.max;


class Lab2 {

    private static InputReader in;
    private static PrintWriter out;
    private static Queue<String> antrian = new ArrayDeque<String>();
    private static Deque<Integer> jumlah = new ArrayDeque<Integer>();
    public static int totalAntrian=0;
    private static Map<String, Integer> total = new TreeMap<String, Integer>();
    // TODO
    static private int handleDatang(String Gi, int Xi) {
        totalAntrian+=Xi;
        antrian.add(Gi);
        jumlah.add(Xi);
        if(!total.containsKey(Gi))
            total.put(Gi, 0);
        return totalAntrian;
    }

    // TODO
    static private String handleLayani(int Yi) {
        totalAntrian-=Yi;
        int layani=Yi;
        String last="";
        int tmp;
        while(layani >0){
            if(layani>jumlah.peek()){
                tmp = total.get(antrian.peek());
                layani -= jumlah.peek();
                total.put(antrian.poll(),tmp+jumlah.poll());
            }

            if(layani<jumlah.peek()){
                tmp = total.get(antrian.peek());
                total.put(antrian.peek(), tmp+layani);
                tmp= jumlah.peek()-layani;
                layani -=jumlah.poll();
                jumlah.addFirst(tmp);
                if(layani<=0){
                    layani=0;
                    last=antrian.peek();
                }
                
            }

            if(layani==jumlah.peek()){
                tmp=total.get(antrian.peek());
                last =antrian.poll();
                total.put(last, tmp+layani);
                layani-=jumlah.poll();
            }
            
        }
        return last;
    }

    // TODO
    static private int handleTotal(String Gi) {
        int totalServiced=0;
        try{
            totalServiced = total.get(Gi);
        }
        catch(Exception e){
            totalServiced=0;
        }
        return totalServiced;
    }

    public static void main(String args[]) throws IOException {

        InputStream inputStream = System.in;
        in = new InputReader(inputStream);
        OutputStream outputStream = System.out;
        out = new PrintWriter(outputStream);

        int N;

        N = in.nextInt();

        for(int tmp=0;tmp<N;tmp++) {
            String event = in.next();

            if(event.equals("DATANG")) {
                String Gi = in.next();
                int Xi = in.nextInt();

                out.println(handleDatang(Gi, Xi));
            } else if(event.equals("LAYANI")) {
                int Yi = in.nextInt();
                
                out.println(handleLayani(Yi));
            } else {
                String Gi = in.next();

                out.println(handleTotal(Gi));
            }
        }

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