package com.graduate.be_txnd_fanzone.dto.media;

import com.graduate.be_txnd_fanzone.validator.NotBlank.NotBlankConstraint;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CreateMediaRequest {

    @NotBlankConstraint(name = "Link cloud")
    String linkCloud;

    @NotBlankConstraint(name = "Phân loại tệp")
    Byte type;
}
