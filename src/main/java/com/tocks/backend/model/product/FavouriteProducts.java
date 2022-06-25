package com.tocks.backend.model.product;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FavouriteProducts
{
    private List<String> cpu;
    private List<String> motherboard;
    private List<String> fan;
    private List<String> hdd;
    private List<String> psu;
    private List<String> ram;
    private List<String> ssd;
    private List<String> graphicCard;
    private List<String> chassis;
}
