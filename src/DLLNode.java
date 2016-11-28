/**
 * Created by Administrator on 2016/4/6.
 */
public class DLLNode {
    public int data;
    public int previous;
    public int next;
    public DLLNode(int data){
        this.data=data;
        previous=-1;
        next=-1;
    }
    public DLLNode(int data,int previous,int next){
        this.data=data;
        this.next=next;
        this.previous=previous;
    }
    public void setData(int data){
        this.data=data;
    }
    public void setPrevious(int previous){
        this.previous=previous;
    }
    public void setNext(int next){
        this.next=next;
    }
    public int getData(){
        return data;
    }
    public int getPrevious(){
        return previous;
    }
    public int getNext(){
        return next;
    }
    public String toString(){
        String message=data+"  Prexious:"+previous+"  Next:"+next;
        return message;
    }
}
