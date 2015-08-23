package controllers;
import java.util.Comparator;
import java.util.PriorityQueue;
import org.jongo.Jongo;
import org.jongo.MongoCollection;
import models.Van;
import play.mvc.*;
import connect.client;
public class check extends Controller {

	public Result fun() {
		// TODO Auto-generated method stub
		Comparator<Van> c=new Comparator<Van>(){
			public int compare(Van r,Van s)
			{
				int l1=r.getL();
				int b1=r.getB();
				int h1=r.getH();
				int l2=s.getL();
				int b2=s.getB();
				int h2=s.getH();
				if(l1!=l2)
					return (l1>l2)?-1:1;
				else if(b1!=b2)
					return (b1>b2)?-1:1;
				else return (h1>h2)?-1:1;
			}
		};
		PriorityQueue<Van> cubes=new PriorityQueue<Van>(20,c);
		Van v1=new Van(1,1,1,30,15,15);
		Van v2=new Van(1,1,1,15,9,6);
		Van v3=new Van(1,1,1,21,15,12);
		Van v10=new Van(1,1,1,30,15,15);
		Van v5=new Van(1,1,1,15,9,6);
		Van v6=new Van(1,1,1,20,15,2);
		Van v7=new Van(1,1,1,30,15,5);
		Van v8=new Van(1,1,1,15,9,6);
		Van v9=new Van(1,1,1,21,15,12);
		Van vft;
		vft=v5;
		//System.out.println(vft.getL()+"/"+vft.getB()+"/"+vft.getH());
		cubes.offer(v1);
		cubes.offer(v2);
		cubes.offer(v3);
		cubes.offer(v10);
		cubes.offer(v5);
		cubes.offer(v6);
		cubes.offer(v7);
		cubes.offer(v8);
		cubes.offer(v9);
		while (cubes.size()>0)
		{
			vft=cubes.poll();
			System.out.println(vft.getL()+"/"+vft.getB()+"/"+vft.getH());
			
		}
		
		return ok();
	}
   public Result ins()
   {
	   Jongo db=client.getDB();
	   MongoCollection c=db.getCollection("van");
	   Van v5=new Van(1,1,1,15,9,6);
		Van v6=new Van(1,1,1,20,15,2);
		Van v7=new Van(1,1,1,30,15,5);
		Van v=new Van(v5);
		//c.remove("{van_id:1,slot_id:1}");
		/*c.insert(new Van(1,1,1,15,9,6));
		c.insert(new Van(1,1,1,20,15,2));*/
		c.insert(v);
		return ok();
   }
}
