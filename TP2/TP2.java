/*

Nama	: Hakim Amarullah
NPM		: 1906293051
Tugas	: TP2
Topik	: LinkedList, BST
Tahun	: 2021

*/
import java.io.IOException;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.*;

public class TP2{
	private static InputReader in;
    public static OutputStream outputStream = System.out;
    public static Map<String, Pulau> pulau = new HashMap<String, Pulau>();
    public static PrintWriter out = new PrintWriter(outputStream);

    public static void main(String[] args) {
    	InputStream inputStream = System.in;
        in = new InputReader(inputStream);

        int N = in.nextInt();

        String namaPulau="";
        for(int i=0; i<N; i++){
        	namaPulau = in.next();
        	int jumlahDataran = in.nextInt();
        	pulau.put(namaPulau, new Pulau(namaPulau));

        	for(int j=0; j<jumlahDataran; j++){
        		int tinggi = in.nextInt();
        		pulau.get(namaPulau).addLast(tinggi);
        	}
        	

        }

        //SET UP RAIDEN
        String pulauRaiden = in.next();
        int indexDataran = in.nextInt();
        Raiden raiden = new Raiden(pulau.get(pulauRaiden), indexDataran);
        pulau.get(pulauRaiden).setRaiden(raiden);
      
        int Q = in.nextInt(); //jumlah kejadian

        for(int i=0; i<Q; i++){
        	String kejadian = in.next();
        	switch(kejadian){
        		case "CRUMBLE":
        			out.println(pulau.get(namaPulau).crumble());
        			break;
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

class Pulau extends TP2{
	String nama;
	int size;
	Dataran header;
	Dataran last;
	Raiden raidenObject;
	Dataran raiden;

	public Pulau(String nama){
		this.header = new Dataran(0, null);
		this.nama = nama;
		this.last = header;
	}

	public void addLast(int tinggi){
		if(header.next == null){
			header.next = last = new Dataran(tinggi);
			header.next.prev = header;
			header.next.setAsKuil();
			this.size++;
		}
		else{
			Dataran dataran = new Dataran(tinggi, null);
			last.next = dataran ;
            dataran.prev = last;
            last = dataran;
			
		}
	}

	public int crumble(){
		int tinggi = 0;
		if(raiden.isKuil()){
			return tinggi;
		}
		else if(raiden.prev == header && raiden.next != null){
            tinggi = raiden.getHeight();
            raiden = raiden.next;
            raiden.prev = header;
            header.next = raiden;
            raidenObject.update(raiden);
        }
        else if(raiden.prev == header && raiden.next == null){
            tinggi = raiden.getHeight();
            header.next = null;
        }
        else if(raiden.prev != header && raiden != last){
            tinggi = raiden.getHeight();
            raiden = raiden.prev;
            raiden.next = raiden.next.next;
            raidenObject.update(raiden);
        }
        else if(raiden.prev != header && raiden == last){
            tinggi = raiden.getHeight();
            raiden = raiden.prev;
            raiden.next = raiden.next.next;
            last = raiden;
            raidenObject.update(raiden);
        }
        return tinggi;
    }

	public void setRaiden(Raiden raiden){
		this.raidenObject = raiden;
		this.raiden = raidenObject.current;
	}

	public void print(){
		Dataran curr = header.next;
		while(curr != null){
			TP2.out.print(String.valueOf(curr.getHeight()) + " ");
			curr = curr.next;
		}
		TP2.out.println();
	}

	public String getName(){
		return this.nama;
	}
}

class Dataran{
	Dataran next;
	Dataran prev;
	boolean isKuil;
	int tinggi;

	public Dataran(int tinggi){
		this.tinggi = tinggi;
		this.isKuil = false;
	}

	public Dataran(int tinggi, Dataran next){
		this.tinggi = tinggi;
		this.next = next;
		this.isKuil= false;
	}

	public int getHeight(){
		return this.tinggi;
	}

	public void setAsKuil(){
		this.isKuil = true;
	}

	public boolean isKuil(){
		return this.isKuil;
	}
}

class Raiden{
	Dataran left;
	Dataran right;
	Pulau pulau;
	Dataran current;

	public Raiden(Pulau pulau, int indexDataran){
		this.pulau = pulau;
		this.setInitial(pulau,indexDataran);
		this.left = current.prev;
		this.right = current.next;
	}

	public Dataran setInitial(Pulau pulau, int indexDataran){
		Dataran out = pulau.header;
		for(int i=0; i<indexDataran; i++){
			out = out.next;
		}
		this.current = out;
		return out;
	}

	public void update(Dataran newCurrent){
		this.current = newCurrent;
		this.left = current.prev;
		this.right = current.next;
	}
}