package com.wonjun.service;

import com.wonjun.model.entity.Bulletin;
import com.wonjun.model.entity.Reply;
import com.wonjun.repository.ReplyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ReplyService {
    private final ReplyRepository replyRepository;

    @Autowired
    public ReplyService(ReplyRepository replyRepository){
        this.replyRepository = replyRepository;
    }

    public void addReply(Reply reply) {
        replyRepository.save(reply);
    }

    public List<Reply> getReplyList(Bulletin bulletin) throws DataAccessException{
        return replyRepository.findRepliesByBulletin(bulletin);
    }

    public void editReply(String content, int id) throws DataAccessException {
        Optional<Reply> reply = replyRepository.findById(id);
        Reply editReply;
        if(reply.isPresent()){
            editReply = reply.get();
            editReply.setContent(content);
            replyRepository.save(editReply);
        }
    }

    public void removeReply(int id) throws DataAccessException {
        replyRepository.deleteById(id);
    }

}
