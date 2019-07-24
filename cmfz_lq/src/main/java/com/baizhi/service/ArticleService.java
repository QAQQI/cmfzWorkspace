package com.baizhi.service;

import com.baizhi.entity.Article;

import java.util.List;
import java.util.Map;

public interface ArticleService {
    public List<Article> limit(Map<String,Integer> map);
    public int count();
    public void delete(String[] id);
    public void save(Article article);
}
