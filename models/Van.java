package models;
import com.fasterxml.jackson.annotation.*;
import org.jongo.marshall.jackson.oid.*;

@JsonAutoDetect(fieldVisibility=JsonAutoDetect.Visibility.ANY)
@JsonIgnoreProperties({"_id"})
public class Van {
	@Id
	private int _id;
private  int van_id;
private  int slot_id;
private  int carton_id;
private  int l;
private  int b;
private  int h;
private static final String vid="van_id";
private static final String sid="slot_id";
private static final String cid="carton_id";
private static final String len="l";
private static final String bth="b";
private static final String hgt="h";

@JsonCreator 
public Van(@JsonProperty(vid) int v,@JsonProperty(sid)int s,@JsonProperty(cid)int c,@JsonProperty(len)int l,@JsonProperty(bth)int b,@JsonProperty(hgt)int h)
{
	this.van_id=v;
	this.slot_id=s;
	this.carton_id=c;
    this.l=l;
    this.b=b;
    this.h=h;
}
public Van()
{
	
}
public Van(Van b)
{
	this.van_id=b.getVan_id();
	this.slot_id=b.getSlot_id();
	this.carton_id=b.getCarton_id();
	this.l=b.getL();
	this.b=b.getB();
	this.h=b.getH();
}
@JsonProperty(vid)
public  int getVan_id() {
	return van_id;
}
@JsonProperty(vid)
public  void setVan_id(int van_id) {
	this.van_id = van_id;
}
@JsonProperty(sid)
public  int getSlot_id() {
	return slot_id;
}
@JsonProperty(sid)
public  void setSlot_id(int slot_id) {
	this.slot_id = slot_id;
}
@JsonProperty(cid)
public  int getCarton_id() {
	return carton_id;
}
@JsonProperty(cid)
public  void setCarton_id(int carton_id) {
	this.carton_id = carton_id;
}
@JsonProperty(len)
public  int getL() {
	return l;
}
@JsonProperty(len)
public  void setL(int l) {
	this.l = l;
}
@JsonProperty(bth)
public  int getB() {
	return b;
}
@JsonProperty(bth)
public  void setB(int b) {
	this.b = b;
}
@JsonProperty(hgt)
public  int getH() {
	return h;
}
@JsonProperty(hgt)
public  void setH(int h) {
	this.h = h;
}
public void set_id(int _id) {
	this._id = _id;
}

}

