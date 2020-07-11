import rentingApi from "@/api/Renting.js";

const state = {
	finishedRequests: [],
	pendingRequests: [],
};

const getters = {
	getFinishedRequests: (state) => state.finishedRequests,
	getPendingRequests: (state) => state.pendingRequests,
};

const actions = {
	getFinishedRentingRequests({ commit }, payload) {
		return new Promise((resolve, reject) => {
			rentingApi
				.getFinishedRentingRequests(payload)
				.then((data) => {
					commit("setFinishedRequests", data);
					resolve(data);
				})
				.catch((error) => reject(error));
		});
	},

	postRentingReport({ commit }, payload) {
		return new Promise((resolve, reject) => {
			rentingApi
				.postRentingRepord(payload)
				.then((data) => {
					resolve(data);
				})
				.catch((error) => reject(error));
		});
	},

	getPendingRentingRequests({ commit }) {
		return new Promise((resolve, reject) => {
			rentingApi
				.getPendingRequests()
				.then((data) => {
					commit("setPendingRequests", data);
					resolve(data);
				})
				.catch((error) => reject(error));
		});
	},

	updateRentingRequest({ commit }, payload) {
		return new Promise((resolve, reject) => {
			rentingApi
				.updateRequestStatus(payload)
				.then((data) => {
					resolve(data);
				})
				.catch((error) => reject(error));
		});
	},
};

const mutations = {
	setFinishedRequests: (state, requests) => (state.finishedRequests = requests),
	setPendingRequests: (state, requests) => (state.pendingRequests = requests),
};

export default {
	state,
	getters,
	actions,
	mutations,
};
