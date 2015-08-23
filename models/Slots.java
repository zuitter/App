package models;
import org.jongo.marshall.jackson.oid.Id;
import org.jongo.marshall.jackson.oid.ObjectId;
import com.fasterxml.jackson.annotation.*;
@JsonAutoDetect(fieldVisibility=JsonAutoDetect.Visibility.ANY)
@JsonIgnoreProperties({"_id"})

public class Slots {
	
  private int slot_id;
  private int order_id;
  private int van_id;
  public static final String id="_id";
  public static final String sid="slot_id";
  public static final String oid="order_id";
  public static final String vid="van_id";
  
  @JsonCreator
  Slots(@JsonProperty(sid)int slot_id,@JsonProperty(oid)int order_id,@JsonProperty(vid)int van_id)
  {
	  
	  this.slot_id=slot_id;
	  this.order_id=order_id;
	  this.van_id=van_id;
  }
  public Slots()
  {
	  
  }
  @JsonProperty(sid)
public int getSlotId() {
	return slot_id;
}
  @JsonProperty(sid)
public void setSlotId(int slot_id) {
	this.slot_id = slot_id;
}
  @JsonProperty(oid)
public int getOrderId() {
	return order_id;
}
  @JsonProperty(oid)
public void setOrderId(int order_id) {
	this.order_id = order_id;
}
  @JsonProperty(vid)
public int getVanId() {
	return van_id;
}
  @JsonProperty(vid)
public void setVanId(int van_id) {
	this.van_id = van_id;
}
}
