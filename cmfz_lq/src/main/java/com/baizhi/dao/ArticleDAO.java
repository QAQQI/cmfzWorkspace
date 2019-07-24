package com.baizhi.dao;

import com.baizhi.entity.Article;

import java.util.List;
import java.util.Map;

public interface ArticleDAO {
    public List<Article> limit(Map<String,Integer> map);
    public int count();
    public void delete(String[] ids);
    public void save(Article article);
}
