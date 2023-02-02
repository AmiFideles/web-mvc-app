package com.itmo.weblab3.repository;

import com.itmo.weblab3.model.Hit;
import org.hibernate.Session;
import spring.annotations.Repository;

/**
 * @author Iskandarov Shakhzodbek P3133
 */
@Repository
public class HitRepository extends RepositoryBase<Long, Hit>{
    public HitRepository(Session session) {
        super(Hit.class, session);
    }
    
    public void deleteAll(){
        getSession().createQuery("DELETE FROM Hit ").executeUpdate();
    }

}
