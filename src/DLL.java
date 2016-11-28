
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

/**
 * Created by Administrator on 2016/4/6.
 */
public class DLL {

    public  static void insertInOrder(int data,DLLNode[] dll){
        try{
            //create a new array to store the value of data
        int[] arrs=new int[10];
        int size=getLength(dll);
       if (size==0){
           dll[0]=new DLLNode(data);
           arrs[0]=data;
       }else {
           for (int i=0;i<size;i++){
               arrs[i]=dll[i].getData();
           }
           //create a new dllnode
           dll[size]=new DLLNode(data);
           arrs[size]=data;
            int[] arr=sort(arrs);

           int previousnumber;
           int current;
           int nextnumber;
           int previouslocation;
           int location;
           int nextlocation;
           //this cycle is to change every dllnode's prenvious and next
           for (int i=0;i<size+1;i++){
               if (i==0){
                   // change the first one
                   current=arr[i];
                   nextnumber=arr[i+1];
                   location=findDataInDLL(current,dll);
                   nextlocation=findDataInDLL(nextnumber,dll);
                   dll[location].setNext(nextlocation);
                   dll[location].setPrevious(-1);
               }else if (i==size){
                   //change the last one
                   previousnumber=arr[i-1];
                   current=arr[i];
                   previouslocation=findDataInDLL(previousnumber,dll);
                   location=findDataInDLL(current,dll);
                   dll[location].setNext(-1);
                   dll[location].setPrevious(previouslocation);

               }else {

                   previousnumber=arr[i-1];
                   current=arr[i];
                   nextnumber=arr[i+1];
                   //find the smaller dllnodelocation
                   previouslocation=findDataInDLL(previousnumber,dll);
                   location=findDataInDLL(current,dll);
                   //find the bigger dllnodelocation
                   nextlocation=findDataInDLL(nextnumber,dll);
                   dll[location].setNext(nextlocation);
                   dll[location].setPrevious(previouslocation);
               }
           }
       }
        }catch (RuntimeException e){
            throw new ListOverflowException("firstFreeDLLNode:"+getLength(dll));
        }
    }

    public static int findDataInDLL(int data,DLLNode[] dllNodes){
        for (int i=0;i<getLength(dllNodes);i++){
            if (dllNodes[i].getData()==data){
                return i;
            }
        }
        return 0;

    }
    public static void toString(DLLNode[] dllNodes){
      int i=0;
        while (dllNodes[i]!=null){
            System.out.println(i+"  "+dllNodes[i].toString()+"\n");
            i++;
        }
    }
    public static int getLength(DLLNode[] dllNodes){
        //this function return the firstfreedllnode
        int i=0;
        while (dllNodes[i]!=null){
            i++;
        }
        return i;
    }
    public static int[] sort(int[] arr){
        //sort the array and make the element in ascennding order
        int size=0;
        int[] result=new int[arr.length];
        while (arr[size]!=0){
            size++;
        }
        int[] temp=new int[size];
        for (int i=0;i<size;i++){
            temp[i]=arr[i];
        }
        Arrays.sort(temp);
        for (int i=0;i<arr.length;i++){
            if (i<size){
                result[i]=temp[i];
            }
        }
        return  result;
    }
    public static void main(String args[]){

        DLLNode[] dll=new DLLNode[10];

        int[] arrays=new int[]{5,9,7,95,23,49,4,8,45,27};
        for (int i=0;i<arrays.length;i++){
               insertInOrder(arrays[i],dll);

        }
        toString(dll);

    }
}
