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
};
