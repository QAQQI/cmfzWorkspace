package com.baizhi.dao;

import com.baizhi.entity.Chapter;

import java.util.List;
import java.util.Map;

public interface ChapterDAO {
    public List<Chapter> limit(Map<String,Object> map);
    public int count();
    public void save(Chapter chapter);
    public void updatePath(Map<String,Object> map);
}
