package net.bangbao.network;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.bangbao.base.BaseApiClient.Page;
import net.bangbao.dao.CompanysApi;
import net.bangbao.dao.CompanysApi.CompanyInfo;
import net.bangbao.dao.InsuListApi;
import net.bangbao.dao.InsuListApi.InsuStr;


/**
 * @author mosl
 *  给选保险的接口
 */
public class InsuranceApi {
	
	final static Map<Integer,String> lists = new HashMap<Integer,String>();
	final static Map<Integer,String> comm_lists = new HashMap<Integer,String>();
	
	
	/**
	 * @param catg_id
	 * @param tag
	 * @param callback
	 */
	public static void getCatgStr(final int catg_id,Object tag ,
			final ApiClient.CallBack<String> callback){
		
		if(lists.get(catg_id) != null){
			callback.success(lists.get(catg_id));
		}
		Page page = new Page();
		page.id_index = 0;
		page.page_index = 0;
		page.page_size = 100;
		new ApiClient().getInsuranceCategry(page, tag, InsuListApi.class, 
				new ApiClient.CallBack<InsuListApi>(){

			@Override
			public void success(InsuListApi api) {
				if(api == null || api.getItem() == null)return;
				List<InsuStr> list = api.getItem();
				for(int i=0;i<list.size();i++){
					InsuStr insuStr = list.get(i);
					lists.put(insuStr.getId(),insuStr.getNm());
				}
				callback.success(lists.get(catg_id));
			}

			@Override
			public void fail(String error) {
				
			}
			
		});
	}
	
	/**根据公司id 给出picUr
	 * @param co_id
	 * @param tag
	 * @param callback
	 */
	public static void getCommPicUrl(final int co_id,Object tag ,
			final ApiClient.CallBack<String> callback){
		if(comm_lists.get(co_id) != null){
			callback.success(comm_lists.get(co_id));
		}
		Page page = new Page();
		page.id_index = 0;
		page.page_index = 0;
		page.page_size = 100;
		new ApiClient().getCompanys(page, 0, tag, CompanysApi.class, new ApiClient.CallBack<CompanysApi>() {

			@Override
			public void success(CompanysApi api) {
				if(api == null || api.getItem() == null)return;
				List<CompanyInfo> list = api.getItem();
				for(int i=0;i<list.size();i++){
					CompanyInfo insuStr = list.get(i);
					comm_lists.put(insuStr.id,insuStr.pic_url);
				}
				callback.success(comm_lists.get(co_id));
			}

			@Override
			public void fail(String error) {
				
			}
		});
	}
}
