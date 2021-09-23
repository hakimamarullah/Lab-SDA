import java.io.IOException;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.*;
import static java.lang.Math.min;
import static java.lang.Math.max;

public class TP1{
    private static InputReader in;
    private static PrintWriter out;
    private static LinkedList<Kang> intel = new LinkedList<Kang>();

    public static void main(String[] args) {
        InputStream inputStream = System.in;
        in = new InputReader(inputStream);
        OutputStream outputStream = System.out;
        out = new PrintWriter(outputStream);
        
        int N = in.nextInt();

        
        for(int i=0; i<N; i++){
            int jumlahIntel= in.nextInt();

            for(int j=0; j<jumlahIntel; j++){
                String code = in.next();
                String tipe = in.next();

                intel.add(new Kang(code,tipe));
            }

            int hari = in.nextInt();

            for (int k=0; k<hari; k++ ) {
                int update = in.nextInt();
                String codeToUpdate="";
                int rank=0;
                for (int l=0; l<update; l++ ) {
                    codeToUpdate = in.next();
                    rank= in.nextInt();
                    updateRanking(codeToUpdate, rank);
                    
                }
                for(Kang obj: intel)
                    out.print(obj+" ");
                out.println();
                
            }
            String command = in.next();
            int num;
            switch(command){
                case "PANUTAN":
                    num = in.nextInt();
                    int[] tmp = panutan(num);
                    out.println(tmp[0]+" "+tmp[1]);
                    break;
                case "KOMPETITIF":
                    out.println(kompetitif());
                    break;

            }
            intel.clear();
        }
        out.flush();
    }

    static String kompetitif(){
        Collections.sort(intel);
        return intel.get(0)+" "+intel.get(0).rankScore();
    }
    static void updateRanking(String code, int y){
        Kang x = getByCode(code);
        if(y==0){
            intel.remove(x);
            intel.addFirst(x);   
        }
        else{
            intel.remove(x);
            intel.addLast(x);
        }
        x.up();

    }

    static int[] panutan(int num){
        int bakso=0;
        int siomay=0;
        int[] rekap = new int[2];
        for(int i=0; i<num; i++){
            Kang tmp = intel.get(i);
            if(tmp.isBakso())
                bakso++;
            else
                siomay++;
        }
        rekap[0] = bakso;
        rekap[1] = siomay;
        return rekap;
    }

    static Kang getByCode(String code) {
        return intel.stream().filter(kang-> code.equals(kang.getCode()))
            .findFirst().orElse(null);
    }
    
    static void removeByCode(String code){
        intel.remove(getByCode(code));
    }
    /* taken from https://codeforces.com/submissions/Petr
    together with PrintWriter, these input-output (IO) is much faster than the usual Scanner(System.in) and System.out
    please use these classes to avoid your fast algorithm gets Time Limit Exceeded caused by slow input-output (IO)*/
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

//KANG BAKSO
class Kang implements Comparable<Kang>{
    private int rank;
    private String code;
    private String type;

    Kang(String code, String type){
        this.code=code;
        this.type = type;
        this.toString();
    }
    Kang(String code){
        this.code = code;
    }

    public String getCode(){
        return this.code;
    }

    public String getType(){
        return this.type;
    }

    public void up(){
        this.rank++;
    }
    public int rankScore(){
        return this.rank;
    }
    public String toString(){
        return this.code;
    }

    public boolean isBakso(){
        return this.type.equals("B")? true:false;
    }

    @Override
    public int compareTo(Kang x){
        if(this.rank<x.rank){
            return 1;
        }
        else if(this.rank > x.rank){
            return -1;
        }
        else{
        return 0;}
    }
}