//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//
package com.dong.repository.impl;

import com.dong.pojo.Customer;
import com.dong.pojo.DetailReceipt;
import com.dong.pojo.Receipt;
import com.dong.pojo.RelativeParkCard;
import com.dong.pojo.Service;
import com.dong.pojo.UseService;
import com.dong.repository.RelativeParkCardRepository;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.stereotype.Repository;

@Repository
@Transactional
public class RelativeParkCardRepositoryImpl implements RelativeParkCardRepository {

    @Autowired
    private LocalSessionFactoryBean factory;

    public RelativeParkCardRepositoryImpl() {
    }

    public List<RelativeParkCard> getRelativeParkCard(Map<String, String> params) {
        Session session = this.factory.getObject().getCurrentSession();
        CriteriaBuilder b = session.getCriteriaBuilder();
        CriteriaQuery<RelativeParkCard> q = b.createQuery(RelativeParkCard.class);
        Root r = q.from(RelativeParkCard.class);

        int type = 0;
        String cusId = "";
        if (params.get("kw") != null
                && !params.get("kw").isEmpty()
                && params.get("type") != null
                && !params.get("type").isEmpty()) {
            type = Integer.parseInt(params.get("type"));
            cusId = params.get("cusId");
        }

        if (type == 3) {
            q.where(b.equal(r.get("customerId").get("id"), cusId));
        }

        q.select(r);
        q.orderBy(new Order[]{b.desc(r.get("id"))});
        Query query = session.createQuery(q);
        return query.getResultList();
    }

    public RelativeParkCard getRelativeParkCardById(int id) {
        Session s = this.factory.getObject().getCurrentSession();
        return s.get(RelativeParkCard.class, id);
    }

    public boolean deleteRelativeParkCard(int id) {
        Session session = ((SessionFactory) Objects.requireNonNull(this.factory.getObject())).getCurrentSession();
        RelativeParkCard c = this.getRelativeParkCardById(id);

        try {
            session.delete(c);
            return true;
        } catch (HibernateException var5) {
            var5.printStackTrace();
            return false;
        }
    }

    public RelativeParkCard addRelativeParkCard(RelativeParkCard r) {
        Session s = this.factory.getObject().getCurrentSession();
        s.save(r);
        return r;
    }

    @Override
    public boolean updateRelativeParkCard(Map<String, String> params) {
        try {
            Session session = this.factory.getObject().getCurrentSession();
            
            int i = 0;
            while (params.containsKey("parkCards[" + i + "].id")) {
                try {
                    // Lấy giá trị id từ params và kiểm tra null hoặc trống
                    String idStr = params.get("parkCards[" + i + "].id");
                    if (idStr == null || idStr.isEmpty()) {
                        i++;
                        continue;
                    }
                    int id = Integer.parseInt(idStr);
    
                    // Tìm RelativeParkCard theo ID
                    RelativeParkCard r = session.get(RelativeParkCard.class, id);
                    if (r == null) {
                        i++;
                        continue; // Nếu không tìm thấy record, bỏ qua và tiếp tục vòng lặp
                    }
    
                    // Lấy giá trị cost từ params và kiểm tra null hoặc trống
                    String costStr = params.get("parkCards[" + i + "].cost");
                    if (costStr != null && !costStr.isEmpty()) {
                        try {
                            BigDecimal cost = new BigDecimal(costStr);
                            r.setCost(cost);
                        } catch (NumberFormatException e) {
                            System.out.println("Invalid cost format: " + costStr);
                            i++;
                            continue; // Nếu format không đúng, bỏ qua và tiếp tục vòng lặp
                        }
                    }
                    r.setActive(Boolean.TRUE);
                    // Cập nhật record
                    session.update(r);
                } catch (Exception e) {
                    e.printStackTrace();
                    i++;
                    continue; // Nếu có lỗi, bỏ qua và tiếp tục vòng lặp
                }
    
                i++;
            }
            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }
    
//    public boolean updateRelativeParkCard(Map<String, String> params) {
//        try {
//            Session session = this.factory.getObject().getCurrentSession();
//            CriteriaBuilder b = session.getCriteriaBuilder();
//            CriteriaQuery<RelativeParkCard> q = b.createQuery(RelativeParkCard.class);
//
//            int i = 0;
//            while (params.containsKey("parkCards[" + i + "].id")) {
//                RelativeParkCard r = session.get(RelativeParkCard.class, Integer.parseInt(params.get("parkCards[" + i + "].id")));
//                BigDecimal cost = new BigDecimal(params.get("parkCards[" + i + "].cost"));
//                r.setCost(cost);
//                session.update(r);
//            }
//            return true;
//        } catch (Exception ex) {
//            ex.printStackTrace();
//            return false;
//        }
//    }
}
