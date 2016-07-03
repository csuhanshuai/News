package net.bangbao.fragment;

import net.bangbao.R;
import net.bangbao.ui.InsuCatgActivity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

/**
 * 点击选保险Tab进来的Fragment
 * 
 * @author Spartacus26
 * @since 2015.3.16
 * @version 2.0 修改如下：类名修改为ChooseInsuranceFragment
 */
public class ChooseInsuranceFragment extends Fragment implements
		View.OnClickListener {

	/** 判断点击的是险种分类还是品牌分类，发送给InsuCatgActivity,默认是0，“险种分类”代表1，“品牌分类”代表2 */
	private int CLICK_TYPE = 0;
	// FIXME 为了速度，命名不规范，但是没有做修改
	private ImageView accident, danger, hospital, life_insurance,
			financial_management, aia, axa, fwd, manulife, prldential;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// 异常捕捉测试
		// int test = 1 / 0;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		super.onCreateView(inflater, container, savedInstanceState);
		View view = inflater.inflate(R.layout.fragment_choose_insuracne, null);

		// 写在initView里面
		accident = (ImageView) view.findViewById(R.id.accident);
		accident.setOnClickListener(this);
		danger = (ImageView) view.findViewById(R.id.dangerous);
		danger.setOnClickListener(this);
		hospital = (ImageView) view.findViewById(R.id.hospital);
		hospital.setOnClickListener(this);
		life_insurance = (ImageView) view.findViewById(R.id.life_insurance);
		life_insurance.setOnClickListener(this);
		financial_management = (ImageView) view
				.findViewById(R.id.financial_management);
		financial_management.setOnClickListener(this);
		aia = (ImageView) view.findViewById(R.id.aia);
		aia.setOnClickListener(this);
		axa = (ImageView) view.findViewById(R.id.axa);
		axa.setOnClickListener(this);
		fwd = (ImageView) view.findViewById(R.id.fw);
		fwd.setOnClickListener(this);
		manulife = (ImageView) view.findViewById(R.id.manulife);
		manulife.setOnClickListener(this);
		prldential = (ImageView) view.findViewById(R.id.prldential);
		prldential.setOnClickListener(this);
		return view;
	}

	@Override
	public void onClick(View v) {

		Intent intent = new Intent(getActivity(), InsuCatgActivity.class);
		switch (v.getId()) {
		case R.id.accident:
			CLICK_TYPE = 1;
			intent.putExtra("CLICK_TYPE", CLICK_TYPE);
			intent.putExtra("categry", 1);
			startActivity(intent);
			break;
		case R.id.dangerous:
			CLICK_TYPE = 1;
			intent.putExtra("CLICK_TYPE", CLICK_TYPE);
			intent.putExtra("categry", 2);
			startActivity(intent);
			break;
		case R.id.hospital:
			CLICK_TYPE = 1;
			intent.putExtra("CLICK_TYPE", CLICK_TYPE);
			intent.putExtra("categry", 3);
			startActivity(intent);
			break;
		case R.id.life_insurance:
			CLICK_TYPE = 1;
			intent.putExtra("CLICK_TYPE", CLICK_TYPE);
			intent.putExtra("categry", 4);
			startActivity(intent);
			break;
		case R.id.financial_management:
			CLICK_TYPE = 1;
			intent.putExtra("CLICK_TYPE", CLICK_TYPE);
			intent.putExtra("categry", 5);
			startActivity(intent);
			break;
		// 下面是5个公司的图标
		case R.id.aia:
			CLICK_TYPE = 2;
			intent.putExtra("CLICK_TYPE", CLICK_TYPE);
			intent.putExtra("company", 2);
			startActivity(intent);
			break;
		case R.id.axa:
			CLICK_TYPE = 2;
			intent.putExtra("CLICK_TYPE", CLICK_TYPE);
			intent.putExtra("company", 3);
			startActivity(intent);
			break;
		case R.id.fw:
			CLICK_TYPE = 2;
			intent.putExtra("CLICK_TYPE", CLICK_TYPE);
			intent.putExtra("company", 5);
			startActivity(intent);
			break;
		case R.id.manulife:
			CLICK_TYPE = 2;
			intent.putExtra("CLICK_TYPE", CLICK_TYPE);
			intent.putExtra("company", 4);
			startActivity(intent);
			break;
		case R.id.prldential:
			CLICK_TYPE = 2;
			intent.putExtra("CLICK_TYPE", CLICK_TYPE);
			intent.putExtra("company", 1);
			startActivity(intent);
			break;
		}
	}

}
