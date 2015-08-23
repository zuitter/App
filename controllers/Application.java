package controllers;

import play.*;
import play.mvc.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.PriorityQueue;

import org.jongo.Jongo;
import org.jongo.MongoCollection;

import connect.client;
import models.Item;
import models.Order;
import models.OrderAt;
import models.Slots;
import models.Van;
import views.html.*;

public class Application{
	Jongo db;
	HashMap<Integer,Item> catalog;
	Application()
	{
		db=client.getDB();
	}
    void refreshcatalog()
    {
    	catalog=new HashMap<Integer,Item>();
    	MongoCollection items= db.getCollection("item");
		Iterable <Item> i=items.find().as(Item.class);
		Iterator <Item> j=i.iterator();
		Item a;
		while(j.hasNext())
		{
			a=j.next();
			catalog.put(a.get_id(),a);
		}
    }
    void newday()
    {
		System.out.println("new day");
    	MongoCollection vans= db.getCollection("van");
    	vans.remove("{van_id:1}");
    	vans.remove("{van_id:2}");
    	vans.remove("{van_id:3}");
    	vans.remove("{van_id:4}");
		Van v;
		int vid,s,c,l,b,h;
		l=30;
		b=h=15;
		int count=0;
		for(vid=1;vid<=4;vid++)
			for(s=1;s<=4;s++)
				for(c=1;c<=20;c++)
				{
					vans.insert("{_id:"+count+",van_id:"+vid+",slot_id:"+s+",carton_id:"+c+",l:"+l+",b:"+b+",h:"+h+"}");
				  count++;
				}
		
    }
    public  boolean bookingsystem(String order_id,String slot_id)
    {
    	try
    	{
    		refreshcatalog();
    	ArrayList <Item> list=getItems(order_id);
    	newday();
        return book(order_id,slot_id,list,1);
        
    	
    	}
    	catch(Exception e)
    	{
    		System.out.println("Exception logged while booking order"+e);
    	}
    	return true;
    }
    boolean book(String order_id,String slot_id,ArrayList<Item> list,int vid)
    {
		System.out.println("book");
    	MongoCollection spaces=db.getCollection("van");
    	String van_id=Integer.toString(vid);
    	Iterable<Van> i=spaces.find("{van_id:"+van_id+",slot_id:"+slot_id+"}").as(Van.class);
    	Iterator <Van> j=i.iterator();
    	Comparator<Van> c=new Comparator<Van>(){
			public int compare(Van r,Van s)
			{
				int l1=r.getL();
				int b1=r.getB();
				int h1=r.getH();
				int l2=s.getL();
				int b2=s.getB();
				int h2=s.getH();
				int c1=s.getCarton_id();
				int c2=s.getCarton_id();
				if(l1!=l2)
					return (l1>l2)?-1:1;
				else if(b1!=b2)
					return (b1>b2)?-1:1;
				else if(h1!=h2) return (h1>h2)?-1:1;
				else return (c1>c2)?1:-1;
			}
		};
    	PriorityQueue <Van> cubes=new PriorityQueue<Van>(20,c);
    	PriorityQueue <Van>  local=new PriorityQueue<Van>(20,c);
    	PriorityQueue <Van>  localunused=new PriorityQueue<Van>(20,c);
    	PriorityQueue <Van>  cubeunused=new PriorityQueue<Van>(20,c);
    	while(j.hasNext())
    		cubes.offer(j.next());
    	Van v;
    	int n=list.size();
    	Van [] at=new Van[n];
    	Item curr;
    	int k=0;
    	while(k<n)
    	{
    		curr=list.get(k);
    	while(cubes.size()>0)
    		{
    		v=cubes.remove();
    		if(fits(curr,v,local))
    				{
    			       
    			       at[k]=v;
    			       k++;
    			      break;
    				}
    		else
    		 {
    			cubeunused.offer(v);
    		  }
    		}
    	if(k==n)
    		break;
    	
    	while((local.size()>0) &&(k<n))
		{
    		curr=list.get(k);
    		v=local.remove();
		if(fits(curr,v,local))
				{
			       at[k]=v;
			       k++;
				}
		else
		   {
			localunused.offer(v);
		    }
		}
    		if(((cubes.size()==0)&&(k!=n)))
    		{
    			if(vid!=4)
    			{
    				vid++;
    			return book(order_id,slot_id,list,vid);
    			}
    			else
    				return false;
    		}
    		while(cubeunused.size()>0)
    			cubes.offer(cubeunused.remove());
    		while(localunused.size()>0)
    			local.offer(localunused.remove());
    	}
		if(local.size()!=0)
    	   {
    		   while(local.size()>0)
    		   {
				   v=local.peek();
			System.out.println("from local to cubes"+v.getL()+"/"+v.getB()+"/"+v.getH());
    			   cubes.offer(local.remove());
    		   }
    	   }
    	update(order_id,slot_id,at,cubes);
    	return true;
    }
    void update(String order_id,String slot_id,Van [] at,PriorityQueue <Van> cubes)
    {
		System.out.println("update:"+at.length);
    	MongoCollection slot=db.getCollection("slot");
    	Slots s;
    	int i;
    	int o,sl;
    	o=Integer.parseInt(order_id);
    	sl=Integer.parseInt(slot_id);
        s=new Slots();
        s.setOrderId(o);
    	s.setSlotId(sl);
    	s.setVanId(at[0].getVan_id());
		slot.insert(s);
    	Van v;
    	 slot=db.getCollection("van");
		String vid=((Integer)at[0].getVan_id()).toString();
		slot.remove("{van_id:"+vid+",slot_id:"+slot_id+"}");
		System.out.println("update remove");
		//vans.insert(cubes);
    	while(cubes.size()>0)
    	{
			v=cubes.remove();
			slot.insert(new Van(v));
			System.out.println("update added to van"+v.getL()+"/"+v.getB()+"/"+v.getH());
    	}
    	slot=db.getCollection("OrderAt");
    	OrderAt l;
    	for(i=0;i<at.length;i++)
    	{
    		l=new OrderAt(at[i].getVan_id(),o,at[i].getCarton_id());
    		slot.insert(l);
    	}
    	return;
    }
    public boolean fits(Item a,Van v,PriorityQueue<Van> local)
    {
		System.out.println("fits");
    	int l1=a.getLength();
    	int b1=a.getBreadth();
    	int h1=a.getHeight();
    	int l=v.getL();
    	int b=v.getB();
    	int h=v.getH();
    	if((l>=l1)&&(b>=b1)&&(h>=h1))
    	{
    		addlocal(v,local,b1,h1,(l-l1));
    		addlocal(v,local,b1,(h-h1),l);
    		addlocal(v,local,(b-b1),l,h);
    	return true;
    	}
    	else
    		return false;
    }
    void addlocal(Van v,PriorityQueue<Van>local,int l,int b,int h)
    {
		System.out.println("addlocal");
    	int d1,d2,d3;
    	if((l==0)||(b==0)||(h==0))
    		return;
    	if(l>b)
    	{
    		if(l>h)
    		{
    			d1=l;
    			d2=(b>h)?b:h;
    			d3=(b>h)?h:b ;
    		}
    		else
    		{
    			d1=h;
    			d2=(b>l)?b:l;
    			d3=(b>l)?l:b ;
    		}
    	}
    	else
    	{
    		if(b>h)
    		{
    			d1=b;
    			d2=(l>h)?l:h;
    			d3=(l>h)?h:l ;
    		}
    		else
    		{
    			d1=h;
    			d2=(b>l)?b:l;
    			d3=(b>l)?l:b ;
    		}
    	}
    	Van p=new Van(v.getVan_id(),v.getSlot_id(),v.getCarton_id(),d1,d2,d3);
    	local.add(p);
    	return;
    }
     public ArrayList<Item> getItems(String order_id) throws Exception
    {
		System.out.println("getitems");
		MongoCollection items= db.getCollection("order");
		Iterable <Order> i=items.find("{order_id:"+order_id+"}").as(Order.class);
		Iterator <Order> j=i.iterator();
		ArrayList <Integer> a=new ArrayList<Integer>();
		while(j.hasNext())
		{
			a.add(j.next().getItem_id());
		}
		Collections.sort(a,new Comparator<Integer>(){
			public int compare(Integer r,Integer s)
			{
				Item x=catalog.get(r);
				Item y=catalog.get(s);
				int l1=x.getLength();
				int b1=x.getBreadth();
				int h1=x.getHeight();
				int l2=y.getLength();
				int b2=y.getBreadth();
				int h2=y.getHeight();
				if(l1!=l2)
					return (l1>l2)?1:-1;
				else if(b1!=b2)
					return (b1>b2)?1:-1;
				else return (h1>h2)?1:-1;
			}
		});
		ArrayList <Item> order_items=new ArrayList<Item>();
		for(int id:a)
			order_items.add(catalog.get(id));
        return order_items;
    }
}
