package com.baizhi.service;

import com.baizhi.entity.Chapter;

import java.util.List;
import java.util.Map;

public interface ChapterService {
    public List<Chapter> limit(Map<String,Object> map);
    public int count();
    public String save(Chapter chapter);
    public void updatePath(Map<String,Object> map);
}
