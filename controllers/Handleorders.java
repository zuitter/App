package controllers;

import play.*;
import play.libs.Json;
import play.mvc.*;

import views.html.*;
import models.Item;
import models.Order;

import org.jongo.Jongo;

import org.jongo.MongoCollection;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.Random;

import models.Slots;
import org.jongo.Find;
import connect.client;
public class Handleorders extends Controller {
	Jongo db;
	Handleorders()
	{
		  db=client.getDB();
	}
	 public Result index() {
	        return ok(index.render());
	    }
	public Result bookslot(String order_id,String slot_id)
	{
		int d=alreadybooked(order_id);
		String [] time={"9 am to 11 am","11 am to 1 pm","2pm to 4pm","4pm to 6pm"};
		ObjectNode res=Json.newObject();
		if(d!=-1)
		{
			res.put("Status","This order is already alloted slot");
			res.put("Slot",time[d-1]);
			return ok(res);
		}
		Application app=new Application();
		boolean b=app.bookingsystem(order_id,slot_id);
		System.out.println(b);
		
		if(b)
		{
			res.put("Status:","Slot Booked Successfully");
		    res.put("Slot",time[Integer.parseInt(slot_id)-1]);
		}
		else
		{
			res.put("Status","Slot Could not be Booked");
		    res.put("Slot","Slot"+time[Integer.parseInt(slot_id)-1]+"could not be booked.Please try another slot.Slots are : 1) 9 to 11 am 2) 11 am to 1 pm 3) 2pm to 4pm 4) 4pm to 6pm");
		}
		return ok(res);
	}
	public Result tellslot(String order_id)
	{
			String [] time={"9 am to 11 am","11 am to 1 pm","2pm to 4pm","4pm to 6pm"};
		int d=alreadybooked(order_id);
		boolean st=false;
		ObjectNode res=Json.newObject();
		String s="";
		if(d==-1)
		{
			s="No slot booked for given Order Id.Slots are : 1) 9 to 11 am 2) 11 am to 1 pm 3) 2pm to 4pm 4) 4pm to 6pm";
		}
		else
		{
			s=time[d-1];
			st=true;
		}
		res.put("Booking_status", st);
		res.put("Slot",s );
		return ok(res);
	  }
	  public int alreadybooked(String order_id)
	  {
		  MongoCollection items= db.getCollection("slot");
			Iterable <Slots> i=items.find("{order_id:"+order_id+"}").as(Slots.class);
			Iterator <Slots> j=i.iterator();
			int d=-1;
			if(j.hasNext())
			{	
				Slots s=j.next();
				d=s.getSlotId();
			}
			return d;
	  }
	  public Result createorder(String items)
	  {
		  String [] l=items.split(",");
		  int i;
		  MongoCollection order=db.getCollection("order");
		  DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		  Date date = new Date();
		  Random r=new Random();
		  int id=r.nextInt(2000);
		  int item;
		  for(i=0;i<l.length;i++)
		  {
			  item=Integer.parseInt(l[i]);
			 order.insert(new Order(id,date,item)); 
		  }
		  ObjectNode res=Json.newObject();
		  res.put("Status","Order Created Successfully");
		  res.put("Order_id",id);
		  return ok(res);
	  }
}
