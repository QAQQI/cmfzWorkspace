package com.baizhi.dao;

import com.baizhi.entity.Album;

import java.util.List;
import java.util.Map;

public interface AlbumDAO {
    public List<Album> limit(Map<String,Integer> map);
    public int count();
    public void save(Album album);
    public void delete(String[] ids);
    public void update(Album album);
    public void updatePath(Map<String,String> map);
}
