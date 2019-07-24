package com.baizhi.service;

import com.baizhi.dao.ArticleDAO;
import com.baizhi.entity.Article;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
@Transactional(propagation = Propagation.SUPPORTS,readOnly = true)
public class ArticleServiceImpl implements ArticleService {
    @Autowired
    ArticleDAO articleDAO;
    @Override
    public List<Article> limit(Map<String, Integer> map) {
        int pageNo = map.get("pageNo");
        int pageSize = map.get("pageSize");
        pageNo = (pageNo - 1) * pageSize;
        map.put("pageNo", pageNo);
        List<Article> limit = articleDAO.limit(map);
        return limit;
    }

    @Override
    public void save(Article article) {
        UUID uuid = UUID.randomUUID();
        String uu = uuid.toString();
        article.setId(uu);
        article.setPublishTime(new Date());
        articleDAO.save(article);
    }

    @Override
    public void delete(String[] id) {
        articleDAO.delete(id);
    }

    @Override
    public int count() {
        return articleDAO.count();
    }
}
