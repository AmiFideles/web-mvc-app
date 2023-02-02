package com.itmo.weblab3.service;

import com.itmo.weblab3.dto.HitDTO;
import com.itmo.weblab3.model.Hit;
import com.itmo.weblab3.repository.HitRepository;
import com.itmo.weblab3.utils.mapper.todto.HitMapper;
import com.itmo.weblab3.utils.mapper.toentity.HitDTOMapper;
import lombok.NoArgsConstructor;
import spring.annotations.Autowired;
import spring.annotations.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author Iskandarov Shakhzodbek P3133
 */
@Service
@NoArgsConstructor
public class HitService {
    @Autowired
    private  HitRepository hitRepository;

    @Transactional
    public void create(HitDTO hitDTO) throws Exception {
        Hit hit = HitDTOMapper.INSTANCE.toHit(hitDTO);
        hitRepository.save(hit);
    }

    @Transactional
    public Optional<HitDTO> findById(long id){
        return hitRepository
                .findById(id)
                .map(HitMapper.INSTANCE::toDTO);
    }

    @Transactional
    public List<HitDTO> findAll(){
        return hitRepository
                .findAll()
                .stream()
                .map(HitMapper.INSTANCE::toDTO)
                .collect(Collectors.toList());
    }


    @Transactional
    public boolean remove(long id){
        Optional<Hit> maybeHit = hitRepository.findById(id);
        maybeHit.ifPresent(hit -> hitRepository.delete(id));
        return maybeHit.isPresent();
    }

    @Transactional
    public void removeAll(){
        hitRepository.deleteAll();
    }

}
