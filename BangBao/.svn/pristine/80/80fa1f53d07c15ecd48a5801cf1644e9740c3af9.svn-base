package net.bangbao.ui;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.database.DataSetObserver;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import net.bangbao.R;
import net.bangbao.adapter.LocateHospAdapter;
import net.bangbao.base.BaseActivity;
import net.bangbao.base.BaseApiClient;
import net.bangbao.dao.CityApi;
import net.bangbao.dao.CityApi.City;
import net.bangbao.dao.LocaHospoApi;
import net.bangbao.dao.LocaHospoApi.HospAddr;
import net.bangbao.network.ApiClient;
/*
 * author:mosl
 * 定点医院
 */
public class LocateHospotal/** extends BaseActivity implements View.OnClickListener **/{
	
//	private ListView mLocateAddress;
////	private AbstractWheel mWhellPicker;
//	private String[] companys = new String[]{"英国保诚","AIA友邦","AXA安盛","宏利","富卫"};
//	private LinearLayout companyLayout,proviceLayout,cityLayout;
//	private WhellPickerAdpater wheelAdapter;
//	
//	private String[] provices;
//	private String[] citys;
//	List<City> listCity;
//	private View picker;
//	
//	private boolean scrolling = false;
//	private int CurrentCompanyId = -1;
//	private int CurrentProviceId = -1;
//	private int CurrentCityId = -1;
//	
//	public static final int CURR_COMM = 1;
//	public static final int CURR_PROV = 2;
//	public static final int CURR_CITY = 3;
//	
//	public int CurrItem;
//	
//	private List<HospAddr> HospAddrList = new ArrayList<HospAddr>();
//	private ListView listHosp;
//	private LocateHospAdapter locaHospAdapter;
//	private ImageButton backButton;
//	
//	private TextView textProvice,textCompany,textCity;
//	
//	private ApiClient apiClient = new ApiClient();
//	
//	@Override
//	protected void onCreate(Bundle arg0) {
//		super.onCreate(arg0);
//		setContentView(R.layout.locate_hospotal_activity);
//		initViews();
//	}
//	
//	private void initViews(){
//		provices = this.getResources().getStringArray(R.array.provice);
//		picker = findViewById(R.id.view_picker);
//		mLocateAddress = (ListView)findViewById(R.id.locate_address);
//		mWhellPicker = (AbstractWheel)findViewById(R.id.picker);
//		mWhellPicker.setVisibleItems(6);
//		wheelAdapter = (WhellPickerAdpater)new WhellPickerAdpater(this,companys);
//		mWhellPicker.setViewAdapter(wheelAdapter);
//		
//		findViewById(R.id.go).setOnClickListener(this);
//		findViewById(R.id.cancel).setOnClickListener(this);
//        
//		listHosp = (ListView)findViewById(R.id.locate_address);
//		locaHospAdapter = new LocateHospAdapter(HospAddrList);
//		listHosp.setAdapter(locaHospAdapter);
//		mWhellPicker.addScrollingListener( new OnWheelScrollListener() {
//            public void onScrollingStarted(AbstractWheel wheel) {
//                scrolling = true;
//            }
//            public void onScrollingFinished(AbstractWheel wheel) {
//                scrolling = false;
//            }
//        });
//		
//		backButton = (ImageButton)findViewById(R.id.back_button);
//		backButton.setOnClickListener(new OnClickListener() {
//			
//			@Override
//			public void onClick(View v) {
//				onBackPressed();
//			}
//		});
//		
//		textCompany = (TextView)findViewById(R.id.spinner_company);
//		textProvice = (TextView)findViewById(R.id.spinner_provice);
//		textCity = (TextView)findViewById(R.id.spinner_city);
//	}
//	
//	public void change(View v){
//		
//		switch (v.getId()) {
//		case R.id.spinner_company:
//			CurrItem = CURR_COMM;
//			picker.setVisibility(View.VISIBLE);
//			mWhellPicker.setViewAdapter(new WhellPickerAdpater(this, companys));
//			break;
//		case R.id.spinner_provice:
//			CurrItem = CURR_PROV;
//			picker.setVisibility(View.VISIBLE);
//			mWhellPicker.setViewAdapter(new WhellPickerAdpater(this, provices));
//			break;
//		case R.id.spinner_city:
//			CurrItem = CURR_CITY;
//			picker.setVisibility(View.VISIBLE);
//			requestCity();
//			break;
//		}
//	}
//	private void requestCity() {
//		apiClient.getCitys(null, CurrentProviceId, this, CityApi.class, new BaseApiClient.CallBack<CityApi>(){
//
//			@Override
//			public void success(CityApi api) {
//				if(api ==null)return;
//				if(api.getItem() ==null)return;
//				listCity = api.getItem();
//				citys = new String[listCity.size()];
//				for(int i=0;i<listCity.size();i++){
//					City city = listCity.get(i);
//					citys[i] = city.nm;
//				}
//				mWhellPicker.setViewAdapter(new WhellPickerAdpater(LocateHospotal.this,citys));
//			}
//
//			@Override
//			public void fail(String error) {
//				
//			}
//			
//		});
//	}
//	
//	private void requestLocateHospotal(int comId, int cityId){
//		
//		HospAddrList.clear();
//		locaHospAdapter.notifyDataSetChanged();
//		apiClient.getLocateHospotal(null,comId,cityId, this, LocaHospoApi.class, new BaseApiClient.CallBack<LocaHospoApi>() {
//
//			@Override
//			public void success(LocaHospoApi api) {
//				if(api == null) return;
//				if(api.getItem() != null){
//					HospAddrList.addAll(api.getItem());
//					locaHospAdapter.notifyDataSetChanged();
//				}
//			}
//
//			@Override
//			public void fail(String error) {
//				Log.d("aa","error"+ "----");
//			}
//		});
//	}
//	class WhellPickerAdpater extends AbstractWheelTextAdapter{
//
//		private String[] whellChar;
//		
//		protected WhellPickerAdpater(Context context,String[] whells) {
//			super(context);
//			this.whellChar = whells;
//		}
//		
//		public void setDataSource(String[] dataSource){
//			whellChar = dataSource;
//		}
//		
//		@Override
//		public int getItemsCount() {
//			return whellChar.length;
//		}
//
//		@Override
//		protected CharSequence getItemText(int index) {
//			return whellChar[index];
//		}
//		
//	}
//	@Override
//	public void onClick(View v) {
//		switch (v.getId()) {
//		case R.id.go:
//			if(!scrolling){
//				int item = mWhellPicker.getCurrentItem();
//				Log.d("aa","item id"+item);
//				switch (CurrItem) {
//				case CURR_COMM: //公司
//					CurrentCompanyId = item +1;
//					textCompany.setText(companys[item]);
//					break;
//				case CURR_PROV:
//					CurrentProviceId = item +1;
//					textProvice.setText(provices[item]);
//					break;
//				case CURR_CITY:
//				{
//					CurrentCityId = item;
//					if(CurrentProviceId !=-1 || CurrentCompanyId != -1)
//						requestLocateHospotal(CurrentCompanyId,listCity.get(item).id);
//					//Log.d("aa","cityId"+CurrentCompanyId)
//					textCity.setText(citys[item]);
//				}
//					break;
//				default:
//					break;
//				}
//			}
//			picker.setVisibility(View.GONE);
//			break;
//		default:
//			picker.setVisibility(View.GONE);
//			break;
//		}
//	}
}
