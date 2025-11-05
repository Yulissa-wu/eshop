


import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import com.example.Employee;

import main.resources.HibernateUtil;

public class Main {
    public static void main(String[] args) {
        // 取得 SessionFactory
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

        // 開啟 Session
        Session session = sessionFactory.openSession();

        // 開始事務
        Transaction transaction = session.beginTransaction();

        try {
            // 資料庫操作（例如新增資料）
            Employee employee = new Employee();
            employee.setName("John Doe");
            employee.setDepartment("IT");
            session.save(employee);

            // 提交事務
            transaction.commit();
            // 查詢資料
            Employee emp = session.get(Employee.class, 1);
            System.out.println("Employee Name: " + emp.getName());

            // 更新資料
            transaction = session.beginTransaction();
            emp.setDepartment("HR");
            session.update(emp);
            transaction.commit();

            // 刪除資料
            transaction = session.beginTransaction();
            session.delete(emp);
            transaction.commit();
        } catch (Exception e) {
            // 發生錯誤時回滾事務
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            // 關閉 Session
            session.close();
        }

        // 關閉 SessionFactory
        HibernateUtil.shutdown();
    }
}

