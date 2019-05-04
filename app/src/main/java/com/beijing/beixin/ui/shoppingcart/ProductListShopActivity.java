package com.beijing.beixin.ui.shoppingcart;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.widget.ListView;

import com.beijing.beixin.R;
import com.beijing.beixin.adapter.CommonAdapter;
import com.beijing.beixin.adapter.ViewHolder;
import com.beijing.beixin.pojo.ShoppingCartBean;
import com.beijing.beixin.pojo.ShoppingCartProBean;
import com.beijing.beixin.pojo.ShoppingCartShopBean;
import com.beijing.beixin.ui.base.BaseActivity;
import com.beijing.beixin.utils.Utils;

public class ProductListShopActivity extends BaseActivity {
	/**
	 * 传过来的整个实体
	 */
	private List<ShoppingCartShopBean> list;
	private ListView listview_prolist;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_product_list_shoppingcart);
		initView();
		initData();
	}

	private void initView() {
		setNavigationTitle("商品清单");
		setNavigationLeftBtnImage(R.drawable.title_bar_back);
		initListView();
	}

	private void initListView() {
		listview_prolist = (ListView) findViewById(R.id.listview_prolist);
	}

	private void initData() {
		ShoppingCartBean cartbean = (ShoppingCartBean) getIntent().getSerializableExtra("cartBean");
		setNavigationRightBtnText("共" + Utils.isInteger(cartbean.getSelectCartNum()) + "件");
		List<ShoppingCartProBean> proList = new ArrayList<ShoppingCartProBean>();

		if (cartbean != null) {
			list = cartbean.getShoppingCartVos();
			for (int i = 0; i < list.size(); i++) {
				for (int j = 0; j < list.get(i).getItems().size(); j++) {
					if (list.get(i).getItems().get(j).getItemSelected() == true) {
						proList.add(list.get(i).getItems().get(j));
					}
				}
			}
		}
		CommonAdapter<ShoppingCartProBean> adapter = new CommonAdapter<ShoppingCartProBean>(this, proList,
				R.layout.item_product_shoppingcart_list) {

			@Override
			public void convert(ViewHolder helper, ShoppingCartProBean item) {
				// 给item赋值
				helper.setText(R.id.textview_commodity_name, item.getName());
				helper.setText(R.id.textview_mobile_acount, "×" + item.getQuantity());
				helper.setText(R.id.textview_commodity_price,
						"¥" + Utils.parseDecimalDouble2(item.getProductUnitPrice()));
				helper.displayImage(R.id.imageview_commodity_icon, item.getImage());
			}
		};
		listview_prolist.setAdapter(adapter);
	}

}
