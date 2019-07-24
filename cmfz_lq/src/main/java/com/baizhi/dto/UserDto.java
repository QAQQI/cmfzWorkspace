package com.baizhi.dto;

import com.baizhi.entity.Carousel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;

@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
public class UserDto<T> implements Serializable {
    private int page;
    private int total;
    private int records;
    private List<T> rows;
}
