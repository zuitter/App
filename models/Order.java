package models;
import com.fasterxml.jackson.annotation.*;
import org.jongo.marshall.jackson.oid.*;
import java.util.Date;
@JsonAutoDetect(fieldVisibility=JsonAutoDetect.Visibility.ANY)
@JsonIgnoreProperties({"_id"})
public class Order {
	private int order_id;
	private int item_id;
	private Date order_date;
    public static final String date="order_date";
    public static final String oid="order_id";
    public static final String item="item_id";
    @JsonCreator
   public Order(@JsonProperty(oid)int order,@JsonProperty(date)Date d,@JsonProperty(item)int it)
    {
    	this.order_id=order;
    	this.item_id=it;
    	this.order_date=d;
    }
    @JsonProperty(date)
	public Date getOrder_date() {
		return order_date;
	}
    @JsonProperty(date)
	public void setOrder_date(Date order_date) {
		this.order_date = order_date;
	}
	@JsonProperty(oid)
	public int getOrder_id() {
		return order_id;
	}
    @JsonProperty(oid)
	public void setOrder_id(int order_id) {
		this.order_id = order_id;
	}
    @JsonProperty(item)
	public int getItem_id() {
		return item_id;
	}
    @JsonProperty(item)
	public void setItem_id(int item_id) {
		this.item_id = item_id;
	}
	
	
    
}