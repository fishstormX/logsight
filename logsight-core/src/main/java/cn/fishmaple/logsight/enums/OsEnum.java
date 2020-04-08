package cn.fishmaple.logsight.enums;


public enum OsEnum {
    /**
     * 系统
     * */
    Linux("linux"),
    Mac_OS("mac os"),
    Windows("windows");

    private String systemName;

    OsEnum(String systemName) {
        this.systemName = systemName;
    }

    public String getSystemName() {
        return systemName;
    }
}
