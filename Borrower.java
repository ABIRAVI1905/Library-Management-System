
import java.util.*;

public class Borrower{
    String email;
    String password;
    double deposit=1500;
    ArrayList<String> borrowed=new ArrayList<>();
    ArrayList<String> fines=new ArrayList<>();
    
    Borrower(String email,String password){
        this.email=email;
        this.password=password;
    }
}