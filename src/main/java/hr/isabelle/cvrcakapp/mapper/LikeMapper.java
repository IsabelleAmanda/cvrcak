package hr.isabelle.cvrcakapp.mapper;

import org.springframework.jdbc.core.RowMapper;
import hr.isabelle.cvrcakapp.model.Like;

import java.sql.ResultSet;
import java.sql.SQLException;

public class LikeMapper implements RowMapper<Like> {
    @Override
    public Like mapRow(ResultSet rs, int rowNum) throws SQLException {
        Like like = new Like();
        like.setLikeId(rs.getInt("like_id"));
        like.setUserId(rs.getInt("user_id"));
        like.setPostId(rs.getInt("post_id"));
        like.setLikingDate(rs.getDate("liking_date"));
        like.setActivityId(rs.getInt("activity_id"));
        return like;
    }
}