package com.tocks.backend.dto.favourite;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class FavouriteProductRequest
{
    private String section;
    private String id;
}
