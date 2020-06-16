import cartApi from "@/api/Cart";

const state = {
	cartAdvertisements: null,
};

const getters = {
	getCartAdvertisements: (state) => state.cartAdvertisements,
};

const actions = {
	getCart({ commit }, cartId) {
		return new Promise((resolve, reject) => {
			cartApi
				.getCart(cartId)
				.then((advetisementIdList) => {
					resolve(advetisementIdList);
				})
				.catch((error) => {
					reject(error);
				});
		});
	},

	getCartItems({ commit }, advertisementIdList) {
		return new Promise((resolve, reject) => {
			cartApi
				.getCartAdvertisements(advertisementIdList)
				.then((advertisements) => {
					commit("setCartAdvertisements", advertisements);
					resolve(advertisements);
				})
				.catch((error) => {
					reject(error);
				});
		});
	},

	addCartItem({ commit }, payload) {
		return new Promise((resolve, reject) => {
			cartApi
				.addCartItem(payload.cartId, payload.cartItemId)
				.then((cartItem) => {
					resolve();
				})
				.catch((error) => {
					reject(error);
				});
		});
	},

	deleteCartItem({ commit }, payload) {
		return new Promise((resolve, reject) => {
			cartApi
				.deleteCartItem(payload.cartId, payload.cartItemId)
				.then((cartItem) => {
					commit("deleteCartItem", payload.cartItemId);
					resolve(cartItem);
				})
				.catch((error) => {
					reject(error);
				});
		});
	},

	sendRequest({ commit }, payload) {
		return new Promise((resolve, reject) => {
			cartApi
				.sendRentingRequest(payload)
				.then((data) => {
					payload.advertisementIDs.forEach((item) => {
						commit("deleteCartItem", item);
					});
					resolve(data);
				})
				.catch((error) => {
					reject(error);
				});
		});
	},
};

const mutations = {
	setCartAdvertisements: (state, cartAdvertisements) => (state.cartAdvertisements = cartAdvertisements),
	deleteCartItem: (state, cartAdvertisementId) => {
		state.cartAdvertisements = state.cartAdvertisements.filter((cartAdvertisement) => cartAdvertisement.id != cartAdvertisementId);
	},
};

export default {
	state,
	getters,
	actions,
	mutations,
};
