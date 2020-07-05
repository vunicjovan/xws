import advertismentApi from "@/api/Advertisment.js";
import searchApi from "@/api/Search.js";

const state = {
	advertisements: [],
	advertisement: null,
	statistic: [],
};

const getters = {
	getAdvertisements: (state) => state.advertisements,
	getAdvertisement: (state) => state.advertisement,
	getStatistic: (state) => state.statistic,
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

	getUserPublishedAdvertisements({ commit }, ownerId) {
		return new Promise((resolve, reject) => {
			advertismentApi
				.getUserPublishedAdvertisements(ownerId)
				.then((advertisements) => {
					commit("setAdvertisements", advertisements);
					resolve();
				})
				.catch((error) => reject(error));
		});
	},

	getStatisticReport({ commit }, ownerId) {
		return new Promise((resolve, reject) => {
			advertismentApi
				.getStatisticReport(ownerId)
				.then((statistic) => {
					commit("setStatistic", statistic);
					resolve();
				})
				.catch((error) => reject(error));
		});
	},

	postRating({ commit }, rate) {
		return new Promise((resolve, reject) => {
			advertismentApi
				.rateAdvertisement(rate.adId, rate.rating)
				.then((rating) => {
					resolve();
				})
				.catch((error) => reject(error));
		});
	},

	putAdvertisement({ commit }, ad) {
		return new Promise((resolve, reject) => {
			advertismentApi
				.updateAdvertisement(ad.adId, ad.data)
				.then(() => {
					resolve();
				})
				.catch((error) => reject(error));
		});
	},

	removeAdvertisement({ commit }, adId) {
		return new Promise((resolve, reject) => {
			advertismentApi
				.deleteAdvertisement(adId)
				.then(() => {
					commit("deleteAdvertisement", adId);
					resolve();
				})
				.catch((error) => reject(error));
		});
	},
};

const mutations = {
	setAdvertisements: (state, advertisements) => (state.advertisements = advertisements),
	setAdvertisement: (state, advertisement) => (state.advertisement = advertisement),
	setStatistic: (state, report) => (state.statistic = report),
	deleteAdvertisement: (state, id) => {
		state.advertisements = state.advertisements.filter((ad) => ad.id != id);
	},
	sortByPriceAsc: (state) => state.advertisements.sort((a, b) => (a.price > b.price ? 1 : -1)),
	sortByPriceDesc: (state) => state.advertisements.sort((a, b) => (a.price < b.price ? 1 : -1)),
	sortByKilometersTraveledAsc: (state) => state.advertisements.sort((a, b) => (a.kmTraveled > b.kmTraveled ? 1 : -1)),
	sortByKilometersTraveledDesc: (state) => state.advertisements.sort((a, b) => (a.kmTraveled < b.kmTraveled ? 1 : -1)),
	sortByRatingAsc: (state) => state.advertisements.sort((a, b) => (a.rating > b.rating ? 1 : -1)),
	sortByRatingDesc: (state) => state.advertisements.sort((a, b) => (a.rating < b.rating ? 1 : -1)),
};

export default {
	state,
	getters,
	actions,
	mutations,
};
