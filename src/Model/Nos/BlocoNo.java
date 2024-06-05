package Model.Nos;

import java.util.List;

public class BlocoNo extends No{
    private final List<No> cmd;
    public BlocoNo(List<No> cmd){
        this.cmd = cmd;
    }
}
