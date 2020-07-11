import axios from "axios";

export default {
	async getFinishedRentingRequests() {
		try {
			const response = await axios.get("/rent/finishedRequests/");
			return response.data;
		} catch (error) {
			throw Error(error);
		}
	},

	async postRentingRepord(report) {
		try {
			const response = await axios.post("/rent/report/", report);
			return response.data;
		} catch (error) {
			throw Error(error);
		}
	},

	async getPendingRequests() {
		try {
			const response = await axios.get("/rent/pending/requests");
			return response.data;
		} catch (error) {
			throw Error(error);
		}
	},

	async updateRequestStatus(request) {
		try {
			const response = await axios.put("/rent/request", request);
			return response.data;
		} catch (error) {
			throw Error(error);
		}
	},
};
