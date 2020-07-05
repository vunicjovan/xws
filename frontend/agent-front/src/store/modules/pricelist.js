import pricelistApi from "@/api/PriceList.js";

const state = {
	pricelist: [],
};

const getters = {
	getPriceList: (state) => state.pricelist,
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
};

const mutations = {
	setPriceList: (state, pricelist) => (state.pricelist = pricelist),
	addNewItem: (state, item) => state.pricelist.push(item),
};

export default {
	state,
	getters,
	actions,
	mutations,
};
