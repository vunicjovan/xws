import axios from "axios";

export default {
	async getSimpleSearchResults(params) {
		try {
			const response = await axios.get("/search/simple", { params });
			return response.data;
		} catch (error) {
			throw Error(error);
		}
	},

	async getAdvancedSearchResults(params) {
		try {
			const response = await axios.get("/search/advanced", { params });
			return response.data;
		} catch (error) {
			throw Error(error);
		}
	},
};
