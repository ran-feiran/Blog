package com.zhao.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class HomeDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer viewsCount;

    private long messageCount;

    private long userCount;

    private long articleCount;

}
