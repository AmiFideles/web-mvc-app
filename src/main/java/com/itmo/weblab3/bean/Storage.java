package com.itmo.weblab3.bean;

import com.itmo.weblab3.model.Hit;
import com.itmo.weblab3.service.HitService;
import com.itmo.weblab3.utils.mapper.todto.HitMapper;
import com.itmo.weblab3.utils.mapper.toentity.HitDTOMapper;
import spring.injector.CustomInjector;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

@ManagedBean
@ApplicationScoped
public class Storage {
    private final CopyOnWriteArrayList<Hit> hits;
    private  HitService hitService;

    public Storage() {
        this.hitService = CustomInjector.getInstance().getService(HitService.class);
        List<Hit> collect = hitService.findAll().stream().map(HitDTOMapper.INSTANCE::toHit).collect(Collectors.toList());
        hits =  new CopyOnWriteArrayList<>(collect);
    }

    public void addHit(Hit hit){
        try {
            hitService.create(HitMapper.INSTANCE.toDTO(hit));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        hits.add(hit);
    }

    public void clear(){
        hitService.removeAll();
        hits.clear();
    }

    public CopyOnWriteArrayList<Hit> getHits() {
        return hits;
    }
}
