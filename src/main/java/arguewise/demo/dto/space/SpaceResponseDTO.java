package arguewise.demo.dto.space;

import arguewise.demo.model.Discussion;
import arguewise.demo.model.Space;
import arguewise.demo.model.UserSpace;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class SpaceResponseDTO {

    private Long id;

    private List<Discussion> discussions = new ArrayList<>();

    private String name;

    private String description;

    private SpaceStatisticsDTO spaceStatistics;

    @JsonProperty("isJoined")
    private boolean isJoined;

    private Timestamp joinedAt;

    public SpaceResponseDTO(UserSpace userSpace, SpaceStatisticsDTO spaceStatisticsDTO) {
        Space space = userSpace.getSpace();
        this.id = space.getId();
        this.name = space.getName();
        this.description = space.getDescription();
        this.isJoined = true;
        this.joinedAt = userSpace.getJoinedAt();
        this.spaceStatistics = spaceStatisticsDTO;
    }

    public SpaceResponseDTO(UserSpace userSpace) {
        Space space = userSpace.getSpace();
        this.id = space.getId();
        this.name = space.getName();
        this.description = space.getDescription();
        this.isJoined = true;
        this.joinedAt = userSpace.getJoinedAt();
    }

    public SpaceResponseDTO(Space space, SpaceStatisticsDTO spaceStatisticsDTO) {
        this.id = space.getId();
        this.name = space.getName();
        this.description = space.getDescription();
        this.spaceStatistics = spaceStatisticsDTO;
    }
}
