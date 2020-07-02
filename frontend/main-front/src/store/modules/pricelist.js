import pricelistApi from "@/api/PriceList.js";

const state = {
	pricelist: [],
};

const getters = {
	getPriceList: (state) => state.pricelist,
};

const actions = {
	getUsersPriceList({ commit }, userId) {
		return new Promise((resolve, reject) => {
			pricelistApi
				.getUsersPriceList(userId)
				.then((data) => {
					commit("setPriceList", data);
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
};

const mutations = {
	setPriceList: (state, pricelist) => (state.pricelist = pricelist),
	addPriceList: (state, pricelistItem) => state.pricelist.push(pricelistItem),
};

export default {
	state,
	getters,
	actions,
	mutations,
};
