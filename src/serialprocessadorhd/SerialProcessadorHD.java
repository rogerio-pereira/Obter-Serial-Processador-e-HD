package serialprocessadorhd;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.InputStreamReader;
import javax.swing.JFrame;
import javax.swing.JOptionPane;


/*
 * Obtido em 
 * http://www.guj.com.br/java/91221-solucao-para-pegar-o-serial-do-hd-e-o-serial-da-cpu
 * 22/09/2014 08:51
 */

/**
 *
 * @author Rogério TI
 */

public class SerialProcessadorHD 
{
	/**
	 * <b>Método getHDSerial</b>
	 * <p>Obtem o serial do HD</p>
	 * @param	drive	Letra do drive que será pego o serial... (C) (D) (F)
	 * @return			O Serial da unidade de Disco Rigido
	 */
	
	public static String getHDSerial(String drive) {
        String result = "";
        try {
            //File file = File.createTempFile("tmp",".vbs");
            File file = File.createTempFile("tmp", ".vbs");
            file.deleteOnExit();
            FileWriter fw = new java.io.FileWriter(file);
 
            String vbs = "Set objFSO = CreateObject(\"Scripting.FileSystemObject\")\n" + "Set colDrives = objFSO.Drives\n" 
                            + "Set objDrive = colDrives.item(\"" + drive + "\")\n" + "Wscript.Echo objDrive.SerialNumber";  
            fw.write(vbs);
            fw.close();
            Process p = Runtime.getRuntime().exec("cscript //NoLogo " + file.getPath());
            BufferedReader input =
                new BufferedReader(new InputStreamReader(p.getInputStream()));
            String line;
            while ((line = input.readLine()) != null) {
                result += line;
            }
            input.close();
        } catch (Exception e) {
 
        }
        if (result.trim().length() < 1  || result == null) {
            result = "NO_DISK_ID";
 
        }
 
        return result.trim();
    }

	/**
	 * <b>Método getCPUSerial</b>
	 * <p>Obtem o serial do Processador</p>
	 * @return	O Serial do processador
	 */
	
    public static String getCPUSerial() {
        String result = "";
        try {
            File file = File.createTempFile("tmp", ".vbs");
            file.deleteOnExit();
            FileWriter fw = new java.io.FileWriter(file);
 
            String vbs =
                "On Error Resume Next \r\n\r\n" +
                "strComputer = \".\"  \r\n" +
                "Set objWMIService = GetObject(\"winmgmts:\" _ \r\n" +
                "    & \"{impersonationLevel=impersonate}!\\\\\" & strComputer & \"\\root\\cimv2\") \r\n" +
                "Set colItems = objWMIService.ExecQuery(\"Select * from Win32_Processor\")  \r\n " +
                "For Each objItem in colItems\r\n " +
                "    Wscript.Echo objItem.ProcessorId  \r\n " +
                "    exit for  ' do the first cpu only! \r\n" +
                "Next                    ";
 
 
            fw.write(vbs);
            fw.close();
            Process p = Runtime.getRuntime().exec("cscript //NoLogo " + file.getPath());
            BufferedReader input =
                new BufferedReader(new InputStreamReader(p.getInputStream()));
            String line;
            while ((line = input.readLine()) != null) {
                result += line;
            }
            input.close();
        } catch (Exception e) {
 
        }
        if (result.trim().length() < 1 || result == null) {
            result = "NO_CPU_ID";
        }
        return result.trim();
    }
	
	

	/**
	 * <b>Método showSerialHD</b>
	 * <p>Exibe as informações de serial do HD</p>
	 */
	
	public static void showSerialHD() 
	{
		JOptionPane.showMessageDialog	(
											new JFrame(), 
											"Serial do HD: " + getHDSerial("c"), 
											"Serial HD", 
											-1
										);
	}
	
	/**
	 * <b>Método showSerialProcessador</b>
	 * <p>Exibe as informações de serial do Processador</p>
	 */
	
	public static void showSerialProcessador() 
	{
		JOptionPane.showMessageDialog	(
											new JFrame(), 
											"Serial da CPU: " + getCPUSerial(), 
											"Serial Processador", 
											-1
										);
	}

	/**
	 * <b>Método showSerialHDProcessador</b>
	 * <p>Exibe as informações de serial do HD e Processador</p>
	 */
	
	public static void showSerialHDProcessador() 
	{
		JOptionPane.showMessageDialog	(
											new JFrame(), 
											"Serial do HD: " + getHDSerial("c") + 
												"\n" +
												"Serial da CPU: " + getCPUSerial(), 
											"Serial HD e Processador", 
											-1
										);
	}
	
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) 
    {
		
    }

}
