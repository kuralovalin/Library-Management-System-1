public class Floor {
    int floorNumber;
    String floorId;
    private String[] tables;
    public Floor(int num){
        this.floorNumber = num;
    }

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

    public int getFloorNumber(){
        return floorNumber;
    }
    public String getFloorId(){
        return floorId;
    }
    public String toString(){
        return floorId+" "+tables.toString();
    }


}
