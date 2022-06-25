package com.tocks.backend.service.implementation;

import com.tocks.backend.dto.configuration.CheckedConfigurationResponse;
import com.tocks.backend.dto.configuration.PreparedConfigurationResponse;
import com.tocks.backend.dto.configuration.UserConfigurationsRequest;
import com.tocks.backend.dto.configuration.UserConfigurationsResponse;
import com.tocks.backend.dto.favourite.FavouriteProductsResponse;
import com.tocks.backend.model.configuration.Configuration;
import com.tocks.backend.model.configuration.PreparedConfiguration;
import com.tocks.backend.model.product.*;
import com.tocks.backend.model.product.additional.*;
import com.tocks.backend.model.product.additional.values.*;
import com.tocks.backend.repository.common.ChassisRepository;
import com.tocks.backend.repository.common.GraphicCardRepository;
import com.tocks.backend.response.exception.ConfigurationException;
import com.tocks.backend.response.exception.Errors;
import com.tocks.backend.service.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class ConfigurationServiceImpl
{
    private final CpuService cpuService;
    private final GraphicCardService graphicCardService;
    private final ChassisService chassisService;
    private final RamService ramService;
    private final FanService fanService;
    private final HDDService hddService;
    private final MotherboardService motherboardService;
    private final PSUService psuService;
    private final SSDService ssdService;

    public ConfigurationServiceImpl(CpuService cpuService, GraphicCardService graphicCardService, ChassisService chassisService, ChassisRepository chassisRepository, GraphicCardRepository graphicCardRepository, RamService ramService, FanService fanService, HDDService hddService, MotherboardService motherboardService, PSUService psuService, SSDService ssdService)
    {
        this.cpuService = cpuService;
        this.graphicCardService = graphicCardService;
        this.chassisService = chassisService;
        this.ramService = ramService;
        this.fanService = fanService;
        this.hddService = hddService;
        this.motherboardService = motherboardService;
        this.psuService = psuService;
        this.ssdService = ssdService;
    }

    public FavouriteProductsResponse findAllFavouriteProducts(FavouriteProducts favouriteProducts) {
        FavouriteProductsResponse response = new FavouriteProductsResponse();
        if(!favouriteProducts.getFan().isEmpty()) {
            List<Fan> fans = fanService.findAllById(favouriteProducts.getFan());
            response.setFan(fans);
        }
        if(!favouriteProducts.getCpu().isEmpty()) {
            List<CPU> cpus = cpuService.findAllById(favouriteProducts.getCpu());

            response.setCpu(cpus);
        }
        if(!favouriteProducts.getChassis().isEmpty()) {
            List<Chassis> chassis = chassisService.findAllById(favouriteProducts.getChassis());
            response.setChassis(chassis);
        }
        if(!favouriteProducts.getMotherboard().isEmpty()) {
            List<Motherboard> motherboards = motherboardService.findAllById(favouriteProducts.getMotherboard());
            response.setMotherboard(motherboards);
        }
        if(!favouriteProducts.getPsu().isEmpty()) {
            List<PSU> psus = psuService.findAllById(favouriteProducts.getPsu());
            response.setPsu(psus);
        }
        if(!favouriteProducts.getRam().isEmpty()) {
            List<Ram> rams = ramService.findAllById(favouriteProducts.getRam());
            response.setRam(rams);
        }
        if(!favouriteProducts.getGraphicCard().isEmpty()) {
            List<GraphicCard> graphicCards = graphicCardService.findAllById(favouriteProducts.getGraphicCard());
            response.setGraphicCard(graphicCards);
        }
        return response;
    }
    public void findConfigurationElements(Configuration configuration) {
        if(configuration.getGraphicCard() != null) {
            GraphicCard graphicCard = configuration.getGraphicCard();
            graphicCard = graphicCardService.findById(graphicCard.getId());
            configuration.setGraphicCard(graphicCard);
        }
        if(configuration.getCpu() != null) {
            CPU cpu = configuration.getCpu();
            cpu = cpuService.findById(cpu.getId());
            configuration.setCpu(cpu);
        }
       if(configuration.getMotherboard() != null) {
           Motherboard motherboard = configuration.getMotherboard();
           motherboard = motherboardService.findById(motherboard.getId());
           configuration.setMotherboard(motherboard);
       }
       if(configuration.getHdd() != null) {
           HDD hdd = configuration.getHdd();
           hdd = hddService.findById(hdd.getId());
           configuration.setHdd(hdd);
       }
       if(configuration.getSsd() != null) {
           SSD ssd = configuration.getSsd();
           ssd = ssdService.findById(ssd.getId());
           configuration.setSsd(ssd);
       }
       if(configuration.getRam() != null) {
           Ram ram = configuration.getRam();
           ram = ramService.findById(ram.getId());
           configuration.setRam(ram);
       }
       if(configuration.getFan() != null) {
           Fan fan = configuration.getFan();
           fan = fanService.findById(fan.getId());
           configuration.setFan(fan);
       }
       if(configuration.getPsu() != null) {
           PSU psu = configuration.getPsu();
           psu = psuService.findById(psu.getId());
           configuration.setPsu(psu);
       }
       if(configuration.getChassis() != null) {
           Chassis chassis = configuration.getChassis();
           chassis = chassisService.findById(chassis.getId());
           configuration.setChassis(chassis);
       }
    }
    public ResponseEntity<UserConfigurationsResponse> getUserConfigurations(UserConfigurationsRequest userConfigurationsRequest) {
        UserConfigurationsResponse userConfigurationsResponse = new UserConfigurationsResponse();
        List<PreparedConfiguration> preparedConfigurations = userConfigurationsRequest.getConfigurations();
        System.out.println(preparedConfigurations);
        List<PreparedConfigurationResponse> preparedConfigurationResponses = preparedConfigurations.stream().map(preparedConfiguration -> {
            CheckedConfigurationResponse checkedConfigurationResponse = checkConfiguration(preparedConfiguration.getConfiguration()).getBody();
            if(checkedConfigurationResponse != null) {
                PreparedConfigurationResponse newPreparedConfiguration = new PreparedConfigurationResponse();
                newPreparedConfiguration.setId(preparedConfiguration.getId());
                newPreparedConfiguration.setName(preparedConfiguration.getName());
                newPreparedConfiguration.setError(checkedConfigurationResponse.getError());
                newPreparedConfiguration.setConfiguration(checkedConfigurationResponse.getConfiguration());
                newPreparedConfiguration.setPositions(preparedConfiguration.getPositions());
                return newPreparedConfiguration;
            }
            return null;
        }).toList();
        preparedConfigurations.removeIf(Objects::isNull);
        userConfigurationsResponse.setConfigurations(preparedConfigurationResponses);
        return new ResponseEntity<>(userConfigurationsResponse, HttpStatus.OK);
    }
    public ResponseEntity<CheckedConfigurationResponse> checkConfiguration(Configuration configuration) {
        CheckedConfigurationResponse checkedConfigurationResponse = new CheckedConfigurationResponse();
        Errors errors = new Errors();
        findConfigurationElements(configuration);
        System.out.println(configuration);
        checkChassis(configuration, errors);
        checkMotherboard(configuration, errors);
        checkCPU(configuration, errors);
        checkPSU(configuration, errors);
        checkedConfigurationResponse.setError(errors);
        checkedConfigurationResponse.setConfiguration(configuration);
        return new ResponseEntity<>(checkedConfigurationResponse, HttpStatus.ACCEPTED);
    }
    public void checkChassis(Configuration configuration, Errors errors) {
        if(configuration.getChassis() != null) {
            Chassis chassis = configuration.getChassis();
            ChassisAdditionalValues values = chassis.getValues();
            ChassisAdditionalInformation additional = chassis.getAdditional();
            if(values != null && additional != null) {
                if(configuration.getGraphicCard() != null) {
                    GraphicCard graphicCard = configuration.getGraphicCard();
                    GraphicCardAdditionalValues graphicCardValues = configuration.getGraphicCard().getValues();
                    if(graphicCardValues != null) {
                        if(values.getMaxGPULength() != null && graphicCardValues.getLength() != null) {
                            if(graphicCardValues.getLength() > values.getMaxGPULength()) {
                                ConfigurationException gpuLength = new ConfigurationException("error", chassis.getName(), graphicCard.getName(), "Длинна видеокарты больше максимально допустимой корпусом.");
                                errors.addGpuException(gpuLength);
                            }
                        }
                        else {
                            String message = "";
                            String name = "";
                            if(values.getMaxGPULength() == null) {
                                message = "Недостаточно данных корпуса для анализа длинны.";
                                name = chassis.getName();
                                ConfigurationException gpuLengthNull = new ConfigurationException("warning", name, message);
                                errors.addChassisException(gpuLengthNull);
                            }
                            else {
                                if(graphicCardValues.getLength() == null) {
                                    message = "Недостаточно данных видеокарты для анализа длинны.";
                                    name = graphicCard.getName();
                                    ConfigurationException gpuLengthNull = new ConfigurationException("warning", name, message);
                                    errors.addGpuException(gpuLengthNull);
                                }
                            }
                        }
                    }
                    else {
                        ConfigurationException gpuNull = new ConfigurationException("warning", chassis.getName(), graphicCard.getName(), "Недостаточно данных для анализа видеокарты.");
                        errors.addGpuException(gpuNull);
                    }
                }
                if(configuration.getPsu() != null) {
                    PSU psu = configuration.getPsu();
                    PSUAdditionalValues psuValues = configuration.getPsu().getValues();
                    if(psuValues != null) {
                        if(values.getMaxPSULength() != null && psuValues.getWidth() != null) {
                            if(values.getMaxPSULength() < psuValues.getWidth()) {
                                ConfigurationException psuLength = new ConfigurationException("error", chassis.getName(), psu.getName(), "Длинна блока питания больше допустимой корпусом.");
                                errors.addPsuException(psuLength);
                            }
                        }
                        else {
                            String message = "";
                            String name = "";
                            if(values.getMaxPSULength() == null) {
                                message = "Недостаточно данных корпуса для анализа длинны БП.";
                                name = chassis.getName();
                                ConfigurationException psuLengthNull = new ConfigurationException("warning", name, message);
                                errors.addChassisException(psuLengthNull);
                            }
                            else {
                                if(psuValues.getWidth() == null) {
                                    message = "Недостаточно данных БП для анализа длинны.";
                                    name = psu.getName();
                                    ConfigurationException psuLengthNull = new ConfigurationException("warning", name, message);
                                    errors.addPsuException(psuLengthNull);
                                }
                            }
                        }
                    }
                    else {
                        ConfigurationException psuInfo = new ConfigurationException("warning", psu.getName(), "Недостаточно данных для анализа БП");
                        errors.addPsuException(psuInfo);
                    }
                }
                if(configuration.getFan() != null) {
                    Fan fan = configuration.getFan();
                    FanAdditionalValues fanValues = fan.getValues();
                    if(fanValues != null) {
                        if(values.getMaxCPUCoolingSystemHeight() != null && fanValues.getHeight() != null) {
                            if(values.getMaxCPUCoolingSystemHeight() < fanValues.getHeight()) {
                                ConfigurationException fanHeight = new ConfigurationException("error", chassis.getName(), fan.getName(), "Высота кулера больше допустимой корпусом.");
                                errors.addFanException(fanHeight);
                            }
                        }
                        else {
                            String message = "";
                            String name = "";
                            if(values.getMaxCPUCoolingSystemHeight() == null) {
                                message = "Недостаточно данных корпуса для анализа высоты.";
                                name = chassis.getName();
                                ConfigurationException fanHeightNull = new ConfigurationException("warning", name, message);
                                errors.addChassisException(fanHeightNull);
                            }
                            else {
                                if(fanValues.getHeight() == null) {
                                    message = "Недостаточно данных кулера для анализа высоты.";
                                    name = fan.getName();
                                    ConfigurationException fanHeightNull = new ConfigurationException("warning", name, message);
                                    errors.addFanException(fanHeightNull);
                                }
                            }
                        }
                    }
                    else {
                        ConfigurationException fanNull = new ConfigurationException("warning", fan.getName(), "Недостаточно данных для анализа кулера.");
                        errors.addFanException(fanNull);
                    }
                }
                if(configuration.getMotherboard() != null) {
                    Motherboard motherboard = configuration.getMotherboard();
                    MotherboardAdditionalInformation motherboardAdditional = motherboard.getAdditional();
                    if(motherboardAdditional.getFormFactor() != null && additional.getMotherboardsCompatibleSizes() != null) {
                        if(!additional.getMotherboardsCompatibleSizes().contains(motherboardAdditional.getFormFactor())) {
                            ConfigurationException formFactor = new ConfigurationException("error", chassis.getName(), motherboard.getName(),"Форм-фактор материнской платы не подходит к корпусу.");
                            errors.addMotherboardException(formFactor);
                        }
                    }
                    else {
                        String message = "";
                        String name = "";
                        if(additional.getMotherboardsCompatibleSizes() == null) {
                            message = "Недостаточно данных корпуса для анализа форм-фактора.";
                            name = chassis.getName();
                            ConfigurationException formFactorNull = new ConfigurationException("warning", name, message);
                            errors.addChassisException(formFactorNull);
                        }
                        else {
                            if(motherboardAdditional.getFormFactor() == null) {
                                message = "Недостаточно данных материнской платы для анализа форм-фактора.";
                                name = motherboard.getName();
                                ConfigurationException formFactorNull = new ConfigurationException("warning", name, message);
                                errors.addMotherboardException(formFactorNull);
                            }
                        }
                    }
                }
            }
            else {
                ConfigurationException chassisInfo = new ConfigurationException("warning", chassis.getName(), "Недостаточно данных для анализа корпуса.");
                errors.addChassisException(chassisInfo);
            }
        }
    }
    public void checkCPU(Configuration configuration, Errors errors) {
        if(configuration.getCpu() != null) {
            CPU cpu = configuration.getCpu();
            CPUAdditionalValues values = cpu.getValues();
            CPUAdditionalInformation additional = cpu.getAdditional();
            if(values != null && additional != null) {
                if (configuration.getRam() != null)
                {
                    Ram ram = configuration.getRam();
                    RamAdditionalValues ramValues = ram.getValues();
                    RamAdditionalInformation ramAdditional = ram.getAdditional();
                    if(ramValues != null) {
                        if(!ramValues.getFrequency().equals(values.getRamFrequency())) {
                            if(ramValues.getFrequency() < values.getRamFrequency()) {
                                ConfigurationException freqInfo = new ConfigurationException("info", ram.getName(), "Процессор поддерживает большую частоту памяти.");
                                errors.addRamException(freqInfo);
                            }
                            else {
                                ConfigurationException freqWarning = new ConfigurationException("warning", cpu.getName(), ram.getName(), "Процессор поддерживает частоту до " + additional.getRamFrequency() + ".");
                                errors.addRamException(freqWarning);
                            }
                        }
                    }
                    if(ramAdditional != null) {
                        String DDR = ramAdditional.getType();
                        System.out.println("---------------------"+ DDR+"-------------");
                        String ddrWithoutDIMM = DDR.replace("DIMM", "").trim();
                        System.out.println("---------------------"+ ddrWithoutDIMM+"-------------");;
                        boolean ddrCompatible = additional.getCpuRam().contains(ddrWithoutDIMM) || ramAdditional.getType().contains(additional.getCpuRam());
                        System.out.println(ddrCompatible);
                        if(!ddrCompatible) {
                            ConfigurationException ramType = new ConfigurationException("error", cpu.getName(), ram.getName(), "Тип памяти " + DDR + " не поддерживается процессором.");
                            errors.addRamException(ramType);
                        }
                    }
                }
                if(configuration.getFan() != null) {
                    Fan fan = configuration.getFan();
                    FanAdditionalValues fanValues = fan.getValues();
                    FanAdditionalInformation fanAdditional = fan.getAdditional();
                    if(fanValues != null) {
                        if(fanValues.getDispelPower() < values.getTdp()) {
                            ConfigurationException tdp = new ConfigurationException("error", cpu.getName(), fan.getName(), "Кулер для процессора " + cpu.getName() + " должен отводить более " + additional.getTdp() + " тепла.");
                            errors.addFanException(tdp);
                        }
                        else {
                            if((values.getTdp() - fanValues.getDispelPower()) < 30) {
                                ConfigurationException tdp = new ConfigurationException("warning", cpu.getName(), fan.getName(), "Желательно подобрать более производительный кулер для стабильной работы процессора в нагрузке.");
                                errors.addFanException(tdp);
                            }
                        }
                    }
                }
            }
        }
    }
    public void checkPSU(Configuration configuration, Errors errors) {
        if(configuration.getPsu() != null) {
            PSU psu = configuration.getPsu();
            PSUAdditionalValues values = psu.getValues();
            PSUAdditionalInformation additional = psu.getAdditional();
            Integer totalTDP = 0;
            if(values != null && additional != null) {
                if(configuration.getCpu() != null) {
                    CPU cpu = configuration.getCpu();
                    CPUAdditionalValues cpuValues = cpu.getValues();
                    if (cpuValues != null) {
                        totalTDP += cpuValues.getTdp();
                    }
                }
                if(configuration.getGraphicCard() != null) {
                    GraphicCard graphicCard = configuration.getGraphicCard();
                    GraphicCardAdditionalValues graphicCardValues = graphicCard.getValues();
                    if(graphicCardValues != null) {
                        totalTDP += graphicCardValues.getTdp();
                    }
                }
                if(totalTDP > values.getPower()) {
                    ConfigurationException power = new ConfigurationException("error", psu.getName(), "Блок питания должен производить более " + totalTDP + " Вт.");
                    errors.addPsuException(power);
                }
            }
        }
    }
    public void checkMotherboard(Configuration configuration, Errors errors) {
        if(configuration.getMotherboard() != null) {
            Motherboard motherboard = configuration.getMotherboard();
            MotherboardAdditionalValues values = motherboard.getValues();
            MotherboardAdditionalInformation additional = motherboard.getAdditional();
            if(values != null && additional != null) {
                if(configuration.getRam() != null) {
                    Ram ram = configuration.getRam();
                    RamAdditionalValues ramValues = ram.getValues();
                    RamAdditionalInformation ramAdditional = ram.getAdditional();
                    if(ramValues != null) {

                    }
                    else {

                    }
                    if(ramAdditional != null) {
                        String DDR = ramAdditional.getType();
                        String ddrWithoutDIMM = DDR.replace("DIMM", "");
                        boolean ddrCompatible = additional.getMemoryType().contains(ddrWithoutDIMM) || ramAdditional.getType().contains(additional.getMemoryType());
                        if(!ddrCompatible) {
                            ConfigurationException ramType = new ConfigurationException("error", motherboard.getName(), ram.getName(), "Тип памяти " + DDR + " не поддерживается материнской платой.");
                            errors.addRamException(ramType);
                        }
                    }
                    else {

                    }
                }
                if(configuration.getCpu() != null) {
                    CPU cpu = configuration.getCpu();
                    CPUAdditionalValues cpuValues = cpu.getValues();
                    CPUAdditionalInformation cpuAdditional = cpu.getAdditional();
                    if(cpuAdditional != null) {
                        if(!additional.getSocket().contains(cpuAdditional.getSocket())) {
                            ConfigurationException socket = new ConfigurationException("error", motherboard.getName(), cpu.getName(), "Сокет не соответсвует " + additional.getSocket() + ".");
                            errors.addCpuException(socket);
                        }
                    }
                    else {
                        ConfigurationException cpuNull = new ConfigurationException("warning", cpu.getName(), "Недостаточно информации для анализа процессора.");
                        errors.addCpuException(cpuNull);
                    }
                }
            }
        }
    }

}
