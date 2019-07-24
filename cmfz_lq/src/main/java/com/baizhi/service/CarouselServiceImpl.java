package com.baizhi.service;

import com.baizhi.Annotation.AopAnnocation;
import com.baizhi.dao.CarouselDAO;
import com.baizhi.entity.Carousel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class CarouselServiceImpl implements CarouselService {
    @Autowired
    CarouselDAO carouselDAO;


    @Override
    public void updatePath(Map<String, String> map) {
        carouselDAO.updatePath(map);
    }

    @Override
    @Transactional
    public String save(Carousel carousel) {
        UUID uuid = UUID.randomUUID();
        String uu = uuid.toString();
        carousel.setId(uu);
        carouselDAO.save(carousel);
        return uu;
    }

    @Transactional
    @Override
    public void delete(String[] id) {
        carouselDAO.delete(id);
    }

    @Transactional
    @Override
    public String update(Carousel carousel) {
        carouselDAO.update(carousel);
        return carousel.getId();
    }

    @Override
    @AopAnnocation
    public int count() {
        return carouselDAO.count();
    }

    @Override
    @AopAnnocation
    public List<Carousel> limit(Map<String, Integer> map) {
        int pageNo = map.get("pageNo");
        int pageSize = map.get("pageSize");
        pageNo = (pageNo - 1) * pageSize;
        map.put("pageNo", pageNo);
        List<Carousel> limit = carouselDAO.limit(map);
        return limit;
    }

    @Override
    @AopAnnocation
    public List<Carousel> queryAll() {
        List<Carousel> carousels = carouselDAO.queryAll();
        return carousels;
    }
}
