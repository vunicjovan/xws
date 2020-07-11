import rentingRequestApi from "@/api/RentingRequest";

const state = {
	rentingRequests: [],
	finishedRequests: [],
	rentingRequest: null,
	reports: [],
};

const getters = {
	getRentingRequests: (state) => state.rentingRequests,
	getRentingRequest: (state) => state.rentingRequest,
	getFinishedRequests: (state) => state.finishedRequests,
	getReports: (state) => state.reports,
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

	getFinishedRentingRequests({ commit }, id) {
		return new Promise((resolve, reject) => {
			rentingRequestApi
				.getFinishedRentingRequests(id)
				.then((data) => {
					commit("setFinishedRequests", data);
					resolve(data);
				})
				.catch((error) => reject(error));
		});
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
					resolve(data);
				})
				.catch((error) => reject(error));
		});
	},

	newRentingReport({ commit }, report) {
		return new Promise((resolve, reject) => {
			rentingRequestApi
				.postRentingReport(report)
				.then((data) => {
					resolve(data);
				})
				.catch((error) => reject(error));
		});
	},

	getRentingReports({ commit }, id) {
		return new Promise((resolve, reject) => {
			rentingRequestApi
				.getReports(id)
				.then((data) => {
					commit("setReports", data);
					resolve(data);
				})
				.catch((error) => reject(error));
		});
	},
};

const mutations = {
	setRentingRequests: (state, rentingRequests) => (state.rentingRequests = rentingRequests),
	setRentingRequest: (state, rentingRequest) => (state.rentingRequest = rentingRequest),
	setFinishedRequests: (state, requests) => (state.finishedRequests = requests),
	setReports: (state, reports) => (state.reports = reports),
	updateStatus: (state, id) => {
		state.rentingRequests = state.rentingRequests.filter((rentingRequest) => rentingRequest.id !== id);
		state.rentingRequest = null;
	},
	deleteRentingRequest: (state, id) => {
		state.rentingRequests = state.rentingRequests.filter((rentingRequest) => rentingRequest.id != id);
	},
};

export default {
	state,
	getters,
	actions,
	mutations,
};
