/**
 * Created by Administrator on 2016/4/9.
 */
public class TreeOverflowException extends RuntimeException {
    public TreeOverflowException(){
    }
    public TreeOverflowException(String message){
        System.out.println(message);
    }
}
