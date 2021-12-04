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
    public static Raiden raiden;

    public static void main(String[] args) {
    	InputStream inputStream = System.in;
        in = new InputReader(inputStream);

        int N = in.nextInt();

        String namaPulau="";
        //O(N X D)
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
        raiden = new Raiden(pulau.get(pulauRaiden), indexDataran);
      
        int Q = in.nextInt(); //jumlah kejadian

        //(N x D) + E + (Q x D)
        for(int i=0; i<Q; i++){
        	String kejadian = in.next();
        	switch(kejadian){
        		case "CRUMBLE":
        			out.println(pulau.get(raiden.pulau.getName()).crumble(raiden.current)); // O(1)
        			//out.println("SUCCESS CRUMBLE ");
        			break;
        		case "UNIFIKASI":
        			String pulauUtama = in.next();
        			String pulauSecondary = in.next();
        			out.println(pulau.get(pulauUtama).unifikasi(pulau.get(pulauSecondary))); //O(N)
        			pulau.put(pulauSecondary, pulau.get(pulauUtama)); //Update pulau after unification
        			break;
        		case "TELEPORTASI":
        			String namaKuil = in.next();
        			out.println(raiden.teleportasi(pulau.get(namaKuil), namaKuil)); //O(Jumlah Kuil)
        			//out.println("SUCCESS TELEPORTASI " + namaKuil);
        			break;
        		case "QUAKE":
        			String namaPulauToQuake= in.next();
        			long height = in.nextLong();
        			long minus = in.nextLong();
        			out.println(pulau.get(namaPulauToQuake).quake(height, minus)); //O(D)
        			//out.println("SUCCESS QUAKE " + namaPulauToQuake + " " + height + " " + minus);
        			break;
        		case "TEBAS":
        			String arah = in.next();
        			int langkah = in.nextInt();
        			out.println(raiden.tebas(arah, langkah));//O(S)
        			//out.println("SUCCESS TEBAS " + arah + " " + langkah);
        			break;
        		case "RISE":
        			String namaPulauToRise = in.next();
        			long acuan = in.nextLong();
        			long rise = in.nextLong();
        			out.println(pulau.get(namaPulauToRise).rise(acuan, rise));
        			//out.println("SUCCESS RISE " + acuan + " " + rise);
        			break;
        		case "GERAK":
        			String arahGerak = in.next();
        			int banyakLangkah = in.nextInt();
        			out.println(raiden.gerak(arahGerak, banyakLangkah));
        			//out.println("SUCCESS GERAK " + arahGerak + " " + banyakLangkah);
        			break;
        		case "PISAH":
        			String kuilToCut = in.next();
        			out.println(Pulau.pisah(kuilToCut));//O(N)
        			//out.println("SUCCESS PISAH " + kuilToCut);
        			break;
        		case "STABILIZE":
        			out.println(raiden.stabilize());
        			//out.println("SUCCESS STABILIZE");
        			break;
        		case "SWEEPING":
        			String pulauTerdampak = in.next();
        			long ketinggianAir = in.nextLong();
        			out.println(pulau.get(pulauTerdampak).sweeping(ketinggianAir)); //O(D)
        			//out.println("SUCCESS SWEEPING " + pulauTerdampak + " " + ketinggianAir);
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

        public long nextLong(){
        	return Long.parseLong(next());
        }
 
    }

}

class Pulau extends TP2{
	String nama;
	int size;
	Dataran header;
	Dataran last;
	Dataran lastKuil;
	Map<String, Dataran> kuil= new HashMap<String, Dataran>();

	public Pulau(String nama){
		this.header = new Dataran(0, null);
		this.nama = nama;
		this.last = header;
	}

	public void addLast(int tinggi){
		if(header.next == null){
			header.next = last = new Dataran(tinggi);
			header.next.prev = header;
			header.next.setAsKuil(this.getName());
			kuil.put(header.next.getKuilName(), header.next);
			header.next.kuilSize = this.size + 1;
			lastKuil = header.next;
			this.size++;
		}
		else{
			Dataran dataran = new Dataran(tinggi, null);
			last.next = dataran ;
            dataran.prev = last;
            last = dataran;
            dataran.setMainKuil(this.header.next);
            this.header.next.kuilSize++;
            this.size++;
			
		}
	}
	public static Pulau findPulau(String namaKuil){
		for (String key : TP2.pulau.keySet()) {
    			Map<String, Dataran> tmp = TP2.pulau.get(key).kuil;
    			try{
    				if(tmp.get(namaKuil) == null)
    				 throw new NullPointerException();
    				else
    					return TP2.pulau.get(key);
    			}
    			catch(NullPointerException e){
    				continue;
    			}
		}
		return null;
	}
	public static String pisah(String namaKuil){
		Pulau current = findPulau(namaKuil);
		Dataran tmp = current.header.next;
		int initialSize = current.size;
		Pulau newPulau = new Pulau(namaKuil);
		current.kuil.clear();
		int counter = 0;
		while(tmp != null){

				if(tmp.getKuilName().equals(namaKuil)){
					Dataran lastKanan = current.last;
					current.lastKuil = tmp.prevKuil;
					tmp.prevKuil = null;
					current.lastKuil.nextKuil = null;
					newPulau.header.next = tmp;
					current.last = tmp.prev;
					tmp.prev = newPulau.header;
					newPulau.last = lastKanan;
					current.last.next = null;
					TP2.pulau.put(namaKuil, newPulau);
					if(TP2.raiden.current == newPulau.header.next){
						TP2.raiden.update(newPulau, newPulau.header.next);
					}
					break;

				}
				else
					current.kuil.put(tmp.getKuilName(), tmp);
			
			tmp = tmp.nextKuil;
		}
		

		tmp = newPulau.header.next;
		while(tmp != null){
			if(tmp.isKuil())
				newPulau.kuil.put(tmp.getKuilName(), tmp);
			newPulau.lastKuil = tmp;
			tmp = tmp.nextKuil;

		}
		return current.getSize() + " " + newPulau.getSize();
	}
	public long rise(long acuan, long rise){
		Dataran tmp = this.header.next;
    	int counter =0;
    	// if(this.getName().equals("TOWIDO")){
    	// 	TP2.out.print("DEPLOY");
    	// 	this.print();
    	// }
    	// System.out.print(this.getName() + " " + acuan + " " + rise);
    	// System.out.println();
    	while(tmp != null){
    		if(tmp.getHeight() > acuan){
    			tmp.increaseHeight(rise);
    			counter++;
    		}
    		tmp = tmp.next;
    	}
    	return counter;
	}
	
	public long crumble(Dataran x){
		try{
		Dataran raiden = x;
		if(raiden.isKuil()){
			return 0;
		}
		if(raiden.next == null){
			x.mainKuil.kuilSize--;
			Dataran tmp = raiden.prev;
			raiden.prev.next = null;
			TP2.raiden.update(this, tmp);
			this.size--;

		}
		else if (raiden.next != null) {
			x.mainKuil.kuilSize--;
			Dataran tmp = raiden.prev;
            raiden.next.prev = raiden.prev;
            raiden.prev.next = raiden.next;
            TP2.raiden.update(this, tmp);
            //this.header.next.kuilSize--;
            this.size--;
        }
    }
    catch(Exception e){
    	//System.out.println(this.getName());
    	return 1000000000;
    }
        
       return x.getHeight(); 
    }
    public int sweeping(long ketinggianAir){
    	int counter =0;
    	Dataran pointer = this.header.next;
    	while( pointer != null){
    		if(pointer.getHeight() < ketinggianAir){
    			counter++;
    		}
    		pointer = pointer.next;
    	}
    	return counter;
    }

    public Dataran search(Dataran dataran, String arah, int langkah){
    	Dataran tmp = arah.equals("KANAN") ? dataran.next : dataran.prev;
    	long height = dataran.getHeight();
    	int counter = 0;
    	Dataran current = dataran;
    	while(tmp != null && counter < langkah){
    		if(tmp.getHeight() ==  height){
    			current = tmp;
    			counter++;
    		}
    		tmp = arah.equals("KANAN") ? tmp.next : tmp.prev;

    	}
    	return current;
    }

    public long quake(long height, long minus){
    	Dataran tmp = this.header.next;
    	int counter =0;
    	while(tmp != null){
    		if(tmp.getHeight() < height){
    			tmp.decreaseHeight(minus);
    			counter++;
    		}
    		tmp = tmp.next;
    	}
    	return counter;
    }

	public int unifikasi(Pulau pulau){
		this.kuil.putAll(pulau.kuil);
		pulau.kuil.clear(); //O(N)
		try{
			if(pulau == TP2.raiden.pulau){
				TP2.raiden.updatePulau(this);
			}

	    	if(this.header.next.next == null){
	    		this.header.next.next = pulau.header.next;
	    	}

			pulau.header.next.prevKuil = this.lastKuil;
			this.lastKuil.nextKuil = pulau.header.next;
			this.lastKuil = pulau.header.next;
			this.last.next = pulau.header.next;



			pulau.header.next = null;
			pulau.kuil.putAll(this.kuil);
			updateAllPulau(this);
			Dataran tmp = this.header.next;
			while(tmp != null){
				this.lastKuil = tmp;
				tmp = tmp.nextKuil;
									
			}

			return getSize();
		}
		catch (Exception e) {
			return 100000;
		}
	}

	public int getSize(){
		Dataran tmp = this.header.next;
		int counter =0;
		while(tmp != null){
			
			counter+= tmp.kuilSize;
			tmp = tmp.nextKuil;
		}
		return counter;
	}

	public static void updateAllPulau(Pulau pulauUtama){
		for(String key : pulauUtama.kuil.keySet()){
			TP2.pulau.put(key, pulauUtama);
		}
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
	String namaKuil;
	long tinggi;
	Dataran nextKuil;
	Dataran prevKuil;
	int kuilSize;
	Dataran mainKuil;

	public Dataran(long tinggi){
		this.tinggi = tinggi;
		this.isKuil = false;
	}

	public Dataran(long tinggi, Dataran next){
		this.tinggi = tinggi;
		this.next = next;
		this.isKuil= false;
	}

	public long getHeight(){
		return this.tinggi;
	}

	public void setMainKuil(Dataran kuil){
		this.mainKuil = kuil;
	}

	public void setAsKuil(String nama){
		this.isKuil = true;
		this.namaKuil = nama;
	}

	public String getKuilName(){
		if(this.isKuil){
			return this.namaKuil;
		}
		return "BUKAN KUIL";
	}

	public boolean isKuil(){
		return this.isKuil;
	}

	public void decreaseHeight(long minus){
		this.tinggi = Math.abs(this.tinggi - minus);
	}

	public void increaseHeight(long rise){
		this.tinggi = this.tinggi + rise;
	}

	@Override
	public String toString(){
		return Long.toString(this.tinggi);
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

	public void update(Pulau pulau,Dataran newCurrent){
		this.pulau = pulau;
		this.current = newCurrent;
		this.left = current.prev;
		this.right = current.next;
	}

	public void updatePulau(Pulau pulau){
		this.pulau = pulau;
	}

	public long teleportasi(Pulau pulau, String namaKuil){
		Dataran tmp = pulau.header.next;
		this.pulau = pulau;
		while(tmp != null){
			if(tmp.getKuilName().equals(namaKuil)){
				this.update(this.pulau, tmp);
				return tmp.getHeight();
			}
			tmp = tmp.nextKuil;
		}
		return 0;
	}

	public long tebas(String arah, int langkah){
		Dataran current = this.current;	
		current = this.pulau.search(current, arah, langkah); // O(S)
		if(current == this.current){
			return 0;
		}
		this.update(pulau, current);
		long height = arah.equals("KANAN") ? this.left.getHeight() : this.right.getHeight();
		return height;
	}
	
	public long gerak(String arah, int langkah){
		if(pulau.header.next == null){
			return 0;
		}
		if(arah.equals("KIRI") && this.current.prev.getHeight() == 0){
			return this.current.getHeight();
		}
		else if(arah.equals("KANAN") && this.current.next == null){
			return this.current.getHeight();
		}
		else if(arah.equals("KIRI")){
			for(int i=0; i<langkah && this.current.prev.getHeight() != 0; i++){
				this.current = this.current.prev;
			}
			this.update(this.pulau, this.current);
			return this.current.getHeight();
		}
		else{
			for(int i=0; i<langkah && this.current.next != null; i++){
				this.current = this.current.next;
			}
			this.update(this.pulau, this.current);

			return this.current.getHeight();
		}
		
	}

	public long stabilize(){
		Dataran raiden = this.current;
		Dataran last = this.pulau.last;
		long height = Math.min(this.current.getHeight(), this.left.getHeight());
		if(raiden.isKuil()){
			return 0;
		}
		if(raiden != last){
			raiden.mainKuil.kuilSize++;
	        Dataran newDataran = new Dataran(height);
	        newDataran.setMainKuil(raiden.mainKuil);
	 
	        /* 4. Make next of new lantai as next of prev */
	        newDataran.next = raiden.next;
	 
	        /* 5. Make the next of prev_lantai as newDataran */
	        newDataran.prev = raiden;
	        raiden.next = newDataran;
	        //raiden = newDataran;
	 
	        /* 6. Make prev as previous of newDataran */
	        
	 
	        /* 7. Change previous of newDataran's next lantai */
	        if (newDataran.next != null)
	            newDataran.next.prev = newDataran;
	        this.pulau.size++;
	        return height;
        }

        else if(raiden==last){
        	raiden.mainKuil.kuilSize++;
            Dataran dataran= new Dataran(height);
            dataran.setMainKuil(raiden.mainKuil);
            raiden.next = dataran;
            dataran.prev = raiden;
            this.pulau.last = dataran;
            this.pulau.size++;
	        //this.pulau.header.next.kuilSize++;
            return height;
        }
        return 0;
	}

}
