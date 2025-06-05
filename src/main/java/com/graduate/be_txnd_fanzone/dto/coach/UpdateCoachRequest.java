package com.graduate.be_txnd_fanzone.dto.coach;

import com.graduate.be_txnd_fanzone.validator.DateOfBirth.DobConstraint;
import com.graduate.be_txnd_fanzone.validator.NotBlank.NotBlankConstraint;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UpdateCoachRequest {

    @NotBlankConstraint(name = "Mã HLV")
    Long coachId;

    @NotBlankConstraint(name = "Họ tên đệm HLV")
    String firstName;

    @NotBlankConstraint(name = "Tên HLV")
    String lastName;

    @NotBlankConstraint(name = "Ngày sinh")
    @DobConstraint(min = 18)
    LocalDate dateOfBirth;

    @NotBlankConstraint(name = "Quốc tịch")
    String nationality;

    @NotBlankConstraint(name = "Địa chỉ")
    String address;

    String description;

    @NotBlankConstraint(name = "Ảnh HLV")
    String image;

    @NotBlankConstraint(name = "Vị trí huấn luyện")
    String position;
}

