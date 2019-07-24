package com.baizhi.service;

import com.baizhi.Annotation.AopAnnocation;
import com.baizhi.dao.ChapterDAO;
import com.baizhi.entity.Chapter;
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
public class ChapterServiceImpl implements ChapterService{
    @Autowired
    ChapterDAO chapterDAO;

    @Override
    public void updatePath(Map<String, Object> map) {
        chapterDAO.updatePath(map);
    }

    @Transactional
    @Override
    public String save(Chapter chapter) {
        UUID uuid = UUID.randomUUID();
        String uu = uuid.toString();
        chapter.setId(uu);
        chapter.setUploadTime(new Date());
        chapterDAO.save(chapter);
        return uu;
    }

    @Override
    @AopAnnocation
    public List<Chapter> limit(Map<String, Object> map) {
        int pageNo = (int)map.get("pageNo");
        int pageSize = (int)map.get("pageSize");
        pageNo = (pageNo - 1) * pageSize;
        map.put("pageNo", pageNo);
        List<Chapter> limit = chapterDAO.limit(map);
        return limit;
    }

    @Override
    @AopAnnocation
    public int count() {
        return chapterDAO.count();
    }
}
