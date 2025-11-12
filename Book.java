

public class Book {
	String isbn;
	String name;
	int quantity;
	double cost;
	Book(String isbn,String name,int quantity,double cost) {
	    this.isbn=isbn;
	    this.name=name;
	    this.quantity=quantity;
	    this.cost=cost;
	}
	public String toString(){
	    return isbn+" | "+name+" | Qty:"+quantity+ " | Cost: ₹" +cost;
	}
}