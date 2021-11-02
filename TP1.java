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
    private static ArrayList<String> intel = new ArrayList<String>();
    private static Map<String, int[]> map = new HashMap<String, int[]>();
    /*

    map[0] = tipe: 1=B 0=S
    map[1] = rank
    map[2] = initial rank
    map[3] = evaluate variable 0=False 1=True



    */
    
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

                intel.add(code);
                map.put(code, new int[]{(int)tipe.charAt(0),0,j,1});
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
                updateEvaluateVar();
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
                case "DEPLOY":
                    num = in.nextInt();
                    // int[] tmp = panutan(num);
                    // out.println(tmp[0]+" "+tmp[1]);
                    out.println(num);
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
            if(maxRank<map.get(intel.get(i))[1]){
                maxRank=map.get(intel.get(i))[1];
                idx=i;
            }

        }
        return intel.get(idx)+" "+map.get(intel.get(idx))[1];
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
        map.get(code)[1] += 1;
        
    }
    static void updateEvaluateVar(){
        for(int i=0; i<intel.size(); i++){
            int[] tmp = map.get(intel.get(i));
            if(i < tmp[2])
                    tmp[3] = 0;
            tmp[2] = i;
            map.put(intel.get(i), tmp);
        }
    }

    //PANUTAN
    static int[] panutan(int num){
        int bakso=0;
        int siomay=0;
        int[] rekap = new int[2];
        for(int i=0; i<num; i++){
            if(map.get(intel.get(i))[0]==66)bakso++;
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
            int tmp = map.get(queue.peek())[0];
            // /System.out.println(tmp);

            try{

                if(!(tmp==(map.get(temp.peek()))[0])){
                    switch(tmp){
                        case 66:
                            res+= queue.poll()+" ";
                            res+= temp.poll()+"\n";
                            break;
                        case 83:
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
            // if(map.get(intel.get(i))[2]<=i )
            //     res += intel.get(i)+" ";
            if(map.get(intel.get(i))[3] == 1)
                res += intel.get(i)+" ";
        }
        if(res.length() == 0)
            res = "TIDAK ADA";
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

