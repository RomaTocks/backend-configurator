package com.tocks.backend.model.common.filters;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Filter
{
    @Id
    private String id;
    private String name;
    private String parameterName;
    private String type;
}
