package br.com.tiagods.config.init;

import br.com.tiagods.config.PropsConfig;
import br.com.tiagods.config.enums.PropsEnum;

public class DataBaseConfig extends PropsConfig {

    private static DataBaseConfig instance;

    public static DataBaseConfig getInstance() {
        if(instance==null) instance = new DataBaseConfig();
        return instance;
    }
    public DataBaseConfig() {
        super(PropsEnum.DB);
    }

}
