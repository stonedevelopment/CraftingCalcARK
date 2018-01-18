package arc.resource.calculator.model.json;

import java.util.List;

public class JSONVersion {
    private String current;
    private List<String> files;

    public JSONVersion() {

    }

    public JSONVersion( String current, List<String> files ) {
        this.current = current;
        this.files = files;
    }

    public String getCurrent() {
        return current;
    }

    public void setCurrent( String current ) {
        this.current = current;
    }

    public List<String> getFiles() {
        return files;
    }

    public void setFiles( List<String> files ) {
        this.files = files;
    }
}
