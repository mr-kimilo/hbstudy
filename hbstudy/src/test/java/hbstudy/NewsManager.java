package hbstudy;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

import pojo.News;

/**
 * 无需配置文件直接生产配置类
 * @author nicolas
 *
 */
public class NewsManager {

	public static void main(String[] args) {
		Configuration cfg=null;
		StandardServiceRegistryBuilder srb=null;
		StandardServiceRegistry sr=null;
		SessionFactory factory=null;
		Session session=null;
		Transaction tx=null;
		cfg = new Configuration();
		cfg.addClass(News.class)
			.setProperty("hibernate.connection.driver_class","com.mysql.jdbc.Driver")
			.setProperty("hibernate.connection.url","jdbc:mysql://localhost:3306/hbstudy?useUnicode=true&amp;characterEncoding=UTF-8")
			.setProperty("hibernate.connection.username","root")
			.setProperty("hibernate.connection.password","")
			.setProperty("hibernate.dialect","org.hibernate.dialect.MySQLDialect")
			.setProperty("hibernate.show_sql","true")
			.setProperty("hibernate.format_sql", "true");
		
		srb = new StandardServiceRegistryBuilder().applySettings(cfg.getProperties());             
		sr = srb.build();             
		factory = cfg.buildSessionFactory(sr);
		session = factory.openSession();
		tx = session.beginTransaction();
		Criteria c = session.createCriteria(News.class);
		List<News> newsList = c.list();
		for(News news: newsList){
			System.out.println(news);
		}
				
		tx.commit();
		session.close();
	}
}
