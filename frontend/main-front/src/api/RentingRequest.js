import axios from "axios";

export default {
	getUserRentingRequests(userId) {
		return axios.get(`/rent/request/user/${userId}`).then((response) => {
			return response.data;
		});
	},

	updateRentingRequest(request) {
		return axios.put(`/rent/request/${request.id}`, request).then((response) => {
			return response.data;
		});
	},

	deleteRentingRequest(requestId) {
		return axios.delete(`/rent/request/${requestId}`).then((response) => {
			return response.data;
		});
	},

	async getRentingRequest(requestId) {
		try {
			const response = await axios.get(`rent/request/${requestId}`);
			return response.data;
		} catch (error) {
			throw Error(error);
		}
	},

	async getFinishedRentingRequests(id) {
		try {
			const response = await axios.get(`/rent/request/finished/${id}`);
			return response.data;
		} catch (error) {
			throw Error(error);
		}
	},

	async postRentingReport(report) {
		try {
			const response = await axios.post(`/rent/report/`, report);
			return response.data;
		} catch (error) {
			throw Error(error);
		}
	},

	async getReports(id) {
		try {
			const response = await axios.get(`/rent/report/user/${id}`);
			return response.data;
		} catch (error) {
			throw Error(error);
		}
	},
};
