import java.io.IOException;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.*;

// TODO - class untuk Lantai


public class Lab4 {
    private static InputReader in;
    public static Gedung Gedung;
    public static Map<String, Gedung> gedung = new HashMap<String, Gedung>();
    public static OutputStream outputStream = System.out;
    public static PrintWriter out = new PrintWriter(outputStream);

    
    public static void main(String[] args) throws IOException {
        InputStream inputStream = System.in;
        in = new InputReader(inputStream);
        
        // N operations
        int N = in.nextInt();
        String cmd;

        // TODO - handle inputs
        for (int zz = 0; zz < N; zz++) {
            
            cmd = in.next();
            
            
            if(cmd.equals("FONDASI")){
                String A = in.next();
                gedung.put(A, new Gedung());

            }
            else if(cmd.equals("BANGUN")){
                String A = in.next();
                String X = in.next();
                // TODO
                gedung.get(A).bangun(X);

            }
            else if(cmd.equals("LIFT")){
                String A = in.next();
                String X = in.next();
                // TODO
                out.println(gedung.get(A).lift(X));

            }
            else if(cmd.equals("SKETSA")){
                String A = in.next();
                // TODO
                gedung.get(A).sketsa();

            }
            else if(cmd.equals("TIMPA")){
                String A = in.next();
                String B = in.next();
                // TODO
                gedung.get(B).timpa(A);

            }
            else if(cmd.equals("HANCURKAN")){
                String A = in.next();
                // TODO
                out.println(gedung.get(A).hancurkan());
            }
        }
     
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

class Lantai {
    Lantai next;
    Lantai prev;
    String data;

    public Lantai(String data){
        this.data = data;
    }
    public Lantai(String data, Lantai next){
        this.data =data;
        this.next = next;
    }

    public String getValue(){
        return this.data;
    }

}


// TODO - class untuk Gedung
class Gedung extends Lab4{
    Lantai base;
    Lantai agen;
    Lantai last;

    public Gedung() {
        this.base = new Lantai("", null);
        this.base.prev = null;
        this.last = base;
        this.agen = base;

    }

    public void bangun(String input){
        // TODO - handle BANGUN
        if(base.next == null){
            base.next = new Lantai(input, null);
            agen = base.next;
            last = base.next;
            base.next.prev = base;
        }
        else if(agen != last){
        Lantai new_lantai = new Lantai(input);
 
        /* 4. Make next of new lantai as next of prev */
        new_lantai.next = agen.next;
 
        /* 5. Make the next of prev_lantai as new_lantai */
        new_lantai.prev = agen;
        agen.next = new_lantai;
        agen = new_lantai;
 
        /* 6. Make prev as previous of new_lantai */
        
 
        /* 7. Change previous of new_lantai's next lantai */
        if (new_lantai.next != null)
            new_lantai.next.prev = new_lantai;
        }
        else if(agen==last){
            Lantai lantai = new Lantai(input, null);
            last.next = lantai;
            lantai.prev = last;
            last = lantai;
            agen = lantai;
        }


    }

    public String lift(String input){
        // TODO - handle LIFT
        String now ="";
        if(base.next == null){
            return "";
        }
        else if(input.equals("BAWAH")){
            if(agen.prev == base)
                now = agen.getValue();
            else{
                Lantai current = agen;
                agen = agen.prev;
                agen.next = current;
                now=agen.getValue();
            }
        }
        else if(input.equals("ATAS")){
            if(agen.next == null)
                now = agen.getValue();
            else{
                Lantai current = agen;
                agen = agen.next;
                current.next = agen;
                agen.prev = current;
                now = agen.getValue();
            }
        }
        return now;
    }

    public String hancurkan(){
        String hancur = "";
        if(agen.prev == base && agen.next != null){
            hancur = agen.getValue();
            agen = agen.next;
            agen.prev = base;
            base.next = agen;
        }
        else if(agen.prev == base && agen.next==null){
            hancur = agen.getValue();
            base.next = null;
        }
        else if(agen.prev != base && agen != last){
            hancur = agen.getValue();
            agen = agen.prev;
            agen.next = agen.next.next;
        }
        else if(agen.prev != base && agen == last){
            hancur = agen.getValue();
            agen = agen.prev;
            agen.next = agen.next.next;
            last = agen;
        }
        return hancur;
    }

    public void timpa(String gedungTarget){
        // TODO - handle TIMPA
        if(this.base.next != null){
            gedung.get(gedungTarget).last.next = this.base.next;
            gedung.get(gedungTarget).last = this.last;
            this.base.next = null;
        }
    }

    public void sketsa(){
        // TODO - handle SKETSA
        String res = "";
        Lantai current = base.next;
        while(current != null){
            Lab4.out.print(current.getValue());
            current = current.next;
        }
        Lab4.out.println();
    }


}