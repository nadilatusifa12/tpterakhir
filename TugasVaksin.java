
import java.io.IOException;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.InputStream;
import java.io.PrintWriter;
import java.security.KeyStore.Entry;
import java.util.*;
import java.io.*;
import java.lang.Comparable;

public class TugasVaksin {
    private static Graph graph = new Graph();
    private static final InputReader in = new InputReader(System.in);
    private static final PrintWriter out = new PrintWriter(System.out);
    public static void main(String[] args) throws IOException{
       int N = in.nextInt();
    
      
       for(int i=0; i<N; i++){
            graph.insertKotaToGraph(i);
       }
       
       int q = in.nextInt();
       for(int i=0; i < q; i++){
            String perintah = in.next();
            doQuerry(perintah);
       }
       

       out.close();
    }
    public static void doQuerry(String q){
        if(q.equalsIgnoreCase("INSERT")){
            int w = in.nextInt();
            if(w == 0){
                int x = in.nextInt();
                int y = in.nextInt();
                int z = in.nextInt();
                graph.insert_jalanTipe0(x, y, z);
            }
            else if(w == 1){
                int x = in.nextInt();
                int y = in.nextInt();
                graph.insert_jalanTipe1(x, y);
            }
            else{
                int x = in.nextInt();
                int y = in.nextInt();
                graph.insert_jalanTipe2(x, y);
            }
        

        }
        else if(q.equalsIgnoreCase("DELETE")) {
            int w = in.nextInt();
            int a = in.nextInt();
            int b = in.nextInt();
            graph.delete_jalan(w, a, b);
            

        }

        else if(q.equalsIgnoreCase("SHORTEST_PATH")){
            int w = in.nextInt();
            int x = in.nextInt();
            int y = in.nextInt();
            if (!(x == y)){
                if (w == 0){
                    out.println(graph.dijkstra_tipe0(x, y));
                }
                else{
                    out.println(graph.dijkstra_tipe1(x, y));
                }
            }
            
        }

        else if (q.equalsIgnoreCase("MIN_PATH")){
            int a = in.nextInt();
            int b = in.nextInt();
            if(a != b){
                out.println(graph.min_path(a, b));
            }
            

        }

        else if(q.equalsIgnoreCase("IS_CONNECTED")){
            int a = in.nextInt();
            int b = in.nextInt();
            if( a != b){
                out.println(graph.isConnected(a,b));
            }
            
        }

        else if(q.equalsIgnoreCase("COUNT_CITY")){
            int a = in.nextInt();
            int m = in.nextInt();
            if(m > 0 && a > 0){
                out.println(graph.count_city(a, m));
            }
            
        }

        else if(q.equalsIgnoreCase("COUNT_CONNECTED")){
            out.println(graph.count_connected());
        }

        else if(q.equalsIgnoreCase("SIMULATE_WALK")){
            int a = in.nextInt();
            long s2 = Long.parseLong(in.next());
            if(s2 >0){
                out.println(graph.simulate_walk(a, s2));
            }
        }


    }

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
 
        public String nextLine() throws IOException {
            return reader.readLine();
        }

        public int nextInt() {
            return Integer.parseInt(next());
        }
 
    }
}

class Graph{
    Map<Integer, Node>himpKota;

    public Graph(){
        this.himpKota = new HashMap<Integer, Node>();
    }

    public void insertKotaToGraph(int nama){
        Node vertex = new Node(nama);
        himpKota.put(vertex.nama, vertex);
    }

    public void insert_jalanTipe0(int kotaStart,int kotaEnd , int panjang){ //int panjang){
        //menghubungkan kota start dan kota end
        Jalan edge1 = new Jalan(himpKota.get(kotaEnd), panjang); // jalan kota start
        Jalan edge2 = new Jalan(himpKota.get(kotaStart) , panjang);
        himpKota.get(kotaEnd).listJalan_tipe0.add(edge2); // ngehubungin kotastart ke kota end
        himpKota.get(kotaStart).listJalan_tipe0.add(edge1);


    }

    public void insert_jalanTipe1(int kotaStart, int kotaEnd){
        Jalan edge1 = new Jalan(himpKota.get(kotaEnd), 0); //jalan menuju kota end
        Jalan edge2 = new Jalan(himpKota.get(kotaStart), 0);
        himpKota.get(kotaEnd).listJalan_tipe1.add(edge2); // ngehubungin kotastart ke kota end
        himpKota.get(kotaStart).listJalan_tipe1.add(edge1);

    }

    public void insert_jalanTipe2(int kotaStart, int kotaEnd){
        Jalan edge1 = new Jalan(himpKota.get(kotaEnd), 0);// jalan menuju kota end
        himpKota.get(kotaStart).listJalan_tipe2.add(edge1);
    }

    public void delete_jalan(int tipe, int kotaStart, int kotaEnd){
        Node kotaMulai = himpKota.get(kotaStart);
        Node kotaTujuan = himpKota.get(kotaEnd);

        if (tipe == 0){
            for(Jalan edge : kotaMulai.listJalan_tipe0){ //dari kota mulai kita cek edge nya
                if(edge.kota.nama == kotaEnd){
                    kotaMulai.listJalan_tipe0.remove(edge);
                    break;
                }
            }
            for(Jalan edge : kotaTujuan.listJalan_tipe0){
                if(edge.kota.nama == kotaStart){
                    kotaTujuan.listJalan_tipe0.remove(edge);
                    break;
                }
            }
          
        }

        else if(tipe == 1){
            for(Jalan edge : kotaMulai.listJalan_tipe1){ //dari kota mulai kita cek edge nya
                if(edge.kota.nama == kotaEnd){
                    kotaMulai.listJalan_tipe1.remove(edge);
                    break;
                }
            }
            for(Jalan edge : kotaTujuan.listJalan_tipe1){
                if(edge.kota.nama == kotaStart){
                    kotaTujuan.listJalan_tipe1.remove(edge);
                    break;
                }
            }
        }

        else{
            for(Jalan edge : kotaMulai.listJalan_tipe2){ //dari kota mulai kita cek edge nya
                if(edge.kota.nama == kotaEnd){
                    kotaMulai.listJalan_tipe2.remove(edge);
                    break;
                } 
            }
        }

    }
    
    //Dijkstra shortest path TIPE 0
    public int dijkstra_tipe0(int kotaStart, int kotaEnd){
        PriorityQueue<Node> urutan_dijkstra = new PriorityQueue<Node>(himpKota.size(), new Node());

        // key nya nama verteks, value jarak dri kotaStart sampai verteks tujuan
        Map<Integer, Integer> mapKota = new HashMap<>();
        
        //Integer berisi nama kota nya
        HashSet<Integer> proses = new HashSet<>();

        //method untuk mengeset jarak maksimal pada stiap verteks
        for(Map.Entry<Integer,Node> entry : this.himpKota.entrySet()){
            mapKota.put(entry.getKey(), Integer.MAX_VALUE);
            entry.getValue().setCost(Integer.MAX_VALUE);
        }

        Node start = himpKota.get(kotaStart);
        start.setCost(0);
        
        //masukin start node ke dijkstra
        urutan_dijkstra.add(start);
        mapKota.put(start.nama, 0);
        int nodesSeen = 0;

        while(!urutan_dijkstra.isEmpty() && nodesSeen < himpKota.size()){
            Node visit = urutan_dijkstra.remove(); //return jalan yang bakal dikunjungi
            //cek apakah kota tsb udah di visit
            //jika belum di visit masukin ke proses
            proses.add(visit.nama); 
            nodesSeen++;

            //ekspand tetangga dari node visit
            for(Jalan e : visit.listJalan_tipe0){
                Node tetangga = e.kota; //ambil verteks tetangga nya
                if(!proses.contains(tetangga.nama)){
                    int bobot = e.panjang;
                    int newCost = mapKota.get(visit.nama) + bobot;
                    //update jarak dari tetangga terhadap start
                    if( newCost < mapKota.get(tetangga.nama)){ 
                        mapKota.put(tetangga.nama, newCost);
                        tetangga.setCost(newCost);
                    }
                    urutan_dijkstra.add(tetangga);
                }

            } 
           
        }

        //System.out.println(mapKota);
        if(mapKota.get(kotaEnd) == Integer.MAX_VALUE){
            return -1;
        }
        else{
            return mapKota.get(kotaEnd); 
        }
 
    }

    //Dijkstra shortest path TIPE 1
    public int dijkstra_tipe1(int kotaStart, int kotaEnd){
        PriorityQueue<Node> urutan_dijkstra = new PriorityQueue<Node>(himpKota.size(), new Node());
        
        // key nya nama verteks, value jarak dri kotaStart sampai verteks tujuan
        Map<Integer, Integer> mapKota = new HashMap<>();
        
        //Map<Integer, Integer> mapAntri = new HashMap<>();
        //Integer berisi nama kota nya
        HashSet<Integer> proses = new HashSet<>();

       //method untuk mengeset jarak maksimal pada stiap verteks
       for(Map.Entry<Integer,Node> entry : this.himpKota.entrySet()){
           mapKota.put(entry.getKey(), Integer.MAX_VALUE);
           entry.getValue().setCost(Integer.MAX_VALUE);
        }

        Node start = himpKota.get(kotaStart);
        start.setCost(0);

        //masukin start node ke dijkstra
        urutan_dijkstra.add(start);
        mapKota.put(start.nama, 0);
        int nodesSeen = 0;

        while(!urutan_dijkstra.isEmpty() && nodesSeen < himpKota.size()){
            Node visit = urutan_dijkstra.remove(); //return jalan yang bakal dikunjungi
            //cek apakah kota tsb udah di visit
 
            proses.add(visit.nama); //maka masukin ke set proses
            nodesSeen++; 
            
            /*int count1 = 0 ;
            //pake jln tipe 1 untuk cost paling gede
            for(Node e : himpKota.get(visit).listJalan_tipe1){
                if(e.kota.nama == kotaEnd){
                    count1 ++;
                }
               
            } */
            
            //ekspand tetangga dari node visit
            for(Jalan e : visit.listJalan_tipe0){
                Node tetangga = e.kota; //ambil verteks tetangga nya

               //klo udsah pernah di visit 
                if(!proses.contains(tetangga.nama)){
                    int bobot = e.panjang;
                    int newCost = mapKota.get(visit.nama) + bobot;
                    //update jarak dari tetangga terhadap start
                    if( newCost < mapKota.get(tetangga.nama)){ 
                        mapKota.put(tetangga.nama, newCost);
                        tetangga.setCost(newCost);
                    }
                    urutan_dijkstra.add(tetangga);
                }
            }

        } 
        if(mapKota.get(kotaEnd) == Integer.MAX_VALUE){
            return -1;
        }
        else{
            return mapKota.get(kotaEnd);
        }
 
    }


    public int isConnected(int kotaStart, int kotaEnd){
        // menyimpan nama verteks yg udh di visit
        HashSet<Integer> visited = new HashSet<>();
 
        // urutan BFS
        Queue<Integer> urutan_bfs = new LinkedList<>();

        // masukin kota start ke visited as initial
        urutan_bfs.add(kotaStart);
        visited.add(kotaStart);

        while(!urutan_bfs.isEmpty()){
            Integer kota = urutan_bfs.poll();
            
            //nyari tetangga dari kota start, initial BFS
            for(Jalan edge : himpKota.get(kota).listJalan_tipe0){
                //cek apakah kotaStart terhubung dgn tetangganya

                if(edge.kota.nama == kotaEnd){
                        return 1;
                }

                // kalo tatangga kota start blm pernah dikunjungi, maka kunjungi dan masukkan ke urutan bfs
                if(visited.add(edge.kota.nama)){
                    urutan_bfs.add(edge.kota.nama);
                }
                
            }
        }
        return 0;
    }
    
    public int count_connected(){
        //menyimpan nama verteks yg udh dikunjungin
        HashSet<Integer> visited = new HashSet();

        int subgraf = 0;
        for(Node vertex : himpKota.values()) {
            // Check if vertex is already visited  or not
            if(!visited.contains(vertex.nama)){         
                // Mark as visited
                findDFS(vertex.nama, visited);
                       
                // Increase the counter
                subgraf++;
            }
        }
        return subgraf;
    }
    

    private void findDFS(int vertex, HashSet<Integer> visited){ 
        // Mark as visited
        visited.add(vertex);

        //loop jalan dari si verteks
        for(Jalan child : himpKota.get(vertex).listJalan_tipe0){
            if(!visited.contains(child.kota.nama)){
               findDFS(child.kota.nama, visited);
            }
        }
    }

    public int min_path(int kotaStart, int kotaEnd){
        PriorityQueue<Node> urutan_dijkstra = new PriorityQueue<Node>(himpKota.size(), new Node());

        // key nya nama verteks, value jarak dri kotaStart sampai verteks tujuan
        Map<Integer, Integer> mapKota = new HashMap<>();
        
        //Integer berisi nama kota nya
        HashSet<Integer> proses = new HashSet<>();

        //method untuk mengeset jarak maksimal pada stiap verteks
        for(Map.Entry<Integer,Node> entry : this.himpKota.entrySet()){
            mapKota.put(entry.getKey(), Integer.MAX_VALUE);
            entry.getValue().setCost(Integer.MAX_VALUE);
        }

        Node start = himpKota.get(kotaStart);
        start.setCost(0);
        
         //masukin start node ke dijkstra
        urutan_dijkstra.add(start);
        mapKota.put(start.nama, 0);

        int nodesSeen = 0;
        while(!urutan_dijkstra.isEmpty() && nodesSeen < himpKota.size()){
            Node visit = urutan_dijkstra.remove(); //return jalan yang bakal dikunjungi
            //Node kotaVisit = visit.kotaTujuan; //kota dari jalan tadi
            //cek apakah kota tsb udah di visit

            proses.add(visit.nama); //maka masukin ke set proses
            nodesSeen++;

            //ekspand tetangga dari node visit
            for(Jalan e : visit.listJalan_tipe0){
                Node tetangga = e.kota; //ambil verteks tetangga nya
               
                if(!proses.contains(tetangga.nama)){
                    int bobot = 1;
                    int newCost = mapKota.get(visit.nama) + bobot;
                    //update jarak dari tetangga terhadap start
                    if( newCost < mapKota.get(tetangga.nama)){ 
                        mapKota.put(tetangga.nama, newCost);
                        tetangga.setCost(newCost);
                    }
                    urutan_dijkstra.add(tetangga);
                }
            }
           
        }
        if(mapKota.get(kotaEnd) == Integer.MAX_VALUE){
            return -1;
        }
        else{
            return mapKota.get(kotaEnd); 
        }
    }
    
    public int count_city(int kotaStart, int panjangmax){
        //menyimpan nama verteks yg udh dikunjungin
        HashSet<Integer> visited = new HashSet();
        return DFSUtil(kotaStart, visited, panjangmax);

    }
    
    int DFSUtil(int v, HashSet<Integer> visited, int max) {
        visited.add(v);
        int counter = 1;
        
        for(Jalan jalan : himpKota.get(v).listJalan_tipe0) {
            if (max - jalan.panjang >= 0 && !visited.contains(jalan.kota.nama)) {
                counter += DFSUtil(jalan.kota.nama, visited, max - jalan.panjang);
            }
        }
        return counter;
    }
   
    public int simulate_walk(int kotaStart, long path){
        Node kotaNow = himpKota.get(kotaStart);
       
        for(long i =0; i < path; i ++){
            for(Jalan e : kotaNow.listJalan_tipe2){
                //System.out.println("a");
                if(e.kota == kotaNow){
                    continue;
                }
                kotaNow = e.kota;
            } 
        }
        return kotaNow.nama;
    }




      
   
  
   
  
       
    
}

class Node implements Comparator<Node>{
    int nama;
    int cost;
    List<Jalan> listJalan_tipe0;
    List<Jalan> listJalan_tipe1;
    List<Jalan> listJalan_tipe2;


    //constructor
    public Node(int nama){
        this.nama = nama;
        this.cost = Integer.MAX_VALUE;
        this.listJalan_tipe0 = new ArrayList<>();
        this.listJalan_tipe1 = new ArrayList<>();
        this.listJalan_tipe2 = new ArrayList<>();
    }

    public Node(){
        
    }
    public void setCost(int cost) {
        this.cost = cost;
    }
    @Override 
    public int compare(Node node1, Node node2) {
        if(node1.cost > node2.cost){
            return 1;
        }
        else if(node1.cost < node2.cost){
            return -1;
        }
        
        return 0;
    }
}

class Jalan{
    int panjang;
    Node kota; 

    //Constructor dengan tipe jalan 0
    public Jalan(Node kota,  int panjang){
        this.kota = kota;
        this.panjang = panjang;
    }

}


//dari node start sampai node kotaTujuan brapa jaraknya
class Path_0{
    Node kotaTujuan;
    int panjang;

    //constructor
    public Path_0(Node kotaTujuan, int panjang){
        this.kotaTujuan = kotaTujuan;
        this.panjang = panjang;
    }
}

//dari node start sampai node kotaTujuan brapa jaraknya

class Path_1 {
    Node kotaTujuan;
    int panjang;
    
    
    //constructor
    public Path_1(Node kotaTujuan, int panjang){
        this.kotaTujuan = kotaTujuan;
        this.panjang = panjang;
        //this.visited = visited;
    }
}