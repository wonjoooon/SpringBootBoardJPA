package com.wonjun.repository;

import com.wonjun.model.entity.Bulletin;
import com.wonjun.model.entity.Reply;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReplyRepository extends JpaRepository<Reply, Integer> {
    List<Reply> findRepliesByBulletin(Bulletin bulletin);
}
