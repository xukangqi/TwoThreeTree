/**
 * Created by Administrator on 2016/4/6.
 */
public class ListOverflowException extends RuntimeException {
    public ListOverflowException(){}
    public ListOverflowException(String message){
        System.out.println(message);
    }
}
