package com.wonjun.service;

import com.wonjun.model.entity.Bulletin;
import com.wonjun.repository.BulletinRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BulletinService {
    @Autowired
    BulletinRepository bulletinRepository;

    public Page<Bulletin> pageList(Pageable pageable){
        return bulletinRepository.findAll(pageable);
    }
    public List<Bulletin> listArticle() throws DataAccessException {
        List<Bulletin> bulletinList = bulletinRepository.findAll(Sort.by(Sort.Direction.DESC, "id"));
        return bulletinList;
    }

    public void addArticle(Bulletin bulletin)throws DataAccessException{
        bulletinRepository.save(bulletin);
    }

    public Bulletin viewArticle(int articleNo) throws DataAccessException{
        Optional<Bulletin> optionalBulletin = bulletinRepository.findById(articleNo);
        Bulletin bulletin = null;
        if(optionalBulletin.isPresent()) {
            bulletin = optionalBulletin.get();
        }
        return bulletin;
    }
    public void editArticle(Bulletin inBoard) throws DataAccessException {
        Optional<Bulletin> optionalBoard = bulletinRepository.findById(inBoard.getId());
        Bulletin board;
        if(optionalBoard.isPresent()){
            board = optionalBoard.get();
            board.setTitle(inBoard.getTitle());
            board.setContent(inBoard.getContent());
            bulletinRepository.save(board);
        }
    }

    public void removeArticle(int articleNo) throws DataAccessException {
        bulletinRepository.deleteById(articleNo);
    }

}
