public class Floor {
    /** floor number is actually vertex number*/
    int floorNumber;
    /** Actual floor number and side*/
    String floorId;
    /** all the table on that floor*/
    private String[] tables;

    /** also has a vertex number*/
    public Floor(int num){
        this.floorNumber = num;
    }
    /***
     * 3 parameter constructor.
     * @param floorNumber floor number
     * @param floorId which side of the floor is. full address of the floor to be visited
     * @param count table id
     */
    public Floor(int floorNumber,String floorId,int count){
        this.floorNumber = floorNumber;
        tables =new String[10];
        this.floorId = floorId;

        for (int i = 0; i <10 ; i++) {
            int temp = count;
            tables[i] = Integer.toString(temp);
            count++;
        }
    }
    /***
     * @return floor number
     */
    public int getFloorNumber(){
        return floorNumber;
    }
    /***
     * @return floor id
     */
    public String getFloorId(){
        return floorId;
    }
    /***
     * @return string all information for floor
     */
    public String toString(){
        return floorId+" "+tables.toString();
    }


}
