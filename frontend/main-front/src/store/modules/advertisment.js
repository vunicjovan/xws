import advertismentApi from "@/api/Advertisment.js";
import searchApi from "@/api/Search.js";

const state = {
	advertisments: [],
};

const getters = {
	//getAdvertisments: (state) => state.advertisments,
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

	simpleSearch({ commit }, params) {
		return new Promise((resolve, reject) => {
			searchApi.getSimpleSearchResults(params)
				.then((response) => {
					commit("setAdvertisements", response)
				})
				.catch((error) => reject(error));
		})
	},

	advancedSearch({ commit }, params) {
		return new Promise((resolve, reject) => {
			searchApi.getAdvancedSearchResults(params)
				.then((response) => {
					commit("setAdvertisements", response)
				})
				.catch((error) => reject(error));
		})
	}

};

const mutations = {
	setAdvertisements: (state, ads) => (state.advertisments = ads)
};

export default {
	state,
	getters,
	actions,
	mutations,
};
