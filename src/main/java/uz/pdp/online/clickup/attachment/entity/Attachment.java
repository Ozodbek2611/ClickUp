package uz.pdp.online.clickup.attachment.entity;

import jakarta.persistence.Entity;
import lombok.*;
import uz.pdp.online.clickup.common.persistence.BaseUuidEntity;

@EqualsAndHashCode(callSuper = true)
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Attachment extends BaseUuidEntity {

    private String name;

    private String originalName;

    private Long size;

    private String contentType;
}
