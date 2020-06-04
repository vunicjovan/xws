import advertismentApi from "@/api/Advertisment.js";
import searchApi from "@/api/Search.js";

const state = {
	advertisements: [],
	advertisement: null,
};

const getters = {
	getAdvertisements: (state) => state.advertisements,
	getAdvertisement: (state) => state.advertisement
};

const actions = {
	addAdvertisment({ commit }, advertisment) {
		return new Promise((resolve, reject) => {
			advertismentApi
				.postAdvertisment(advertisment)
				.then((ad) => {
					resolve(ad);
				})
				.catch((error) => reject(error));
		});
	},

	addPhotots({ commit }, payload) {
		return new Promise((resolve, reject) => {
			advertismentApi
				.postPhotos(payload.id, payload.photos)
				.then((data) => resolve())
				.catch((error) => reject(error));
		});
	},

	getAllAdvertisements({ commit }) {
		return new Promise((resolve, reject) => {
			advertismentApi
				.getAllAdvertisements()
				.then((advertisements) => {
					commit("setAdvertisements", advertisements);
					resolve();
				})
				.catch((error) => reject(error));
		});
	},

	simpleSearch({ commit }, params) {
		return new Promise((resolve, reject) => {
			searchApi
				.getSimpleSearchResults(params)
				.then((response) => {
					commit("setAdvertisements", response);
				})
				.catch((error) => reject(error));
		});
	},

	getDetailedAdvertisement({ commit }, id) {
		return new Promise((resolve, reject) => {
			advertismentApi
				.getDetailedAdvertisement(id)
				.then((advertisement) => {
					commit("setAdvertisement", advertisement);
					resolve();
				})
				.catch((error) => reject(error));
		});
	},

	advancedSearch({ commit }, params) {
		return new Promise((resolve, reject) => {
			searchApi
				.getAdvancedSearchResults(params)
				.then((response) => {
					commit("setAdvertisements", response);
				})
				.catch((error) => reject(error));
		});
	},
};

const mutations = {
	setAdvertisements: (state, advertisements) => (state.advertisements = advertisements),
	setAdvertisement: (state, advertisement) => (state.advertisement = advertisement),
};

export default {
	state,
	getters,
	actions,
	mutations,
};
