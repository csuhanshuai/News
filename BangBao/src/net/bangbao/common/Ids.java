package net.bangbao.common;

import java.util.ArrayList;
import java.util.List;

import net.bangbao.AppInit;
import net.bangbao.R;

/*
 * id映射
 */
public class Ids {

	public static String[] comms;
	public static String[] edus;
	public static String[] sexs;
	public static String[] provices;
	
	private static List<String> listComms;
	private static List<String> listSexs;
	private static List<String> listPassAnswer;
	//from hanshuai
	private static List<String> listEdu;
	private static List<String> listProv;
	
	public static final String[] constellationArr = {"水瓶座","双鱼座","牡羊座","金牛座","双子座",
		"巨蟹座","狮子座","处女座","天秤座","天蝎座","射手座","摩羯座"};
	public static final int[] constellationEdgeDay = {20,19,21,21,21,22,23,23,23,23,22,22};
	
	public static List<String> getCommlist(){
		
		if(listComms == null){
			listComms = new ArrayList<String>();
			if(comms == null)
				comms = AppInit.getContext().getResources().
				getStringArray(R.array.companys);
			for(int i=0;i<comms.length;i++){
				listComms.add(comms[i]);
			}
		}
		return listComms;
	}
	
	public static List<String> getEdulist(){
		if(listEdu == null) {
			listEdu = new ArrayList<String>();
			if(edus == null)
				edus = AppInit.getContext().getResources().getStringArray(R.array.education);
			for(int i=0;i<edus.length;i++){
				listEdu.add(edus[i]);
			}
		}
		return listEdu;
	}
	
	public static List<String> getProvlist() {
		if(listProv == null) {
			listProv = new ArrayList<String>();
			if(provices == null)
				provices = AppInit.getContext().getResources().getStringArray(R.array.provice);
			for(int i = 0;i < provices.length;i++) {
				listProv.add(provices[i]);
			}
		}
		return listProv;
	}
	public static List<String> getPassAnswer(){
		
		if(listPassAnswer == null){
			listPassAnswer = new ArrayList<String>();
			String[] answers = AppInit.getContext().getResources().
			getStringArray(R.array.questions);
			
			for(int i=0;i<answers.length ;i++){
				listPassAnswer.add(answers[i]);
			}
		}
		return listPassAnswer;
			}
					public static List<String> getSexs(){
		if(listSexs == null) {
			listSexs = new ArrayList<String>();
			String[] sexs = AppInit.getContext().getResources().getStringArray(R.array.sex);
			
			for(int i=0;i<sexs.length;i++) {
				listSexs.add(sexs[i]);
			}
		}
		return listSexs;
	}
	//公司id 和字符串 映射
	public static final String getComm(int commid){
		if(comms == null){
			comms = AppInit.getContext().getResources().getStringArray(R.array.companys);
		}
		if(commid >=0 && commid<comms.length){
			return comms[commid];
		}
		return "其他公司";
	}
	/**
	 *  hs
	 * @param commid
	 * @return
	 */
	public static final String getComm1(int commid){
        if(comms == null){
            comms = AppInit.getContext().getResources().getStringArray(R.array.companys);
        }
        if(commid >=0 && commid<comms.length){
            return comms[commid-1];
        }
        return "其他公司";
    }
	/*
	 * 学历映射
	 */
	public static final String getEdu(int eduId){
		if(edus == null){
			edus = AppInit.getContext().getResources().getStringArray(R.array.education);
		}
		if(eduId >= 0 && eduId < edus.length){
			return edus[eduId];
		}
		return "其他学历";
	}
	/*
	 * 性别映射
	 */
	public static final String getSex(int sex){
		if(sexs == null){
			sexs = AppInit.getContext().getResources().getStringArray(R.array.sex);
		}
		if(sex >=0 && sex <sexs.length){
			return sexs[sex];
		}
		return "未知";
	}
	/*
	 * 省份id映射
	 */
	public static final String getProvice(int provice){
		if(provices == null){
			provices = AppInit.getContext().getResources().getStringArray(R.array.provice);
		}
		if(provice >= 0 && provice <provices.length){
			return provices[provice];
		}
		return "其他省份";
	}
	
	/** 星座映射 **/
	public static String date2Constellation(long mills){
		
//		SimpleDateFormat format =  new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss" );
//	    Long time=new Long(mills);
//	    String d = format.format(time);
//	    Date date;
//		try {
//			date = format.parse(d);
//			Log.d("test",date.getMonth()+ "month" + date.getDay());
//		} catch (ParseException e) {
//			e.printStackTrace();
//		}
		
		
		return null;
//		int mon = month;
//		int d = day;
//		if(d < constellationEdgeDay[mon]){
//			mon = mon - 2;
//		}
//		if(mon >= 0){
//			return constellationArr[mon];
//		}
//		
//		return constellationArr[11];
	}
}
