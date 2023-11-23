package arguewise.demo.model;

import arguewise.demo.dto.argument.CreateArgumentDTO;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@Entity
@Table(name = "arguments")
public class Argument {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "discussion_id", nullable = false)
    private Discussion discussion;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "author_id", nullable = false)
    private User author;

    @NotBlank(message = "Title is required")
    @Size(max = 100, message = "Title should be no longer than 100 characters")
    private String title;

    @OneToMany(mappedBy = "argument", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ArgumentDetail> argumentDetails;

    @CreationTimestamp
    private LocalDateTime creationTimestamp;

    public Argument(CreateArgumentDTO dto, User author, Discussion discussion) {
        this.discussion = discussion;
        this.author = author;
        this.title = dto.getTitle();
        this.argumentDetails = processArgumentDetails(dto.getDetails());
        this.argumentDetails.forEach(detail -> detail.setArgument(this));
    }

    public static List<ArgumentDetail> processArgumentDetails(List<String> argumentDetails) {
        List<ArgumentDetail> details = new ArrayList<>();

        for (int i = 0; i < argumentDetails.size(); i++) {
            ArgumentDetail detail = new ArgumentDetail();
            detail.setPosition(i + 1);
            detail.setText(argumentDetails.get(i));
            details.add(detail);
        }

        return details;
    }

    public void removeArgumentDetail(ArgumentDetail detail) {
        this.argumentDetails.remove(detail);
        detail.setArgument(null);
    }

    public void addArgumentDetail(ArgumentDetail detail) {
        this.argumentDetails.add(detail);
        detail.setArgument(this);
    }

    public void removeAllArgumentDetails() {
        this.argumentDetails.forEach(detail -> detail.setArgument(null));
        this.argumentDetails.clear();
    }

    public boolean isActive() {
        return this.discussion.isActive();
    }

}

