import pricelistApi from "@/api/PriceList.js";

const state = {
	pricelist: [],
	discount: null,
};

const getters = {
	getPriceList: (state) => state.pricelist,
	getDiscount: (state) => state.discount,
};

const actions = {
	getUsersPriceList({ commit }, userId) {
		return new Promise((resolve, reject) => {
			pricelistApi
				.getUsersPriceList(userId)
				.then((data) => {
					commit("setPriceList", data.priceListItems);
					commit("setDiscount", data.discount);
					resolve(data);
				})
				.catch((error) => reject(error));
		});
	},

	addNewPriceListItem({ commit }, payload) {
		return new Promise((resolve, reject) => {
			pricelistApi
				.postPriceListItem(payload)
				.then((data) => {
					commit("addPriceList", data);
					resolve(data);
				})
				.catch((error) => reject(error));
		});
	},

	addDiscount({ commit }, payload) {
		return new Promise((resolve, reject) => {
			pricelistApi
				.postDiscount(payload.userId, payload.discount)
				.then((data) => {
					commit("setDiscount", payload.discount);
					resolve(data);
				})
				.catch((error) => reject(error));
		});
	},
};

const mutations = {
	setPriceList: (state, pricelist) => (state.pricelist = pricelist),
	setDiscount: (state, discount) => (state.discount = discount),
	addPriceList: (state, pricelistItem) => state.pricelist.push(pricelistItem),
};

export default {
	state,
	getters,
	actions,
	mutations,
};
