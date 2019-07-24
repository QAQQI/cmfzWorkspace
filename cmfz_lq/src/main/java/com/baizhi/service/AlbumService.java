package com.baizhi.service;

import com.baizhi.entity.Album;
import com.baizhi.entity.Carousel;

import java.util.List;
import java.util.Map;

public interface AlbumService {
    public List<com.baizhi.entity.Album> limit(Map<String,Integer> map);
    public int count();
    public String save(Album album);
    public void delete(String[] id);
    public String update(Album album);
    public void updatePath(Map<String,String> map);
}
