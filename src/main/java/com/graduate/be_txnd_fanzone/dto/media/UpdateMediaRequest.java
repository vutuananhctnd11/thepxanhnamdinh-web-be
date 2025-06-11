package com.graduate.be_txnd_fanzone.dto.media;

import com.graduate.be_txnd_fanzone.validator.NotBlank.NotBlankConstraint;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UpdateMediaRequest {

    @NotBlankConstraint(name = "ID tệp phương tiện")
    Long mediaId;

    @NotBlankConstraint(name = "Link cLoud")
    String linkCloud;

    @NotBlankConstraint(name = "Phân loại tệp phương tiện")
    Byte type;
}
