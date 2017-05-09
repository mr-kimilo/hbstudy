package hbstudy;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import pojo.HibernateUtil;
import pojo.News;

public class NewsTest {
	Configuration cfg=null;
	StandardServiceRegistryBuilder srb=null;
	StandardServiceRegistry sr=null;
	SessionFactory factory=null;
	Session session=null;
	
	@Before 
	public void init(){
		cfg = new Configuration().configure();
		srb = new StandardServiceRegistryBuilder().applySettings(cfg.getProperties());             
		sr = srb.build();             
		factory = cfg.buildSessionFactory(sr);
		session = factory.openSession();
	}

	
	@Test
	public void insertNews() {
		News news = new News();
		news.setId(UUID.randomUUID().toString());
		news.setTitle("news title");
		news.setContent("news content");
		news.setWriter("Nic");
		news.setCreateDate(new Date());
		
		Transaction tx = null;
		
		try {
			
			cfg = new Configuration().configure();
			srb = new StandardServiceRegistryBuilder().applySettings(cfg.getProperties());             
			sr = srb.build();             
			factory = cfg.buildSessionFactory(sr);
			session = factory.openSession();
			tx = session.beginTransaction();
			
			session.save(news);
			tx.commit();
			session.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Ignore
	@Test
	public void updateNews(){
		Transaction tx = null;
		
		try {
			
			/*tx=session.beginTransaction();
			News news= (News)session.get(News.class, "0f0619ad-0162-4ac9-ba6d-ea2bfe4d6a3d");
			
			news.setTitle("文章标题");
			news.setContent("文章内容 content title 10");
			session.save(news);
			tx.commit();
			session.close();*/
			
			tx = session.beginTransaction();
			Criteria c = session.createCriteria(News.class);
			List<News> newsList = c.list();
			for(News news: newsList){
				news.setTitle("文章标题");
				news.setContent("文章内容");
				session.save(news);
				break;
			}
			
			tx.commit();
			session.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	@Ignore
	@Test
	public void testqueryByHQL(){
		try {
			
			String hql = "from News as n where n.writer=:writer";
			Query query = session.createQuery(hql);
			query.setString("writer", "Nic");
			
			List<News> newsList = query.list();
			for(News news:newsList){
				
				System.out.println(news);
			}
			
			session.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	@Test
	public void testQueryByCriteria(){
		
		try {
			Criteria c = session.createCriteria(News.class);
			c.add(Restrictions.eq("writer", "Nic"));
			
			List<News> newsList = c.list();
			for(News news:newsList){
				
				System.out.println(news);
			}
			
			session.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Ignore
	@Test
	public void testQueryDynamicCriteria(){
		DetachedCriteria dc = DetachedCriteria.forClass(News.class);
		dc.add(Restrictions.eq("writer", "Nic"));
		List<News> ll =callDetachedCriteria(dc);
		
		for(News news:ll){
			System.out.println(news);
		}
	}

	public static List callDetachedCriteria(DetachedCriteria  dc){
		Criteria c = dc.getExecutableCriteria(HibernateUtil.getSession());
		List list = c.list();
		HibernateUtil.getSession().close();
		return list;
	}
	
	@Ignore
	@Test
	public void testQueryBySQL(){
		
		String querySql = "select * from news where writer='Nic'";
		Query q = session.createSQLQuery(querySql).addEntity(News.class);
		List<News> list = q.list();
		
		for(News news:list){
			System.out.println(news);
		}
		session.close();
	}
}
