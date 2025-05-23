package com.boram.look.domain.outfit;

import com.boram.look.api.dto.FileDto;
import com.boram.look.api.dto.outfit.OutfitImageDto;
import com.boram.look.domain.s3.FileMetadata;
import jakarta.persistence.*;
import lombok.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@ToString
@Table(name = "outfit_image")
public class OutfitImage {
    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    private Outfit outfit;
    private String title;
    private String description;

    @ManyToOne
    private FileMetadata fileMetadata;

    public OutfitImageDto toDto(FileDto fileDto) {
        return OutfitImageDto.builder()
                .id(this.id)
                .title(this.title)
                .description(this.description)
                .metadata(fileDto)
                .build();
    }
}