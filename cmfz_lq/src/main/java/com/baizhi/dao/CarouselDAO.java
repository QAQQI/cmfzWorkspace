package com.baizhi.dao;

import com.baizhi.entity.Carousel;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface CarouselDAO {
    public List<Carousel> queryAll();
    public int count();
    public List<Carousel> limit(Map<String,Integer> map);
    public void save(Carousel carousel);
    public void delete(String[] ids);
    public void update(Carousel carousel);
    public void updatePath(Map<String,String> map);
}
