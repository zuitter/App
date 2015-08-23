package models;
import com.fasterxml.jackson.annotation.*;
import org.jongo.marshall.jackson.oid.*;

@JsonAutoDetect(fieldVisibility=JsonAutoDetect.Visibility.ANY)
@JsonIgnoreProperties({"name"})
public class Item {
	@Id
	private int _id;
	private int l;
	private int b;
	private int h;
	public static final String id="_id";
	public static final String length="l";
	public static final String breadth="b";
	public static final String height="h";
	
	@JsonCreator
	public Item(@JsonProperty(id)int i,@JsonProperty(length)int l,@JsonProperty(breadth)int b,@JsonProperty(height)int h)
	{
		this._id=i;
		this.l=l;
		this.b=b;
		this.h=h;
	}
	@JsonProperty(length)
	public int getLength() {
		return l;
	}
	@JsonProperty(length)
	public void setLength(int l) {
		this.l = l;
	}
	@JsonProperty(breadth)
	public int getBreadth() {
		return b;
	}
	@JsonProperty(id)
	public int get_id() {
		return _id;
	}
	@JsonProperty(breadth)
	public void setBreadth(int b) {
		this.b = b;
	}
	@JsonProperty(height)
	public int getHeight() {
		return h;
	}
	@JsonProperty(height)
	public void setHeight(int h) {
		this.h = h;
	}
	
}
