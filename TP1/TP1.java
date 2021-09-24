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
    private static ArrayList<String> intel = new ArrayList<String>();
    public static InputStream inputStream = System.in;
    public static InputReader in = new InputReader(inputStream);
    public static OutputStream outputStream = System.out;
    public static PrintWriter out = new PrintWriter(outputStream);
    private static Map<String, Kang> map = new HashMap<String, Kang>();
    public static void main(String[] args) {
        
        
        int N = in.nextInt();

        
        for(int i=0; i<N; i++){
            int jumlahIntel= in.nextInt();

            for(int j=0; j<jumlahIntel; j++){
                String code = in.next();
                String tipe = in.next();

                intel.add(code);
                map.put(code, new Kang(code,tipe,j));
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
                for(String obj: intel)
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
                case "DUO":
                    out.println(duo());
                    break;
                case "EVALUASI":
                    out.println(evaluasi());
                    break;

            }
            map.clear();
            intel.clear();
        }
        out.flush();
    }

    static String kompetitif(){
        int maxRank = Integer.MIN_VALUE;
        int idx=0;
        for(int i=0; i<intel.size(); i++){
            if(maxRank<map.get(intel.get(i)).rankScore()){
                maxRank=map.get(intel.get(i)).rankScore();
                idx=i;
            }

        }
        return intel.get(idx)+" "+map.get(intel.get(idx)).rankScore();
    }

    //UPDATE RANKING
    static void updateRanking(String code, int y){
        if(y==0){
            intel.remove(code);
            intel.add(0,code);   
        }
        else{
            intel.remove(code);
            intel.add(code);
        }
        map.get(code).up();

    }

    //PANUTAN
    static int[] panutan(int num){
        int bakso=0;
        int siomay=0;
        int[] rekap = new int[2];
        for(int i=0; i<num; i++){
            if(map.get(intel.get(i)).isBakso())bakso++;
            else siomay++;
            
        }
        rekap[0] = bakso;
        rekap[1] = siomay;
        return rekap;
    }

    //DUO
    static String duo(){
        Queue<String> queue = new LinkedList<String>(intel);
        Queue<String> temp = new LinkedList<String>();
        String res ="";

        while(!queue.isEmpty()){
            String tmp = map.get(queue.peek()).getType();

            try{

                if(!tmp.equals(map.get(temp.peek()).getType())){
                    switch(tmp){
                        case "B":
                            res+= queue.poll()+" ";
                            res+= temp.poll()+"\n";
                            break;
                        case "S":
                            res+= temp.poll()+" ";
                            res+= queue.poll()+"\n";
                            break;
                    }
                    continue;
                }
            }
            catch(NullPointerException e){
                temp.add(queue.poll());
                continue;
            }
            temp.add(queue.poll());
        }

        if(!temp.isEmpty()){
            res+= "TIDAK DAPAT: ";
            while(!temp.isEmpty())
                res+= temp.poll()+" ";
        }
        return res;

    }

    //EVALUASI
    static String evaluasi(){
        String res ="";
        for(int i=0; i<intel.size(); i++){
            if(map.get(intel.get(i)).initialRank()<=i )
                res += intel.get(i)+" ";
        }
        return res;
    }

    // static Kang getByCode(String code) {
    //     return intel.stream().filter(kang-> code.equals(kang.getCode()))
    //         .findFirst().orElse(null);
    // }
    
    // static void removeByCode(String code){
    //     intel.remove(getByCode(code));
    // }
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
class Kang{
    private int rank;
    private String code;
    private String type;
    private int initialRank;

    Kang(String code, String type, int initialRank){
        this.code=code;
        this.type = type;
        this.initialRank = initialRank;
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
    public int initialRank(){
        return this.initialRank;
    }
    public String toString(){
        return this.code;
    }

    public boolean isBakso(){
        return this.type.equals("B")? true:false;
    }

    
}