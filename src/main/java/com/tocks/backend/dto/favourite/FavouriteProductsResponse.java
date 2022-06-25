package com.tocks.backend.dto.favourite;

import com.tocks.backend.model.product.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FavouriteProductsResponse
{
    private List<CPU> cpu;
    private List<Motherboard> motherboard;
    private List<Fan> fan;
    private List<HDD> hdd;
    private List<PSU> psu;
    private List<Ram> ram;
    private List<SSD> ssd;
    private List<GraphicCard> graphicCard;
    private List<Chassis> chassis;
}
