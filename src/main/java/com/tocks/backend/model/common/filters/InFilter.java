package com.tocks.backend.model.common.filters;

import lombok.*;

import java.util.List;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
@ToString(callSuper = true)
public class InFilter extends Filter
{
    private List<String> values;
}
