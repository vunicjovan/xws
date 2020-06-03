import advertismentApi from "@/api/Advertisment.js";

const state = {
	advertisments: [],
	advertisement: null
};

const getters = {
	//getAdvertisments: (state) => state.advertisments,
	//getAdvertisement: (state) => state.advertisement
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
	
	getAllAdvertisements({commit}) {
		return new Promise((resolve, reject) => {
			advertismentApi
				.getAllAdvertisements()
				.then((advertisements) => {
					commit("setAdvertisements", advertisements)
				})
				.catch((error) => reject(error));
		})
	},

	getDetailedAdvertisement({commit}, id) {
		return new Promise((resolve, reject) => {
			advertismentApi
				.getAllAdvertisements(id)
				.then((advertisement) => {
					commit("setAdvertisement", advertisement)
				})
				.catch((error) => reject(error));
		})
	}
	
};

const mutations = {
	setAdvertisements: (state, advertisements) => (state.advertisements = advertisements),
	setAdvertisement: (state, advertisement) => (state.advertisement = advertisement)
};

export default {
	state,
	getters,
	actions,
	mutations,
};
