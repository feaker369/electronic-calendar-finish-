package myself;

import java.util.Calendar;

public class AlarmShowNow {

    /**
     * ���ϵͳ�ĵ�ǰʱ�䣬������
     * @return ����ϵͳ��ǰʱ��
     */
    public static String now() {
        Calendar cal = Calendar.getInstance();
        int hour = cal.get(Calendar.HOUR_OF_DAY);
        int min = cal.get(Calendar.MINUTE);
        int sec = cal.get(Calendar.SECOND);
        String current = new String(hour+ ":" + min + ":" + sec);
        return current;
    }
   public static void main(String args[]){
       System.out.println(now());
   }
}