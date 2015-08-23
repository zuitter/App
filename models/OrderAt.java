package models;
import com.fasterxml.jackson.annotation.*;
import org.jongo.marshall.jackson.oid.*;

@JsonAutoDetect(fieldVisibility=JsonAutoDetect.Visibility.ANY)
@JsonIgnoreProperties({"_id"})
public class OrderAt {
	 private int order_id;
	 private int van_id;
	 private  int carton_id;
	 public static final String oid="order_id";
	 public static final String vid="van_id";
	 public static final String cid="carton_id";
     public OrderAt(@JsonProperty(vid) int v,@JsonProperty(oid)int o,@JsonProperty(cid)int c)
     {
    	 this.van_id=v;
    	 this.order_id=o;
    	 this.carton_id=c;
     }
     @JsonProperty(oid)
	public int getOrder_id() {
		return order_id;
	}
     @JsonProperty(oid)
	public void setOrder_id(int order_id) {
		this.order_id = order_id;
	}
	@JsonProperty(vid)
	public int getVan_id() {
		return van_id;
	}
	@JsonProperty(vid)
	public void setVan_id(int van_id) {
		this.van_id = van_id;
	}
	@JsonProperty(cid)
	public int getCarton_id() {
		return carton_id;
	}
	@JsonProperty(cid)
	public void setCarton_id(int carton_id) {
		this.carton_id = carton_id;
	}
     
}
