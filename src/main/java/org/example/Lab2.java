package org.example;

import org.example.entity.Gruppyi;
import org.example.entity.Studentyi;
import org.example.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.Date;
import java.util.List;
import java.util.Set;

public class Lab2 {
    private static Session session;

    public Lab2(Session session) {
        this.session = session;
    }

/*Установить статус расформирована для групп сформированных более 4 лет назад,
 у студентов данных групп установить статус "выпускник"*/

    public static void lab2() {
        String sql = "from Gruppyi g";
        Query query = session.createQuery(sql);

        List<Gruppyi> groups = query.list();
        for (Gruppyi g : groups) {
            Date dt = new Date();
            if (dt.getYear() - g.getDataFormir().getYear() >= 4) {
                g.setStatus("DISBANDED");
                g.setStatusDate(dt);
                Set<Studentyi> studs = (Set<Studentyi>) g.getStudentyis();
                for (Studentyi s : studs) {
                    s.setStatus("GRADUATE");
                    s.setStatusDate(dt);
                    session.update(s);
                }
                session.update(g);
            }
        }
    }
}
