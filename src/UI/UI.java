package UI;

/**
 *
 * @author Alexander
 */
public interface UI {
    
    public String getInput();
    public void println(String words);

    public void printf(String str, Object ... format);
}
