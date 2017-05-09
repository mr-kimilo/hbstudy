package pojo;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

public class HibernateUtil {

	private static Session session =  null;
	
	public static synchronized Session getSession(){
		if(session !=null){
			return session;
		}else{
			Configuration cfg=null;
			StandardServiceRegistryBuilder srb=null;
			StandardServiceRegistry sr=null;
			SessionFactory factory=null;
			try {
				
				cfg = new Configuration().configure();
				srb = new StandardServiceRegistryBuilder().applySettings(cfg.getProperties());             
				sr = srb.build();             
				factory = cfg.buildSessionFactory(sr);
				session = factory.openSession();
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return session;
	}

}
