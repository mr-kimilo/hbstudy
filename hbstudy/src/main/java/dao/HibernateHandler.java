package dao;

import java.io.Serializable;

import org.hibernate.Session;

public interface HibernateHandler extends Serializable{
	public abstract Object doInHibernate(Session session);
}
