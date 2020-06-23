import java.util.Arrays;
import java.util.Random;
public class SkipList<E extends Comparable> implements SkipListInterface<E>{

    private SLNode<E> head;
    private int maxLevel;
    private int size;
    private int maxCap;
    private static final double LOG2 = Math.log(2.0);
    private Random rand;
    private static final int MIN = Integer.MIN_VALUE;


    @SuppressWarnings({"unchecked", "rawtypes"})
    public SkipList() {
        rand = new Random();
        size = 0;
        maxLevel = 0;
        maxCap = computeMaxCap();
        head = new SLNode(null, maxLevel);
    }

    @Override
    public boolean remove(E target) {
        SLNode<E>[] pred = search(target);
        if( pred[0].links[0].data.equals(target) ){
            --size;
            SLNode<E> temp = pred[0];
            for( int i = 0; i<temp.links.length; ++i ){

                pred[i].links[i] = temp.links[i];
            }
        }else
            return false;
        return true;
    }

    @Override
    public boolean insert(E data) {
        ++size;
        SLNode<E> pred[] = search(data);

        if(size > maxCap){
            maxLevel ++;
            maxCap = computeMaxCap();
            head.links = Arrays.copyOf(head.links, maxLevel);
            pred = Arrays.copyOf(pred,maxLevel);
            pred[maxLevel-1] = head;
        }

        SLNode<E> new_node = new SLNode<>(data,logRandom());
        for(int i = 0; i<new_node.links.length; ++i) {
            new_node.links[i] = pred[i].links[i];
            pred[i].links[i] = new_node;
        }
        pred[0].links[0] = new_node;
        return true;
    }

    private int computeMaxCap(){
        return (int)Math.pow(2,maxLevel)-1;
    }

    /**
     * returns the value if it is inside of the Skip list, null otherwise, uses the search private method
     * @param data data to be searched
     * @return returns the data or null if not found
     */
    @Override
    public E find(E data) {

        SLNode<E>[] pred = search(data);
        if(pred[0].links[0] != null && ((Student)pred[0].links[0].data).compareTo(data)==0)
            return data;
        else
            return null;
    }

    /**
     * searches for an element in the list and returns references of predecing nodes
     * @param target target to be found
     * @return array of predecing nodes(links)
     */
    @SuppressWarnings("unchecked")
    private SLNode<E>[] search(E target){
        SLNode<E>[] pred = new SLNode[maxLevel];
        SLNode<E> current = head;

        for(int i = current.links.length-1; i>=0; --i){
            while(current.links[i] != null && current.links[i].data.compareTo(target)<0)
                current = current.links[i];
            pred[i] = current;
        }
        return pred;
    }

    /**
     * generating random level for the new node to be inserted
     * @return returns the level of the new node
     */
    private int logRandom(){
        int r = rand.nextInt(maxCap);
        int k = (int)(Math.log(r+1)/LOG2);
        if(k > maxLevel - 1)
            k = maxLevel - 1;
        return maxLevel - k;
    }

    @SuppressWarnings("rawtypes")
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        SLNode<E> temp = head.links[0];
        while(temp != null){
            sb.append(temp);
            if(temp.links[0] != null)
                sb.append(" ---> ");
            temp = temp.links[0];
        }
        return sb.toString();
    }


    //getter
    public int getSize() { return size; }
    public SLNode<E> getHead() { return head; }



    public class SLNode<E>{
        SLNode<E>[] links;
        E data;
        @SuppressWarnings("unchecked")
        public SLNode(E data, int m){
            links = (SLNode<E>[])new SLNode[m];//links to other nodes
            this.data = data;//data stored
        }

        @Override
        public String toString() {
            return(data.toString() + " |" + links.length + "|");
        }

        public boolean hasNext() {
            if (this.links[0] != null)
                return true;
            else
                return false;
        }

        public SLNode next(){
            return this.links[0];
        }
    }

    public String printHead(){
        return head.data.toString();
    }
}
