package com.baizhi.service;

import com.baizhi.dao.AlbumDAO;
import com.baizhi.entity.Album;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
@Transactional(propagation = Propagation.SUPPORTS,readOnly = true)
public class AlbumServiceImpl implements AlbumService {

    @Autowired
    AlbumDAO albumDAO;

    @Override
    public int count() {
        int count = albumDAO.count();
        return count;
    }
    @Transactional
    @Override
    public String save(Album album) {
        UUID uuid = UUID.randomUUID();
        String uu = uuid.toString();
        album.setId(uu);
        albumDAO.save(album);
        return uu;
    }
    @Transactional
    @Override
    public void delete(String[] id) {
        albumDAO.delete(id);
    }
    @Transactional
    @Override
    public String update(Album album) {
        albumDAO.update(album);
        return album.getId();
    }
    @Transactional
    @Override
    public void updatePath(Map<String, String> map) {
        albumDAO.updatePath(map);
    }

    @Override
    public List<Album> limit(Map<String, Integer> map) {
        int pageNo = map.get("pageNo");
        int pageSize = map.get("pageSize");
        pageNo = (pageNo - 1) * pageSize;
        map.put("pageNo", pageNo);
        List<Album> limit = albumDAO.limit(map);
        return limit;
    }
}
