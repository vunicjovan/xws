import advertisementApi from "@/api/Advertisement.js";

const state = {
	advertisements: [],
	advertisement: null,
};

const getters = {
	getAdvertisements: (state) => state.advertisements,
	getAdvertisement: (state) => state.advertisement,
};

const actions = {
	addAdvertisement({ commit }, advertisement) {
		return new Promise((resolve, reject) => {
			advertisementApi
				.postAdvertisement(advertisement)
				.then((ad) => {
					resolve(ad);
				})
				.catch((error) => reject(error));
		});
	},

	addPhotos({ commit }, payload) {
		return new Promise((resolve, reject) => {
			advertisementApi
				.postPhotos(payload.id, payload.photos)
				.then((data) => resolve())
				.catch((error) => reject(error));
		});
	},

	getAllAdvertisements({ commit }) {
		return new Promise((resolve, reject) => {
			advertisementApi
				.getAllAdvertisements()
				.then((advertisements) => {
					commit("setAdvertisements", advertisements);
					resolve();
				})
				.catch((error) => reject(error));
		});
	},

	getDetailedAdvertisement({ commit }, id) {
		return new Promise((resolve, reject) => {
			advertisementApi
				.getDetailedAdvertisement(id)
				.then((advertisement) => {
					commit("setAdvertisement", advertisement);
					resolve();
				})
				.catch((error) => reject(error));
		});
	},

	updateAd({ commit }, updatedAd) {
		return new Promise((resolve, reject) => {
			advertisementApi
				.updateAdvertisement(updatedAd)
				.then((data) => {
					resolve(data);
				})
				.catch((error) => reject(error));
		});
	},

	deleteAd({ commit }, id) {
		return new Promise((resolve, reject) => {
			advertisementApi
				.deleteAdvertisement(id)
				.then((data) => {
					commit("deleteAdvertisement", id);
					resolve(data);
				})
				.catch((error) => reject(error));
		});
	},
};

const mutations = {
	setAdvertisements: (state, advertisements) => (state.advertisements = advertisements),
	setAdvertisement: (state, advertisement) => (state.advertisement = advertisement),
	deleteAdvertisement: (state, adId) => {
		const index = state.advertisements.findIndex((ad) => ad.id === adId);
		if (index != -1) {
			state.advertisements.splice(index, 1);
		}
	},
};

export default {
	state,
	getters,
	actions,
	mutations,
};
