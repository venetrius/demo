package arguewise.demo.dto.space;

import lombok.Data;

@Data
public class SpaceStatisticsDTO {

    private long totalLikes;

    private long totalJoinedUsers;

    private long totalDiscussions;

    public SpaceStatisticsDTO(long totalLikes, long totalJoinedUsers, long totalDiscussions) {
        this.totalLikes = totalLikes;
        this.totalJoinedUsers = totalJoinedUsers;
        this.totalDiscussions = totalDiscussions;
    }
}

