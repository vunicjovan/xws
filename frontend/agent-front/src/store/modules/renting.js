import rentingApi from "@/api/Renting.js";

const state = {
	finishedRequests: [],
};

const getters = {
	getFinishedRequests: (state) => state.finishedRequests,
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
};

const mutations = {
	setFinishedRequests: (state, requests) => (state.finishedRequests = requests),
};

export default {
	state,
	getters,
	actions,
	mutations,
};
