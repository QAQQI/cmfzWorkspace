package com.baizhi.service;

import com.baizhi.entity.Carousel;

import java.util.List;
import java.util.Map;

public interface CarouselService {
    public List<Carousel> queryAll();
    public int count();
    public List<Carousel> limit(Map<String,Integer> map);
    public String save(Carousel carousel);
    public void delete(String[] id);
    public String update(Carousel carousel);
    public void updatePath(Map<String,String> map);
}
