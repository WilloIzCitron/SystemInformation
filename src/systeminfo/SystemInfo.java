package systeminfo;

import arc.*;
import arc.util.*;
import mindustry.game.EventType.*;
import mindustry.gen.*;
import mindustry.mod.*;
import mindustry.ui.dialogs.*;

import static mindustry.Vars.ui;

public class SystemInfo extends Mod{

    private BaseDialog systemInfo;

    public SystemInfo(){
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
        systemInfo.addCloseButton();
        loadButton();
    }
    void loadButton(){
        ui.settings.button(Icon.info, systemInfo::show).row();
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
