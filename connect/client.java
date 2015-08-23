package connect;
import java.util.ArrayList;
import com.mongodb.DB;
import com.mongodb.MongoClient;
import org.jongo.Jongo;
public class client {
        private static int count;
        private client()
        {
        	count=0;
        }
      public static Jongo  getDB()
        {
    	  try
    	  {
    	  Jongo j=new Jongo(new MongoClient().getDB("mydb"));
        	count++;
        	return j;
    	  }
    	  catch(Exception e)
    	  {
    		  System.out.println("Exception"+e);
    		  return null;
    	  }
        }
      public static void close (Jongo j)
      {
    	  count--;
      }
}
