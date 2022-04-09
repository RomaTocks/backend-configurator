package com.tocks.backend.response.exception;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor

public class Errors
{
    private Integer count;
    private List<ConfigurationException> cpu;
    private List<ConfigurationException> gpu;
    private List<ConfigurationException> chassis;
    private List<ConfigurationException> ram;
    private List<ConfigurationException> hdd;
    private List<ConfigurationException> ssd;
    private List<ConfigurationException> fan;
    private List<ConfigurationException> motherboard;
    private List<ConfigurationException> psu;

    public Errors()
    {
        count = 0;
        gpu = new ArrayList<>();
        cpu = new ArrayList<>();
        chassis = new ArrayList<>();
        ram = new ArrayList<>();
        hdd = new ArrayList<>();
        ssd = new ArrayList<>();
        fan = new ArrayList<>();
        motherboard = new ArrayList<>();
        psu = new ArrayList<>();
    }
    public void addGpuException(ConfigurationException exception) {
        gpu.add(exception);
        count++;
    }
    public void addCpuException(ConfigurationException exception) {
        cpu.add(exception);
        count++;
    }
    public void addChassisException(ConfigurationException exception) {
        chassis.add(exception);
        count++;
    }
    public void addRamException(ConfigurationException exception) {
        ram.add(exception);
        count++;
    }
    public void addHddException(ConfigurationException exception) {
        hdd.add(exception);
        count++;
    }
    public void addSsdException(ConfigurationException exception) {
        ssd.add(exception);
        count++;
    }
    public void addFanException(ConfigurationException exception) {
        fan.add(exception);
        count++;
    }
    public void addMotherboardException(ConfigurationException exception) {
        motherboard.add(exception);
        count++;
    }
    public void addPsuException(ConfigurationException exception) {
        psu.add(exception);
        count++;
    }
}
