package systeminfo;

import arc.*;
import arc.func.Floatc;
import arc.func.Floatp;
import arc.util.*;
import mindustry.game.EventType.*;
import mindustry.gen.*;
import mindustry.graphics.Pal;
import mindustry.mod.*;
import mindustry.ui.Bar;
import mindustry.ui.dialogs.*;
import com.sun.management.OperatingSystemMXBean;

import java.lang.management.ManagementFactory;

import static mindustry.Vars.ui;

public class SystemInfo extends Mod{

    private BaseDialog systemInfo;
    private Bar RAMbar;
    private Bar CPUbar;
    private float RAMValue;
    private float CPUValue;
    OperatingSystemMXBean bean = (com.sun.management.OperatingSystemMXBean) ManagementFactory
            .getOperatingSystemMXBean();
    public SystemInfo() {
        Log.info("Loaded ExampleJavaMod constructor.");
    }
    @Override
    public void init() {

        systemInfo = new BaseDialog("System Info");
        systemInfo.cont.image(Core.atlas.find(osIndent())).maxHeight(100).maxWidth(500).row();
        if (OS.isAndroid) {
            systemInfo.cont.add("OS: Android ( " + OS.osArch + ")").row();
        } else{
            systemInfo.cont.add("OS: " + OS.osName + " ( " + OS.osArch + " | " + OS.osArchBits + "-bit )").row();
        }
        systemInfo.cont.add("Java VM: "+System.getProperty("java.vm.vendor")+" ("+System.getProperty("java.vm.name")+") "+System.getProperty("java.vm.version")).row();
        RAMbar = systemInfo.cont.add(new Bar()).pad(2).padTop(5).center().size(500f, 40).visible(true).get();
        systemInfo.row();
        CPUbar = systemInfo.cont.add(new Bar()).pad(2).padTop(5).center().size(500f, 40).visible(true).get();
        systemInfo.addCloseButton();
        loadButton();
        systemInfo.update( () -> {
            //Update RAM Usage

                 long RAM_TOTAL = Runtime.getRuntime().totalMemory();
                 long RAM_FREE = Runtime.getRuntime().freeMemory();
                 long RAM_USED = RAM_TOTAL - RAM_FREE;
                double RAM_USED_PERCENTAGE = ((double)RAM_USED / RAM_TOTAL) * 100;
            Floatc cons = len <= 0 ? f -> {} : p -> modImportProgress = p;
                setRAMbar((float) RAM_USED_PERCENTAGE);
                setCPUbar((float) bean.getSystemCpuLoad());
        });
    }
    void loadButton(){
        ui.settings.button(Icon.info, systemInfo::show).row();
    }
    //RAM USAGE
    void setRAMbar(Floatp progress) {
        RAMbar.reset(0f);
        RAMbar.set(() -> ("Ram Usage: "+((int)progress.get()) + "%"), progress, Pal.accent);
    }
    void setRAMbar(float progress) {
        RAMValue = progress;
        setRAMbar(() -> progress);
    }
    //CPU USAGE
    void setCPUbar(Floatp progress) {
        CPUbar.reset(0f);
        CPUbar.set(() -> ("CPU Usage: "+((int)progress.get()) + "%"), progress, Pal.accent);
    }
    void setCPUbar(float progress) {
        CPUValue = progress;
        setCPUbar(() -> progress);
    }
    @Override
    public void loadContent(){
        Log.info("Loading some example content.");
    }

    protected static String osIndent(){
        if(OS.isWindows){
            if(OS.osName.equals("Windows 10")){
                return "systeminfo-windows10";
            } if(OS.osName.equals("Windows 11")){
                return "systeminfo-windows11";
            }
        } else if(OS.isAndroid){
            //vendor based
            if(System.getProperty("http.agent").contains("Infinix")){
                return "systeminfo-infinix";
            }
            return "systeminfo-android";
        }
        return "systeminfo-frog";
    }

}
