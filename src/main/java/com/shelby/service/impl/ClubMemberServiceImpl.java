package com.shelby.service.impl;

import com.shelby.entity.ClubMember;
import com.shelby.mapper.ClubMemberMapper;
import com.shelby.service.ClubMemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author Shelby Li
 * @Date 2021/6/2 19:02
 * @Version 1.0
 */
@Service
public class ClubMemberServiceImpl implements ClubMemberService {
    @Autowired
    private ClubMemberMapper clubMemberMapper;
    @Override
    public int add(ClubMember clubMember) {
        return clubMemberMapper.add(clubMember);
    }
}
