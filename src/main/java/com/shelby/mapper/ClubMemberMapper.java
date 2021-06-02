package com.shelby.mapper;

import com.shelby.entity.ClubMember;
import com.shelby.entity.Student;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

/**
 * @Author Shelby Li
 * @Date 2021/6/2 19:03
 * @Version 1.0
 */

@Mapper
public interface ClubMemberMapper {
    @Insert({"INSERT INTO club_member (club_id, student_id, position) VALUES(#{clubId}, #{studentId}, #{position})"})
    public int add(ClubMember clubMember);
}
