package main.resources;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateUtil {
    private static final SessionFactory sessionFactory = buildSessionFactory();

    private static SessionFactory buildSessionFactory() {
        try {
            // 從 hibernate.cfg.xml 加載設定
            return new Configuration().configure().buildSessionFactory();
        } catch (Throwable ex) {
            System.err.println("初始化 SessionFactory 失敗：" + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public static void shutdown() {
        // 關閉 SessionFactory
        getSessionFactory().close();
    }
}