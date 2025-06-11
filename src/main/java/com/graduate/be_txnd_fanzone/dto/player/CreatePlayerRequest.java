package com.graduate.be_txnd_fanzone.dto.player;

import com.graduate.be_txnd_fanzone.validator.DateOfBirth.DobConstraint;
import com.graduate.be_txnd_fanzone.validator.NotBlank.NotBlankConstraint;
import com.graduate.be_txnd_fanzone.validator.Size.SizeConstraint;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CreatePlayerRequest {

    @NotBlankConstraint(name = "ID CLB")
    Long clubId;

    @NotBlankConstraint(name = "Họ tên đệm")
    String firstName;

    @NotBlankConstraint(name = "Tên")
    String lastName;

    @NotBlankConstraint(name = "Tên in trên áo")
    String nameInShirt;

    @NotBlankConstraint( name = "Số áo")
    @SizeConstraint(min = 1, max = 99, name = "Số áo")
    Integer shirtNumber;

    @NotBlankConstraint(name = "Ngày sinh")
    @DobConstraint(min = 16)
    LocalDate dateOfBirth;

    @NotBlankConstraint(name = "Chiều cao")
    Float height;

    @NotBlankConstraint(name = "Cân nặng")
    @SizeConstraint(min = 50, max = 120, name = "Cân nặng")
    Integer weight;

    @NotBlankConstraint(name = "Quốc tịch")
    String nationality;

    @NotBlankConstraint(name = "Vị trí thi đấu")
    String position;

    Integer goal;

    Integer assist;

    @NotBlankConstraint(name = "Ảnh đại diện")
    String avatarImage;

    @NotBlankConstraint(name = "Ảnh toàn thân")
    String fullBodyImage;

    String description;

}
