import rentingRequestApi from "@/api/RentingRequest";

const state = {
	rentingRequests: [],
	rentingRequest: null,
};

const getters = {
	getRentingRequests: (state) => state.rentingRequests,
	getRentingRequest: (state) => state.rentingRequest,
};

const actions = {
	getUserRentingRequests({ commit }, userId) {
		return new Promise((resolve, reject) => {
			rentingRequestApi
				.getUserRentingRequests(userId)
				.then((rentingRequests) => {
					commit("setRentingRequests", rentingRequests);
					resolve();
				})
				.catch((error) => reject(error));
		});
	},

	getUserRentingRequest({ commit }, id) {
		for (let i = 0; i < state.rentingRequests.length; i++) {
			if (id == state.rentingRequests[i].id) {
				commit("setRentingRequest", state.rentingRequests[i]);
			}
		}
	},

	updateRentingRequestStatus({ commit }, request) {
		return new Promise((resolve, reject) => {
			rentingRequestApi
				.updateRentingRequest(request)
				.then((data) => {
					commit("updateStatus", request.id);
					resolve(data);
				})
				.catch((error) => reject(error));
		});
	},

	cancelRentingRequest({ commit }, requestId) {
		return new Promise((resolve, reject) => {
			rentingRequestApi
				.deleteRentingRequest(requestId)
				.then((data) => {
					commit("deleteRentingRequest", requestId);
					resolve();
				})
				.catch((error) => reject(error));
		});
	},
};

const mutations = {
	setRentingRequests: (state, rentingRequests) => (state.rentingRequests = rentingRequests),
	setRentingRequest: (state, rentingRequest) => (state.rentingRequest = rentingRequest),
	updateStatus: (state, id) => {
		state.rentingRequests = state.rentingRequests.filter((rentingRequest) => rentingRequest.id !== id);
		state.rentingRequest = null;
	},
	deleteRentingRequest: (state, id) => {
		state.rentingRequests = state.rentingRequests.filter((rentingRequest) => rentingRequest.id != id);
	}
};

export default {
	state,
	getters,
	actions,
	mutations,
};
