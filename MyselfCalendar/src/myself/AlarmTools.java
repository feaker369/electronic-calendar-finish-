package myself;

public class AlarmTools {

	/** 定义截图功能 */
	@SuppressWarnings("unused")
	private static DesktopCapture deskTopCapture;
	/**
	 * 截图
	 */
	public static void screenshot(){
		deskTopCapture=new DesktopCapture();
	}
}
