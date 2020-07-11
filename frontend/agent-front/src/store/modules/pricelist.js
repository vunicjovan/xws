import pricelistApi from "@/api/PriceList.js";

const state = {
	pricelist: [],
	discount: null
};

const getters = {
	getPriceList: (state) => state.pricelist,
	getDiscount: (state) => state.discount
};

const actions = {
	pullPriceList({ commit }) {
		return new Promise((resolve, reject) => {
			pricelistApi
				.getPriceList()
				.then((data) => {
					commit("setPriceList", data);
					resolve(data);
				})
				.catch((error) => reject(error));
		});
	},

	newPriceListItem({ commit }, payload) {
		return new Promise((resolve, reject) => {
			pricelistApi
				.postPriceListItem(payload)
				.then((data) => {
					commit("addNewItem", data);
					resolve(data);
				})
				.catch((error) => reject(error));
		});
	},

	createDiscount({ commit }, discount) {
		return new Promise((resolve, reject) => {
			pricelistApi
				.createDiscount(discount)
				.then((data) => {
					commit("setDiscount", discount);
				})
				.catch((error) => reject(error));
		});
	}
};

const mutations = {
	setPriceList: (state, pricelist) => {
		state.pricelist = pricelist.priceListItems;
		state.discount = pricelist.discount;
	},
	addNewItem: (state, item) => state.pricelist.push(item),
	setDiscount: (state, discount) => state.discount = discount,
	setPriceListItems: (state, items) => state.pricelist = items
};

export default {
	state,
	getters,
	actions,
	mutations,
};
