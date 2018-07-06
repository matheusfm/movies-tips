package io.github.matheusfm.moviestips.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@ApiModel
public class Genre {
    @ApiModelProperty(example = "16")
    private Integer id;
    @ApiModelProperty(example = "Animação")
    private String name;
}
