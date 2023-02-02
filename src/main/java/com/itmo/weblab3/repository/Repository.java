package com.itmo.weblab3.repository;

import com.itmo.weblab3.model.BaseEntity;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

/**
 * @author Iskandarov Shakhzodbek P3133
 */
public interface Repository<Key extends Serializable,Entity extends BaseEntity<Key>> {

    Entity save(Entity entity);

    void delete (Key id);

    void update (Entity entity);

    Optional<Entity> findById(Key id);

    List<Entity> findAll();

}
